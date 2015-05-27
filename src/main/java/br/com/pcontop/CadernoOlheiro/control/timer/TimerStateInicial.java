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
    public TimerState getProximoEstado() {
        return TimerStateFactory.crie(olheiroController, TimerStateType.PRIMEIRO_TEMPO_EM_ANDAMENTO, this);
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.INICIAL;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        super.recuperar(timerFragment);
        inicializeDisplay();
        return this;
    }

    private void inicializeDisplay() {
        getTimerFragment().setBotaoTimerTexto(R.string.inicio_de_jogo);
        getTimerFragment().setTimerValue("00:00:00");
    }

    @Override
    public void destroy() {
        //
    }
}
