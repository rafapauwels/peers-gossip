package br.edu.ufabc.gossip.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufabc.gossip.models.Peer;

import static br.edu.ufabc.gossip.App.me;
import static br.edu.ufabc.gossip.Auxiliar.*;

public class Discovery {

  private static final String REGISTRY_ROUTE = "https://arcane-stream-28575.herokuapp.com";
  private static String PUBLIC_HOST = "http://169.254.169.254/latest/meta-data/public-hostname";
  private static String INSTANCE_ID = "http://169.254.169.254/latest/meta-data/instance-id";

  public static void register() {
    if(me.getNome()=="localhost") return;
    
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(REGISTRY_ROUTE + "/peers").openConnection();

      con.setConnectTimeout(30000);
      con.setDoOutput(true);
      con.setRequestMethod("POST");
      con.addRequestProperty("Content-Type", "application/json");
      con.addRequestProperty("Content-Length", Integer.toString(me.toString().length()));
      con.getOutputStream().write(me.toString().getBytes("UTF8"));

      if (con.getResponseCode() == 200) {
        System.out.println(ANSI_CYAN + "DISCOVERY" + ANSI_RESET + " - Serviço " + me.getNome() + " registrado");
      } else {
        System.out.println("Falha de comunicação com o serviço registrador");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Peer[] getPeers() {
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(REGISTRY_ROUTE + "/peers").openConnection();

      con.setConnectTimeout(30000);
      con.setRequestMethod("GET");
      con.addRequestProperty("Content-Type", "application/json");

      BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuffer resposta = new StringBuffer();
      String input;

      while ((input = buffer.readLine()) != null) {
        resposta.append(input);
      }
      buffer.close();

      ObjectMapper mapper = new ObjectMapper();
      Peer[] peers = mapper.readValue(resposta.toString(), Peer[].class);

      return peers;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getHostIp() {
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(PUBLIC_HOST).openConnection();

      con.setConnectTimeout(30000);
      con.setRequestMethod("GET");
      con.addRequestProperty("Content-Type", "application/json");

      BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuffer resposta = new StringBuffer();
      String input;

      while ((input = buffer.readLine()) != null) {
        resposta.append(input);
      }
      buffer.close();

      return resposta.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getInstanceId() {
    try {
      HttpURLConnection con = (HttpURLConnection) new URL(INSTANCE_ID).openConnection();

      con.setConnectTimeout(30000);
      con.setRequestMethod("GET");
      con.addRequestProperty("Content-Type", "application/json");

      BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));
      StringBuffer resposta = new StringBuffer();
      String input;

      while ((input = buffer.readLine()) != null) {
        resposta.append(input);
      }
      buffer.close();

      return resposta.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}