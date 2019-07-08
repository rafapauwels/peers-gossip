package br.edu.ufabc.gossip.controllers;

import static br.edu.ufabc.gossip.Auxiliar.ANSI_PURPLE;
import static br.edu.ufabc.gossip.Auxiliar.ANSI_RED;
import static br.edu.ufabc.gossip.Auxiliar.ANSI_RESET;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import br.edu.ufabc.gossip.models.Metadados;

public class GossipReceiver extends Thread {
  private DatagramSocket serverSocket;
  private HashMap<String, Metadados> payload;
  private byte[] dadosRecebidos;

  public GossipReceiver() {
    try {
      this.dadosRecebidos = new byte[1024];
      this.serverSocket = new DatagramSocket(49152);
    } catch (Exception e) {
      System.out.println(ANSI_PURPLE + "GOSSIP" + ANSI_RESET + " - Falha ao bindar a porta, esse peer não está apto a receber dados");
      this.serverSocket = null;
    }
  }

  @Override
  public void run() {
    if (this.serverSocket==null) return;

    while (true) {
      DatagramPacket pacote = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
  
      try {
        serverSocket.receive(pacote);
        payload = new ObjectMapper().readValue(new String(pacote.getData()), new TypeReference<Map<String, Metadados>>() {});
        System.out.println(payload);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}