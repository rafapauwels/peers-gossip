package br.edu.ufabc.gossip;

import static br.edu.ufabc.gossip.App.me;
import static br.edu.ufabc.gossip.Auxiliar.ANSI_GREEN;
import static br.edu.ufabc.gossip.Auxiliar.ANSI_RESET;
import static br.edu.ufabc.gossip.Auxiliar.filetimeToString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ufabc.gossip.controllers.Memoria;
import br.edu.ufabc.gossip.controllers.TimeController;
import br.edu.ufabc.gossip.models.Metadado;
import br.edu.ufabc.gossip.models.Metadados;

public class FileScanner extends Thread {

  private String dir;
  private Metadados metadados;

  public FileScanner(String dir) {
    this.dir = dir;
    this.metadados = new Metadados();
  }
  
  @Override
  public void run() {
    System.out.println(ANSI_GREEN + "ARQUIVOS" + ANSI_RESET + " - O scanner de arquivos foi iniciado e será executado a cada " + TimeController.T1
        + " milisegundos no caminho " + dir);

    try {
      File diretorio = new File(dir);
      if (diretorio.isDirectory()) {
        while (true) {
          File[] arquivos = diretorio.listFiles();
          List<Metadado> listaMeta = new ArrayList<Metadado>();
          
          for (File arquivo : arquivos) {
            Metadado meta = new Metadado();
            BasicFileAttributes atributos = Files.readAttributes(Paths.get(arquivo.getPath()), BasicFileAttributes.class);

            meta.setNome(arquivo.getName());
            meta.setTamanho(atributos.size());
            meta.setAcessadoEm(filetimeToString(atributos.lastAccessTime()));
            meta.setCriadoEm(filetimeToString(atributos.creationTime()));
            meta.setModificadoEm(filetimeToString(atributos.lastModifiedTime()));

            listaMeta.add(meta);
          }

          if (shouldUpdateMeta(listaMeta)) {
            System.out.println(ANSI_GREEN + "ARQUIVOS" + ANSI_RESET + " - Atualizando metadados");
            metadados.setMetadados(listaMeta);
            Memoria.adiciona(me.getNome(), metadados);
          }

          Thread.sleep(TimeController.T1);
        }
      } else {
        System.out.println(ANSI_GREEN + "ARQUIVOS" + ANSI_RESET + " - O caminho informado não é um diretório, portando o scanner não será ser executado");
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }

  private boolean shouldUpdateMeta(List<Metadado> listaMetadado) {
    List<Metadado> listaMemoria = metadados.getMetadados();

    if (listaMetadado.size() == listaMemoria.size()) {
      listaMemoria = new ArrayList<Metadado>(listaMemoria);
      listaMetadado = new ArrayList<Metadado>(listaMetadado);

      return !listaMemoria
                .stream()
                .map(meta -> meta.getModificadoEm())
                .collect(Collectors.toList())
                .containsAll(listaMetadado
                              .stream()
                              .map(meta -> meta.getModificadoEm())
                              .collect(Collectors.toList()));
    }

    return true;
  }
}