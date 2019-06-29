package br.edu.ufabc.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static br.edu.ufabc.App.servico;

public class Discovery {

    public static final String REGISTRY_ROUTE = "https://arcane-stream-28575.herokuapp.com";

    public static void register() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(REGISTRY_ROUTE + "/peers").openConnection();

            con.setConnectTimeout(30000);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("Content-Length", Integer.toString(servico.toString().length()));            
            con.getOutputStream().write(servico.toString().getBytes("UTF8"));

            if (con.getResponseCode() == 200) {
                System.out.println("Serviço " + servico.getNome() + " registrado");
            } else {
                System.out.println("Falha de comunicação com o serviço registrador");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}