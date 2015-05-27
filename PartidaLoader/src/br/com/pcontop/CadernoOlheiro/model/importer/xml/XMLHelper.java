package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.DefaultFormats;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.Date;

/**
 * Classe de utilitários para a navegação Dom de arquivos Xml.
 */
public class XMLHelper {
    private XMLHelper(){}

    public static Date leiaData(String data) throws ParseException {
        if (data==null || data.isEmpty()) {
            return null;
        }
        return DefaultFormats.getDefaultDateFormat().parse(data);
    }

    public static Date getDataDeTag(Element element, TagsXml tagsXml) throws ParseException {
        String dataTexto = getAtributoDeTag(element, tagsXml);
        Date data = leiaData(dataTexto);
        return data;

    }

    public static String getAtributoDeTag(Element element, TagsXml tag){
        return element.getAttribute(tag.getDescritor()).toString();
    }

    public static Double getDoubleDeTag(Element element, TagsXml tagsXml){
        String doubleTexto = getAtributoDeTag(element,tagsXml);
        Double numero = leiaDouble(doubleTexto);
        return numero;
    }

    private static Double leiaDouble(String doubleTexto) {
        if (doubleTexto==null || doubleTexto.isEmpty()){
            return null;
        }
        return Double.parseDouble(doubleTexto);
    }

}
