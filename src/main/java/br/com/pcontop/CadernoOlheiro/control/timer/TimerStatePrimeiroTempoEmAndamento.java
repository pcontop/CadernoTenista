package br.com.pcontop.CadernoOlheiro.control.timer;

import android.os.Handler;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.view.TimerFragment;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 01/11/13
 * time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class TimerStatePrimeiroTempoEmAndamento extends TimerStateAdapter {
    TimerTask task;
    private final Handler handler = new Handler();

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        olheiroController.setDataInicioPrimeiroTempo(new Date());
        setTextoBotao(getTimerFragment());
        inicieTimer();
        return this;
    }

    private void setTextoBotao(TimerFragment timerFragment) {
        timerFragment.setBotaoTimerTexto(R.string.fim_primeiro_tempo);
    }

    @Override
    public TimerState transiteProximoEstado() {
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }


    @Override
    public TimerState getProximoEstado() {
        pareTimer();
        return TimerStateFactory.crie(olheiroController, TimerStateType.FIM_PRIMEIRO_TEMPO, this);
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.PRIMEIRO_TEMPO_EM_ANDAMENTO;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        super.recuperar(timerFragment);
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
        if (olheiroController.getDataInicioPrimeiroTempo()!=null){
            long diferenca= new Date().getTime() - olheiroController.getDataInicioPrimeiroTempo().getTime();
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
