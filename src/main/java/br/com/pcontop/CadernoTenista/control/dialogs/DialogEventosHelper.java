package br.com.pcontop.CadernoTenista.control.dialogs;

import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TipoEvento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by PauloBruno on 06/01/14.
 */
public class DialogEventosHelper {
    private DialogEventosHelper(){}

    public static Set<Integer> buildSelectedTiposEventos(boolean[] tiposEventos) {
        Set<Integer> selectedPositions = new HashSet<>();
        int i=0;
        for (boolean eventoSelecionado:tiposEventos){
            if (eventoSelecionado){
                selectedPositions.add(i);
            }
            i++;
        }
        return selectedPositions;
    }

    public static boolean[] getTiposEventosJogadorSelecionados(Partida partida) {
        List<Boolean> ativos = new ArrayList<>();
        for (TipoEvento evento: TipoEvento.values()){
            ativos.add(partida.getTiposEventosSelecionados().contains(evento));
        }
        boolean[] ativosArray = new boolean[ativos.size()];
        int i=0;
        for (Boolean ativo:ativos) {
            ativosArray[i]=ativo;
            i++;
        }
        return ativosArray;
    }

    public static boolean[] getTiposEventosJogadorSelecionados(Partida partida, Jogador jogador) {
        List<Boolean> ativos = new ArrayList<>();
        if (jogador.getTiposEventos()==null){
            return getTiposEventosJogadorSelecionados(partida);
        }
        for (TipoEvento evento: TipoEvento.values()){
            ativos.add(jogador.getTiposEventos().contains(evento));
        }
        boolean[] ativosArray = new boolean[ativos.size()];
        int i=0;
        for (Boolean ativo:ativos) {
            ativosArray[i]=ativo;
            i++;
        }
        return ativosArray;
    }

    public static void setTiposEventosSelecionadosPartida(Partida partida, Set<Integer> selectedTiposEventos){
        partida.getTiposEventosSelecionados().clear();
        for (Integer posicao:selectedTiposEventos){
            partida.getTiposEventosSelecionados().add(TipoEvento.values()[posicao]);
        }
    }

    public static void setTiposEventosSelecionadosPartida(Jogador jogador, Set<Integer> selectedTiposEventos){
        if (jogador.getTiposEventos()!=null){
            jogador.getTiposEventos().clear();
        } else {
            jogador.inicializeTiposEventos();
        }
        for (Integer posicao:selectedTiposEventos){
            jogador.getTiposEventos().add(TipoEvento.values()[posicao]);
        }
    }

}
