package br.com.pcontop.CadernoOlheiro.model.export.xml;

import org.w3c.dom.Node;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * Time: 23:45
 * To change this template use File | Settings | File Templates.
 */
public interface XmlExporter {
    public String nodeToString(Node node) throws Exception;
    //public String documentToString(Document document) throws IOException;
}
