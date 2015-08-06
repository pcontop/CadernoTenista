package br.com.pcontop.CadernoTenista.bean;

import java.util.Date;

/**
 * Created by pcont_000 on 26/07/2015.
 */
public class TempoPartidaFactory {

    public static TempoPartida getProximoTempo(TempoPartida tempoPartida, Date date) {
        TipoTempoPartida proximoTipoPartida = tempoPartida.getTipoTempoPartida().getProximoTipoTempo();
        if (proximoTipoPartida==null) {
            return null;
        }
        TempoPartida proximoTempoPartida = TempoPartida.create()
                .setDataInicio(date)
                .setTipo(proximoTipoPartida)
                .commit();
        return proximoTempoPartida;
    }
}
