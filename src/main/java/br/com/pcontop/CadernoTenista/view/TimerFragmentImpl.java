package br.com.pcontop.CadernoTenista.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Time;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.control.timer.TimerState;
import br.com.pcontop.CadernoTenista.control.timer.TimerStateFactory;

import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 30/10/13
 * time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public class TimerFragmentImpl extends Fragment implements TimerFragment {
    private Button botaoTimer;
    private static TextView relogio;
    private static int estadoSalvo;
    private static final String SALVAR_ESTADO="estado";
    private static Timer timer;

    public TimerFragmentImpl(){

    }

    public static TimerFragmentImpl getInstance(){
        return new TimerFragmentImpl();
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    OlheiroController olheiroController;

    public TimerState getTimerState(){
        return olheiroController.getTimerState();
    }

    public TimerState setTimerState(TimerState timerState){
        return olheiroController.setTimerState(timerState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getTimerState()!=null){
            getTimerState().destroy();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.timer_layout, container, false);
        inicializeObjetos();
        inicializeViews(relativeLayout);
        verifiqueEstado(savedInstanceState);
        inicializeTimes(relativeLayout);
        return relativeLayout;
    }

    private void inicializeTimes(ViewGroup layoutPrincipal) {
        Time time1 = olheiroController.getTime1();
        TextView nomeTime1 = (TextView) layoutPrincipal.findViewById(R.id.timer_nome_time1);
        nomeTime1.setText(time1.getNome());
        TextView corTime1 = (TextView) layoutPrincipal.findViewById(R.id.timer_cor_time1);
        corTime1.setBackgroundColor(time1.getCorAsInt());

        Time time2 = olheiroController.getTime2();
        TextView nomeTime2 = (TextView) layoutPrincipal.findViewById(R.id.timer_nome_time2);
        nomeTime2.setText(time2.getNome());
        TextView corTime2 = (TextView) layoutPrincipal.findViewById(R.id.timer_cor_time2);
        corTime2.setBackgroundColor(time2.getCorAsInt());
    }

    private void verifiqueEstado(Bundle savedInstanceState){
        if (savedInstanceState!=null){
            recupereEstadoSalvo(savedInstanceState);
        } else {
            definaEstadoInicial();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        olheiroController = FabricaController.getOlheiroController(activity);
        olheiroController.setTimerFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        olheiroController.removeTimerFragment();
    }

    private void definaEstadoInicial() {
        olheiroController.definaEstadoInicial();
    }

    private void recupereEstadoSalvo(Bundle savedInstanceState) {
        estadoSalvo = savedInstanceState.getInt(SALVAR_ESTADO);
        setTimerState(TimerStateFactory.recupere(olheiroController, estadoSalvo));
        getTimerState().recuperarDescanso(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getTimerState()!=null && getTimerState().getTimerStateType()!=null) {
            outState.putInt(SALVAR_ESTADO, getTimerState().getTimerStateType().ordinal());
        }
        olheiroController.salvePartida();
    }

    private void inicializeObjetos() {

    }

    private void inicializeViews(RelativeLayout relativeLayout) {
        botaoTimer = (Button) relativeLayout.findViewById(R.id.timer_botao);
        relogio = (TextView) relativeLayout.findViewById(R.id.timer_display);
        definaComportamentoBotao();
    }

    private void definaComportamentoBotao() {
        botaoTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acaoBotao();
            }
        });
    }

    private void acaoBotao() {
        setTimerState(getTimerState().transiteProximoEstado());
    }

    @Override
    public void setTimerValue(String texto){
        this.relogio.setText(texto);
    }

    @Override
    public void setBotaoTimerTexto(int ResourceId){
        if (botaoTimer!=null){
            this.botaoTimer.setText(ResourceId);
        }
    }

}
