package br.com.pcontop.CadernoTenista.model;

import android.content.Context;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;

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
