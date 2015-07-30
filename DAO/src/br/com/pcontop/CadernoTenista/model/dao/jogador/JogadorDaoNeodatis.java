package br.com.pcontop.CadernoTenista.model.dao.jogador;

import android.content.Context;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.model.dao.NeodatisDAOAdapter;
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
public class JogadorDaoNeodatis extends NeodatisDAOAdapter implements JogadorDAO {

    public JogadorDaoNeodatis(Context context){
        super(context);
    }


    @Override
    public synchronized Jogador get(String id) {
        IQuery query = getODB().criteriaQuery(Jogador.class, Where.equal("id", id));
        Objects<Jogador> jogadores = getODB().getObjects(query);
        if (jogadores.hasNext()){
            commit();
            return jogadores.next();
        }
        commit();
        return null;
    }

    public synchronized void insiraOuAtualize(Jogador jogador) {
        if (jogador.getId()==null){
            return;
        }
        getODB().store(jogador);
        commit();
    }

    public synchronized void remova(Jogador jogador) {
        getODB().delete(jogador);
        commit();
    }
}
