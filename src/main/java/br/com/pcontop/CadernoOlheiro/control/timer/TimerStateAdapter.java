package br.com.pcontop.CadernoOlheiro.control.timer;

import br.com.pcontop.CadernoOlheiro.control.OlheiroController;
import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class TimerStateAdapter implements TimerState {
    protected static final SimpleDateFormat dateFormatDisplay = new SimpleDateFormat("HH:mm:ss");
    protected long updateInterval=100;
    protected OlheiroController olheiroController;
    static
    {
        dateFormatDisplay.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public TimerFragment getTimerFragment() {
        return olheiroController.getTimerFragment();
    }

    public TimerStateAdapter setOlheiroController(OlheiroController olheiroController){
        this.olheiroController=olheiroController;
        return this;
    }

    protected TimerStateAdapter(){
    }

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        inheritObjects(lastTimerState);
        updateTimerWatchers();
        return this;
    }

    private void inheritObjects(TimerState lastTimerState) {
    }

    @Override
    public void updateTimerWatchers() {
        for (TimerWatcher timerWatcher: olheiroController.getTimerWatcherList()){
            timerWatcher.activate(this);
        }
    }

    public abstract TimerState getProximoEstado();

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = getProximoEstado();
        this.destroy();
        return proximoEstado;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        recuperarObjetos(timerFragment);
        return null;
    }

    private void recuperarObjetos(TimerFragment timerFragment) {
    }

}
