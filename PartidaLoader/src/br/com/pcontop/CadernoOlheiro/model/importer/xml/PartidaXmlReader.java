package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.Localidade;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getAtributoDeTag;
import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getDataDeTag;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class PartidaXmlReader {
    private PartidaXmlReader (){ }

    public static Partida read(Document document) throws ParseException {
        Element elementPartida = (Element) document.getFirstChild();
        String nodeName = elementPartida.getTagName();
        if (!nodeName.equals(TagsXml.PARTIDA.getDescritor())){
            return null;
        }
        String id = getAtributoDeTag(elementPartida, TagsXml.ID);

        Date dataInicio = getDataDeTag(elementPartida, TagsXml.INICIO_PARTIDA);
        Date dataFimPrimeiroTempo = getDataDeTag(elementPartida, TagsXml.FIM_PRIMEIRO_TEMPO);
        Date dataInicioSegundoTempo = getDataDeTag(elementPartida, TagsXml.INICIO_SEGUNDO_TEMPO);
        Date dataFimSegundoTempo = getDataDeTag(elementPartida, TagsXml.FIM_PARTIDA);
        Date dataCriacao = getDataDeTag(elementPartida,TagsXml.CRIACAO_PARTIDA);

        Map<String, Time> times= leiaTimes(elementPartida);

        Time time1 = times.get(Time.TIME_1);
        Time time2 = times.get(Time.TIME_2);

        if(time1==null || time2==null){
            Iterator<String> chaves = times.keySet().iterator();
            time1 = times.get(chaves.next());
            time2 = times.get(chaves.next());
        }

        Localidade localidade = getLocalidade(elementPartida);

        Partida partida=
                Partida.create()
                        .setId(id)
                        .setDataInicio(dataInicio)
                        .setDataFimPrimeiroTempo(dataFimPrimeiroTempo)
                        .setDataInicioSegundoTempo(dataInicioSegundoTempo)
                        .setDataFimSegundoTempo(dataFimSegundoTempo)
                        .setTime1(time1)
                        .setTime2(time2)
                        .setLocal(localidade)
                        .setDataCriacao(dataCriacao)
                        .commit();
        return partida;
    }

    private static Localidade getLocalidade(Element elementPartida) {
        NodeList resultadoLocalidade = elementPartida.getElementsByTagName(TagsXml.LOCALIDADE.getDescritor());
        if (resultadoLocalidade.getLength()==0){
            return null;
        }
        Element elementLocalidade = (Element) resultadoLocalidade.item(0);
        Localidade localidade = LocalidadeXmlReader.read(elementLocalidade);
        return localidade;
    }

    private static Map<String, Time> leiaTimes(Element elementPartida) throws ParseException {
        Map<String, Time> times = new HashMap<>();
        NodeList nodesTimes = elementPartida.getElementsByTagName(TagsXml.TIME.getDescritor());
        for (int i=0;i<nodesTimes.getLength();i++){
            Node node = nodesTimes.item(i);
            if (!(node instanceof Element)){
                continue;
            }
            Element elementTime = (Element) node;
            Time time = TimeXmlReader.read(elementTime);
            times.put(time.getNome(), time);
        }
        return times;

    }

}
