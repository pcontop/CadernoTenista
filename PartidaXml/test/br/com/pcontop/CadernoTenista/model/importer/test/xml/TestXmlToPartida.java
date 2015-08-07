package br.com.pcontop.CadernoTenista.model.importer.test.xml;

import org.junit.Test;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.test.PartidaHelper;
import br.com.pcontop.CadernoTenista.model.xml.XmlConverter;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by pcont_000 on 26/07/2015.
 */
public class TestXmlToPartida {
    @Test
    public void testXmlToPartida(){
        Partida partidaInicial = PartidaHelper.buildPartida();
        String partidaXml = XmlConverter.toXml(partidaInicial);
        Partida partidaRecuperada = XmlConverter.fromXml(partidaXml);
        assertNotNull(partidaRecuperada);
        assertTrue(partidaRecuperada.equals(partidaInicial));
    }
}
