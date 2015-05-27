package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getAtributoDeTag;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class JogadorXmlReader {
    private JogadorXmlReader(){}
    public static Jogador read(Element elementJogador) throws ParseException {
        String id = getAtributoDeTag(elementJogador, TagsXml.ID);
        String nome = getAtributoDeTag(elementJogador, TagsXml.NOME);

        List<EventoJogo> eventos = getEventosJogo(elementJogador);

        Jogador jogador = Jogador.create()
                .setId(id)
                .setNome(nome)
                .setEventos(eventos)
                .commit();
        return jogador;

    }

    private static List<EventoJogo> getEventosJogo(Element elementJogador) throws ParseException {
        List<EventoJogo> eventos = new ArrayList<>();
        NodeList nodesEventos = elementJogador.getElementsByTagName(TagsXml.EVENTO_JOGO.getDescritor());
        for (int i=0;i<nodesEventos.getLength();i++){
            Node node = nodesEventos.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element elementEvento = (Element) node;
            EventoJogo eventoJogo = EventoJogoXmlReader.read(elementEvento);
            eventos.add(eventoJogo);
        }

        return eventos;
    }
}
