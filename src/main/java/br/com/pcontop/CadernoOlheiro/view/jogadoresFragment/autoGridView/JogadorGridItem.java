package br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.autoGridView;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.Jogador;
import br.com.pcontop.CadernoOlheiro.bean.TiposEvento;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;
import br.com.pcontop.CadernoOlheiro.control.disabler.CanBeDisabled;
import br.com.pcontop.CadernoOlheiro.control.disabler.ExecuteEnablerDisabler;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerState;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerWatcher;
import br.com.pcontop.CadernoOlheiro.view.components.GridViewItemLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PauloBruno on 09/01/14.
 */
public class JogadorGridItem extends GridViewItemLayout implements TimerWatcher, CanBeDisabled {
    private static Context context;
    private static LayoutInflater inflater;
    private static OlheiroController olheiroController;
    private Jogador jogador;
    private List<ViewGroup> botoes = new ArrayList<>();

    // Public constructor
    public JogadorGridItem(Context context) {
        super(context);
    }

    // Public constructor
    public JogadorGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JogadorGridItem(Context context, LayoutInflater inflater, OlheiroController olheiroController){
        super(context);
        this.context = context;
        this.inflater = inflater;
        this.olheiroController = olheiroController;
    }

    public void updateItemDisplay(Jogador jogador){
        inflater.inflate(R.layout.jogador_layout_com_linear, this, true);
        this.jogador = jogador;
        definaCor();
        definaComportamento();
        olheiroController.addTimerWatcher(this);
    }

    private void definaCor() {
        int cor = olheiroController.getCorTime(jogador);
        this.setBackgroundResource(R.drawable.jogador);
        GradientDrawable shapeDrawable = (GradientDrawable) this.getBackground();
        shapeDrawable.setColor(cor);
        //this.setBackgroundColor(cor);
    }

    private void definaComportamento() {
        final String textoDescritor = jogador.getNome();
        TextView textView = (TextView) this.findViewById(R.id.nome_jogador);
        textView.setText(textoDescritor);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
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
        for (TiposEvento tiposEvento : olheiroController.getTiposEventosJogadoresSelecionados(jogador)){
            final TiposEvento tiposEventoPassar = tiposEvento;
            final ViewGroup externalButton = (ViewGroup) inflater.inflate(R.layout.botao_evento, null, false);
            final TextView quantidadeEventos = (TextView) externalButton.findViewById(R.id.jogador_quantidade_eventos);
            atualizeContagemEvento(tiposEventoPassar, jogador, quantidadeEventos);
            Button botaoEvento = getButtonFromLayout(externalButton);
            botaoEvento.setText(olheiroController.getStringDeNomeRef(tiposEvento.getDescricao()));
            botaoEvento.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    olheiroController.addEvento(tiposEventoPassar, jogador, new Date());
                    atualizeContagemEvento(tiposEventoPassar, jogador, quantidadeEventos);
                }
            });
            botoes.add(externalButton);

        }
        //ExpandableHeightGridView gridBotoes = (ExpandableHeightGridView) this.findViewById(R.id.jogador_grid_botoes);
        //gridBotoes.setExpanded(true);
        //gridBotoes.setAdapter(new BotoesAdapter());
        LinearLayout layoutBotoes = (LinearLayout) this.findViewById(R.id.jogador_layout_botoes);
        for (View botao:botoes){
            layoutBotoes.addView(botao);
        }
    }

    private void atualizeContagemEvento(TiposEvento tiposEventoPassar, Jogador jogador, TextView quantidadeEventos) {
        int quantidade = olheiroController.getQuantidadeEventos(jogador, tiposEventoPassar);
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
        if (olheiroController!=null){
            olheiroController.removeTimerWatcher(this);
        }
    }

    private class BotoesAdapter extends BaseAdapter {

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

