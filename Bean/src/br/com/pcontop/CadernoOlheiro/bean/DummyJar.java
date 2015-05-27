package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;

/**
 * Created by PauloBruno on 03/07/2014.
 */
public class DummyJar implements Serializable {
    private String valor;

    public DummyJar(){

    }

    public DummyJar(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
