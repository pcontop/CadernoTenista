package br.com.pcontop.CadernoOlheiro.view.partidaDisplay;

import android.app.Activity;
import br.com.pcontop.CadernoOlheiro.bean.Partida;

/**
 * Created by PauloBruno on 03/02/14.
 */
public interface PartidaDisplay {
    public Partida getPartida();
    public Activity getActivity();
    public void refreshDisplay();
}
