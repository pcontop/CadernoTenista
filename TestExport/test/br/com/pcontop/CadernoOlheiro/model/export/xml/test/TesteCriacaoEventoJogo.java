package br.com.pcontop.CadernoOlheiro.model.export.xml.test;

import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.TipoEvento;
import br.com.pcontop.CadernoOlheiro.model.export.xml.eventoJogo.EventoJogoExporterImpl;
import org.junit.Test;
import org.w3c.dom.Element;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class TesteCriacaoEventoJogo  {
      @Test
      public void testCriacaoEventoJogo(){
          EventoJogoExporterImpl eventoJogoExporter = (EventoJogoExporterImpl) EventoJogoExporterImpl.getInstance();
          EventoJogo eventoJogo = getEventoJogo();
          try {
              Element element =  eventoJogoExporter.export(eventoJogo);
              assertNotNull(element);
              String resultado = eventoJogoExporter.nodeToString(element);
              assertNotNull(resultado);
              System.out.println(resultado);
          } catch (Exception e) {
              e.printStackTrace();
              assertTrue(false);
          }
      }

    private EventoJogo getEventoJogo() {
        return EventoJogo.create()
                      .setId("AAA")
                      .setTipoEvento(TipoEvento.DESARME_FALTA)
                      .setHora(new Date())
                      .commit();
    }

}
