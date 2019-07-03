package br.edu.ufabc.gossip.models;

public class Peer {
    private String nome;
    private String endereco;

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "{\"nome\":\"" + getNome() + "\",\"endereco\":\"" + getEndereco() + "\"}";
    }    
}