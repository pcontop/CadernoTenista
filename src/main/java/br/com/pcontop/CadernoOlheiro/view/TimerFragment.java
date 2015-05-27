package br.com.pcontop.CadernoOlheiro.view;

import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 04/11/13
 * time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public interface TimerFragment {
    public void setBotaoTimerTexto(int ResourceId);
    public void setTimerValue(String texto);
    public Timer getTimer();
    public void setTimer(Timer timer);
}
