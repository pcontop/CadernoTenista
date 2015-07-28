package br.com.pcontop.CadernoOlheiro.bean;


import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class Partida implements Comparable<Partida>, Serializable {
    private String id;
    private Olheiro olheiro;
    private Localidade local;
    private Jogador jogador1;
    private Jogador jogador2;
    private Date dataCriacao;
    private Date ultimoEnvio;
    private SetTiposEventos tiposEventosSelecionados;
    private String pathVideoPrimeiroTempo;
    private String pathVideoSegundoTempo;
    private TempoPartida tempoPartida;
    private ListTemposPartida temposPartida = new ListTemposPartida();

    private Partida(){
        dataCriacao = new Date();
    }

    @Override
    public String toString(){
        String retorno="Partida ["
                + "Id: " + id
                + ", " + olheiro
                + ", " + local
                + ", " + jogador1
                + ", " + jogador2
                + ", Data Criação: " + dataCriacao
                + ", Data Último Envio: " + ultimoEnvio
                + ", Tempo Atual: " + tempoPartida
                + ", Tempos Passados: " + temposPartida
                + ", TiposEventosSelecionados: " + tiposEventosSelecionados
                + "]";
        return retorno;
    }

    public void setJogador1(Jogador jogador){
        jogador1 = jogador;
    }

    public void setJogador2(Jogador jogador){
        jogador2 = jogador;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public static Builder create(){
        return new Builder();
    }

    public List<EventoPartida> getEventos() {
        List<EventoPartida> eventoPartidaList = new ArrayList<>();
        eventoPartidaList.addAll(jogador1.getEventos());
        eventoPartidaList.addAll(jogador2.getEventos());
        return eventoPartidaList;
    }

    @Override
    public int compareTo(Partida o) {
        return dataCriacao.compareTo(o.dataCriacao);
    }

    public void transiteProximoTempo(Date date) {
        tempoPartida.setDataFim(date);
        TempoPartida proximoTempo = TempoPartidaFactory.getProximoTempo(tempoPartida, date);
        setTempoPartida(proximoTempo);
    }

    public static class Builder {
        Partida partida = new Partida();

        public Builder setOlheiro(Olheiro olheiro) {
            partida.olheiro = olheiro;
            return this;
        }

        public Builder setLocal(Localidade local) {
            partida.local = local;
            return this;
        }

        public Builder setJogador1(Jogador jogador1) {
            partida.jogador1 = jogador1;
            return this;
        }

        public Builder setJogador2(Jogador jogador2) {
            partida.jogador2 = jogador2;
            return this;
        }

        public Builder setTiposEventosSelecionados(Set<TiposEvento> tiposEventosSelecionados) {
            partida.tiposEventosSelecionados = new SetTiposEventos(tiposEventosSelecionados);
            return this;
        }

        public Builder setDataCriacao(Date dataCriacao){
            partida.dataCriacao = dataCriacao;
            return this;
        }

        public Builder setPathVideoPrimeiroTempo(String pathVideoPrimeiroTempo) {
            partida.pathVideoPrimeiroTempo = pathVideoPrimeiroTempo;
            return this;
        }

        public Builder setPathVideoSegundoTempo(String pathVideoSegundoTempo) {
            partida.pathVideoSegundoTempo = pathVideoSegundoTempo;
            return this;
        }

        public Builder setTemposPartida(ListTemposPartida temposPartida) {
            partida.temposPartida=temposPartida;
            return this;
        }

        public Builder setTempoPartida(TempoPartida tempoPartida){
            partida.tempoPartida=tempoPartida;
            return this;
        }

        public Partida commit(){
            if (partida.getTemposPartida().size()==0 && partida.getTempoPartida()!=null){
                partida.getTemposPartida().add(partida.getTempoPartida());
            }
            partida.setId(UUIDProvider.getNew());
            return partida;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partida partida = (Partida) o;

        if (id != null ? !id.equals(partida.id) : partida.id != null) return false;

        return true;
    }

    private int hashCodeCalc(int result, Object o){
        if (o !=null){
            return 31 * result + o.hashCode();
        } else {
            return result;
        }
    }

    @Override
    public int hashCode() {
        int result=17;
        result = hashCodeCalc(result, id);

        return result;
    }

    public String getId() {
        return id;
    }

    public Olheiro getOlheiro() {
        return olheiro;
    }

    public Localidade getLocal() {
        return local;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public Set<TiposEvento> getTiposEventosSelecionados() {
        return tiposEventosSelecionados;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOlheiro(Olheiro olheiro) {
        this.olheiro = olheiro;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public String getPathVideoPrimeiroTempo() {
        return pathVideoPrimeiroTempo;
    }

    public void setPathVideoPrimeiroTempo(String pathVideoPrimeiroTempo) {
        this.pathVideoPrimeiroTempo = pathVideoPrimeiroTempo;
    }

    public String getPathVideoSegundoTempo() {
        return pathVideoSegundoTempo;
    }

    public void setPathVideoSegundoTempo(String pathVideoSegundoTempo) {
        this.pathVideoSegundoTempo = pathVideoSegundoTempo;
    }

    public Date getUltimoEnvio() {
        return ultimoEnvio;
    }

    public void setUltimoEnvio(Date ultimoEnvio) {
        this.ultimoEnvio = ultimoEnvio;
    }

    public TempoPartida getTempoPartida() {
        return tempoPartida;
    }

    public void setTempoPartida(TempoPartida tempoPartida) {
        temposPartida.add(this.tempoPartida);
        this.tempoPartida = tempoPartida;
    }

    public ListTemposPartida getTemposPartida() {
        return temposPartida;
    }

    public TiposTempoPartida getTipoTempoPartida(){
        if (tempoPartida==null){
            return null;
        }
        return tempoPartida.getTipoTempoPartida();
    }

    public String getDescricaoTempoAtual(){
        if (tempoPartida==null||tempoPartida.getTipoTempoPartida()==null){
            return null;
        }
        return tempoPartida.getTipoTempoPartida().getDescricao();
    }

    public List<Jogador> getJogadores(){
        List<Jogador> jogadores = new ArrayList<>();
        if (jogador1!=null){
            jogadores.add(jogador1);
        }
        if (jogador2!=null){
            jogadores.add(jogador2);
        }
        return jogadores;
    }

}
