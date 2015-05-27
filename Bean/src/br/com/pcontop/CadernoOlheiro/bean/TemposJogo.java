package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;

/**
 * Created by PauloBruno on 13/01/14.
 */
public enum TemposJogo implements Serializable {
    ANTES_JOGO("antes_jogo"),
    PRIMEIRO_TEMPO("primeiro_tempo"),
    INTERVALO("intervalo"),
    SEGUNDO_TEMPO("segundo_tempo"),
    APOS_JOGO("apos_jogo"),
    INDEFINIDA("indefinida");

    private final String descricao;

    TemposJogo(String descricao) {
        this.descricao=descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
