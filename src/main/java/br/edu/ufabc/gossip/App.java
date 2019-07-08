package br.edu.ufabc.gossip;

import br.edu.ufabc.gossip.controllers.Discovery;
import br.edu.ufabc.gossip.controllers.Gossip;
import br.edu.ufabc.gossip.controllers.GossipReceiver;
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
      me.setNome("localhost");
      me.setEndereco("http://localhost");
    } else {
      me.setNome(Discovery.getInstanceId());
      me.setEndereco(Discovery.getHostIp());
    }
    FileScanner fs = new FileScanner(args[0]);
    Peers.setup();

    Gossip selfGossip = new Gossip(me.getNome());
    Gossip gossip = new Gossip();
    GossipReceiver gossipReceiver = new GossipReceiver();
    // GarbageGossip gc = new GarbageGossip();

    
    fs.start();
    gossipReceiver.start();
    selfGossip.start();
    // gossip.start();
    // gc.start();
  }
}
