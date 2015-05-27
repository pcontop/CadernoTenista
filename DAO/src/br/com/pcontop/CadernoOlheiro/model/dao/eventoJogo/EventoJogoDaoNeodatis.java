package br.com.pcontop.CadernoOlheiro.model.dao.eventoJogo;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.EventoJogo;
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
public class EventoJogoDaoNeodatis extends NeodatisDAOAdapter implements EventoJogoDAO {

    public EventoJogoDaoNeodatis(Context context){
        super(context);
    }


    @Override
    public synchronized EventoJogo get(String id) {
        IQuery query = getODB().criteriaQuery(EventoJogo.class, Where.equal("id", id));
        Objects<EventoJogo> eventosJogo = getODB().getObjects(query);
        if (eventosJogo.hasNext()){
            return eventosJogo.next();
        }
        return null;
    }

    public void insiraOuAtualize(EventoJogo eventoJogo)  {
        getODB().store(eventoJogo);
        commit();
    }

    public synchronized void remova(EventoJogo eventoJogo) {
        getODB().delete(eventoJogo);
        commit();
    }
}
