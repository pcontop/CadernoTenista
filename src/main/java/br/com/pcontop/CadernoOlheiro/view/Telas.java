package br.com.pcontop.CadernoOlheiro.view;

import android.app.Fragment;
import br.com.pcontop.CadernoOlheiro.view.partidaDisplay.PartidaDisplayMainFragment;
import br.com.pcontop.CadernoOlheiro.view.partidaDisplay.PartidaDisplayVideoFragment;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 22/11/13
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public enum Telas {
    PARTIDA {Fragment getTela() { return PartidaMainFragment.getInstance();}},
    LISTA_PARTIDAS {Fragment getTela() { return ListaPartidasMainFragment.getInstance();}},
    DISPLAY_PARTIDA {Fragment getTela() { return PartidaDisplayMainFragment.getInstance();}},
    DISPLAY_VIDEO_PARTIDA {Fragment getTela() { return PartidaDisplayVideoFragment.getInstance();}},
    SOBRE {Fragment getTela() { return SobreFragment.getInstance();}};

    abstract Fragment getTela();
}
