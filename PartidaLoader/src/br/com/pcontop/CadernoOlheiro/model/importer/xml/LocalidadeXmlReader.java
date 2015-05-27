package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.Localidade;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import org.w3c.dom.Element;

import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getAtributoDeTag;
import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getDoubleDeTag;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class LocalidadeXmlReader {
    private LocalidadeXmlReader(){}
    public static Localidade read(Element elementLocalidade){
        String id = getAtributoDeTag(elementLocalidade, TagsXml.ID);
        Double latitude = getDoubleDeTag(elementLocalidade, TagsXml.LATITUDE);
        Double longitude = getDoubleDeTag(elementLocalidade, TagsXml.LONGITUDE);
        String descricao = getAtributoDeTag(elementLocalidade,TagsXml.DESCRICAO);

        Localidade localidade = Localidade.create()
                .setId(id)
                .setDescricao(descricao)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .commit();

        return localidade;
    }
}
