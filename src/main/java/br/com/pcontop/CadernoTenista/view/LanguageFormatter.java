package br.com.pcontop.CadernoTenista.view;

import android.content.Context;
import android.text.format.DateUtils;

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
        return DateUtils.formatDateTime(context, dataApresentar.getTime(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_24HOUR
                        | DateUtils.FORMAT_SHOW_TIME);
    }

}
