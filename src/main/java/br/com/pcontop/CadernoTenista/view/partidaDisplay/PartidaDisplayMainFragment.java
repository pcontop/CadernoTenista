package br.com.pcontop.CadernoTenista.view.partidaDisplay;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TiposEvento;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.view.LanguageFormatter;
import br.com.pcontop.CadernoTenista.view.ParametroTela;
import br.com.pcontop.CadernoTenista.view.TelaPrincipal;
import br.com.pcontop.CadernoTenista.view.Telas;

import java.util.*;

/**
 * Created by PauloBruno on 12/12/13.
 */
public class PartidaDisplayMainFragment extends Fragment implements TelaPrincipal, PartidaDisplay {
    OlheiroController olheiroController;
    private static Partida partida;
    private View mainView;
    private static final String PARTIDA = "Partida";

    public static PartidaDisplayMainFragment getInstance(){
        return new PartidaDisplayMainFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        olheiroController = FabricaController.getOlheiroController(getActivity());
        olheiroController.setPartidaDisplayMainFragment(this);
        if (savedInstanceState!=null){
            recuperePartida(savedInstanceState);
        }
        refreshDisplay();

    }

    private void recuperePartida(Bundle savedInstanceState) {
        String idPartida = savedInstanceState.getString(PARTIDA);
        partida = olheiroController.getPartida(idPartida);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (olheiroController!=null){
            olheiroController.removePartidaDisplayMainFragment();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.partida_display_main_layout,null,false);
        return mainView;
    }


    @Override
    public Telas getTela() {
        return Telas.DISPLAY_PARTIDA;
    }

    @Override
    public void setParams(Map<ParametroTela, Object> params) {
        for (ParametroTela param:params.keySet()){
            switch (param){
                case PARTIDA:
                    Partida partida = (Partida) params.get(param);
                    refreshDisplay(partida);
                    break;
                default:

            }
        }
    }

    private void refreshDisplay(Partida partida) {
        PartidaDisplayMainFragment.partida = partida;
        refreshDisplay();
    }

    @Override
    public void refreshDisplay(){
        if (mainView!=null && partida!=null){
            apresenteDatas();
            adicioneJogadores();
        }
    }

    private void adicioneJogadores() {
        GridView gridJogadores = (GridView) mainView.findViewById(R.id.partida_display_grid_jogadores);
        JogadoresAdapter jogadoresAdapter = new JogadoresAdapter();
        gridJogadores.setAdapter(jogadoresAdapter);
    }

    private void apresenteDatas() {
        TextView textLocal = (TextView) mainView.findViewById(R.id.partida_display_localizacao_valor);
        String strLocal = partida.getLocal()==null?getString(R.string.sem_localidade_definida):partida.getLocal().getDescricao();
        textLocal.setText(strLocal);
        setTextViewDate(
                R.id.partida_display_data_inicio_valor,
                partida.getDataInicio(),
                R.string.sem_data_inicio_definida
        );
        setTextViewDate(
                R.id.partida_display_data_fim_primeiro_tempo_valor,
                partida.getDataFimPrimeiroSet(),
                R.string.sem_data_fim_primeiro_tempo_definida
        );
        setTextViewDate(
                R.id.partida_display_data_inicio_segundo_tempo_valor,
                partida.getDataInicioSegundoSet(),
                R.string.sem_data_inicio_definida
        );
        setTextViewDate(
                R.id.partida_display_data_fim_segundo_tempo_valor,
                partida.getDataFimSegundoSet(),
                R.string.sem_data_fim_primeiro_tempo_definida
        );
        setTextViewDate(
                R.id.partida_display_data_ultimo_envio_valor,
                partida.getUltimoEnvio(),
                R.string.nao_enviado
        );
    }
    
   private void setTextViewDate(int idTextView, Date data, int idTextoDataVazia){
        TextView textData = (TextView) mainView.findViewById(idTextView);
        String strData = LanguageFormatter.getDateFormat(data, idTextoDataVazia, getActivity());
        textData.setText(strData);

    }

    /**
     * Display de um Jogador.
     */
    private class DisplayJogador extends LinearLayout {
        private Jogador jogador;
        private LayoutInflater inflater;

        public DisplayJogador(Context context, LayoutInflater inflater, Jogador jogador){
            super(context);
            inflater.inflate(R.layout.jogador_display_layout,this,true);
            this.inflater = inflater;
            this.jogador=jogador;
            definaNome();
            definaCor();
            definaPasses();
        }

        private void definaNome() {
            TextView displayNome = (TextView) findViewById(R.id.jogador_display_nome_jogador);
            displayNome.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.context_menu_display_jogador, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int menuItemIndex = menuItem.getItemId();
                            switch (menuItemIndex) {
                                case R.id.context_menu_passe_jogador_mostrar_video:
                                    olheiroController.apresentarVideo(partida, jogador);
                                    return true;
                                default:
                                    //
                            }
                            return true;
                        }
                    });
                    popupMenu.show();

                }
            });
            displayNome.setText(jogador.getNome());
        }

        private void definaPasses() {
            LinearLayout layoutTiposPasses = (LinearLayout) this.findViewById(R.id.jogador_grid_passes);
            layoutTiposPasses.removeAllViews();
            Map<TiposEvento, Integer> mapPassesRealizados = getMapaPasses();
            for (TiposEvento tiposEvento : mapPassesRealizados.keySet()){
                ViewGroup layoutTipoEvento = getLayoutTipoEvento(tiposEvento, mapPassesRealizados.get(tiposEvento));
                layoutTiposPasses.addView(layoutTipoEvento);
            }
        }

        private ViewGroup getLayoutTipoEvento(TiposEvento tiposEvento, Integer quantidade) {

            LinearLayout layoutPasse = (LinearLayout) inflater.inflate(R.layout.passe_display_layout, null);

            TextView nomeTipoEvento = (TextView) layoutPasse.findViewById(R.id.passe_display_header);
            String textoEvento = olheiroController.getStringDeNomeRef(tiposEvento.getDescricao());
            nomeTipoEvento.setText(textoEvento);

            TextView quantidadeEventos = (TextView) layoutPasse.findViewById(R.id.passe_display_value);
            quantidadeEventos.setText(quantidade.toString());

            definaCorTipoEvento(layoutPasse, tiposEvento);
            return layoutPasse;

        }


        private void definaCorTipoEvento(ViewGroup viewGroup, TiposEvento tiposEvento) {
            int cor = getCorTipoEvento(tiposEvento);
            /* Antigo processo - mantido para referência futura.
            //viewGroup.setBackgroundResource(R.drawable.partida_passe_display);
            //GradientDrawable shapeDrawable = (GradientDrawable) viewGroup.getBackground();
            //shapeDrawable.setColor(cor);
            */
            viewGroup.setBackgroundColor(cor);
        }

        private int getCorTipoEvento(TiposEvento tiposEvento){
            switch (tiposEvento.getQualificadorJogada()){
                case BOA:
                    return Color.GREEN;
                case RUIM:
                    return Color.RED;
                case NEUTRA:
                    return Color.WHITE;
                default:
                    return Color.TRANSPARENT;
            }
        }

        private Map<TiposEvento, Integer> getMapaPasses() {
            Map<TiposEvento, Integer> passesPorTipo = new LinkedHashMap<>();
            Set<TiposEvento> tiposEventos;
            if (jogador.getTiposEventos()==null){
                tiposEventos = partida.getTiposEventosSelecionados();
            } else {
                tiposEventos = jogador.getTiposEventos();
            }
            tiposEventos = new TreeSet<>(tiposEventos);
            for (TiposEvento tiposEvento : tiposEventos){
                int quantidade = jogador.busqueEventosdoTipo(tiposEvento).size();
                passesPorTipo.put(tiposEvento, quantidade);
            }
            return passesPorTipo;
        }


        private void definaCor() {
            int cor = olheiroController.getCorTime(partida, jogador);
            this.setBackgroundResource(R.drawable.jogador);
            GradientDrawable shapeDrawable = (GradientDrawable) this.getBackground();
            shapeDrawable.setColor(cor);

        }

    }

    /**
     * Adapter para o grid de Jogadores.
     */
    private class JogadoresAdapter extends BaseAdapter {
        List<Jogador> jogadorList;
        public JogadoresAdapter(){
            this.jogadorList = partida.getJogadores();
        }

        @Override
        public int getCount() {
            return jogadorList.size();
        }

        @Override
        public Object getItem(int i) {
            return jogadorList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return new DisplayJogador(getActivity(), getActivity().getLayoutInflater(), jogadorList.get(i));
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.partida_display, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public Partida getPartida(){
        return partida;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (partida!=null){
            outState.putString(PARTIDA,partida.getId());
        }
    }


}
