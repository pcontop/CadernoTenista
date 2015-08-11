package br.com.pcontop.CadernoTenista.view.partidaDisplay;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

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
public class PartidaDisplayMainFragment extends Fragment implements TelaPrincipal, PartidaDisplay, OnChartGestureListener, OnChartValueSelectedListener {
    OlheiroController olheiroController;
    private static Partida partida;
    private View mainView;
    private static final String PARTIDA = "Partida";
    private LineChart mChart;

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
            apresenteGrafico();
        }
    }

    private void apresenteGrafico() {
        mChart = (LineChart) mainView.findViewById(R.id.chart1);
        mChart.setOnChartGestureListener(this);
        //mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        mChart.setHighlightEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        //mChart.setMarkerView(mv);

        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLabelPosition.POS_RIGHT);
//        llXAxis.setTextSize(10f);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.addLimitLine(llXAxis);

        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(45, 100);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
//        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
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
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
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
                int quantidade = jogador.busqueEventosdoTipo(tipoEvento).size();
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
