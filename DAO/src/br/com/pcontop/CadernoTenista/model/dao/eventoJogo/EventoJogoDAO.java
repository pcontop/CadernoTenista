package br.com.pcontop.CadernoTenista.model.dao.eventoJogo;

import br.com.pcontop.CadernoTenista.bean.EventoPartida;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public interface EventoJogoDAO {

    public EventoPartida get(String id);
    public void remova(EventoPartida eventoPartida);

}
