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
public class TimerStateSegundoTempoEmAndamento extends TimerStateAdapter {
    TimerTask task;
    private final Handler handler = new Handler();

    @Override
    public TimerState inicialize(TimerState lastTimerState) {
        super.inicialize(lastTimerState);
        definaBotao();
        olheiroController.setDataInicioSegundoTempo(new Date());
        inicieTimer();
        return this;
    }

    private void definaBotao(){
        getTimerFragment().setBotaoTimerTexto(R.string.fim_de_jogo);
    }

    @Override
    public TimerState transiteProximoEstado() {
        pareTimer();
        TimerState proximoEstado = super.transiteProximoEstado();
        return proximoEstado;
    }

    @Override
    public TimerState getProximoEstado() {
        return TimerStateFactory.crie(olheiroController, TimerStateType.FIM_SEGUNDO_TEMPO, this);
    }

    @Override
    public TimerStateType getTimerStateType() {
        return TimerStateType.SEGUNDO_TEMPO_EM_ANDAMENTO;
    }

    @Override
    public TimerState recuperar(TimerFragment timerFragment) {
        super.recuperar(timerFragment);
        Date dataInicio = olheiroController.getDataInicioSegundoTempo();
        definaBotao();
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
        if (olheiroController.getDataInicioSegundoTempo()!=null){
            long diferenca= new Date().getTime() - olheiroController.getDataInicioSegundoTempo().getTime();
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
