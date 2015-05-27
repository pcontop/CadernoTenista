package br.com.pcontop.CadernoOlheiro.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.pcontop.CadernoOlheiro.R;

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
    public void setParams(Map<ParametroTela, Object> params) {

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
