package br.com.pcontop.CadernoTenista.view.jogadoresFragment.autoGridView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.view.jogadoresFragment.JogadoresFragment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 30/10/13
 * time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public class JogadoresFragmentGridResizebleImpl extends Fragment implements JogadoresFragment {
    private static LayoutInflater inflater;
    private static ViewGroup layoutPasses;
    private static JogadoresAutoMeasureAdapter jogadoresAutoMeasureAdapter;
    private static GridView gridJogadores;

    private static OlheiroController olheiroController;

    public JogadoresFragmentGridResizebleImpl(){

    }


    public static JogadoresFragmentGridResizebleImpl getInstance(){
        return new JogadoresFragmentGridResizebleImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        layoutPasses = monteJogadoresLayout(container);
        return layoutPasses;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        olheiroController.removePasseFragment();
    }

    private ViewGroup monteJogadoresLayout(ViewGroup container){
        ViewGroup layoutJogadoresMainLayout = (ViewGroup) inflater.inflate(R.layout.jogadores_fragment_main_layout,null, false);
        Button botaoAdicioneJogadores = (Button) layoutJogadoresMainLayout.findViewById(R.id.passe_fragment_adicione_jogador);
        botaoAdicioneJogadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJogador();
            }
        });
        gridJogadores = monteGridJogadores(layoutJogadoresMainLayout);
        layoutJogadoresMainLayout.addView(gridJogadores);
        return layoutJogadoresMainLayout;
    }

    private GridView monteGridJogadores(ViewGroup container) {
        GridView gridJogadores =  (GridView) inflater.inflate(R.layout.jogadores_grid_layout, null, false);
        jogadoresAutoMeasureAdapter = new JogadoresAutoMeasureAdapter(getActivity(), inflater, olheiroController);
        JogadorGridItem.initItemLayout(getContext().getResources().getInteger(R.integer.list_num_columns), getJogadores().size());
        gridJogadores.setAdapter(jogadoresAutoMeasureAdapter);
        return gridJogadores;
    }

    public void redesenhe(){
        jogadoresAutoMeasureAdapter.notifyDataSetChanged();
    }

    private void addJogador() {
        olheiroController.addNewJogador();
    }

    private List<Jogador> getJogadores(){
        return olheiroController.getJogadoresPartida();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        olheiroController = FabricaController.getOlheiroController(getActivity());
        olheiroController.setJogadoresFragment(this);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
