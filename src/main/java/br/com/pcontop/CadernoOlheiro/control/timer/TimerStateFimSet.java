package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFimSet extends TimerStateAdapter {

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        getTimerFragment().setBotaoTimerTexto(R.string.iniciar_set);
        getTimerFragment().setTimerValue("00:00:00");
        return this;
    }

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.INTERVALO_SET;
    }

    @Override
    public TimerState recuperarDescanso(TimerFragment timerFragment) {
        super.recuperarDescanso(timerFragment);
        timerFragment.setBotaoTimerTexto(R.string.iniciar_set);
        timerFragment.setTimerValue("00:00:00");
        return this;
    }

    @Override
    public void destroy() {
        //
    }
}
