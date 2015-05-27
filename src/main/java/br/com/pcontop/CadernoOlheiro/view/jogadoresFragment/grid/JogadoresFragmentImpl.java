package br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.grid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TipoEvento;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;
import br.com.pcontop.CadernoOlheiro.control.disabler.CanBeDisabled;
import br.com.pcontop.CadernoOlheiro.control.disabler.ExecuteEnablerDisabler;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerState;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerWatcher;
import br.com.pcontop.CadernoOlheiro.view.components.ExpandableHeightGridView;
import br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.JogadoresFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 30/10/13
 * time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public class JogadoresFragmentImpl extends Fragment implements JogadoresFragment {
    private static LayoutInflater inflater;
    private static ViewGroup layoutPasses;
    private static PasseGridAdapter passeGridAdapter;
    private static GridView gridJogadores;

    private static OlheiroController olheiroController;

    public JogadoresFragmentImpl(){

    }


    public static JogadoresFragmentImpl getInstance(){
        return new JogadoresFragmentImpl();
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
        passeGridAdapter = new PasseGridAdapter(olheiroController,getActivity(), inflater);
        gridJogadores.setAdapter(passeGridAdapter);
        return gridJogadores;
    }

    public void redesenhe(){
        passeGridAdapter.notifyDataSetChanged();
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

    /**
     * Created with IntelliJ IDEA.
     * User: PauloBruno
     * Date: 05/11/13
     * time: 21:12
     * To change this template use File | Settings | File Templates.
     */
    public class PasseGridAdapter extends BaseAdapter {
        private final OlheiroController olheiroController;
        private final LayoutInflater inflater;
        private final Context context;

        public PasseGridAdapter(OlheiroController olheiroController, Context context, LayoutInflater inflater){
            this.olheiroController = olheiroController;
            this.inflater = inflater;
            this.context=context;
        }

        @Override
        public int getCount() {
            return olheiroController.getJogadoresPartida().size();
        }

        @Override
        public Object getItem(int i) {
            return olheiroController.getJogadoresPartida().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            try {
                Jogador jogador = olheiroController.getJogadoresPartida().get(i);
                return new PasseView(context, inflater, jogador);
            }  catch (ArrayIndexOutOfBoundsException e) {
                Log.e("CadernoOlheiro", "Erro na busca do jogador " + i,e);
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return olheiroController.getJogadoresPartida().isEmpty();
        }

        private class PasseView extends LinearLayout implements TimerWatcher, CanBeDisabled {
            final Jogador jogador;
            List<ViewGroup> botoes = new ArrayList<>();

            public PasseView(Context context, LayoutInflater inflater, Jogador jogador) {
                super(context);
                this.jogador = jogador;
                inflater.inflate(R.layout.jogador_layout, this, true);
                definaCor(jogador);
                definaComportamento();
                olheiroController.addTimerWatcher(this);
            }

            private void definaCor(Jogador jogador) {
                int cor = olheiroController.getCorTime(jogador);
                this.setBackgroundResource(R.drawable.jogador);
                GradientDrawable shapeDrawable = (GradientDrawable) this.getBackground();
                shapeDrawable.setColor(cor);

            }

            private void definaComportamento() {
                final String textoDescritor = jogador.getNome();
                TextView textView = (TextView) this.findViewById(R.id.nome_jogador);
                textView.setText(textoDescritor);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                        popupMenu.getMenuInflater().inflate(R.menu.context_menu_passe_jogador, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                int menuItemIndex = menuItem.getItemId();
                                switch (menuItemIndex) {
                                    case R.id.context_menu_passe_jogador_alterar_nome:
                                        olheiroController.alterarJogador(jogador);
                                        return true;
                                    case R.id.context_menu_passe_jogador_delete:
                                        olheiroController.removaJogador(jogador);
                                        return true;
                                    case R.id.context_menu_passe_jogador_alterar_tipos_passe:
                                        olheiroController.alterarTiposPasse(jogador);
                                        return true;
                                    default:
                                        //
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                }
                );
                adicioneBotoes();

            }

            private void adicioneBotoes() {
                for (TipoEvento tipoEvento: olheiroController.getTiposEventosJogadoresSelecionados(jogador)){
                    final TipoEvento tipoEventoPassar = tipoEvento;
                    final ViewGroup externalButton = (ViewGroup) inflater.inflate(R.layout.botao_evento, null, false);
                    final TextView quantidadeEventos = (TextView) externalButton.findViewById(R.id.jogador_quantidade_eventos);
                    atualizeContagemEvento(tipoEventoPassar, jogador, quantidadeEventos);
                    Button botaoEvento = getButtonFromLayout(externalButton);
                    botaoEvento.setText(olheiroController.getStringDeNomeRef(tipoEvento.getDescricao()));
                    botaoEvento.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            olheiroController.addEvento(tipoEventoPassar, jogador, new Date());
                            atualizeContagemEvento(tipoEventoPassar, jogador, quantidadeEventos);
                        }
                    });
                    botoes.add(externalButton);
                }
                ExpandableHeightGridView gridBotoes = (ExpandableHeightGridView) this.findViewById(R.id.jogador_grid_botoes);
                gridBotoes.setExpanded(true);
                gridBotoes.setAdapter(new BotoesAdapter());
            }

            private void atualizeContagemEvento(TipoEvento tipoEventoPassar, Jogador jogador, TextView quantidadeEventos) {
                int quantidade = olheiroController.getQuantidadeEventos(jogador, tipoEventoPassar);
                quantidadeEventos.setText(quantidade+"");
            }

            private Button getButtonFromLayout(ViewGroup externalButton) {
                return (Button) externalButton.findViewById(R.id.jogador_botao_evento);
            }


            @Override
            public void activate(TimerState timerState) {
                ExecuteEnablerDisabler executeEnablerDisabler;
                executeEnablerDisabler = olheiroController.getHabilitacaoPassesEmTempos().get(timerState.getTimerStateType());
                executeEnablerDisabler.execute(this);

            }

            private void enableButton(ViewGroup externalButton) {
                Button button = getButtonFromLayout(externalButton);
                button.setEnabled(true);
            }

            private void disableButton(ViewGroup externalButton) {
                Button button = getButtonFromLayout(externalButton);
                button.setEnabled(false);
            }

            @Override
            public void enable() {
                for (ViewGroup externalButton:botoes){
                    enableButton(externalButton);
                }
            }

            @Override
            public void disable() {
                for (ViewGroup externalButton:botoes){
                    disableButton(externalButton);
                }
            }

            @Override
            protected void onDetachedFromWindow() {
                super.onDetachedFromWindow();
                olheiroController.removeTimerWatcher(this);
            }

            private class BotoesAdapter extends BaseAdapter{

                @Override
                public int getCount() {
                    return botoes.size();
                }

                @Override
                public Object getItem(int i) {
                    return botoes.get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    return botoes.get(i);
                }
            }

        }

    }

}
