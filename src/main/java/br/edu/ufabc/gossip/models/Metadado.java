package br.edu.ufabc.gossip.models;

import java.nio.file.attribute.FileTime;

import br.edu.ufabc.gossip.Auxiliar;

public class Metadado {
  private String nome;
  private long tamanho;
  private FileTime criadoEm;
  private FileTime acessadoEm;
  private FileTime modificadoEm;
  
  public Metadado() {
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public long getTamanho() {
    return this.tamanho;
  }

  public void setTamanho(long tamanho) {
    this.tamanho = tamanho;
  }

  public String getCriadoEm() {
    return Auxiliar.filetimeToString(this.criadoEm);
  }

  public void setCriadoEm(String criadoEm) {
    this.criadoEm = Auxiliar.stringTofiletime(criadoEm);
  }

  public String getAcessadoEm() {
    return Auxiliar.filetimeToString(this.acessadoEm);
  }

  public void setAcessadoEm(String acessadoEm) {
    this.acessadoEm = Auxiliar.stringTofiletime(acessadoEm);
  }

  public String getModificadoEm() {
    return Auxiliar.filetimeToString(this.modificadoEm);
  }

  public void setModificadoEm(String modificadoEm) {
    this.modificadoEm = Auxiliar.stringTofiletime(modificadoEm);
  }
}