package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;

/**
 * Created by PauloBruno on 13/01/14.
 */
public enum TemposPartida implements Serializable {
    ANTES_PARTIDA("antes_partida"),
    PRIMEIRO_SET("primeiro_set"),
    PRIMEIRO_INTERVALO("primeiro_intervalo"),
    SEGUNDO_SET("segundo_set"),
    SEGUNDO_INTERVALO("segundo_intervalo"),
    TERCEIRO_SET("terceiro_set"),
    TERCEIRO_INTERVALO("terceiro_intervalo"),
    QUARTO_SET("quarto_set"),
    QUARTO_INTERVALO("quarto_intervalo"),
    QUINTO_SET("quinto_set"),
    QUINTO_INTERVALO("quinto_intervalo"),
    SEXTO_SET("sexto_set"),
    SEXTO_INTERVALO("sexto_intervalo"),
    SETIMO_SET("setimo_set"),
    APOS_JOGO("apos_jogo"),
    INDEFINIDA("indefinida");

    private final String descricao;

    TemposPartida(String descricao) {
        this.descricao=descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
