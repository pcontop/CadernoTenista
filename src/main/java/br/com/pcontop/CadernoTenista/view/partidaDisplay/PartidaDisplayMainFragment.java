package br.com.pcontop.CadernoTenista.view.partidaDisplay;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TipoEvento;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.view.LanguageFormatter;
import br.com.pcontop.CadernoTenista.view.ParametroTela;
import br.com.pcontop.CadernoTenista.view.TelaPrincipal;
import br.com.pcontop.CadernoTenista.view.Telas;

/**
 * Created by PauloBruno on 12/12/13.
 */
public class PartidaDisplayMainFragment extends Fragment implements TelaPrincipal, PartidaDisplay, OnChartValueSelectedListener {
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
        olheiroController.setTelaAtual(this);
        if (savedInstanceState!=null){
            recuperePartida(savedInstanceState);
        }
        refresh();

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
        mainView = inflater.inflate(R.layout.partida_display_main_layout, null, false);
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
        refresh();
    }

    @Override
    public void refresh(){
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    private int getCorTipoEvento(TipoEvento tipoEvento){
        switch (tipoEvento.getQualificadorJogada()){
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


    /**
     * Display de um Jogador.
     */
    private class DisplayJogador extends LinearLayout {
        private Jogador jogador;
        private LayoutInflater inflater;
        private PieChart mChart;
        private Typeface tf;

        public DisplayJogador(Context context, LayoutInflater inflater, Jogador jogador){
            super(context);
            inflater.inflate(R.layout.jogador_display_layout, this, true);
            this.inflater = inflater;
            this.jogador=jogador;
            definaNome();
            definaCor();
            definaPasses();
            apresenteGrafico();
        }

        private void apresenteGrafico() {
            mChart = (PieChart) this.findViewById(R.id.chart_jogador);
            mChart.setUsePercentValues(true);
            mChart.setDescription("");

            mChart.setDragDecelerationFrictionCoef(0.95f);

            tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

            mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));

            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColorTransparent(true);

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);

            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);

            // add a selection listener
            mChart.setOnChartValueSelectedListener(PartidaDisplayMainFragment.this);

            mChart.setCenterText(jogador.getNome());

            setData(100);

            mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

        }

        private void setData(float range) {

            float mult = range;

            if (jogador==null){
                return;
            }

            ArrayList<Entry> yVals1 = new ArrayList<Entry>();

            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
            int i=0;
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (TipoEvento tipoEvento: jogador.getTiposEventos()) {
                float quantidade = (float) jogador.busqueEventosDoTipo(tipoEvento).size();
                yVals1.add(new Entry(quantidade, i));
                xVals.add(olheiroController.getStringDeNomeRef(tipoEvento.getDescricao()));
                colors.add(getCorTipoEvento(tipoEvento));
                i++;
            }

            PieDataSet dataSet = new PieDataSet(yVals1, getText(R.string.jogadas).toString());
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            dataSet.setColors(colors);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLACK);
            data.setValueTypeface(tf);
            mChart.setData(data);

            // undo all highlights
            mChart.highlightValues(null);

            mChart.invalidate();
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
            Map<TipoEvento, Integer> mapPassesRealizados = getMapaPasses();
            for (TipoEvento tipoEvento : mapPassesRealizados.keySet()){
                ViewGroup layoutTipoEvento = getLayoutTipoEvento(tipoEvento, mapPassesRealizados.get(tipoEvento));
                layoutTiposPasses.addView(layoutTipoEvento);
            }
        }

        private ViewGroup getLayoutTipoEvento(TipoEvento tipoEvento, Integer quantidade) {

            LinearLayout layoutPasse = (LinearLayout) inflater.inflate(R.layout.passe_display_layout, null);

            TextView nomeTipoEvento = (TextView) layoutPasse.findViewById(R.id.passe_display_header);
            String textoEvento = olheiroController.getStringDeNomeRef(tipoEvento.getDescricao());
            nomeTipoEvento.setText(textoEvento);

            TextView quantidadeEventos = (TextView) layoutPasse.findViewById(R.id.passe_display_value);
            quantidadeEventos.setText(quantidade.toString());

            definaCorTipoEvento(layoutPasse, tipoEvento);
            return layoutPasse;

        }


        private void definaCorTipoEvento(ViewGroup viewGroup, TipoEvento tipoEvento) {
            int cor = getCorTipoEvento(tipoEvento);
            /* Antigo processo - mantido para referência futura.
            //viewGroup.setBackgroundResource(R.drawable.partida_passe_display);
            //GradientDrawable shapeDrawable = (GradientDrawable) viewGroup.getBackground();
            //shapeDrawable.setColor(cor);
            */
            viewGroup.setBackgroundColor(cor);
        }


        private Map<TipoEvento, Integer> getMapaPasses() {
            Map<TipoEvento, Integer> passesPorTipo = new LinkedHashMap<>();
            Set<TipoEvento> tipoEventos;
            if (jogador.getTiposEventos()==null){
                tipoEventos = partida.getTiposEventosSelecionados();
            } else {
                tipoEventos = jogador.getTiposEventos();
            }
            tipoEventos = new TreeSet<>(tipoEventos);
            for (TipoEvento tipoEvento : tipoEventos){
                int quantidade = jogador.busqueEventosDoTipo(tipoEvento).size();
                passesPorTipo.put(tipoEvento, quantidade);
            }
            return passesPorTipo;
        }


        private void definaCor() {
            int cor = olheiroController.getCor(jogador);
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
