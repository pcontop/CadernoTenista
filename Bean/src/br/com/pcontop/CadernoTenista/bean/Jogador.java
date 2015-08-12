package br.com.pcontop.CadernoTenista.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:33
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Jogador implements Serializable {
    @Id
    private String id;
    private String nome;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EventoPartida> eventos = new ArrayList<>();
    private SetTiposEventos tiposEventos = new SetTiposEventos();
    private String cor;

    public Set<TipoEvento> getTiposEventos() {
        return tiposEventos;
    }

    public List<EventoPartida> getEventos() {
        return eventos;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId(){
        return id;
    }

    private Jogador(){

    }

    public String getCor() {
        return cor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jogador jogador = (Jogador) o;

        if (id != null ? !id.equals(jogador.id) : jogador.id != null) return false;

        return true;
    }

    public static Builder create(){
        return new Builder();
    }

    public List<EventoPartida> busqueEventosDoTipo(TipoEvento tipoEvento){
        List<EventoPartida> eventosDoTipo = new ArrayList<>();
        for (EventoPartida eventoPartida :eventos) {
            if (eventoPartida.getTipoEvento().equals(tipoEvento)){
                eventosDoTipo.add(eventoPartida);
            }
        }
        return eventosDoTipo;
    }

    public void inicializeTiposEventos() {
        this.tiposEventos = new SetTiposEventos();
    }

    public static class Builder {
        private Jogador jogador = new Jogador();

        public Builder setNome(String nome) {
            jogador.nome = nome;
            return this;
        }

        public Builder setEventos(List<EventoPartida> eventos) {
            jogador.eventos = eventos;
            return this;
        }

        public Builder setTiposEventos(Set<TipoEvento> tipoEventos){
            jogador.tiposEventos = new SetTiposEventos(tipoEventos);
            return this;
        }

        public Builder setCor(String cor){
            jogador.cor = cor;
            return this;
        }

        public Jogador commit(){
            jogador.id=UUIDProvider.getNew();
            return jogador;
        }

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

    @Override
    public String toString() {
        String result = "Jogador ["
                +"id: " + id
                +", Nome: " + nome
                +", Eventos: " + eventos
                +"]";
        return result;    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Retorna os eventos de jogo, filtrados pelo qualificador.
     * @param qualificadorJogada
     * @return
     */
    public List<EventoPartida> busqueEventos(QualificadorJogada qualificadorJogada){
        List<EventoPartida> eventosDoQualificador = new ArrayList<>();
        for (EventoPartida eventoPartida : eventos){
            if (eventoPartida.getTipoEvento().getQualificadorJogada().equals(qualificadorJogada)){
                eventosDoQualificador.add(eventoPartida);
            }
        }
        return eventosDoQualificador;
    }

}
