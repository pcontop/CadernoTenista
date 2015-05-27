package br.com.pcontop.CadernoOlheiro.model.export.xml.jogador;

import br.com.pcontop.CadernoOlheiro.bean.Jogador;
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
public interface JogadorExporter  extends XmlExporter {
    public Element export(Jogador jogador) throws ParserConfigurationException;
}
