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
    private Time time1;
    private Time time2;
    private final Date dataCriacao;
    private Date dataInicio;
    private Date dataFimPrimeiroTempo;
    private Date dataInicioSegundoTempo;
    private Date dataFimSegundoTempo;
    private Date ultimoEnvio;
    private SetTipoEvento tiposEventosSelecionados;
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
                + ", " + time1
                + ", " + time2
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

    public void addJogadorTime(Jogador jogador, Time time){
        if (time.equals(time1)){
            addJogadorTime1(jogador);
        }
        if (time.equals(time2)){
            addJogadorTime2(jogador);
        }
    }

    public void addJogadorTime1(Jogador jogador){
        if (time2.getJogadores().contains(jogador)){
            time2.getJogadores().remove(jogador);
        }
        time1.getJogadores().add(jogador);
    }

    public void addJogadorTime2(Jogador jogador){
        if (time1.getJogadores().contains(jogador)){
            time1.getJogadores().remove(jogador);
        }
        time2.getJogadores().add(jogador);
    }

    public void removaJogador(Jogador jogador){
        time1.getJogadores().remove(jogador);
        time2.getJogadores().remove(jogador);
    }

    public List<Jogador> getJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.addAll(time1.getJogadores());
        jogadores.addAll(time2.getJogadores());
        return jogadores;
    }

    public Time getTime(Jogador jogador){
        if (time1.getJogadores().contains(jogador)){
            return time1;
        }
        if (time2.getJogadores().contains(jogador)){
            return time2;
        }
        return null;
    }

    public static Builder create(){
        return new Builder();
    }

    public List<EventoJogo> getEventos() {
        List<EventoJogo> eventoJogoList = new ArrayList<>();
        eventoJogoList.addAll(time1.getEventos());
        eventoJogoList.addAll(time2.getEventos());
        return eventoJogoList;
    }

    @Override
    public int compareTo(Partida o) {
        return dataCriacao.compareTo(o.dataCriacao);
    }

    public Jogador busqueJogadorPorId(String idJogador) {
        List<Jogador> jogadores = getJogadores();
        for (Jogador jogador: jogadores){
            if (jogador.getId().equals(idJogador)){
                return jogador;
            }
        }
        return null;
    }


    public static class Builder {
        private String id;
        private Olheiro olheiro;
        private Localidade local;
        private Time time1;
        private Time time2;
        private Date dataInicio;
        private Date dataFimPrimeiroTempo;
        private Date dataInicioSegundoTempo;
        private Date dataFimSegundoTempo;
        private Date dataCriacao;
        private Set<TipoEvento> tiposEventosSelecionados = new HashSet<>();
        private String pathVideoPrimeiroTempo;
        private String pathVideoSegundoTempo;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setOlheiro(Olheiro olheiro) {
            this.olheiro = olheiro;
            return this;
        }

        public Builder setLocal(Localidade local) {
            this.local = local;
            return this;
        }

        public Builder setTime1(Time time1) {
            this.time1 = time1;
            return this;
        }

        public Builder setTime2(Time time2) {
            this.time2 = time2;
            return this;
        }

        public Builder setDataInicio(Date dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        public Builder setDataFimPrimeiroTempo(Date dataFimPrimeiroTempo) {
            this.dataFimPrimeiroTempo = dataFimPrimeiroTempo;
            return this;
        }

        public Builder setDataInicioSegundoTempo(Date dataInicioSegundoTempo) {
            this.dataInicioSegundoTempo = dataInicioSegundoTempo;
            return this;
        }

        public Builder setDataFimSegundoTempo(Date dataFimSegundoTempo) {
            this.dataFimSegundoTempo = dataFimSegundoTempo;
            return this;
        }

        public Builder setTiposEventosSelecionados(Set<TipoEvento> tiposEventosSelecionados) {
            this.tiposEventosSelecionados = tiposEventosSelecionados;
            return this;
        }

        public Builder setDataCriacao(Date dataCriacao){
            this.dataCriacao = dataCriacao;
            return this;
        }

        public Builder setPathVideoPrimeiroTempo(String pathVideoPrimeiroTempo) {
            this.pathVideoPrimeiroTempo = pathVideoPrimeiroTempo;
            return this;
        }

        public Builder setPathVideoSegundoTempo(String pathVideoSegundoTempo) {
            this.pathVideoSegundoTempo = pathVideoSegundoTempo;
            return this;
        }

        public Partida commit(){
            Partida partida = new Partida(dataCriacao);
            partida.id=this.id;
            partida.olheiro=this.olheiro;
            partida.local=this.local;
            partida.time1=this.time1;
            partida.time2=this.time2;
            partida.dataInicio=this.dataInicio;
            partida.dataFimPrimeiroTempo=this.dataFimPrimeiroTempo;
            partida.dataInicioSegundoTempo=this.dataInicioSegundoTempo;
            partida.dataFimSegundoTempo=this.dataFimSegundoTempo;
            if (tiposEventosSelecionados!=null){
                SetTipoEvento setTiposEventosSelecionados = new SetTipoEvento();
                setTiposEventosSelecionados.addAll(tiposEventosSelecionados);
                partida.tiposEventosSelecionados =  setTiposEventosSelecionados;
            }
            partida.pathVideoPrimeiroTempo = pathVideoPrimeiroTempo;
            partida.pathVideoSegundoTempo = pathVideoSegundoTempo;
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

    public Time getTime1() {
        return time1;
    }

    public Time getTime2() {
        return time2;
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
