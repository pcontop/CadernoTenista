package br.com.pcontop.CadernoOlheiro.model.export.xml.time;

import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XMLAdapter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.jogador.JogadorExporterImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class TimeExporterImpl extends XMLAdapter implements TimeExporter {
    Document document;
    private static final TimeExporterImpl instance = new TimeExporterImpl();
    private TimeExporterImpl(){

    }

    public static TimeExporterImpl getInstance(){
        return instance;
    }

    @Override
    public Element export(Time time) throws ParserConfigurationException {
        document = getNewDocument();
        Element timeNode = document.createElement(TagsXml.TIME.getDescritor());
        timeNode.setAttribute(TagsXml.ID.getDescritor(), time.getId());
        timeNode.setAttribute(TagsXml.NOME.getDescritor(), time.getNome());
        timeNode.setAttribute(TagsXml.COR.getDescritor(), time.getCor() + "");
        adicioneJogadores(timeNode, time.getJogadores());
        return timeNode;
    }

    private void adicioneJogadores(Element timeNode, Set<Jogador> jogadores) throws ParserConfigurationException {
        for (Jogador jogador:jogadores){
            Element nodeJogador = JogadorExporterImpl.getInstance().export(jogador);
            importeNode(document, timeNode, nodeJogador);
        }
    }

}
