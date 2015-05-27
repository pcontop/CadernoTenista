package br.com.pcontop.CadernoOlheiro.model.dao.time;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.bean.Time;
import br.com.pcontop.CadernoOlheiro.model.dao.NeodatisDAOAdapter;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 10/11/13
 * time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class TimeDaoNeodatis extends NeodatisDAOAdapter implements TimeDAO {

    public TimeDaoNeodatis(Context context){
        super(context);
    }


    @Override
    public synchronized Time get(String id) {
        IQuery query = getODB().criteriaQuery(Partida.class, Where.equal("id", id));
        Objects<Time> times = getODB().getObjects(query);
        if (times.hasNext()){
            return times.next();
        }
        commit();
        return null;
    }

    public synchronized void insiraOuAtualize(Time time) {
        getODB().store(time);
        commit();
    }

    public synchronized void remova(Time time) {
        getODB().delete(time);
        commit();
    }
}
