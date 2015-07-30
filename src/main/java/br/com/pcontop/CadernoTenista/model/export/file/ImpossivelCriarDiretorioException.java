package br.com.pcontop.CadernoTenista.model.export.file;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class ImpossivelCriarDiretorioException extends Exception {
    public ImpossivelCriarDiretorioException(String diretorio){
        super(diretorio);
    }
}
