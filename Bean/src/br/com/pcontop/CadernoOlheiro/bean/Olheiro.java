package br.com.pcontop.CadernoOlheiro.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Olheiro implements Serializable {
    @Id
    private String id;
    private String nome;
    private String login;
    private String senha;

    public static Builder create(){
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String nome;
        private String login;
        private String senha;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }
        public Builder setSenha(String senha) {
            this.senha = senha;
            return this;
        }

        public Olheiro commit(){
            Olheiro olheiro = new Olheiro();
            olheiro.id=this.id;
            olheiro.nome=this.nome;
            olheiro.login=this.login;
            olheiro.senha=this.senha;
            return olheiro;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Olheiro olheiro = (Olheiro) o;

        if (id != null ? !id.equals(olheiro.id) : olheiro.id != null) return false;

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

    @Override
    public String toString() {
        String result = "Olheiro ["
                + "id: " + id
                + "nome: " + nome
                + "login: " + login
                + "senha: " + senha
                +"]";
        return result;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
