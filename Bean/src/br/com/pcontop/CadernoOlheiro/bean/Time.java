package br.com.pcontop.CadernoOlheiro.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:38
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Time implements Serializable {
    @Id
    private String id;
    private String nome;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Jogador> jogadores;
    private String cor;

    @Transient
    public final static String TIME_1="time 1";
    @Transient
    public final static String TIME_2="time 2";

    private Time(){

    }

    public static Builder create(){
        return new Builder();
    }

    @JsonIgnore
    public int getCorAsInt(){
        int cor = 0xff000000 + Integer.parseInt(this.cor,16);
        return cor;
    }

    public List<EventoJogo> getEventos() {
        List<EventoJogo> eventosJogo = new ArrayList<>();
        for (Jogador jogador:jogadores){
            eventosJogo.addAll(jogador.getEventos());
        }
        return eventosJogo;
    }


    public static class Builder {
        private String id;
        private String nome;
        private Set<Jogador> jogadores = new LinkedHashSet<>();
        private String cor;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setJogadores(Set<Jogador> jogadores) {
            this.jogadores = jogadores;
            return this;
        }

        public Builder setCor(String cor) {
            this.cor = cor;
            return this;
        }

        public Time commit(){
            Time time = new Time();
            time.id=this.id;
            time.cor=this.cor;
            time.nome=this.nome;
            time.jogadores=this.jogadores;
            return time;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (id != null ? !id.equals(time.id) : time.id != null) return false;

        return true;
    }

    @Override
    public String toString() {
        String result = "Time ["
                + "id: " + id
                + ", nome: " + nome
                + ", jogadores: " + jogadores
                + ", cor: " + cor
                + "]";
        return result;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Set<Jogador> getJogadores() {
        return jogadores;
    }

    public String getCor() {
        return cor;
    }
}
