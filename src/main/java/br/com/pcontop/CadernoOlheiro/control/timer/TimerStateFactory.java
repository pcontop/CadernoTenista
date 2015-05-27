package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.control.OlheiroController;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFactory {
    private static TimerStateType TIMER_STATE_PADRAO =TimerStateType.INICIAL;


    private static TimerState busque(TimerStateType timerStateType, OlheiroController olheiroController){
        switch(timerStateType){
            case INICIAL:
                return new TimerStateInicial().setOlheiroController(olheiroController);
            case PRIMEIRO_TEMPO_EM_ANDAMENTO:
                return new TimerStatePrimeiroTempoEmAndamento().setOlheiroController(olheiroController);
            case FIM_PRIMEIRO_TEMPO:
                return new TimerStateFimPrimeiroTempo().setOlheiroController(olheiroController);
            case SEGUNDO_TEMPO_EM_ANDAMENTO:
                return new TimerStateSegundoTempoEmAndamento().setOlheiroController(olheiroController);
            case FIM_SEGUNDO_TEMPO:
                return new TimerStateFimSegundoTempo().setOlheiroController(olheiroController);
            default:
                return busqueInicial(olheiroController);
        }
    }

    private static TimerState busqueInicial(OlheiroController olheiroController){
        return busque(TIMER_STATE_PADRAO, olheiroController);
    }

    public static TimerState crie(OlheiroController olheiroController, TimerState lastTimerState){
        return busqueInicial(olheiroController).inicialize(lastTimerState);
    }

    public static TimerState recupere(OlheiroController olheiroController, int numeroEstado){
        TimerStateType type = TimerStateType.values()[numeroEstado];
        return busque(type, olheiroController);
    }

    public static TimerState crie(OlheiroController olheiroController, TimerStateType tipoEstado, TimerState lastTimerState){
        TimerState timerState = busque(tipoEstado, olheiroController);
        return timerState.inicialize(lastTimerState);
    }
}
