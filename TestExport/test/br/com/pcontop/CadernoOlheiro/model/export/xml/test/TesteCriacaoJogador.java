package br.com.pcontop.CadernoOlheiro.model.export.xml.test;

import br.com.pcontop.CadernoOlheiro.bean.EventoPartida;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TipoEvento;
import br.com.pcontop.CadernoOlheiro.model.export.xml.jogador.JogadorExporterImpl;
import org.junit.Test;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class TesteCriacaoJogador {
    @Test
    public void testCriacaoEventoJogo(){
        JogadorExporterImpl jogadorExporter = JogadorExporterImpl.getInstance();
        Jogador jogador = buildJogador();
        try {
            Element element =  jogadorExporter.export(jogador);
            assertNotNull(element);
            String resultado = jogadorExporter.nodeToString(element);
            assertNotNull(resultado);
            System.out.println(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    private Jogador buildJogador() {
        Jogador jogador = Jogador.create().setId("1111").setNome("Robisverson").setEventos(buildListaEventos()).commit();
        return jogador;
    }

    private List<EventoPartida> buildListaEventos(){
        EventoPartida eventoPartida = EventoPartida.create()
                .setId("AAA")
                .setTipoEvento(TipoEvento.DESARME_FALTA)
                .setHora(new Date())
                .commit();
        List<EventoPartida> eventos = new ArrayList<EventoPartida>();
        eventos.add(eventoPartida);
        EventoPartida eventoPartida2 = EventoPartida.create()
                .setId("BBB")
                .setTipoEvento(TipoEvento.CHUTE_A_GOL_DEFENDIDO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoPartida2);
        return eventos;
    }

}
