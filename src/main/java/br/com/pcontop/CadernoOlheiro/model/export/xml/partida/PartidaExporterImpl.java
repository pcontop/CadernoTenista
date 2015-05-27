package br.com.pcontop.CadernoOlheiro.model.export.xml.partida;

import br.com.pcontop.CadernoOlheiro.bean.DefaultFormats;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.model.export.xml.XMLAdapter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.localidade.LocalidadeExporter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.localidade.LocalidadeExporterImpl;
import br.com.pcontop.CadernoOlheiro.model.export.xml.time.TimeExporter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.time.TimeExporterImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class PartidaExporterImpl extends XMLAdapter implements PartidaExporter {
    Document document;
    private static final PartidaExporterImpl instance = new PartidaExporterImpl();
    private PartidaExporterImpl(){

    }

    public static PartidaExporterImpl getInstance(){
        return instance;
    }

    @Override
    public Element export(Partida partida) throws ParserConfigurationException {
        document = getNewDocument();
        Element partidaNode = document.createElement(TagsXml.PARTIDA.getDescritor());
        partidaNode.setAttribute(TagsXml.ID.getDescritor(), partida.getId());
        partidaNode.setAttribute(TagsXml.CRIACAO_PARTIDA.getDescritor(), getFormattedDate(partida.getDataCriacao()));
        partidaNode.setAttribute(TagsXml.INICIO_PARTIDA.getDescritor(), getFormattedDate(partida.getDataInicio()));
        partidaNode.setAttribute(TagsXml.FIM_PRIMEIRO_TEMPO.getDescritor(), getFormattedDate(partida.getDataFimPrimeiroTempo()));
        partidaNode.setAttribute(TagsXml.INICIO_SEGUNDO_TEMPO.getDescritor(), getFormattedDate(partida.getDataInicioSegundoTempo()));
        partidaNode.setAttribute(TagsXml.FIM_PARTIDA.getDescritor(), getFormattedDate(partida.getDataFimSegundoTempo()));
        adicioneTimes(partidaNode, partida);
        //TODO-Adicione olheiro.
        adicioneLocalidade(partidaNode, partida);
        return partidaNode;
    }

    private void adicioneLocalidade(Element partidaNode, Partida partida) throws ParserConfigurationException {
        if (partida.getLocal()==null){
            return;
            //Nada pra fazer, saia.
        }
        LocalidadeExporter localidadeExporter = LocalidadeExporterImpl.getInstance();
        Element nodeLocalidade = localidadeExporter.export(partida.getLocal());
        importeNode(document, partidaNode, nodeLocalidade);

    }

    private String getFormattedDate(Date date){
        if (date ==null){
            return "";
        }
        return DefaultFormats.getDefaultDateFormat().format(date);
    }

    private void adicioneTimes(Element partidaNode, Partida partida) throws ParserConfigurationException {
        TimeExporter timeExporter = TimeExporterImpl.getInstance();
        Element nodeTime1 = timeExporter.export(partida.getTime1());
        Element nodeTime2 = timeExporter.export(partida.getTime2());
        importeNode(document, partidaNode, nodeTime1);
        importeNode(document, partidaNode, nodeTime2);

    }

}
