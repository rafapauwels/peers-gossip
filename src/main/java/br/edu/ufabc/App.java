package br.edu.ufabc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ufabc.controllers.Peers;
import br.edu.ufabc.models.Peer;

public class App {
  public static Peer servico = new Peer();

  public static void main(String[] args) {
    String PUBLIC_HOST = "http://169.254.169.254/latest/meta-data/public-hostname";
    String INSTANCE_ID = "http://169.254.169.254/latest/meta-data/instance-id";

    // try {
    //   servico.setNome(Auxiliar.getText(INSTANCE_ID));
    //   servico.setEndereco(Auxiliar.getText(PUBLIC_HOST));
    // } catch (IOException e) {
    //   // TODO Auto-generated catch block
    //   e.printStackTrace();
    // }
    servico.setNome("Teste");
    servico.setEndereco("http:teste");

    FileScanner fs = new FileScanner(args[0]);
    
    Peers.setup();
    fs.start();

    Gossip selfGossip = new Gossip(servico.getNome());
    Gossip gossip = new Gossip();

    selfGossip.start();
    // gossip.start();
  }
}
