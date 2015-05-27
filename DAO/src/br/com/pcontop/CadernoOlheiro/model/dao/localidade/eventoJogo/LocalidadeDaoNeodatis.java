package br.com.pcontop.CadernoOlheiro.model.dao.localidade.eventoJogo;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.bean.Localidade;
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
public class LocalidadeDaoNeodatis extends NeodatisDAOAdapter implements LocalidadeDAO {

    public LocalidadeDaoNeodatis(Context context){
        super(context);
    }


    @Override
    public synchronized Localidade get(String id) {
        IQuery query = getODB().criteriaQuery(Localidade.class, Where.equal("id", id));
        Objects<Localidade> localidades = getODB().getObjects(query);
        if (localidades.hasNext()){
            return localidades.next();
        }
        return null;
    }

    @Override
    public Localidade getPorDescricao(String descricao) {
        IQuery query = getODB().criteriaQuery(Localidade.class, Where.equal("descricao", descricao));
        Objects<Localidade> localidades = getODB().getObjects(query);
        if (localidades.hasNext()){
            return localidades.next();
        }
        return null;
    }

    @Override
    public synchronized boolean insiraOuAtualize(Localidade localidade)  {
        if (localidade.getId() ==null){
            return false;
        }
        getODB().store(localidade);
        commit();
        return true;
    }

    @Override
    public synchronized void remova(Localidade localidade) {
        getODB().delete(localidade);
        commit();
    }
}
