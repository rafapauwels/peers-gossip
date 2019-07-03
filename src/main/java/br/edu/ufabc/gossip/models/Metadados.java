package br.edu.ufabc.gossip.models;

import java.util.ArrayList;
import java.util.List;

public class Metadados {
  private long metaVersion;
  private List<Metadado> metadados;

  public Metadados() {
    metaVersion = 0;
    metadados = new ArrayList<Metadado>();
  }

  public Metadados(Metadados clone) {
    this.metaVersion = clone.getMetaVersion();
    this.metadados = clone.getMetadados();
  }

  public long getMetaVersion() {
    return metaVersion;
  }
  
  public void incrementaMetaVersion() {
    metaVersion++;
  }
  public List<Metadado> getMetadados() {
    return metadados;
  }

  public void setMetadados(List<Metadado> meta) {
    incrementaMetaVersion();
    metadados = meta;
  }
}