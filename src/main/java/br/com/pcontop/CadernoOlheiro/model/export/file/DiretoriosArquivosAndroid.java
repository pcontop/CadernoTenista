package br.com.pcontop.CadernoOlheiro.model.export.file;

import android.content.Context;
import android.os.Environment;
import br.com.pcontop.CadernoOlheiro.R;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 12/04/13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class DiretoriosArquivosAndroid extends DiretorioArquivosAdapter {

    private final Context context;

    public DiretoriosArquivosAndroid(Context context){
        this.context=context;
    }

    public File getDiretorioGravacaoArquivo() throws ImpossivelCriarDiretorioException {
        String nomeAplicacao = context.getText(R.string.nome_diretorio_geracao).toString();
        String dirPath = Environment.getExternalStorageDirectory().toString() + "/download/" + nomeAplicacao;
        File root = new File(dirPath);
        if (!root.exists()&&!root.mkdirs()){
            throw new ImpossivelCriarDiretorioException(root.getAbsolutePath());
        }
        return root;
    }


}
