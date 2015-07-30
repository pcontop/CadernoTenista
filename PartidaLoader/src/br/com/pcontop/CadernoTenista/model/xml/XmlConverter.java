package br.com.pcontop.CadernoTenista.model.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.InputStream;

/**
 * Created by pcont_000 on 25/07/2015.
 */
public class XmlConverter {
    private XmlConverter(){}

    private static XStream xs = new XStream(new StaxDriver());
    public static String toXml(Object object){
        return xs.toXML(object);
    }

    public static <E> E fromXml(String xmlText){
        return (E) xs.fromXML(xmlText);
    }

    public static <E> E fromXml(InputStream stream){
        return (E) xs.fromXML(stream);
    }
}
