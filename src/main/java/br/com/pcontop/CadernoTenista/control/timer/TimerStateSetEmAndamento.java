package br.com.pcontop.CadernoTenista.control.timer;

import android.os.Handler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.view.TimerFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class TimerStateSetEmAndamento extends TimerStateAdapter {
    TimerTask task;
    private final Handler handler = new Handler();

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        setTextoBotao(getTimerFragment());
        inicieTimer();
        return this;
    }

    private void setTextoBotao(TimerFragment timerFragment) {
        timerFragment.setBotaoTimerTexto(R.string.terminar_set);
    }

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.SET_EM_ANDAMENTO;
    }

    @Override
    public TimerState recuperarDescanso(TimerFragment timerFragment) {
        super.recuperarDescanso(timerFragment);
        setTextoBotao(timerFragment);
        inicieTimer();
        return this;
    }

    private void pareTimer() {
        if (getTimerFragment()!=null){
            Timer timerAtual = getTimerFragment().getTimer();
            if (timerAtual!=null){
                timerAtual.cancel();
            }
        }
    }

    private void inicieTimer() {
        if (getTimerFragment().getTimer()!=null ){
            pareTimer();
        }
        Timer timerAtual = new Timer();
        getTimerFragment().setTimer(timerAtual);
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        atualizeRelogio();
                    }
                });
            }};

        timerAtual.schedule(task, updateInterval, updateInterval);
    }

    private void atualizeRelogio() {
        if (olheiroController.getDataInicioTempoAtual()!=null){
            long diferenca= new Date().getTime() - olheiroController.getDataInicioTempoAtual().getTime();
            Date tempoPercorrido = new Date();
            tempoPercorrido.setTime(diferenca);
            String textoDisplay = dateFormatDisplay.format(tempoPercorrido);
            getTimerFragment().setTimerValue(textoDisplay);
        }
    }

    @Override
    public void destroy() {
        pareTimer();
    }
}
