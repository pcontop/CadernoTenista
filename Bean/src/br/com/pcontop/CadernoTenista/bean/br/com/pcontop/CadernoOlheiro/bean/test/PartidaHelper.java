package br.com.pcontop.CadernoTenista.bean.br.com.pcontop.CadernoOlheiro.bean.test;

import br.com.pcontop.CadernoTenista.bean.*;

import java.util.*;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class PartidaHelper {

    private PartidaHelper(){

    }

    public static Partida buildPartidaMinima(){
        Partida partida = Partida.create()
                .setTiposEventosSelecionados(null)
                .setLocal(null)
                .commit();
        return partida;
    }

    public static Partida buildPartida(){
        Partida partida = Partida.create()
                .setTiposEventosSelecionados(buildTiposEventos())
                .setLocal(buildLocal())
                .setOlheiro(buildOlheiro())
                .setPathVideoPrimeiroTempo("www.noplace.org")
                .setPathVideoSegundoTempo("www.omelete.com")
                .setJogador1(buildJogador())
                .setJogador2(buildJogador2())
                .setTempoPartida(buildTempoPartida())
                .setTemposPartida(buildTemposPartida())
                .commit();
        return partida;
    }

    private static ListTemposPartida buildTemposPartida() {
        return null;
    }

    private static TempoPartida buildTempoPartida() {
        Date date = new Date();
        Date dataInicio = new Date();
        Date dataFim = new Date();
        dataInicio.setTime(date.getTime()-400000);
        dataFim.setTime(date.getTime()-4000);
        TempoPartida tempoPartida = TempoPartida.create()
                .setDataInicio(dataInicio)
                .setDataFim(dataFim)
                .setTipo(TiposTempoPartida.PRIMEIRO_SET)
                .commit();
        return null;
    }

    private static Set<TiposEvento> buildTiposEventos() {
        HashSet<TiposEvento> tiposEventos = new HashSet<>();
        tiposEventos.add(TiposEvento.ACE_CERTO);
        tiposEventos.add(TiposEvento.BACKHAND_CERTO);
        return tiposEventos;
    }

    private static Localidade buildLocal() {
        Localidade localidade = Localidade.create()
                .setDescricao("Curitiba")
                .setLatitude(0.0)
                .setLongitude(0.0)
                .commit();
        return localidade;
    }

    public static Jogador buildJogador() {
        return Jogador.create().setNome("James Hartmann").setEventos(buildListaEventos2()).commit();
    }

    public static Jogador buildJogador2() {
        return Jogador.create().setNome("Leonard Bellarmy").setEventos(buildListaEventos()).commit();
    }

    private static List<EventoPartida> buildListaEventos(){
        EventoPartida eventoPartida = buildEventoJogo();
        List<EventoPartida> eventos = new ArrayList<>();
        eventos.add(eventoPartida);
        EventoPartida eventoPartida2 = EventoPartida.create()
                .setTipoEvento(TiposEvento.ACE_CERTO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoPartida2);
        return eventos;
    }

    private static List<EventoPartida> buildListaEventos2(){
        EventoPartida eventoPartida = EventoPartida.create()
                .setTipoEvento(TiposEvento.BACKHAND_ERRADO)
                .setHora(new Date())
                .commit();
        List<EventoPartida> eventos = new ArrayList<EventoPartida>();
        eventos.add(eventoPartida);
        EventoPartida eventoPartida2 = EventoPartida.create()
                .setTipoEvento(TiposEvento.ACE_ERRADO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoPartida2);
        return eventos;
    }

    private static List<EventoPartida> buildListaEventos3(){
        EventoPartida eventoPartida = EventoPartida.create()
                .setTipoEvento(TiposEvento.BACKHAND_CERTO)
                .setHora(new Date())
                .commit();
        List<EventoPartida> eventos = new ArrayList<EventoPartida>();
        eventos.add(eventoPartida);
        EventoPartida eventoPartida2 = EventoPartida.create()
                .setTipoEvento(TiposEvento.BACKHAND_ERRADO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoPartida2);
        return eventos;
    }

    private static List<EventoPartida> buildListaEventos4(){
        EventoPartida eventoPartida = EventoPartida.create()
                .setTipoEvento(TiposEvento.ACE_ERRADO)
                .setHora(new Date())
                .commit();
        List<EventoPartida> eventos = new ArrayList<EventoPartida>();
        eventos.add(eventoPartida);
        EventoPartida eventoPartida2 = EventoPartida.create()
                .setTipoEvento(TiposEvento.ACE_CERTO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoPartida2);
        return eventos;
    }


    public static EventoPartida buildEventoJogo(){
        EventoPartida eventoPartida = EventoPartida.create()
                .setTipoEvento(TiposEvento.ACE_CERTO)
                .setHora(new Date())
                .commit();
        return eventoPartida;

    }

    public static Olheiro buildOlheiro(){
        Olheiro olheiro = Olheiro.create()
                .setId("A1A")
                .setNome("GuiViado")
                .setLogin("Viado")
                .setSenha("Bicha")
                .commit();
        return olheiro;
    }

    public static Localidade buildLocalidade(){
        Localidade localidade = Localidade
                .create()
                .setDescricao("Parnaiba")
                .setLatitude(0.0)
                .setLongitude(1.5)
                .commit();
        return localidade;
    }

}
