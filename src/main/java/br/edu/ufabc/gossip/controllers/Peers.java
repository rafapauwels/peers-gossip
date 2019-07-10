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
    updatePeers();
  }
  
  public static void updatePeers() {
    for (Peer vizinho : Discovery.getPeers()) {
      peers.put(vizinho.getNome(), vizinho.getEndereco());    
    }
    
    peers.remove(me.getNome());
  }

  public static Map<String, String> select() {
    Map<String, String> retorno = new HashMap<String, String>();
    if (peers.size() > 0) {
      int index = new Random().nextInt(peers.size());
      String id = (String) peers.keySet().toArray()[index];
      String address = (String) peers.get(id);
      retorno.put("id", id);
      retorno.put("address", address);
      return retorno;
    } else {
      return null;
    }
  }

  public static String getPeerAddress(String cliente) {
    return peers.get(cliente);
  }
}