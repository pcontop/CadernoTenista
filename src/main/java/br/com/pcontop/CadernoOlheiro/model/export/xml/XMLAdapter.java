package br.com.pcontop.CadernoOlheiro.model.export.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class XMLAdapter implements XmlExporter {

    public Document getNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();
        return document;
    }

    /*
    public String documentToString(Document document) throws IOException {
        OutputFormat format = new OutputFormat(document);
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);
        return out.toString();
    }

    @Override
    public String nodeToString(Node node) throws ParserConfigurationException, IOException {
        Document document = getNewDocument();
        Node documentNode = document.importNode(node,true);
        document.appendChild(documentNode);
        return documentToString(document);
    }

    */

    public String nodeToString(Node root) throws IOException {

        StringBuilder result = new StringBuilder();

        if (root.getNodeType() == 3)
            result.append(root.getNodeValue());
        else {
            if (root.getNodeType() != 9) {
                StringBuffer attrs = new StringBuffer();
                for (int k = 0; k < root.getAttributes().getLength(); ++k) {
                    attrs.append(" ").append(
                            root.getAttributes().item(k).getNodeName()).append(
                            "=\"").append(
                            root.getAttributes().item(k).getNodeValue())
                            .append("\" ");
                }
                result.append("<").append(root.getNodeName()).append(" ")
                        .append(attrs).append(">");
            } else {
                result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            }

            NodeList nodes = root.getChildNodes();
            for (int i = 0, j = nodes.getLength(); i < j; i++) {
                Node node = nodes.item(i);
                result.append(nodeToString(node));
            }

            if (root.getNodeType() != 9) {
                result.append("</").append(root.getNodeName()).append(">");
            }
        }
        return result.toString();
    }


    /*
    @Override
    public String nodeToString(Node source) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        String subscrXML=null;
        StringWriter stringWriter=new StringWriter();

        DOMImplementationRegistry registry =  DOMImplementationRegistry.newInstance();

        DOMImplementationLS impls =  (DOMImplementationLS)registry.getDOMImplementation("LS");

        //Prepare the output
        LSOutput domOutput = impls.createLSOutput();
        domOutput.setEncoding(java.nio.charset.Charset.defaultCharset().name());
        domOutput.setCharacterStream(stringWriter);
        //domOutput.setEncoding(ENCODING);
        //Prepare the serializer
        LSSerializer domWriter = impls.createLSSerializer();
        DOMConfiguration domConfig = domWriter.getDomConfig();
        domConfig.setParameter("format-pretty-print", true);
        domConfig.setParameter("element-content-whitespace", true);
        domWriter.setNewLine("\r\n");
        domConfig.setParameter("cdata-sections", Boolean.TRUE);
        //And finaly, write
        domWriter.write(source, domOutput);
        subscrXML = domOutput.getCharacterStream().toString();
        DOMStringList dsl=domConfig.getParameterNames();

        return subscrXML;
    }
    */

    protected void importeNode(Document document, Element parentElement, Element childElement){
        Element nativeChild = (Element) document.importNode(childElement, true);
        parentElement.appendChild(nativeChild);
    }

}
