package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public enum TiposEvento implements Serializable {
    BACKHAND_CERTO("jogador_backhand_certo", QualificadorJogada.BOA),
    BACKHAND_ERRADO("jogador_backhand_errado", QualificadorJogada.RUIM),
    ACE_CERTO("jogador_ace_certo", QualificadorJogada.BOA),
    ACE_ERRADO("jogador_ace_errado", QualificadorJogada.RUIM)
    ;

    private final String descricao;
    private final QualificadorJogada qualificadorJogada;

    public String getDescricao(){
        return descricao;
    }

    public QualificadorJogada getQualificadorJogada(){
        return qualificadorJogada;
    }

    TiposEvento(String descricao, QualificadorJogada qualificadorJogada){
        this.descricao = descricao;
        this.qualificadorJogada = qualificadorJogada;
    }

}
