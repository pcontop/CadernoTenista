package br.com.pcontop.CadernoTenista.model.dao.partida;

import android.content.Context;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.model.dao.NeodatisDAOAdapter;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 10/11/13
 * time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class PartidaDaoNeodatis extends NeodatisDAOAdapter implements PartidaDAO {

    public PartidaDaoNeodatis(Context context){
        super(context);
    }


    @Override
    public synchronized Partida get(String id) {
        IQuery query = getODB().criteriaQuery(Partida.class, Where.equal("id", id));
        Objects<Partida> partidas = getODB().getObjects(query);
        if (partidas.hasNext()){
            commit();
            return partidas.next();
        }
        commit();
        return null;
    }

    @Override
    public synchronized void insiraOuAtualize(Partida partida) {
        getODB().store(partida);
        commit();
    }

    @Override
    public synchronized void remova(Partida partida) {
        getODB().delete(partida);
        commit();
    }

    @Override
    public synchronized List<Partida> getAll() {
        List<Partida> listaPartidas = new ArrayList<>();
        IQuery query = getODB().criteriaQuery(Partida.class);
        Objects<Partida> partidas = getODB().getObjects(query);
        while (partidas.hasNext()){
            listaPartidas.add(partidas.next());
        }
        commit();
        return listaPartidas;
    }

    @Override
    public synchronized List<Partida> getAllDataInicioNotEmpty() {
        List<Partida> listaPartidas = new ArrayList<>();
        IQuery query = getODB().criteriaQuery(Partida.class, Where.isNotNull("dataInicio"));
        Objects<Partida> partidas = getODB().getObjects(query);
        while (partidas.hasNext()){
            listaPartidas.add(partidas.next());
        }
        commit();
        return listaPartidas;
    }

    @Override
    public synchronized void removaPartidasNaoIniciadasMenosAtual() {
        IQuery query = getODB().criteriaQuery(Partida.class, Where.isNull("dataInicio"));
        query.orderByDesc("dataCriacao");
        Objects<Partida> partidas = getODB().getObjects(query);
        if (partidas.hasNext()){
            partidas.next();
        }
        while (partidas.hasNext()){
            this.remova(partidas.next());
        }
        commit();
    }

    @Override
    public void removaTodosRegistros() {
        List<Partida> partidas = getAll();
        for (Partida partida:partidas) {
            remova(partida);
        }

    }

    public OID getOid(Partida partida){
        OID oid = getODB().getObjectId(partida);
        commit();
        return oid;
    }


}
