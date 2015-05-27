package br.com.pcontop.CadernoOlheiro.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;
import br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.relative.JogadoresFragmentLinearImpl;

import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 12/11/13
 * time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class PartidaMainFragment extends Fragment implements TelaPrincipal {
    private OlheiroController olheiroController;
    private static final String TIMER_FRAGMENT = "Fragment_Timer";
    private static final String JOGADORES_FRAGMENT="Fragment Jogadores";

    private final static Telas tela = Telas.PARTIDA;

    public Telas getTela(){
        return tela;
    }

    @Override
    public void setParams(Map<ParametroTela, Object> params) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        olheiroController = FabricaController.getOlheiroController(getActivity());
        olheiroController.setPartidaMainFragment(this);
    }

    public PartidaMainFragment(){}

    public static PartidaMainFragment getInstance(){
        return new PartidaMainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partida_main,null,false);
        if (savedInstanceState == null) {
            TimerFragmentImpl timerFragmentImpl = TimerFragmentImpl.getInstance();
            getFragmentManager().beginTransaction().replace(R.id.timer_fragment, timerFragmentImpl, TIMER_FRAGMENT).commit();
            //JogadoresFragmentImpl jogadoresFragmentImpl = JogadoresFragmentImpl.getInstance();
            //JogadoresFragmentGridResizebleImpl jogadoresFragmentImpl = JogadoresFragmentGridResizebleImpl.getInstance();
            JogadoresFragmentLinearImpl jogadoresFragmentImpl = JogadoresFragmentLinearImpl.getInstance();

            getFragmentManager().beginTransaction().replace(R.id.passe_fragment, jogadoresFragmentImpl, JOGADORES_FRAGMENT).commit();
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (olheiroController!=null){
            olheiroController.removePartidaMainFragment();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        setRetainInstance(true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.partida, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
