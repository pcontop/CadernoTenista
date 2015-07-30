package br.com.pcontop.CadernoTenista.model.export.xml.xml.test;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.br.com.pcontop.CadernoOlheiro.bean.test.PartidaHelper;
import br.com.pcontop.CadernoTenista.model.export.xml.xml.file.FileXmlExporter;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class TesteCriacaoXml {

    @Test
    public void TesteCriacao(){
        FileXmlExporter fileExporter = new FileXmlExporter();
        Partida partida = PartidaHelper.buildPartida();
        try {
            fileExporter.export(null, partida);
            File filePartida = fileExporter.getArquivoPartida(partida,null);
            assert(filePartida.exists());
            assert(filePartida.getName()!=null);
            assert(filePartida.getName().endsWith(".xml"));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
