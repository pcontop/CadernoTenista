package br.com.pcontop.CadernoOlheiro.model.export.file;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 27/11/13
 * Time: 23:54
 * To change this template use File | Settings | File Templates.
 */
public class DiretorioArquivosFactory {
    private static DiretorioArquivos diretoriosArquivos;
    public static  DiretorioArquivos getInstance(Context context){
        if (diretoriosArquivos==null){
            if (context==null){
                diretoriosArquivos = new DiretoriosArquivosWindows();
            } else {
                diretoriosArquivos = new DiretoriosArquivosAndroid(context);
            }
        }
        return diretoriosArquivos;
    }
}
