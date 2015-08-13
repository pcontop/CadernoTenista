package br.com.pcontop.CadernoTenista.bean;

import java.io.Serializable;

/**
 * Created by PauloBruno on 13/01/14.
 */
public enum TipoTempoPartida implements Serializable {
    APOS_PARTIDA("apos_partida", null, false),
    SETIMO_SET("setimo_set", APOS_PARTIDA, true),
    SEXTO_INTERVALO("sexto_intervalo", SETIMO_SET, false),
    SEXTO_SET("sexto_set",SEXTO_INTERVALO, true),
    QUINTO_INTERVALO("quinto_intervalo",SEXTO_SET, false),
    QUINTO_SET("quinto_set", QUINTO_INTERVALO, true),
    QUARTO_INTERVALO("quarto_intervalo",QUINTO_SET, false),
    QUARTO_SET("quarto_set",QUARTO_INTERVALO, true),
    TERCEIRO_INTERVALO("terceiro_intervalo",QUARTO_SET, false),
    TERCEIRO_SET("terceiro_set", TERCEIRO_INTERVALO, true),
    SEGUNDO_INTERVALO("segundo_intervalo",TERCEIRO_SET, false),
    SEGUNDO_SET("segundo_set",SEGUNDO_INTERVALO, true),
    PRIMEIRO_INTERVALO("primeiro_intervalo", SEGUNDO_SET, false),
    PRIMEIRO_SET("primeiro_set",PRIMEIRO_INTERVALO, true),
    ANTES_PARTIDA("antes_partida", PRIMEIRO_SET, false)
            ;

    private final String descricao;
    private final TipoTempoPartida proximoTipoTempo;
    private final boolean isAtivo;

    TipoTempoPartida(String descricao, TipoTempoPartida proximoTipoTempo, boolean isAtivo) {
        this.descricao=descricao;
        this.proximoTipoTempo = proximoTipoTempo;
        this.isAtivo=isAtivo;
    }

    public String getDescricao(){
        return descricao;
    }

    public TipoTempoPartida getProximoTipoTempo(){
        return proximoTipoTempo;
    }

    public boolean isAtivo(){
        return isAtivo;
    }
}
