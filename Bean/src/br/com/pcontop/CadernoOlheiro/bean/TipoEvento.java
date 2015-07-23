package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public enum TipoEvento implements Serializable {
    BACKHAND_CERTO("jogador_backhand_certo", QualificadorJogada.BOA),
    BACKHAND_ERRADO("jogador_backhand_errado", QualificadorJogada.RUIM),
    ACE_CERTO("jogador_ace_certo", QualificadorJogada.BOA),
    ACE_ERRADO("jogador_ace_errado", QualificadorJogada.RUIM),

    CHUTE_A_GOL_GOL("jogador_chute_a_gol_gol", QualificadorJogada.BOA),
    LANCAMENTO_CERTO("jogador_lancamento_certo", QualificadorJogada.BOA),
    LANCAMENTO_ERRADO("jogador_lancamento_errado", QualificadorJogada.RUIM),
    DRIBLE_CERTO("jogador_drible_certo", QualificadorJogada.BOA),
    DRIBLE_ERRADO("jogador_drible_errado", QualificadorJogada.RUIM),
    DESARME_RECUPERACAO ("jogador_desarme_recuperacao", QualificadorJogada.BOA),
    DESARME_FALTA("jogador_desarme_falta", QualificadorJogada.RUIM),
    DESARME_TENTATIVA("jogador_desarme_tentativa", QualificadorJogada.NEUTRA),
    INTERCEPTACAO_CERTA("jogador_interceptacao_certa", QualificadorJogada.BOA),
    INTERCEPTACAO_ERRADA("jogador_interceptacao_errada", QualificadorJogada.RUIM),
    FALTA_SOFRIDA("jogador_falta_sofrida", QualificadorJogada.NEUTRA)
    ;

    private final String descricao;
    private final QualificadorJogada qualificadorJogada;

    public String getDescricao(){
        return descricao;
    }

    public QualificadorJogada getQualificadorJogada(){
        return qualificadorJogada;
    }

    TipoEvento(String descricao, QualificadorJogada qualificadorJogada){
        this.descricao = descricao;
        this.qualificadorJogada = qualificadorJogada;
    }

}
