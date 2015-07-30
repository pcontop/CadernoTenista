package br.com.pcontop.CadernoTenista.model.export.file;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 19:47
 * To change this template use File | Settings | File Templates.
 */
public interface DiretorioArquivos {
    public File getDiretorioGravacaoArquivo() throws ImpossivelCriarDiretorioException;
    public File getArquivoEmDiretorioGravacao(String fileName) throws ImpossivelCriarDiretorioException;
}
