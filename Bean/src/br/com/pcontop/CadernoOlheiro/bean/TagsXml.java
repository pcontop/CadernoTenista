package br.com.pcontop.CadernoOlheiro.bean;

/**
 * Created by PauloBruno on 25/01/14.
 */
public enum TagsXml {
    PARTIDA("PARTIDA"),
    INICIO_PARTIDA ("INICIO_PARTIDA"),
    FIM_PRIMEIRO_TEMPO ("FIM_PRIMEIRO_TEMPO"),
    INICIO_SEGUNDO_TEMPO ("INICIO_SEGUNDO_TEMPO"),
    FIM_PARTIDA ("FIM_PARTIDA"),
    ID ("ID"),
    NOME ("NOME"),
    COR("COR"),
    JOGADORES("JOGADORES"),
    TIME("TIME"),
    EVENTOS("EVENTOS"),
    JOGADOR("JOGADOR"),
    TIPO_EVENTO("TIPO_EVENTO"),
    EVENTO_JOGO("EVENTO_JOGO"),
    HORA("HORA"),
    LATITUDE("LATITUDE"),
    LONGITUDE("LONGITUDE"),
    LOCALIDADE("LOCALIDADE"),
    DESCRICAO("DESCRICAO"),
    CRIACAO_PARTIDA("CRIACAO_PARTIDA"),
    TIMES("TIMES")
    ;



    private String descritor;

    TagsXml(String descritor){
        this.descritor = descritor;
    }

    public String getDescritor(){
        return descritor;
    }
}
