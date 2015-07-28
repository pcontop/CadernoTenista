package br.com.pcontop.CadernoOlheiro.bean;

import java.util.Date;

/**
 * Created by pcont_000 on 26/07/2015.
 */
public class TempoPartidaFactory {

    public static TempoPartida getProximoTempo(TempoPartida tempoPartida, Date date) {
        TiposTempoPartida proximoTipoPartida = tempoPartida.getTipoTempoPartida().getProximoTipoTempo();
        TempoPartida proximoTempoPartida = TempoPartida.create()
                .setDataInicio(date)
                .setTipo(proximoTipoPartida)
                .commit();
        return proximoTempoPartida;
    }
}
