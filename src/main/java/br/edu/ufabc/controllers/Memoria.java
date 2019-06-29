package br.edu.ufabc.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.edu.ufabc.App;
import br.edu.ufabc.models.Metadados;

import static br.edu.ufabc.App.servico;

public class Memoria {

  private static Map<String, Metadados> memoria = new HashMap<String, Metadados>();

  public static void adiciona(String cliente, Metadados meta) {
    if(memoria.get(cliente) != null) {
      if (isNewer(memoria.get(cliente), meta)) {
        System.out.println("Recebido difere da memória e é mais atual");
        memoria.put(cliente, meta);
      } else {
        System.out.println("Recebido duplicado ou mais velho, não entrou na memória");
      }
    } else {
      System.out.println("Esse cliente não possui registros na memória");
      memoria.put(cliente, meta);
    }
  }

  public static Map<String, Metadados> getMeta(String cliente) {
    Map<String, Metadados> retorno = new HashMap<String, Metadados>();

    if (cliente.equals(servico.getNome())) {
      retorno.put(cliente, memoria.get(cliente));
      return retorno;
    }

    String peerTransmitido = selectName(cliente);
    retorno.put(peerTransmitido, memoria.get(peerTransmitido));

    return retorno;
  }

  private static String selectName(String cliente) {
    Map<String, Metadados> memoriaLocal = memoria;
    memoriaLocal.remove(cliente);

    Integer index = new Random().nextInt(memoriaLocal.size());
    return (String) memoriaLocal.keySet().toArray()[index];
  }

  private static boolean isNewer(Metadados metaMem, Metadados meta) {
    return metaMem.getMetaVersion() > metaMem.getMetaVersion();
  }
}