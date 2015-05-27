package br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.relative;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;
import br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.JogadoresFragment;

import java.util.List;

/**
 * Created by PauloBruno on 12/01/14.
 */
public class JogadoresFragmentLinearImpl extends Fragment implements JogadoresFragment {
    private static LayoutInflater inflater;
    private static ViewGroup layoutPasses;
    private ScrollView scrollView;

    private static OlheiroController olheiroController;

    public JogadoresFragmentLinearImpl(){

    }


    public static JogadoresFragmentLinearImpl getInstance(){
        return new JogadoresFragmentLinearImpl();
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
        monteViewJogadores();
        layoutJogadoresMainLayout.addView(scrollView);
        return layoutJogadoresMainLayout;
    }

    private ViewGroup monteViewJogadores() {
        if (scrollView ==null){
            scrollView =  (ScrollView) inflater.inflate(R.layout.jogadores_scroll_layout, null, false);
        }
        LinearLayout viewJogadores = (LinearLayout) scrollView.findViewById(R.id.jogadores_linear_layout);
        viewJogadores.removeAllViews();
        adicioneJogadores2(viewJogadores);
        return scrollView;
    }

    private void adicioneJogadores(LinearLayout viewJogadores) {
        LinearLayout jogadoresLinha = novoJogadoresLinha();
        int numCols = getActivity().getResources().getInteger(R.integer.list_num_columns);
        int posicaoLinha=0;
        for (int i=0;i<getJogadores().size();i++){
            posicaoLinha= i % numCols;
            if (i!=0 && posicaoLinha==0){
                viewJogadores.addView(jogadoresLinha);
                jogadoresLinha = novoJogadoresLinha();
            }
            Jogador jogador = getJogadores().get(i);
            JogadorViewItem jogadorViewItem = new JogadorViewItem(getActivity(), inflater, olheiroController);
            jogadorViewItem.updateItemDisplay(jogador);
            jogadoresLinha.addView(jogadorViewItem);
            posicaoLinha++;
        }
        //Adiciona views vazias para igualar colunas com menos jogadores.
        if (getJogadores().size()>0 && posicaoLinha<numCols){
            for (int i=posicaoLinha;i<numCols;i++){
                jogadoresLinha.addView(getJogadorPlaceholder());
            }
        }
        viewJogadores.addView(jogadoresLinha);
    }

    private void adicioneJogadores2(LinearLayout viewJogadores) {
        LinearLayout jogadoresLinha = novoJogadoresLinha2();
        int numCols = getActivity().getResources().getInteger(R.integer.list_num_columns);
        int posicaoLinha=0;
        for (int i=0;i<getJogadores().size();i++){
            posicaoLinha= i % numCols;
            if (i!=0 && posicaoLinha==0){
                viewJogadores.addView(jogadoresLinha);
                jogadoresLinha = novoJogadoresLinha2();
            }
            Jogador jogador = getJogadores().get(i);
            JogadorViewItem jogadorViewItem = new JogadorViewItem(getActivity(), inflater, olheiroController);
            jogadorViewItem.updateItemDisplay(jogador);
            ViewGroup viewGrid;
            switch (posicaoLinha){
                case 0:
                    viewGrid = (ViewGroup) jogadoresLinha.findViewById(R.id.jogador_coluna_1);
                    break;
                case 1:
                    viewGrid = (ViewGroup) jogadoresLinha.findViewById(R.id.jogador_coluna_2);
                    break;
                default:
                    viewGrid=null;
            }
            viewGrid.addView(jogadorViewItem);
            posicaoLinha++;
        }
        //Adiciona views vazias para igualar colunas com menos jogadores.
        if (getJogadores().size()>0 && posicaoLinha<numCols){
            for (int i=posicaoLinha;i<numCols;i++){
                ViewGroup viewGrid;
                switch (posicaoLinha){
                    case 0:
                        viewGrid = (ViewGroup) jogadoresLinha.findViewById(R.id.jogador_coluna_1);
                        break;
                    case 1:
                        viewGrid = (ViewGroup) jogadoresLinha.findViewById(R.id.jogador_coluna_2);
                        break;
                    default:
                        viewGrid=null;
                }
                viewGrid.addView(getJogadorPlaceholder());
            }
        }
        viewJogadores.addView(jogadoresLinha);
    }


    private LinearLayout novoJogadoresLinha2(){
        return(LinearLayout) inflater.inflate(R.layout.jogadores_linha_2, null);
    }

    private LinearLayout novoJogadoresLinha(){
        return(LinearLayout) inflater.inflate(R.layout.jogadores_linha, null);
    }

    public void redesenhe(){
        monteViewJogadores();

    }

    private void addJogador() {
        olheiroController.addNewJogador();
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

    private List<Jogador> getJogadores(){
        return olheiroController.getJogadoresPartida();
    }


    public View getJogadorPlaceholder() {
        View view = inflater.inflate(R.layout.jogador_view_vazia,null);
        return view;
    }
}
