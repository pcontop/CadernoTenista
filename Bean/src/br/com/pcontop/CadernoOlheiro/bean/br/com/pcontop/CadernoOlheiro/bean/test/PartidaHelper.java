package br.com.pcontop.CadernoOlheiro.bean.br.com.pcontop.CadernoOlheiro.bean.test;

import br.com.pcontop.CadernoOlheiro.bean.*;

import java.util.*;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class PartidaHelper {

    private PartidaHelper(){

    }

    public static Partida buildPartidaMinima(){
        Partida partida = Partida.create()
                .setId("AAAA")
                .setDataInicio(new Date())
                .setTiposEventosSelecionados(null)
                .setTime1(null)
                .setTime2(null)
                .setLocal(null)
                .setDataFimPrimeiroTempo(new Date())
                .setDataInicioSegundoTempo(new Date())
                .setDataFimSegundoTempo(new Date())
                .commit();
        return partida;
    }

    public static Partida buildPartida(){
        Partida partida = Partida.create()
                .setId("AAAA")
                .setDataInicio(new Date())
                .setTiposEventosSelecionados(buildTiposEventos())
                .setTime1(buildTime())
                .setTime2(buildTime2())
                .setLocal(buildLocal())
                .setDataFimPrimeiroTempo(new Date())
                .setDataInicioSegundoTempo(new Date())
                .setDataFimSegundoTempo(new Date())
                .setLocal(buildLocal())
                .setOlheiro(buildOlheiro())
                .setPathVideoPrimeiroTempo("www.noplace.org")
                .setPathVideoSegundoTempo("www.omelete.com")
                .commit();
        return partida;
    }

    private static Set<TipoEvento> buildTiposEventos() {
        HashSet<TipoEvento> tiposEventos = new HashSet<>();
        tiposEventos.add(TipoEvento.CHUTE_A_GOL_DEFENDIDO);
        tiposEventos.add(TipoEvento.DESARME_FALTA);
        return tiposEventos;
    }

    private static Localidade buildLocal() {
        Localidade localidade = Localidade.create()
                .setId("1")
                .setDescricao("Curitiba")
                .setLatitude(0.0)
                .setLongitude(0.0)
                .commit();
        return localidade;
    }

    public static Time buildTime(){
        Set<Jogador> jogadores = new LinkedHashSet<Jogador>();
        Jogador jogador = Jogador.create().setId("1111").setNome("Robisverson").setEventos(buildListaEventos()).commit();
        jogadores.add(jogador);
        Time time = Time.create().setId("NNNN").setCor("FF0000").setJogadores(jogadores).setNome("Atretico").commit();
        return time;
    }

    private static Time buildTime2(){
        Set<Jogador> jogadores = new LinkedHashSet<Jogador>();
        Jogador jogador2 = buildJogador();
        Jogador jogador3 = Jogador.create().setId("3333").setNome("Robisverson").setEventos(buildListaEventos3()).commit();
        Jogador jogador4 = Jogador.create().setId("4444").setNome("Robisverson").setEventos(buildListaEventos4()).commit();
        jogadores.add(jogador2);
        jogadores.add(jogador3);
        jogadores.add(jogador4);
        Time time = Time.create().setId("MMMM").setCor("00FF00").setJogadores(jogadores).setNome("Parnaense").commit();
        return time;
    }

    public static Jogador buildJogador() {
        return Jogador.create().setId("2222").setNome("Claudinever").setEventos(buildListaEventos2()).commit();
    }

    private static List<EventoJogo> buildListaEventos(){
        EventoJogo eventoJogo = buildEventoJogo();
        List<EventoJogo> eventos = new ArrayList<>();
        eventos.add(eventoJogo);
        EventoJogo eventoJogo2 = EventoJogo.create()
                .setId("BBB")
                .setTipoEvento(TipoEvento.CHUTE_A_GOL_GOL)
                .setHora(new Date())
                .commit();
        eventos.add(eventoJogo2);
        return eventos;
    }

    private static List<EventoJogo> buildListaEventos2(){
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

    private static List<EventoJogo> buildListaEventos3(){
        EventoJogo eventoJogo = EventoJogo.create()
                .setId("FDF")
                .setTipoEvento(TipoEvento.CHUTE_A_GOL_FORA)
                .setHora(new Date())
                .commit();
        List<EventoJogo> eventos = new ArrayList<EventoJogo>();
        eventos.add(eventoJogo);
        EventoJogo eventoJogo2 = EventoJogo.create()
                .setId("LOL")
                .setTipoEvento(TipoEvento.DRIBLE_CERTO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoJogo2);
        return eventos;
    }

    private static List<EventoJogo> buildListaEventos4(){
        EventoJogo eventoJogo = EventoJogo.create()
                .setId("LAP")
                .setTipoEvento(TipoEvento.CHUTE_A_GOL_FORA)
                .setHora(new Date())
                .commit();
        List<EventoJogo> eventos = new ArrayList<EventoJogo>();
        eventos.add(eventoJogo);
        EventoJogo eventoJogo2 = EventoJogo.create()
                .setId("TAAW")
                .setTipoEvento(TipoEvento.DRIBLE_CERTO)
                .setHora(new Date())
                .commit();
        eventos.add(eventoJogo2);
        return eventos;
    }


    public static EventoJogo buildEventoJogo(){
        EventoJogo eventoJogo = EventoJogo.create()
                .setId("AAA")
                .setTipoEvento(TipoEvento.DESARME_FALTA)
                .setHora(new Date())
                .commit();
        return eventoJogo;

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
                .setId("LOC1")
                .setDescricao("Parnaiba")
                .setLatitude(0.0)
                .setLongitude(1.5)
                .commit();
        return localidade;
    }

}
