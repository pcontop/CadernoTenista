package br.com.pcontop.CadernoOlheiro.model.importer.test.xml;

import org.junit.Test;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.br.com.pcontop.CadernoOlheiro.bean.test.PartidaHelper;
import br.com.pcontop.CadernoOlheiro.model.xml.XmlConverter;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pcont_000 on 25/07/2015.
 */
public class TestPartidaToObject {
    @Test
    public void partidaToObject(){
        Partida partida = PartidaHelper.buildPartida();
        assertNotNull(partida);
        String xmlTexto = XmlConverter.toXml(partida);
        assertNotNull(xmlTexto);
    }
}
