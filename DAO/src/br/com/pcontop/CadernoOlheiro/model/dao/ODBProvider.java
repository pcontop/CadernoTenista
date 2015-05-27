package br.com.pcontop.CadernoOlheiro.model.dao;

import android.content.Context;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 10/11/13
 * time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class ODBProvider {

    private final static String ODB_NAME="caderno_olheiro";
    private static ODB odb;

    private ODBProvider(){}

    public static ODB getODB(Context context){
        if (context==null){
            odb = getODBPureJava();
        }  else {
            odb = getODBAndroid(context);
        }

        return odb;
    }

    private static ODB getODBAndroid(Context context) {
        String fileName = getAndroidFilename(context);
        return ODBFactory.open(fileName);
    }

    private static String getAndroidFilename(Context context) {
        File directory = context.getDir("data", Context.MODE_PRIVATE);
        return directory.getAbsolutePath()+"/" + ODB_NAME;
    }

    private static ODB getODBPureJava() {
        return ODBFactory.open(ODB_NAME);
    }

    public static void removeDAOFile(Context context) {
        if (context!=null){
            String fileName = getAndroidFilename(context);
            context.deleteFile(fileName);
        }  else {
            File file = new File(ODB_NAME);
            file.delete();
        }
    }

}
