package br.com.pcontop.CadernoTenista.model.dao.partida;

import br.com.pcontop.CadernoTenista.bean.Partida;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface PartidaDAO {

    public Partida get(String id);
    public void insiraOuAtualize(Partida partida);
    public void remova(Partida partida);
    public List<Partida> getAll();

    List<Partida> getAllDataInicioNotEmpty();

    public void removaPartidasNaoIniciadasMenosAtual();
    public void removaTodosRegistros();

}
