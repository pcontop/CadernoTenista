package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateInicial extends TimerStateAdapter {

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        olheiroController.criePartida();
        inicializeDisplay();
        return this;
    }

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }


    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.INICIAL;
    }

    @Override
    public TimerState recuperarDescanso(TimerFragment timerFragment) {
        super.recuperarDescanso(timerFragment);
        inicializeDisplay();
        return this;
    }

    private void inicializeDisplay() {
        getTimerFragment().setBotaoTimerTexto(R.string.iniciar_partida);
        getTimerFragment().setTimerValue("00:00:00");
    }

    @Override
    public void destroy() {
        //
    }
}
