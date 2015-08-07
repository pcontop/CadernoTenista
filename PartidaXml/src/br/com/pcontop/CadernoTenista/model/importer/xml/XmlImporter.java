package br.com.pcontop.CadernoTenista.model.importer.xml;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.model.importer.ImporterException;
import br.com.pcontop.CadernoTenista.model.importer.file.FileImporter;
import br.com.pcontop.CadernoTenista.model.xml.XmlConverter;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class XmlImporter extends FileImporter {
    private final static XmlImporter instance = new XmlImporter();

    @Override
    public Partida leiaPartida(InputStream inputStream) throws ImporterException {
        XStream xs = new XStream();
        try {
            Partida partida = XmlConverter.fromXml(inputStream);
            return partida;
        } catch(ClassCastException e){
            throw new ImporterException("Xml não é de uma partida." + e.toString());
        }
    }


    private XmlImporter(){}

    public static XmlImporter getInstance(){
        return instance;
    }


}
