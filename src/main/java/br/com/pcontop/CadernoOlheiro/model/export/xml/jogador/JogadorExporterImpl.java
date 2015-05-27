package br.com.pcontop.CadernoOlheiro.model.export.xml.jogador;

import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XMLAdapter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.eventoJogo.EventoJogoExporterImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class JogadorExporterImpl extends XMLAdapter implements br.com.pcontop.CadernoOlheiro.model.export.xml.jogador.JogadorExporter {
    Document document;
    private static final JogadorExporterImpl instance = new JogadorExporterImpl();
    private JogadorExporterImpl(){

    }

    public static JogadorExporterImpl getInstance(){
        return instance;
    }

    @Override
    public Element export(Jogador jogador) throws ParserConfigurationException {
        document = getNewDocument();
        Element jogadorNode = document.createElement(TagsXml.JOGADOR.getDescritor());
        jogadorNode.setAttribute(TagsXml.ID.getDescritor(), jogador.getId());
        jogadorNode.setAttribute(TagsXml.NOME.getDescritor(), jogador.getNome());
        adicioneEventos (jogadorNode, jogador.getEventos());
        return jogadorNode;
    }

    private void adicioneEventos(Element jogadorNode, List<EventoJogo> eventos) throws ParserConfigurationException {
        for (EventoJogo eventoJogo:eventos){
            Element nodeEvento = EventoJogoExporterImpl.getInstance().export(eventoJogo);
            importeNode(document, jogadorNode, nodeEvento);
        }
    }
}
