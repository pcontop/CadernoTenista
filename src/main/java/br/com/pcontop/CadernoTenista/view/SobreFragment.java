package br.com.pcontop.CadernoTenista.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;

import java.util.Map;

/**
 * Created by PauloBruno on 14/01/14.
 */
public class SobreFragment extends Fragment implements TelaPrincipal {
    @Override
    public Telas getTela() {
        return Telas.SOBRE;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        OlheiroController olheiroController = FabricaController.getOlheiroController(this.getActivity());
        olheiroController.setTelaAtual(this);
    }

    @Override
    public void setParams(Map<ParametroTela, Object> params) {

    }

    @Override
    public void refresh() {

    }

    public static SobreFragment getInstance(){
        return new SobreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sobre,null);
        return view;
    }
}
