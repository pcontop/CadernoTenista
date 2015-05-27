package br.com.pcontop.CadernoOlheiro.model.export.xml.eventoJogo;

import br.com.pcontop.CadernoOlheiro.bean.DefaultFormats;
import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XMLAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class EventoJogoExporterImpl extends XMLAdapter implements br.com.pcontop.CadernoOlheiro.model.export.xml.eventoJogo.EventoJogoExporter {
    private static EventoJogoExporterImpl instance = new EventoJogoExporterImpl();
    private EventoJogoExporterImpl(){

    }
    @Override
    public Element export(EventoJogo eventoJogo) throws ParserConfigurationException {
        Document document = getNewDocument();
        Element eventNode = document.createElement(TagsXml.EVENTO_JOGO.getDescritor());
        eventNode.setAttribute(TagsXml.ID.getDescritor(),eventoJogo.getId());
        if (eventoJogo.getHora()!=null){
            eventNode.setAttribute(TagsXml.HORA.getDescritor(), DefaultFormats.getDefaultDateFormat().format(eventoJogo.getHora()));
        }
        eventNode.setAttribute(TagsXml.TIPO_EVENTO.getDescritor(),eventoJogo.getTipoEvento().toString());
        return eventNode;
    }

    public static EventoJogoExporter getInstance() {
        return instance;
    }
}
