package br.com.pcontop.CadernoOlheiro.model.importer.xml;

import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TagsXml;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static br.com.pcontop.CadernoOlheiro.model.importer.xml.XMLHelper.getAtributoDeTag;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class TimeXmlReader {
    private TimeXmlReader(){}

    public static Time read(Element elementTime) throws ParseException {
        String id = getAtributoDeTag(elementTime, TagsXml.ID);
        String cor = getAtributoDeTag(elementTime, TagsXml.COR);
        String nome = getAtributoDeTag(elementTime, TagsXml.NOME);

        Set<Jogador> jogadores = getJogadores(elementTime);

        Time time = Time.create()
                .setId(id)
                .setCor(cor)
                .setNome(nome)
                .setJogadores(jogadores)
                .commit();
        return time;
    }

    private static Set<Jogador> getJogadores(Element elementTime) throws ParseException {
        Set<Jogador> jogadores = new HashSet<>();
        NodeList nodesJogadores = elementTime.getElementsByTagName(TagsXml.JOGADOR.getDescritor());
        for (int i=0;i<nodesJogadores.getLength();i++){
            Node node = nodesJogadores.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element elementJogador = (Element) node;
            Jogador jogador = JogadorXmlReader.read(elementJogador);
            jogadores.add(jogador);
        }

        return jogadores;

    }
}
