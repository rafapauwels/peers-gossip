package br.edu.ufabc.gossip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufabc.gossip.controllers.Memoria;
import br.edu.ufabc.gossip.controllers.Peers;
import br.edu.ufabc.gossip.controllers.TimeController;
import br.edu.ufabc.gossip.models.Metadados;

import static br.edu.ufabc.gossip.App.me;;

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

  public void setSelf(boolean self) {
    this.waitTime = self ? TimeController.T2 : TimeController.T3;
    this.self = self;
  }

  @Override
  public void run() {
    try {
      while (true) {
        String selectedTargetPeer = Peers.select();
        Map<String, Metadados> dados;

        if (self) {
          dados = Memoria.getMeta(me.getNome());
        } else {
          dados = Memoria.getMeta(selectedTargetPeer);
        }

        if (!dados.values().contains(null)) {
          sendData(selectedTargetPeer, dados);
        }

        Thread.sleep(waitTime);
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  private void sendData(String target, Map<String, Metadados> data) throws IOException {
    ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    String json = mapper.writeValueAsString(data);
    
    InetAddress ip = InetAddress.getByName(target.split(":")[0]);
    int porta = Integer.parseInt(target.split(":")[1]);

    System.out.println("Enviando " + data.keySet() + " para " + target); 
    
    DatagramPacket datagramPacket = new DatagramPacket(json.getBytes(), json.getBytes().length, ip, porta);
    DatagramSocket socket = new DatagramSocket();
    socket.send(datagramPacket);
    socket.close();
    //TODO
  }
}