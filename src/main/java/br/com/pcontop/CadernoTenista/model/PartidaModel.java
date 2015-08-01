package br.com.pcontop.CadernoTenista.model;

import android.content.Context;

import java.util.Date;
import java.util.List;

import br.com.pcontop.CadernoTenista.bean.EventoPartida;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Localidade;
import br.com.pcontop.CadernoTenista.bean.Olheiro;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TempoPartida;
import br.com.pcontop.CadernoTenista.bean.TiposEvento;
import br.com.pcontop.CadernoTenista.bean.TipoTempoPartida;
import br.com.pcontop.CadernoTenista.model.dao.DAOFactory;
import br.com.pcontop.CadernoTenista.model.dao.eventoJogo.EventoJogoDAO;
import br.com.pcontop.CadernoTenista.model.dao.jogador.JogadorDAO;
import br.com.pcontop.CadernoTenista.model.dao.localidade.eventoJogo.LocalidadeDAO;
import br.com.pcontop.CadernoTenista.model.dao.partida.PartidaDAO;
import br.com.pcontop.CadernoTenista.model.export.Exporter;
import br.com.pcontop.CadernoTenista.model.export.ExporterException;
import br.com.pcontop.CadernoTenista.model.export.ExporterFactory;
import br.com.pcontop.CadernoTenista.view.ColorConstants;
import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class PartidaModel {
    Context context;
    JogadorDAO jogadorDAO;
    PartidaDAO partidaDAO;
    EventoJogoDAO eventoJogoDAO;
    LocalidadeDAO localidadeDAO;


    private Partida partidaAtual;
    public PartidaModel(Context context){
        this.context=context;
        this.jogadorDAO = DAOFactory.getJogadorDAO(context);
        this.eventoJogoDAO = DAOFactory.getEventoJogoDAO(context);
        this.partidaDAO = DAOFactory.getPartidaDAO(context);
        this.localidadeDAO=DAOFactory.getLocalidadeDAO(context);
    }

    public Partida criePartida(Localidade local, Olheiro olheiro){
        Jogador jogador1 = crieJogador(null, ColorConstants.COR_PADRAO_JOGADOR_1_AVAI);
        TempoPartida tempoPartida = crieTempoPartida(TipoTempoPartida.ANTES_PARTIDA, new Date());

        Partida partida = Partida.create()
                                 .setLocal(local)
                                 .setOlheiro(olheiro)
                                 .setJogador1(jogador1)
                                 .setTempoPartida(tempoPartida)
                                 .commit();

        partida.getTiposEventosSelecionados().add(TiposEvento.BACKHAND_CERTO);
        partida.getTiposEventosSelecionados().add(TiposEvento.BACKHAND_ERRADO);
        setPartidaAtual(partida);
        return partida;
    }

    private TempoPartida crieTempoPartida(TipoTempoPartida tipoTempoPartida, Date dataInicio) {
        TempoPartida tempoPartida = TempoPartida.create()
                .setTipo(tipoTempoPartida)
                .setDataInicio(dataInicio)
                .commit();
        return tempoPartida;

    }

    private void setPartidaAtual(Partida partida) {
        this.partidaAtual = partida;
    }

    public Jogador crieJogador(String nome, String cor) {
        Jogador jogador = Jogador.create()
                .setNome(nome)
                .setCor(cor)
                .commit()
                ;
        return jogador;
    }

    public Partida getPartida(String id)  {
        return partidaDAO.get(id);
    }

    public void insiraOuAtualize(Partida partida) {
        setPartidaAtual(partida);
        partidaDAO.insiraOuAtualize(partida);
    }

    public void remova(Partida partida) {
        partidaDAO.remova(partida);
        for (Jogador jogador:partida.getJogadores()) {
            jogadorDAO.remova(jogador);
            for (EventoPartida eventoPartida :jogador.getEventos()){
                eventoJogoDAO.remova(eventoPartida);
            }
        }
    }

    public Jogador getJogador(String id) {
        return jogadorDAO.get(id);
    }

    public void insiraOuAtualize(EventoPartida eventoPartida) {
        insiraOuAtualize(partidaAtual);
    }

    public void insiraOuAtualize(Jogador jogador) {
        insiraOuAtualize(partidaAtual);
    }


    public void crieEventoEInsira(TiposEvento tiposEvento, Jogador jogador, Date hora){
        EventoPartida eventoPartida = crieEvento(tiposEvento, hora);
        jogador.getEventos().add(eventoPartida);
        insiraOuAtualize(eventoPartida);
    }

    public EventoPartida crieEvento(TiposEvento tiposEvento, Date hora){
            EventoPartida eventoPartida = EventoPartida.create()
                    .setTipoEvento(tiposEvento)
                    .setHora(hora)
                    .commit();
            return eventoPartida;
    }

    public Olheiro getOlheiro(){
        //TODO - Implementar regras olheiro.
        return null;
    }


    public void addJogadorPartida(Jogador jogador) {
        partidaAtual.setJogador2(jogador);
        insiraOuAtualize(partidaAtual);
    }

    public int getCor(Jogador jogador) {
        return jogador.getCorAsInt();
    }



    public List<Jogador> getJogadoresPartida() {
        return partidaAtual.getJogadores();
    }

    public Partida getPartidaAtual(){
        return partidaAtual;
    }

    public void recuperePartida(String idPartida) {
        Partida partida =  getPartida(idPartida);
        setPartidaAtual(partida);
    }

    public void salvePartida() {
        insiraOuAtualize(partidaAtual);
    }

    public List<Partida> getPartidasPassadas() {
        List<Partida> partidas = partidaDAO.getAllDataInicioNotEmpty();
        return partidas;
    }

    public void releaseResources() {
        partidaDAO.removaPartidasNaoIniciadasMenosAtual();
        //ODBProvider.closeConnection();
    }

    public int getQuantidadeEventos(Jogador jogador, TiposEvento tiposEventoPassar) {
        return jogador.busqueEventosdoTipo(tiposEventoPassar).size();
    }

    public boolean exportePartida(Partida partida) throws ExporterException {
        Exporter exporter = ExporterFactory.getInstance();
        if (exporter.export(context, partida)){
            insiraOuAtualize(partida);
            return true;
        }
        return false;
    }

    public Localidade definaLocalidade(String descricao, Double latitude, Double longitude) {
        Localidade localidade = localidadeDAO.getPorDescricao(descricao);
        if (localidade==null){
            localidade = getNewLocalidade(descricao, latitude, longitude);
        }
        partidaAtual.setLocal(localidade);
        salvePartida();
        return localidade;
    }

    private Localidade getNewLocalidade(String descricao, Double latitude, Double longitude) {
        Localidade localidade = Localidade.create()
                .setDescricao(descricao)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .commit();
        return localidade;
    }

    public void transiteProximoTempoPartida(Date date) {
        partidaAtual.transiteProximoTempo(date);
    }

    public void removaJogador(Jogador jogador) {
        partidaAtual.remova(jogador);
        salvePartida();
    }

    public int getCorJogador1() {
        return partidaAtual.getJogador1().getCorAsInt();
    }

    public int getCorJogador2() {
        Jogador jogador2 = partidaAtual.getJogador2();
        if (jogador2!=null) {
            return partidaAtual.getJogador2().getCorAsInt();
        }
        return 0xff000000 + Integer.parseInt(Color.AQUAMARINE.toString(),16);
    }
}
