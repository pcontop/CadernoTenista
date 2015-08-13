package br.com.pcontop.CadernoTenista.view;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PauloBruno on 12/12/13.
 */
public class LanguageFormatter {

    private LanguageFormatter(){}

    public static String getDateFormat(Date dataApresentar, int id_texto_data_vazia, Context context) {
        if (dataApresentar==null){
            return context.getString(id_texto_data_vazia);
        }

        char[] ordem = android.text.format.DateFormat.getDateFormatOrder(context);
        String format = "";
        for (char item: ordem){
            String parcela;
            switch (item){
                case 'd':
                    parcela="dd";
                    break;
                case 'M':
                    parcela="MM";
                    break;
                case 'y':
                    parcela="yyyy";
                    break;
                default:
                    parcela="";
            }
            if (!format.equals("")){
                parcela="/" + parcela;
            }
            format +=parcela;
        }
        format+=" hh:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(dataApresentar);
    }

}
