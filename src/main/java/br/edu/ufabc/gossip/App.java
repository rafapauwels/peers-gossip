package br.edu.ufabc.gossip;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ufabc.gossip.controllers.Discovery;
import br.edu.ufabc.gossip.controllers.Peers;
import br.edu.ufabc.gossip.models.Peer;

public class App {
  public static Peer me = new Peer();

  private static boolean LOCAL = false;

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Informe o diretório como paramêtro da JVM");
      System.exit(0);
    } else if (args.length == 2) {
      if (args[1].equals("LOCAL")) {
        LOCAL = true;
      }
    }

    if (LOCAL) {
      me.setNome("Localhost");
      me.setEndereco("http://localhost");
    } else {
      me.setNome(Discovery.getInstanceId());
      me.setEndereco(Discovery.getHostIp());
    }

    FileScanner fs = new FileScanner(args[0]);
    
    Peers.setup();
    fs.start();

    Gossip selfGossip = new Gossip(me.getNome());
    Gossip gossip = new Gossip();
    
    // selfGossip.start();
    // gossip.start();
  }
}
