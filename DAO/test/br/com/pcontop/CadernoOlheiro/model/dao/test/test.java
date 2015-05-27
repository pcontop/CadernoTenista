package br.com.pcontop.CadernoOlheiro.model.dao.test;

import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.model.dao.DAOFactory;
import br.com.pcontop.CadernoOlheiro.model.dao.UUIDProvider;
import br.com.pcontop.CadernoOlheiro.model.dao.partida.PartidaDaoNeodatis;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neodatis.odb.OID;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 20/11/13
 * time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class test extends Assert {
    PartidaDaoNeodatis partidaDAO;

    @Before
    public void inicializeObjetos(){
        partidaDAO = (PartidaDaoNeodatis) DAOFactory.getPartidaDAO(null);
    }

    @After
    public void finalizeObjetos(){
        DAOFactory.removeDAOFile(null);
    }

    @Test
    public void testCreateInsertPartida(){
        partidaDAO.removaTodosRegistros();
        String uuid = UUIDProvider.getNew();
        System.out.println("Criando Partida com id: " + uuid);
        Partida partida = Partida.create().setId(uuid).setDataInicio(new Date()).commit();
        partidaDAO.insiraOuAtualize(partida);
        Partida partida2 = partidaDAO.get(uuid);
        assertNotNull(partida2);
        assertEquals(partida.getId(), partida2.getId());
        testListaPartidasEqualIds();
        testListaPartidasHash();
        testListaPartidasEquals();
        testListPartidasSet();
    }

    @Test
    public void testAlterPartida(){
        partidaDAO.removaTodosRegistros();
        String uuid = UUIDProvider.getNew();
        Partida partida = Partida.create().setId(uuid).setDataInicio(new Date()).commit();
        int hash = partida.hashCode();
        String id = partida.getId();
        partidaDAO.insiraOuAtualize(partida);
        //OID oid1 = partidaDAO.getOid(partida);
        //assertEquals(hash, partida.hashCode());
        hash = partida.hashCode();
        partida.setDataInicio(new Date());
        assertEquals(hash,partida.hashCode());
        assertEquals(id,partida.getId());
        partidaDAO.insiraOuAtualize(partida);
        OID oid2 = partidaDAO.getOid(partida);
        //assertEquals(oid1, oid2);
        Partida partida2 = partidaDAO.get(uuid);
        assertNotNull(partida2);
        assertEquals(partida.getId(), partida2.getId());
        OID oid3 =partidaDAO.getOid(partida2);
        //assertEquals(oid1, oid3);
        testListaPartidasEqualIds();
        testListaPartidasHash();
        testListaPartidasEquals();
        testListPartidasSet();
    }

    @Test
    public void testListPartidas(){
        String uuid = UUIDProvider.getNew();
        Partida partida = Partida.create().setId(uuid).setDataInicio(new Date()).commit();
        partidaDAO.insiraOuAtualize(partida);
        List<Partida> partidas= partidaDAO.getAll();
        assertNotNull(partidas);
        assertTrue(partidas.size() > 0);
    }

    private void testListPartidasSet(){
        List<Partida> partidas= partidaDAO.getAll();
        Set<Partida> setPartidas = new HashSet<>(partidas);
        assert(setPartidas.size()==partidas.size());
    }

    private void testListaPartidasHash(){
        List<Partida> partidas = partidaDAO.getAll();
        for (Partida partida: partidas){
            for (Partida partida2: partidas){
                if (partida==partida2) continue;
                assert(partida.hashCode()!=partida2.hashCode());
            }
        }
        assert(true);
    }

    private void testListaPartidasEquals(){
        List<Partida> partidas = partidaDAO.getAll();
        for (Partida partida: partidas){
            for (Partida partida2: partidas){
                if (partida==partida2) continue;
                if (partida.equals(partida2)){
                    assert (false);
                }
            }
        }
        assert(true);
    }

    private void testListaPartidasEqualIds(){
        List<Partida> partidas = partidaDAO.getAll();
        for (Partida partida: partidas){
            for (Partida partida2: partidas){
                if (partida==partida2) continue;
                if (partida.getId().equals(partida2.getId())){
                    assert (false);
                }
            }
        }
        assert(true);
    }

}
