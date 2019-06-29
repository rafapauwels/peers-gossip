package br.edu.ufabc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufabc.controllers.Memoria;
import br.edu.ufabc.controllers.TimeController;
import br.edu.ufabc.models.Metadado;
import br.edu.ufabc.models.Metadados;

import static br.edu.ufabc.App.servico;

public class FileScanner extends Thread {

  public String dir;

  public FileScanner(String dir) {
    this.dir = dir;
  }

  @Override
  public void run() {
    System.out.println("O scanner de arquivos foi iniciado e será executado a cada " + TimeController.T1
        + " milisegundos no caminho " + dir);

    try {
      File diretorio = new File(dir);

      if (diretorio.isDirectory()) {
        while (true) {

          File[] arquivos = diretorio.listFiles();
          Metadados metadados = new Metadados();
          List<Metadado> listaMeta = new ArrayList<Metadado>();
          
          for (File arquivo : arquivos) {
            Metadado meta = new Metadado();
            BasicFileAttributes atributos = Files.readAttributes(Paths.get(arquivo.getPath()), BasicFileAttributes.class);

            meta.setNome(arquivo.getName());
            meta.setTamanho(atributos.size());
            meta.setAcessadoEm(atributos.lastAccessTime());
            meta.setCriadoEm(atributos.creationTime());
            meta.setModificadoEm(atributos.lastModifiedTime());

            listaMeta.add(meta);
          }

          metadados.setMetadados(listaMeta);
          Memoria.adiciona(servico.getNome(), metadados);

          Thread.sleep(TimeController.T1);
        }
      } else {
        System.out.println("O caminho informado não é um diretório, portando o scanner não será ser executado");
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }
}