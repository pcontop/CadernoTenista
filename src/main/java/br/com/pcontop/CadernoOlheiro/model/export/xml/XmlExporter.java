package br.com.pcontop.CadernoOlheiro.model.export.xml;

import java.io.OutputStream;
import java.io.PrintWriter;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.export.file.FileExporter;
import br.com.pcontop.CadernoOlheiro.model.xml.XmlConverter;

/**
 * Created by pcont_000 on 26/07/2015.
 */
public class XmlExporter extends FileExporter{
    @Override
    public String getSufixo() {
        return ".xml";
    }

    @Override
    protected boolean imprimaPartida(OutputStream outputStream, Partida partida) {
        String xmlPartida = XmlConverter.toXml(partida);
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.print(xmlPartida);
        return true;
    }
}
