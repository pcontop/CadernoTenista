package br.com.pcontop.CadernoTenista.model.export.file;

import java.io.File;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class DiretoriosArquivosWindows extends DiretorioArquivosAdapter {

    public  DiretoriosArquivosWindows(){

    }

    @Override
    public File getDiretorioGravacaoArquivo() throws ImpossivelCriarDiretorioException {
        String dirname = "./teste/";
        File dir = new File(dirname);
        if (!dir.exists() && !dir.mkdirs()){
            throw new ImpossivelCriarDiretorioException(dirname);
        }
        return dir;
    }

}
