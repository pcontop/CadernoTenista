package br.com.pcontop.CadernoOlheiro.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<EventoJogo> eventos;
    private SetTipoEvento tiposEventos;

    public Set<TipoEvento> getTiposEventos() {
        return tiposEventos;
    }

    public List<EventoJogo> getEventos() {
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

    public List<EventoJogo> busqueEventosdoTipo(TipoEvento tipoEvento){
        List<EventoJogo> eventosDoTipo = new ArrayList<>();
        for (EventoJogo eventoJogo:eventos) {
            if (eventoJogo.getTipoEvento().equals(tipoEvento)){
                eventosDoTipo.add(eventoJogo);
            }
        }
        return eventosDoTipo;
    }

    public void inicializeTiposEventos() {
        this.tiposEventos = new SetTipoEvento();
    }

    public static class Builder {
        private String id;
        private String nome;
        private List<EventoJogo> eventos = new ArrayList<>();
        private SetTipoEvento tiposEventos;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setEventos(List<EventoJogo> eventos) {
            this.eventos = eventos;
            return this;
        }

        public Builder setTiposEventos(Set<TipoEvento> tipoEventos){
            this.tiposEventos = (SetTipoEvento) tipoEventos;
            return this;
        }

        public Jogador commit(){
            Jogador jogador = new Jogador();
            jogador.id=this.id;
            jogador.nome=this.nome;
            jogador.eventos=this.eventos;
            jogador.tiposEventos=this.tiposEventos;
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
    public List<EventoJogo> busqueEventos(QualificadorJogada qualificadorJogada){
        List<EventoJogo> eventosDoQualificador = new ArrayList<>();
        for (EventoJogo eventoJogo: eventos){
            if (eventoJogo.getTipoEvento().getQualificadorJogada().equals(qualificadorJogada)){
                eventosDoQualificador.add(eventoJogo);
            }
        }
        return eventosDoQualificador;
    }
}
