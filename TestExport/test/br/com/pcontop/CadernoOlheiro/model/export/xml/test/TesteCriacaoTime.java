package br.com.pcontop.CadernoOlheiro.model.export.xml.test;

import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.bean.TipoEvento;
import br.com.pcontop.CadernoOlheiro.model.export.xml.time.TimeExporterImpl;
import org.junit.Test;
import org.w3c.dom.Element;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class TesteCriacaoTime {
    Jogador jogador = Jogador.create().setId("1111").setNome("Robisverson").setEventos(buildListaEventos()).commit();

    @Test
    public void test(){
       Time time = buildTime();
       assertNotNull(time);
        try {
            Element timeElement = TimeExporterImpl.getInstance().export(time);
            String resultado = TimeExporterImpl.getInstance().nodeToString(timeElement);
            assertNotNull(resultado);
            System.out.println(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    private Time buildTime(){
        Set<Jogador> jogadores = new LinkedHashSet<Jogador>();
        jogadores.add(buildJogador());
        Time time = Time.create().setId("NNNN").setCor("FF0000").setJogadores(jogadores).setNome("Atretico").commit();
        return time;
    }

    private Jogador buildJogador() {
        return jogador;
    }

    private List<EventoJogo> buildListaEventos(){
        EventoJogo eventoJogo = EventoJogo.create()
                .setId("AAA")
                .setTipoEvento(TipoEvento.DESARME_FALTA)
                .setHora(new Date())
                .commit();
        List<EventoJogo> eventos = new ArrayList<EventoJogo>();
        eventos.add(eventoJogo);
        EventoJogo eventoJogo2 = EventoJogo.create()
                .setId("BBB")
                .setTipoEvento(TipoEvento.DESARME_FALTA)
                .setHora(new Date())
                .commit();
        eventos.add(eventoJogo2);
        return eventos;
    }


}
