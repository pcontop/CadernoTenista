package br.com.pcontop.CadernoOlheiro.model.export.xml.partida;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XmlExporter;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public interface PartidaExporter extends XmlExporter {

    public Element export(Partida partida) throws ParserConfigurationException;
}
