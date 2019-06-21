package br.edu.ufabc.models;

import java.util.List;

public class Metadados {
  private long metaVersion;
  private List<Metadado> metadados;
  
  public long getMetaVersion() {
    return this.metaVersion;
  }

  public void setMetaVersion(long metaVersion) {
    this.metaVersion = metaVersion;
  }

  public List<Metadado> getMetadados() {
    return this.metadados;
  }

  public void setMetadados(List<Metadado> metadados) {
    this.metadados = metadados;
  }
}