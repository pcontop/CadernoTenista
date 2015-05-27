package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.importer.ImporterException;
import br.com.pcontop.CadernoOlheiro.model.importer.file.FileImporter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class XmlImporter extends FileImporter {
    private final static XmlImporter instance = new XmlImporter();

    @Override
    public Partida leiaPartida(InputStream inputStream) throws ImporterException {
        try {
            Document partidaDocument = getDocument(inputStream);
            Partida partida = leiaPartida(partidaDocument);
            return partida;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new ImporterException("Problemas ao importar xml: " + e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new ImporterException("Problemas ao importar xml: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImporterException("Problemas ao importar xml: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ImporterException("Problemas ao importar xml: " + e.getMessage());
        }
    }

    private Partida leiaPartida(Document partidaDocument) throws ParseException {
        Partida partida = PartidaXmlReader.read(partidaDocument);
        return partida;
    }

    private Document getDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(inputStream);
        return document;
    }

    private XmlImporter(){}

    public static XmlImporter getInstance(){
        return instance;
    }


}
