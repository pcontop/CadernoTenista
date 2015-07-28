package br.com.pcontop.CadernoOlheiro.model.export.file;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.export.Exporter;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private static Context context;

    public abstract String getSufixo();

    @Override
    public boolean export(Context context, Partida partida) throws ExporterException {
        try {
            this.context = context;
            File arquivoPartida = getArquivoPartida(partida,context);
            OutputStream outputStream = new FileOutputStream(arquivoPartida);
            outputStream.close();
            return imprimaPartida(outputStream, partida);
        } catch (ImpossivelCriarDiretorioException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected abstract boolean imprimaPartida(OutputStream outputStream, Partida partida);

    public String getNomeArquivoPartida(Partida partida) {
        String strLocal = "indefinido";
        if (partida.getLocal()!=null  && partida.getLocal().getDescricao() !=null) {
            strLocal = partida.getLocal().getDescricao();
        }

        return "partida_" + strLocal + "_" + simpleDateFormat.format(partida.getDataCriacao()) + getSufixo();
    }

    public File getArquivoPartida(Partida partida, Context context) throws ImpossivelCriarDiretorioException {
        DiretorioArquivos diretorioArquivos = DiretorioArquivosFactory.getInstance(context);
        File file = diretorioArquivos.getArquivoEmDiretorioGravacao(getNomeArquivoPartida(partida));
        return file;
    }

    public Context getContext(){
        return context;
    }

}
