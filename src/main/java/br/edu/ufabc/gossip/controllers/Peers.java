package br.edu.ufabc.gossip.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.edu.ufabc.gossip.App;
import br.edu.ufabc.gossip.Auxiliar;
import br.edu.ufabc.gossip.models.Peer;

import static br.edu.ufabc.gossip.App.me;;

public class Peers {
  private static Map<String, String> peers = new HashMap<String, String>();

  public static void setup() {
    Discovery.register();

    for (Peer vizinho : Discovery.getPeers()) {
      peers.put(vizinho.getNome(), vizinho.getEndereco());    
    }
    
    peers.remove(me.getNome());
  }

  public static String select() {
    Integer index = new Random().nextInt(peers.size());
    return (String) peers.values().toArray()[index];
  }

  public static String getPeerAddress(String cliente) {
    return peers.get(cliente);
  }
}