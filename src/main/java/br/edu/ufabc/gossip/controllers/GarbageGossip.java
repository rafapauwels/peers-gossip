package br.edu.ufabc.gossip.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static br.edu.ufabc.gossip.App.me;
import static br.edu.ufabc.gossip.Auxiliar.*;

public class GarbageGossip extends Thread {
  private static Map<String, Instant> beats;

  public GarbageGossip() {
    beats = new HashMap<String, Instant>();
  }

  @Override
  public void run() {
    try {
      while(true) {
        Instant limiteAtualizacao = Instant.now().minus(TimeController.T4, ChronoUnit.MILLIS);
  
        for (String cliente : beats.keySet()) {
          if (beats.get(cliente).isBefore(limiteAtualizacao)) {
            if (!cliente.equals(me.getNome())) {
              System.out.println(ANSI_BLACK + "GARBAGE" + ANSI_RESET + " - Cliente " + cliente + " removido da memória por falta de atualização");
              Memoria.remove(cliente);
            }
          }
        }
  
        Thread.sleep(TimeController.T4);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void heartbeat(String cliente) {
    beats.put(cliente, Instant.now());
  }
}