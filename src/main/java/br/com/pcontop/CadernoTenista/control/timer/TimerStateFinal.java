package br.com.pcontop.CadernoTenista.control.timer;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.view.TimerFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateFinal extends TimerStateAdapter {
    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        getTimerFragment().setBotaoTimerTexto(R.string.iniciar_partida);
        return transiteProximoEstado();
    }

    @Override
    public TimerState transiteProximoEstado() {
        //TimerState proximoEstado = super.transiteProximoEstado();
        olheiroController.apresenteResultadoPartidaAtual();
        return null;
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.FINAL;
    }

    @Override
    public TimerState recuperarDescanso(TimerFragment timerFragment) {
        super.recuperarDescanso(timerFragment);
        return transiteProximoEstado();
    }

    @Override
    public void destroy() {
        //
    }
}
