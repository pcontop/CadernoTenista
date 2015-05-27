package br.com.pcontop.CadernoOlheiro.model.export.xml.test;

import br.com.pcontop.CadernoOlheiro.bean.*;
import br.com.pcontop.CadernoOlheiro.model.export.xml.partida.PartidaExporter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.partida.PartidaExporterImpl;
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
public class TesteCriacaoPartida {
    Jogador jogador = Jogador.create().setId("1111").setNome("Robisverson").setEventos(buildListaEventos()).commit();
    Jogador jogador2 = Jogador.create().setId("2222").setNome("Claudinever").setEventos(buildListaEventos2()).commit();

    @Test
    public void test(){
       Partida partida = buildPartida();
       assertNotNull(partida);
        try {
            PartidaExporter partidaExporter = PartidaExporterImpl.getInstance();
            Element partidaElement = partidaExporter.export(partida);
            String resultado = partidaExporter.nodeToString(partidaElement);
            assertNotNull(resultado);
            System.out.println(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    private Partida buildPartida(){
        Partida partida = Partida.create()
                .setId("AAAA")
                .setDataInicio(new Date())
                .setDataFimPrimeiroTempo(new Date())
                .setDataInicioSegundoTempo(new Date())
                .setDataFimSegundoTempo(new Date())
                .setTiposEventosSelecionados(null)
                .setTime1(buildTime())
                .setTime2(buildTime2())
                .setLocal(buildLocal())
                .commit();
        return partida;
    }

    private Localidade buildLocal() {
        Localidade localidade = Localidade.create()
                .setId("1")
                .setDescricao("Curitiba")
                .setLatitude(0.0)
                .setLongitude(0.0)
                .commit();
        return localidade;
    }

    private Time buildTime(){
        Set<Jogador> jogadores = new LinkedHashSet<Jogador>();
        jogadores.add(jogador);
        Time time = Time.create().setId("NNNN").setCor("FF0000").setJogadores(jogadores).setNome("Atretico").commit();
        return time;
    }

    private Time buildTime2(){
        Set<Jogador> jogadores = new LinkedHashSet<Jogador>();
        jogadores.add(jogador2);
        Time time = Time.create().setId("MMMM").setCor("00FF00").setJogadores(jogadores).setNome("Parnaense").commit();
        return time;
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

    private List<EventoJogo> buildListaEventos2(){
        EventoJogo eventoJogo = EventoJogo.create()
                .setId("HHH")
                .setTipoEvento(TipoEvento.CHUTE_A_GOL_FORA)
                .setHora(new Date())
                .commit();
        List<EventoJogo> eventos = new ArrayList<EventoJogo>();
        eventos.add(eventoJogo);
        EventoJogo eventoJogo2 = EventoJogo.create()
                .setId("III")
                .setTipoEvento(TipoEvento.DRIBLE_CERTO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoJogo2);
        return eventos;
    }


}
