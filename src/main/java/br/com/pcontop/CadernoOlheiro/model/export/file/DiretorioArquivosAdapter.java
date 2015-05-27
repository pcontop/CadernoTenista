package br.com.pcontop.CadernoOlheiro.model.export.file;

import java.io.File;

/**
 * Created by PauloBruno on 12/01/14.
 */
public abstract class DiretorioArquivosAdapter implements DiretorioArquivos {
    @Override
    public File getArquivoEmDiretorioGravacao(String fileName) throws ImpossivelCriarDiretorioException{
        File file = new File(getDiretorioGravacaoArquivo(),fileName);
        return file;
    }
}
