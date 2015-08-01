package br.com.pcontop.CadernoTenista.bean;

import java.io.Serializable;

/**
 * Created by PauloBruno on 13/01/14.
 */
public enum TipoTempoPartida implements Serializable {
    APOS_JOGO("apos_jogo", null),
    SETIMO_SET("setimo_set", APOS_JOGO),
    SEXTO_INTERVALO("sexto_intervalo", SETIMO_SET),
    SEXTO_SET("sexto_set",SEXTO_INTERVALO),
    QUINTO_INTERVALO("quinto_intervalo",SEXTO_SET),
    QUINTO_SET("quinto_set", QUINTO_INTERVALO),
    QUARTO_INTERVALO("quarto_intervalo",QUINTO_SET),
    QUARTO_SET("quarto_set",QUARTO_INTERVALO),
    TERCEIRO_INTERVALO("terceiro_intervalo",QUARTO_SET),
    TERCEIRO_SET("terceiro_set", TERCEIRO_INTERVALO),
    SEGUNDO_INTERVALO("segundo_intervalo",TERCEIRO_SET),
    SEGUNDO_SET("segundo_set",SEGUNDO_INTERVALO),
    PRIMEIRO_INTERVALO("primeiro_intervalo", SEGUNDO_SET),
    PRIMEIRO_SET("primeiro_set",PRIMEIRO_INTERVALO),
    ANTES_PARTIDA("antes_partida", PRIMEIRO_SET)
            ;

    private final String descricao;
    private final TipoTempoPartida proximoTipoTempo;

    TipoTempoPartida(String descricao, TipoTempoPartida proximoTipoTempo) {
        this.descricao=descricao;
        this.proximoTipoTempo = proximoTipoTempo;
    }

    public String getDescricao(){
        return descricao;
    }

    public TipoTempoPartida getProximoTipoTempo(){
        return proximoTipoTempo;
    }
}
