package br.edu.ufabc;

import br.edu.ufabc.controllers.Peers;

public class App 
{
  public static String name;

  public static void main(String[] args)
  {    
    App.name = args[0];
    FileScanner fs = new FileScanner(args[1]);
    
    Peers.setup();
    fs.start();

    Gossip selfGossip = new Gossip(App.name);
    Gossip gossip = new Gossip();

    selfGossip.start();
    // gossip.start();
  }
}
