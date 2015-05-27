package br.com.pcontop.CadernoOlheiro.model.export.xml.file;

import android.content.Context;
import android.util.Log;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;
import br.com.pcontop.CadernoOlheiro.model.export.file.FileExporter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.partida.PartidaExporter;
import br.com.pcontop.CadernoOlheiro.model.export.xml.partida.PartidaExporterImpl;

import java.io.*;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class FileXmlExporter extends FileExporter {
    @Override
    public boolean export(Context context, Partida partida) throws ExporterException {
        try {
            PartidaExporter partidaExporter = PartidaExporterImpl.getInstance();
            String xml = partidaExporter.nodeToString(PartidaExporterImpl.getInstance().export(partida));
            File file = getArquivoPartida(partida, context);
            imprimaXml(xml, file);
            return true;

        } catch (Exception e){
            Log.e("FileExporter", "Erro ao exportar", e);
            throw new ExporterException();
        }

    }

    private void imprimaXml(String xml, File file) throws IOException {
        StringReader stringReader = new StringReader(xml);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        String linha;
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.println("<?xml version='1.0' encoding='UTF-8' ?>");
        while ((linha=bufferedReader.readLine())!=null){
            writer.println(linha);
        }
        writer.close();
    }

    @Override
    public String getSufixo(){
        return ".xml";
    }

}
