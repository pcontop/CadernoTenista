package br.com.pcontop.CadernoOlheiro.model.dao;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.model.dao.eventoJogo.EventoJogoDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.eventoJogo.EventoJogoDaoNeodatis;
import br.com.pcontop.CadernoOlheiro.model.dao.jogador.JogadorDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.jogador.JogadorDaoNeodatis;
import br.com.pcontop.CadernoOlheiro.model.dao.localidade.eventoJogo.LocalidadeDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.localidade.eventoJogo.LocalidadeDaoNeodatis;
import br.com.pcontop.CadernoOlheiro.model.dao.partida.PartidaDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.partida.PartidaDaoNeodatis;
import br.com.pcontop.CadernoOlheiro.model.dao.time.TimeDAO;
import br.com.pcontop.CadernoOlheiro.model.dao.time.TimeDaoNeodatis;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 09/11/13
 * time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class DAOFactory {

    private static JogadorDAO jogadorDAO;
    public static JogadorDAO getJogadorDAO(Context context) {
        if (jogadorDAO==null){
            jogadorDAO = new JogadorDaoNeodatis(context);
        }
        return jogadorDAO;
    }


    private static EventoJogoDAO eventoJogoDAO;
    public static EventoJogoDAO getEventoJogoDAO(Context context) {
        if (eventoJogoDAO==null){
            eventoJogoDAO = new EventoJogoDaoNeodatis(context);
        }
        return eventoJogoDAO;
    }

    private static PartidaDAO partidaDAO;
    public static PartidaDAO getPartidaDAO(Context context) {
        if (partidaDAO==null){
            partidaDAO = new PartidaDaoNeodatis(context);
        }
        return partidaDAO;
    }

    private static TimeDAO timeDAO;
    public static TimeDAO getTimeDAO(Context context) {
        if (timeDAO==null){
            timeDAO = new TimeDaoNeodatis(context);
        }
        return timeDAO;
    }

    private static LocalidadeDAO localidadeDAO;
    public static LocalidadeDAO getLocalidadeDAO(Context context) {
        if (localidadeDAO==null){
            localidadeDAO = new LocalidadeDaoNeodatis(context);
        }
        return localidadeDAO;
    }

    public static void removeDAOFile(Context context) {
        ODBProvider.removeDAOFile(context);
    }
}
