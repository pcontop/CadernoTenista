package br.com.pcontop.CadernoOlheiro.model.dao.eventoJogo;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.EventoPartida;
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
    public synchronized EventoPartida get(String id) {
        IQuery query = getODB().criteriaQuery(EventoPartida.class, Where.equal("id", id));
        Objects<EventoPartida> eventosJogo = getODB().getObjects(query);
        if (eventosJogo.hasNext()){
            return eventosJogo.next();
        }
        return null;
    }

    public void insiraOuAtualize(EventoPartida eventoPartida)  {
        getODB().store(eventoPartida);
        commit();
    }

    public synchronized void remova(EventoPartida eventoPartida) {
        getODB().delete(eventoPartida);
        commit();
    }
}
