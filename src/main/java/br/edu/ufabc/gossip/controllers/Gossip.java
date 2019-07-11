package br.edu.ufabc.gossip.controllers;

import static br.edu.ufabc.gossip.App.me;
import static br.edu.ufabc.gossip.Auxiliar.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufabc.gossip.models.Metadados;

public class Gossip extends Thread {
  private boolean self;
  private long waitTime;

  public Gossip() {
    setSelf(false);
  }

  public Gossip(String cliente) {
    if (cliente.equals(me.getNome())) {
      setSelf(true);
    } else {
      setSelf(false);
    }
  }

  private void setSelf(boolean self) {
    this.waitTime = self ? TimeController.T2 : TimeController.T3;
    this.self = self;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Peers.updatePeers();
        Map<String, String> selectedTargetPeer = Peers.select();
        if (selectedTargetPeer != null) {
          Map<String, Metadados> dados;
          
          if (self) {
            dados = Memoria.getMeta(me.getNome());
          } else {
            dados = Memoria.getMeta(selectedTargetPeer.get("id"));
          }

          if (!dados.values().contains(null)) {
            sendData(selectedTargetPeer.get("id"), selectedTargetPeer.get("address"), dados);
          }
        }

        Thread.sleep(waitTime);
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  private void sendData(String targetName, String target, Map<String, Metadados> data) throws IOException {
    ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    String json = mapper.writeValueAsString(data);

    InetAddress ip = InetAddress.getByName(target);
    int porta = 49152;

    System.out.println((self?ANSI_WHITE+"SELF":ANSI_PURPLE) + "GOSSIP" + ANSI_RESET + " - Enviando metadados de " + data.keySet() + " para " + targetName + "(" + target + ")");
    DatagramPacket datagramPacket = new DatagramPacket(json.getBytes(), json.getBytes().length, ip, porta);
    DatagramSocket socket = new DatagramSocket();
    socket.send(datagramPacket);
    socket.close();
  } 
}