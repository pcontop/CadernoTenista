package br.com.pcontop.CadernoTenista.control.timer;

import br.com.pcontop.CadernoTenista.view.TimerFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public interface TimerState {
    public TimerState inicialize(TimerState lastTimerState);
    public TimerState transiteProximoEstado();
    public TimerState transiteFimDePartida();
    public TimerStateType getTimerStateType();
    public TimerState recuperarDescanso(TimerFragment timerFragment);
    public void updateTimerWatchers();
    public void destroy();
}
