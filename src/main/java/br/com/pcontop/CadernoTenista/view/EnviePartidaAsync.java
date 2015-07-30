package br.com.pcontop.CadernoTenista.view;

import android.app.Activity;
import android.os.AsyncTask;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.control.OlheiroController;

/**
* Created by PauloBruno on 07/07/2014.
*/
public class EnviePartidaAsync extends AsyncTask<Partida,Integer,Boolean> {

    private final OlheiroController olheiroController;
    private final Activity activity;

    public EnviePartidaAsync(Activity activity, OlheiroController olheiroController){
        this.olheiroController = olheiroController;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Partida... partidas) {
            return olheiroController.exportePartida(activity, partidas[0]);
    }
}
