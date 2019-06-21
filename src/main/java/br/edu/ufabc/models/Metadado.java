package br.edu.ufabc.models;

import java.nio.file.attribute.FileTime;

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

  public FileTime getCriadoEm() {
    return this.criadoEm;
  }

  public void setCriadoEm(FileTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public FileTime getAcessadoEm() {
    return this.acessadoEm;
  }

  public void setAcessadoEm(FileTime acessadoEm) {
    this.acessadoEm = acessadoEm;
  }

  public FileTime getModificadoEm() {
    return this.modificadoEm;
  }

  public void setModificadoEm(FileTime modificadoEm) {
    this.modificadoEm = modificadoEm;
  }
}