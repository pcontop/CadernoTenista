package br.com.pcontop.CadernoOlheiro.model.importer.test.xml;

import br.com.pcontop.CadernoOlheiro.bean.*;
import br.com.pcontop.CadernoOlheiro.model.importer.ImporterException;
import br.com.pcontop.CadernoOlheiro.model.importer.xml.XmlImporter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class XmlImportTest extends Assert {
    private final static String xmlImportacao="teste\\partida_Curitiba_20140125204510.xml";

    @Test
    public void TestImportacao(){
        XmlImporter xmlImporter = XmlImporter.getInstance();
        File file = new File(xmlImportacao);
        assert (file.exists());
        try {
            Partida partida = xmlImporter.leiaPartida(file);
            assertNotNull(partida);
            assertNotNull(partida.getId());
            assertNotNull(partida.getDataCriacao());
            assertNotNull(partida.getTime1());
            verifiqueAtributosTime(partida.getTime1());
            assertNotNull(partida.getTime2());
            assertNotNull(partida.getLocal());
            verifiqueLocal(partida.getLocal());
            verifiqueAtributosTime(partida.getTime2());
        } catch (ImporterException e) {
            e.printStackTrace();
            assert (false);
        }
    }

    private void verifiqueLocal(Localidade local) {
        assertNotNull(local.getId());
        assertNotNull(local.getDescricao());
        assertNotNull(local.getLatitude());
        assertNotNull(local.getLongitude());
    }

    private void verifiqueAtributosTime(Time time) {
        assertNotNull(time.getId());
        assertNotNull(time.getNome());
        assertNotNull(time.getCor());
        assertNotNull(time.getJogadores());
        assert(time.getJogadores().size()>0);
        for (Jogador jogador: time.getJogadores()){
            verifiqueAtributosJogador(jogador);
        }

    }

    private void verifiqueAtributosJogador(Jogador jogador) {
        assertNotNull(jogador.getId());
        assertNotNull(jogador.getNome());
        assertNotNull(jogador.getEventos());
        assert(jogador.getEventos().size()>0);
        for (EventoPartida eventoPartida : jogador.getEventos()){
            verifiqueAtributosEvetoJogo(eventoPartida);
        }
    }

    private void verifiqueAtributosEvetoJogo(EventoPartida eventoPartida) {
        assertNotNull(eventoPartida.getId());
        assertNotNull(eventoPartida.getHora());
        assertNotNull(eventoPartida.getTipoEvento());
    }
}
