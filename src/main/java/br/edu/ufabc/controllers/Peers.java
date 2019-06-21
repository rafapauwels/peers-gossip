package br.edu.ufabc.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.edu.ufabc.App;

public class Peers {
  private static Map<String, String> peers = new HashMap<String, String>();

  public static void setup() {
    peers.put("Q", "localhost:9090");
    peers.put("W", "localhost:9091");
    peers.put("E", "localhost:9092");
    peers.put("R", "localhost:9093");
    peers.put("T", "localhost:9094");
    peers.remove(App.name);
  }

  public static String select() {
    Integer index = new Random().nextInt(peers.size());
    return (String) peers.values().toArray()[index];
  }

  public static String getPeerAddress(String cliente) {
    return peers.get(cliente);
  }
}