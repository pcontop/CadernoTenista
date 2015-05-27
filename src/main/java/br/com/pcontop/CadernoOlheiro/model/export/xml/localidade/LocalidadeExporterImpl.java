package br.com.pcontop.CadernoOlheiro.model.export.xml.localidade;

import br.com.pcontop.CadernoOlheiro.bean.Localidade;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XMLAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class LocalidadeExporterImpl extends XMLAdapter implements LocalidadeExporter {
    private static LocalidadeExporterImpl instance = new LocalidadeExporterImpl();
    private LocalidadeExporterImpl(){

    }
    @Override
    public Element export(Localidade localidade) throws ParserConfigurationException {
        if (localidade==null){
            return null;
        }
        Document document = getNewDocument();
        Element localidadeNode = document.createElement(TagsXml.LOCALIDADE.getDescritor());
        localidadeNode.setAttribute(TagsXml.ID.getDescritor(), localidade.getId());
        if (localidade.getDescricao()!=null){
            localidadeNode.setAttribute(TagsXml.DESCRICAO.getDescritor(), localidade.getDescricao());
        }
        if (localidade.getLatitude()!=null){
            localidadeNode.setAttribute(TagsXml.LATITUDE.getDescritor(), localidade.getLatitude().toString());
        }
        if (localidade.getLongitude()!=null){
            localidadeNode.setAttribute(TagsXml.LONGITUDE.getDescritor(), localidade.getLongitude().toString());
        }
        return localidadeNode;
    }

    public static LocalidadeExporter getInstance() {
        return instance;
    }
}
