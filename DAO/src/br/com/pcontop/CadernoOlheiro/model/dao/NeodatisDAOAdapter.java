package br.com.pcontop.CadernoOlheiro.model.dao;

import android.content.Context;
import org.neodatis.odb.ODB;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 11/12/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class NeodatisDAOAdapter {
    private final Context context;
    private static ODB odb;
    public NeodatisDAOAdapter(Context context){
        this.context = context;
    }

    protected synchronized ODB getODB(){
        if (odb!=null && !odb.isClosed()){
            return odb;
        }
        odb = ODBProvider.getODB(context);
        return odb;
    }

    protected synchronized void commit(){
        if (odb!=null && !odb.isClosed()){
            odb.commit();
        }
    }
}
