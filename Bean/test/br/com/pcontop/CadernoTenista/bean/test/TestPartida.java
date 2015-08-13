package br.com.pcontop.CadernoTenista.bean.test;

import org.junit.Test;

import java.util.Date;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.PartidaHelper;
import br.com.pcontop.CadernoTenista.bean.TipoTempoPartida;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pcont_000 on 06/08/2015.
 */
public class TestPartida {
    @Test
    public void testCriacao(){
        Partida partida = PartidaHelper.buildPartida();
        assertNotNull(partida);
    }

    @Test
    public void testTempos(){
        Partida partida = PartidaHelper.buildPartidaMinima();
        assertNotNull(partida.getTempoPartida());
        assertTrue(partida.getTipoTempoPartida().equals(TipoTempoPartida.ANTES_PARTIDA));
        Date dataInicio = new Date();
        partida.transiteProximoTempo(dataInicio);
        assertTrue(partida.getTipoTempoPartida().equals(TipoTempoPartida.PRIMEIRO_SET));
        assertNotNull(partida.getDataInicio() != null);
        assertTrue(partida.getDataInicio().equals(dataInicio));
        partida.transiteProximoTempo(dataInicio);
        assertTrue(partida.getTipoTempoPartida().equals(TipoTempoPartida.PRIMEIRO_INTERVALO));
        partida.transiteProximoTempo(dataInicio);
        assertTrue(partida.getTipoTempoPartida().equals(TipoTempoPartida.SEGUNDO_SET));
        partida.transiteFimDePartida(new Date());
        assertTrue(partida.getTipoTempoPartida().equals(TipoTempoPartida.APOS_PARTIDA));

    }


}
