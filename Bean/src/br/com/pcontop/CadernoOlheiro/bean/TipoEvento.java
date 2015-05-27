package br.com.pcontop.CadernoOlheiro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 28/10/13
 * time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public enum TipoEvento implements Serializable {
    PASSE_CERTO("jogador_passe_certo", QualificadorJogada.BOM),
    PASSE_ERRADO("jogador_passe_errado", QualificadorJogada.MAL),
    CHUTE_A_GOL_DEFENDIDO("jogador_chute_a_gol_defendido", QualificadorJogada.NEUTRO),
    CHUTE_A_GOL_FORA("jogador_chute_a_gol_fora", QualificadorJogada.MAL),
    CHUTE_A_GOL_GOL("jogador_chute_a_gol_gol", QualificadorJogada.BOM),
    LANCAMENTO_CERTO("jogador_lancamento_certo", QualificadorJogada.BOM),
    LANCAMENTO_ERRADO("jogador_lancamento_errado", QualificadorJogada.MAL),
    DRIBLE_CERTO("jogador_drible_certo", QualificadorJogada.BOM),
    DRIBLE_ERRADO("jogador_drible_errado", QualificadorJogada.MAL),
    DESARME_RECUPERACAO ("jogador_desarme_recuperacao", QualificadorJogada.BOM),
    DESARME_FALTA("jogador_desarme_falta", QualificadorJogada.MAL),
    DESARME_TENTATIVA("jogador_desarme_tentativa", QualificadorJogada.NEUTRO),
    INTERCEPTACAO_CERTA("jogador_interceptacao_certa", QualificadorJogada.BOM),
    INTERCEPTACAO_ERRADA("jogador_interceptacao_errada", QualificadorJogada.MAL),
    FALTA_SOFRIDA("jogador_falta_sofrida", QualificadorJogada.NEUTRO)
    ;

    private static List<TipoEvento> listaTiposEventosJogadores;

    static {
        listaTiposEventosJogadores = new ArrayList<>();
        for (TipoEvento tipoEvento:TipoEvento.values()){
            if (tipoEvento.getDescricao().startsWith("jogador_")) {
                listaTiposEventosJogadores.add(tipoEvento);
            }
        }
    }

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
