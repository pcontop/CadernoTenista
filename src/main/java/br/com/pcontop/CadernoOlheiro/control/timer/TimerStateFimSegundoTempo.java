package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFimSegundoTempo extends TimerStateAdapter {
    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        olheiroController.setDataFimSegundoTempo(new Date());
        return transiteProximoEstado();
    }

    @Override
    public TimerState transiteProximoEstado() {
        //TimerState proximoEstado = super.transiteProximoEstado();
        olheiroController.apresenteResultadoPartidaAtual();
        return null;
    }

    @Override
    public TimerState getProximoEstado() {
        return TimerStateFactory.crie(olheiroController, TimerStateType.INICIAL, this);
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.FIM_SEGUNDO_TEMPO;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        super.recuperar(timerFragment);
        return transiteProximoEstado();
    }

    @Override
    public void destroy() {
        //
    }
}
