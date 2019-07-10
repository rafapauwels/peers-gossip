package br.edu.ufabc.gossip.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.edu.ufabc.gossip.App;
import br.edu.ufabc.gossip.models.Metadados;

import static br.edu.ufabc.gossip.App.me;
import static br.edu.ufabc.gossip.Auxiliar.*;

public class Memoria {

  private static Map<String, Metadados> memoria = new HashMap<String, Metadados>();

  public static void remove(String cliente) {
    synchronized (memoria) {
      if (memoria.get(cliente) == null) {
        return;
      } else {
        memoria.remove(cliente);
      }
    }
  }

  public static void adiciona(String cliente, Metadados metaRecebido) {
    synchronized (memoria) {
      GarbageGossip.heartbeat(cliente);
      Metadados meta = new Metadados(metaRecebido);
      
      if(memoria.get(cliente) != null) {
        if (isNewer(memoria.get(cliente), meta)) {
          System.out.println(ANSI_RED + "MEMORIA" + ANSI_RESET + " - Recebido difere da memória e é mais atual");
          memoria.put(cliente, meta);
        } else {
          System.out.println(ANSI_RED + "MEMORIA" + ANSI_RESET + " - Recebido duplicado ou mais velho, não entrou na memória");
        }
      } else {
        System.out.println(ANSI_RED + "MEMORIA" + ANSI_RESET + " - Esse cliente não possui registros na memória, adicionando");
        memoria.put(cliente, meta);
      }
    }
  }

  public static Map<String, Metadados> getMeta(String cliente) {
    synchronized (memoria) {
      Map<String, Metadados> retorno = new HashMap<String, Metadados>();
  
      if (cliente.equals(me.getNome())) {
        retorno.put(cliente, memoria.get(cliente));
        return retorno;
      }
  
      String peerTransmitido = selectDataToGossip(cliente);
      retorno.put(peerTransmitido, memoria.get(peerTransmitido));
  
      return retorno;
    }
  }

  /*
    Seleciona informações para serem transmitidas que não são do próprio alvo nem de si mesmo, o segundo caso é gerenciado pelo selfgossip
  */
  private static String selectDataToGossip(String cliente) {
    synchronized (memoria) {
      Map<String, Metadados> memoriaLocal = new HashMap<String, Metadados>(memoria);
      memoriaLocal.remove(me.getNome());
      memoriaLocal.remove(cliente);
  
      if (memoriaLocal.size() > 0) {
        Integer index = new Random().nextInt(memoriaLocal.size());
        return (String) memoriaLocal.keySet().toArray()[index];
      }
      return null;
    }
  }

  private static boolean isNewer(Metadados metaMem, Metadados meta) {
    return meta.getMetaVersion() > metaMem.getMetaVersion();
  }
}