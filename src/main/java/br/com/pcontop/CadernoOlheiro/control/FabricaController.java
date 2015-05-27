package br.com.pcontop.CadernoOlheiro.control;

import android.content.Context;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerStateType;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 02/11/13
 * time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class FabricaController {
    private static OlheiroController olheiroController;
    public static OlheiroController getOlheiroController(Context context){
        if (olheiroController==null){
            olheiroController = new OlheiroController(context);
        }
        return olheiroController;
    }

    /**
     * Provavelmente será descartada.
     * User: Paulo
     * Date: 01/11/13
     * time: 16:48
     * To change this template use File | Settings | File Templates.
     */
    public static interface TimerController {
        public Date getDataInicioPrimeiroTempo();

        public Date getDataInicioSegundoTempo();

        public Date getDataFimPrimeiroTempo();

        public Date getDataFimSegundoTempo();

        public TimerStateType getEstadoJogo();
    }
}
