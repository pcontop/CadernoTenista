package br.com.pcontop.CadernoOlheiro.model;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.*;
import br.com.pcontop.CadernoOlheiro.model.dao.DAOFactory;
import br.com.pcontop.CadernoOlheiro.model.dao.UUIDProvider;
import br.com.pcontop.CadernoOlheiro.model.dao.eventoJogo.EventoJogoDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.jogador.JogadorDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.localidade.eventoJogo.LocalidadeDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.partida.PartidaDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.time.TimeDAO;
import br.com.pcontop.CadernoOlheiro.model.export.Exporter;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterFactory;
import br.com.pcontop.CadernoOlheiro.view.ColorConstants;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class JogoModel {
    Context context;
    JogadorDAO jogadorDAO;
    TimeDAO timeDAO;
    PartidaDAO partidaDAO;
    EventoJogoDAO eventoJogoDAO;
    LocalidadeDAO localidadeDAO;



    private Partida partidaAtual;
    public JogoModel(Context context){
        this.context=context;
        this.jogadorDAO = DAOFactory.getJogadorDAO(context);
        this.timeDAO = DAOFactory.getTimeDAO(context);
        this.eventoJogoDAO = DAOFactory.getEventoJogoDAO(context);
        this.partidaDAO = DAOFactory.getPartidaDAO(context);
        this.localidadeDAO=DAOFactory.getLocalidadeDAO(context);
    }

    public Partida criePartida(Localidade local, Olheiro olheiro){
        Time time1 = crieTime(Time.TIME_1, ColorConstants.COR_PADRAO_TIME_1_AVAI);
        Time time2 = crieTime(Time.TIME_2, ColorConstants.COR_PADRAO_TIME_2_AVAI);

        Partida partida = Partida.create()
                                 .setId(getUniqueId())
                                 .setLocal(local)
                                 .setOlheiro(olheiro)
                                 .setTime1(time1)
                                 .setTime2(time2)
                                 .commit();

        partida.getTiposEventosSelecionados().add(TipoEvento.PASSE_CERTO);
        partida.getTiposEventosSelecionados().add(TipoEvento.PASSE_ERRADO);
        setPartidaAtual(partida);
        return partida;
    }

    private void setPartidaAtual(Partida partida) {
        this.partidaAtual = partida;
    }

    public Time crieTime(String nome, String cor) {
        Time time = Time.create()
                    .setId(getUniqueId())
                    .setNome(nome)
                    .setCor(cor)
                    .commit();
        return time;
    }

    public Jogador crieJogador(String nome) {
        Jogador jogador = Jogador.create()
        .setId(getUniqueId())
        .setNome(nome).commit();
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
        timeDAO.remova(partida.getTime1());
        timeDAO.remova(partida.getTime2());
        for (Jogador jogador:partida.getJogadores()) {
            jogadorDAO.remova(jogador);
            for (EventoJogo eventoJogo:jogador.getEventos()){
                eventoJogoDAO.remova(eventoJogo);
            }
        }
    }

    public Jogador getJogador(String id) {
        return jogadorDAO.get(id);
    }

    public void insiraOuAtualize(EventoJogo eventoJogo) {
        insiraOuAtualize(partidaAtual);
    }

    public void insiraOuAtualize(Jogador jogador) {
        insiraOuAtualize(partidaAtual);
    }

    public void remova(Jogador jogador){
        partidaAtual.removaJogador(jogador);
        insiraOuAtualize(partidaAtual);
        jogadorDAO.remova(jogador);
    }


    public void crieEventoEInsira(TipoEvento tipoEvento, Jogador jogador, Date hora){
        EventoJogo eventoJogo = crieEvento(tipoEvento,hora);
        jogador.getEventos().add(eventoJogo);
        insiraOuAtualize(eventoJogo);
    }

    public EventoJogo crieEvento(TipoEvento tipoEvento, Date hora){
            EventoJogo eventoJogo = EventoJogo.create()
                    .setTipoEvento(tipoEvento)
                    .setHora(hora)
                    .setId(getUniqueId())
                    .commit();
            return eventoJogo;
    }

    public Time getTime(String id) {
        return timeDAO.get(id);
    }

    public String getUniqueId(){
        return UUIDProvider.getNew();
    }

    public Olheiro getOlheiro(){
        //TODO - Implementar regras olheiro.
        return null;
    }


    public void addJogadorTime(Jogador jogador, Time time) {
        partidaAtual.addJogadorTime(jogador, time);
        insiraOuAtualize(partidaAtual);
    }

    public int getCorTime(Jogador jogador) {
        return getCorTime(partidaAtual, jogador);
    }

    public int getCorTime(Partida partida, Jogador jogador) {
        Time time = partida.getTime(jogador);
        return time.getCorAsInt();
    }


    public Time getTime1(){
        return partidaAtual.getTime1();
    }
    public Time getTime2(){
        return partidaAtual.getTime2();
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

    public int getQuantidadeEventos(Jogador jogador, TipoEvento tipoEventoPassar) {
        return jogador.busqueEventosdoTipo(tipoEventoPassar).size();
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
                .setId(getUniqueId())
                .setLatitude(latitude)
                .setLongitude(longitude)
                .commit();
        return localidade;
    }

}
