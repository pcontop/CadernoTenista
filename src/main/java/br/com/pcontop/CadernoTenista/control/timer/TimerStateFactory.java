package br.com.pcontop.CadernoTenista.control.timer;

import br.com.pcontop.CadernoTenista.control.OlheiroController;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFactory {

    public static TimerState busque(OlheiroController olheiroController){
        switch(olheiroController.getTempoPartidaAtual().getTipoTempoPartida()){
            case ANTES_PARTIDA:
                return new TimerStateInicial().setOlheiroController(olheiroController);
            case PRIMEIRO_SET:case SEGUNDO_SET:case TERCEIRO_SET:case QUARTO_SET: case QUINTO_SET:case SEXTO_SET:case SETIMO_SET:
                return new TimerStateSetEmAndamento().setOlheiroController(olheiroController);
            case PRIMEIRO_INTERVALO:case SEGUNDO_INTERVALO:case TERCEIRO_INTERVALO:case QUARTO_INTERVALO: case QUINTO_INTERVALO:case SEXTO_INTERVALO:
                return new TimerStateFimSet().setOlheiroController(olheiroController);
            case APOS_JOGO:
                return new TimerStateFinal().setOlheiroController(olheiroController);
            default:
                return null;
        }
    }


    public static TimerState crie(OlheiroController olheiroController, TimerState lastTimerState){
        return busque(olheiroController).inicialize(lastTimerState);
    }

    public static TimerState recupere(OlheiroController olheiroController){
        return busque(olheiroController);
    }

}
