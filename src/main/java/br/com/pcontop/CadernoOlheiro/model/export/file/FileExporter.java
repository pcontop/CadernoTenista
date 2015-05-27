package br.com.pcontop.CadernoOlheiro.model.export.file;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.export.Exporter;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 27/11/13
 * Time: 23:38
 * To change this template use File | Settings | File Templates.
 */
public abstract class FileExporter implements Exporter {
    public static final DateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public abstract String getSufixo();

    public String getNomeArquivoPartida(Partida partida) {
        String strLocal = "indefinido";
        if (partida.getLocal()!=null  && partida.getLocal().getDescricao() !=null) {
            strLocal = partida.getLocal().getDescricao();
        }

        return "partida_" + strLocal + "_" + simpleDateFormat.format(partida.getDataInicio()) + getSufixo();
    }

    public File getArquivoPartida(Partida partida, Context context) throws ImpossivelCriarDiretorioException {
        DiretorioArquivos diretorioArquivos = DiretorioArquivosFactory.getInstance(context);
        File file = diretorioArquivos.getArquivoEmDiretorioGravacao(getNomeArquivoPartida(partida));
        return file;
    }

}
