package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFimPrimeiroTempo extends TimerStateAdapter {

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        getTimerFragment().setBotaoTimerTexto(R.string.inicio_segundo_tempo);
        getTimerFragment().setTimerValue("00:00:00");
        olheiroController.setDataInicioSegundoTempo(null);
        olheiroController.setDataFimPrimeiroTempo(new Date());
        return this;
    }

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }

    @Override
    public TimerState getProximoEstado() {
        return TimerStateFactory.crie(olheiroController, TimerStateType.SEGUNDO_TEMPO_EM_ANDAMENTO, this);
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.FIM_PRIMEIRO_TEMPO;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        super.recuperar(timerFragment);
        timerFragment.setBotaoTimerTexto(R.string.inicio_segundo_tempo);
        timerFragment.setTimerValue("00:00:00");
        return this;
    }

    @Override
    public void destroy() {
        //
    }
}
