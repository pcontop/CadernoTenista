package br.com.pcontop.CadernoOlheiro.model.export.rest.test;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.br.com.pcontop.CadernoOlheiro.bean.test.PartidaHelper;
import org.junit.Test;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class TesteCriacaoRest {

    @Test
    public void testeJSON(){
        Partida partida = PartidaHelper.buildPartida();
    }

    @Test
    public void testeCriacao(){
    }
}
