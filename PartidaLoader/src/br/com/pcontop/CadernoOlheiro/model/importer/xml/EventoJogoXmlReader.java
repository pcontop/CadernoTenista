package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.EventoPartida;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.bean.TipoEvento;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getAtributoDeTag;
import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getDataDeTag;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class EventoJogoXmlReader {
    private EventoJogoXmlReader(){}

    public static EventoPartida read(Element elementEvento) throws ParseException {
        String id = getAtributoDeTag(elementEvento, TagsXml.ID);
        Date hora = getDataDeTag(elementEvento, TagsXml.HORA);
        String descTipoEvento = getAtributoDeTag(elementEvento, TagsXml.TIPO_EVENTO);
        TipoEvento tipoEvento = TipoEvento.valueOf(descTipoEvento);

        EventoPartida eventoPartida = EventoPartida.create()
                .setId(id)
                .setHora(hora)
                .setTipoEvento(tipoEvento)
                .commit();

        return eventoPartida;
    }
}
