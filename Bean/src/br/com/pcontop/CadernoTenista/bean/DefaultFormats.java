package br.com.pcontop.CadernoTenista.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by PauloBruno on 25/01/14.
 */
public class DefaultFormats {
    private static final DateFormat formatoDataPadrao = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    private DefaultFormats(){ }

    public static DateFormat getDefaultDateFormat(){
        return formatoDataPadrao;
    }
}
