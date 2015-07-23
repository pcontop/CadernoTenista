package br.com.pcontop.CadernoOlheiro.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:35
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PartidaTransf implements Serializable {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Olheiro olheiro;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Localidade local;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Jogador jogador1;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    private PartidaTransf(){
    }

    public PartidaTransf(Partida partida){
        this.id =partida.getId();
        this.dataCriacao = partida.getDataCriacao();
        this.dataInicio = partida.getDataInicio();
        this.dataFimPrimeiroTempo = partida.getDataFimPrimeiroTempo();
        this.dataInicioSegundoTempo = partida.getDataInicioSegundoTempo();
        this.dataFimSegundoTempo = partida.getDataFimSegundoTempo();
        this.jogador1 = partida.getJogador1();
        this.jogador2 = partida.getJogador2();
        this.olheiro = partida.getOlheiro();
        this.local = partida.getLocal();
        this.pathVideoPrimeiroTempo = partida.getPathVideoPrimeiroTempo();
        this.pathVideoSegundoTempo = partida.getPathVideoSegundoTempo();
        this.ultimoEnvio = partida.getUltimoEnvio();

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartidaTransf partida = (PartidaTransf) o;

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

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
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
