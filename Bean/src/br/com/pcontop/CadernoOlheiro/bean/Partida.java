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
    private Date dataInicio;
    private Date dataFimPrimeiroTempo;
    private Date dataInicioSegundoTempo;
    private Date dataFimSegundoTempo;
    private Date ultimoEnvio;
    private SetTiposEventos tiposEventosSelecionados;
    private String pathVideoPrimeiroTempo;
    private String pathVideoSegundoTempo;

    private Partida(){
        dataCriacao = new Date();
    }

    private Partida(Date dataCriacao){
        if (dataCriacao!=null){
            this.dataCriacao = dataCriacao;
        } else {
            this.dataCriacao = new Date();
        }
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
                + ", Data Início: " + dataInicio
                + ", Data Fim Primeiro Tempo: " + dataFimPrimeiroTempo
                + ", Data Início Segundo Tempo: " + dataInicioSegundoTempo
                + ", Data Fim Segundo Tempo: " + dataFimSegundoTempo
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

    public static class Builder {
        Partida partida = new Partida();

        public Builder setId(String id) {
            partida.id = id;
            return this;
        }

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

        public Builder setDataInicio(Date dataInicio) {
            partida.dataInicio = dataInicio;
            return this;
        }

        public Builder setDataFimPrimeiroTempo(Date dataFimPrimeiroTempo) {
            partida.dataFimPrimeiroTempo = dataFimPrimeiroTempo;
            return this;
        }

        public Builder setDataInicioSegundoTempo(Date dataInicioSegundoTempo) {
            partida.dataInicioSegundoTempo = dataInicioSegundoTempo;
            return this;
        }

        public Builder setDataFimSegundoTempo(Date dataFimSegundoTempo) {
            partida.dataFimSegundoTempo = dataFimSegundoTempo;
            return this;
        }

        public Builder setTiposEventosSelecionados(Set<TipoEvento> tiposEventosSelecionados) {
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

        public Partida commit(){
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFimPrimeiroTempo() {
        return dataFimPrimeiroTempo;
    }

    public Date getDataInicioSegundoTempo() {
        return dataInicioSegundoTempo;
    }

    public Date getDataFimSegundoTempo() {
        return dataFimSegundoTempo;
    }

    public Set<TipoEvento> getTiposEventosSelecionados() {
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

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFimPrimeiroTempo(Date dataFimPrimeiroTempo) {
        this.dataFimPrimeiroTempo = dataFimPrimeiroTempo;
    }

    public void setDataInicioSegundoTempo(Date dataInicioSegundoTempo) {
        this.dataInicioSegundoTempo = dataInicioSegundoTempo;
    }

    public void setDataFimSegundoTempo(Date dataFimSegundoTempo) {
        this.dataFimSegundoTempo = dataFimSegundoTempo;
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
}
