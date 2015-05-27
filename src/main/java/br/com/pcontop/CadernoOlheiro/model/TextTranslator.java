package br.com.pcontop.CadernoOlheiro.model;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class TextTranslator {
    private static OlheiroController olheiroController;

    public static String translateFromStringName(Context context, String text) {
        if (context!=null){
            return translateFromStringNameAndroid(context, text);
        } else {
            return text;
        }
    }

    private static String translateFromStringNameAndroid(Context context, String text) {
        olheiroController = FabricaController.getOlheiroController(context);
        return olheiroController.getStringDeNomeRef(text);
    }
}
