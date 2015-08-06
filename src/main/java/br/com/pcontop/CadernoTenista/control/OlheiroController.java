package br.com.pcontop.CadernoTenista.control;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Localidade;
import br.com.pcontop.CadernoTenista.bean.Olheiro;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TempoPartida;
import br.com.pcontop.CadernoTenista.bean.TipoEvento;
import br.com.pcontop.CadernoTenista.control.dialogs.DialogEventosHelper;
import br.com.pcontop.CadernoTenista.control.disabler.Disabler;
import br.com.pcontop.CadernoTenista.control.disabler.Enabler;
import br.com.pcontop.CadernoTenista.control.disabler.ExecuteEnablerDisabler;
import br.com.pcontop.CadernoTenista.control.timer.TimerState;
import br.com.pcontop.CadernoTenista.control.timer.TimerStateFactory;
import br.com.pcontop.CadernoTenista.control.timer.TimerStateType;
import br.com.pcontop.CadernoTenista.control.timer.TimerWatcher;
import br.com.pcontop.CadernoTenista.model.PartidaModel;
import br.com.pcontop.CadernoTenista.model.export.ExporterException;
import br.com.pcontop.CadernoTenista.view.ColorConstants;
import br.com.pcontop.CadernoTenista.view.DrawerPartida;
import br.com.pcontop.CadernoTenista.view.ListaPartidasMainFragment;
import br.com.pcontop.CadernoTenista.view.ParametroTela;
import br.com.pcontop.CadernoTenista.view.PartidaMainFragment;
import br.com.pcontop.CadernoTenista.view.Telas;
import br.com.pcontop.CadernoTenista.view.TimerFragment;
import br.com.pcontop.CadernoTenista.view.dialogs.ActionDialogJogador;
import br.com.pcontop.CadernoTenista.view.dialogs.DialogEventos;
import br.com.pcontop.CadernoTenista.view.dialogs.DialogEventosJogador;
import br.com.pcontop.CadernoTenista.view.dialogs.DialogJogador;
import br.com.pcontop.CadernoTenista.view.dialogs.DialogLocal;
import br.com.pcontop.CadernoTenista.view.jogadoresFragment.JogadoresFragment;
import br.com.pcontop.CadernoTenista.view.partidaDisplay.PartidaDisplay;


/**
 * Created with IntelliJ IDEA.
 * User: Paulo
 * Date: 02/11/13
 * time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class OlheiroController {
    private static PartidaMainFragment partidaMainFragment;
    Context context;
    private static Map<TimerStateType, ExecuteEnablerDisabler> habilitacaoPassesEmTempos;
    static JogadoresFragment jogadoresFragment;
    private static DrawerPartida drawerPartida;
    private static TimerFragment timerFragment;
    private static TimerState timerState;
    static PartidaModel partidaModel;
    private ListaPartidasMainFragment listaPartidasMainFragment;
    private static List<TimerWatcher> timerWatcherList = new ArrayList<>();

    static {
        Map<TimerStateType, ExecuteEnablerDisabler> actionMap = new HashMap<>();
        actionMap.put(TimerStateType.INICIAL, new Disabler());
        actionMap.put(TimerStateType.SET_EM_ANDAMENTO, new Enabler());
        actionMap.put(TimerStateType.INTERVALO_SET, new Disabler());
        actionMap.put(TimerStateType.FINAL, new Disabler());
        habilitacaoPassesEmTempos = actionMap;
    }

    private PartidaDisplay partidaDisplayMainFragment;

    protected OlheiroController(Context context){
        this.context=context;
        partidaModel = new PartidaModel(context);
    }

    public Context getContext(){
        return context;
    }

    public void criePartida(){
        partidaModel.criePartidaAtual();
        if (jogadoresFragment!=null){
            jogadoresFragment.redesenhe();
        }
    }

    public void setDrawerPartida(DrawerPartida drawerPartida){
        OlheiroController.drawerPartida = drawerPartida;
    }

    private Olheiro getOlheiro(){
        return partidaModel.getOlheiro();
    }

    public Localidade getLocal() {
        return partidaModel.getLocal();
    }

    public void setJogadoresFragment(JogadoresFragment jogadoresFragment){
        this.jogadoresFragment = jogadoresFragment;
    }

    public void removePasseFragment(){
        this.jogadoresFragment =null;
    }

    public void setTimerFragment(TimerFragment timerFragment){
        this.timerFragment = timerFragment;
    }

    public void removeTimerFragment(){
        if (getTimerState()!=null){
            getTimerState().destroy();
        }
        this.timerFragment=null;
    }

    public void addTimerWatcher(TimerWatcher timerWatcher){
        timerWatcherList.add(timerWatcher);
        updateTimeWatchers();
    }

    public void updateTimeWatchers(){
        getTimerState().updateTimerWatchers();
    }


    public void removeTimerWatcher(TimerWatcher timerWatcher){
        timerWatcherList.remove(timerWatcher);
    }

    public List<TimerWatcher> getTimerWatcherList(){
        return timerWatcherList;
    }

    public List<Jogador> getJogadoresPartida(){
        List<Jogador> jogadorList = partidaModel.getJogadoresPartida();
        if (jogadorList ==null) {
            jogadorList = new ArrayList<>();
        }
        return jogadorList;
    }

    private Jogador crieJogador(String nome){
        Jogador jogador = partidaModel.crieJogador(nome, ColorConstants.COR_PADRAO_JOGADOR_2_AVAI);
        return jogador;
    }

    public void addJogador(Jogador jogador){
        partidaModel.addJogadorPartida(jogador);
        jogadoresFragment.redesenhe();
    }

    public void addNewJogador(){
        DialogJogador dialogJogador = new DialogJogador(ActionDialogJogador.ADICIONAR,null);
        dialogJogador.show(drawerPartida.getFragmentManager(), "AdicioneJogadorFragment");

    }

    public void addNewJogadorPartida(){
        String nomeJogador = getProximoNomeJogador();
        addNewJogador(nomeJogador);
    }

    public String getProximoNomeJogador(){
        return "Jogador " + (getPartida().getJogadores().size()+1);
    }

    public void addNewJogador(String nome){
        if (nome==null||nome.trim().equals("")){
            addNewJogadorPartida();
            return;
        }
        Jogador jogador = crieJogador(nome);
        addJogador(jogador);
    }

    public Map<TimerStateType, ExecuteEnablerDisabler> getHabilitacaoPassesEmTempos(){
        return habilitacaoPassesEmTempos;
    }

    public int getCor(Jogador jogador){
        return partidaModel.getCor(jogador);
    }

    public void editarJogador(Jogador jogador, String nomeJogador){
        jogador.setNome(nomeJogador);
        partidaModel.addJogadorPartida(jogador);
        jogadoresFragment.redesenhe();
    }

    public void alterarJogador(Jogador jogador) {
        DialogJogador dialogJogador = new DialogJogador(ActionDialogJogador.EDITAR,jogador);
        dialogJogador.show(drawerPartida.getFragmentManager(), "AdicioneJogadorFragment");
    }

    public void addEvento(TipoEvento tipoEvento, Jogador jogador, Date date) {
        partidaModel.crieEventoEInsira(tipoEvento, jogador, date);
    }

    public String getStringDeNomeRef(String name) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(name, "string", packageName);
        String text;
        try {
            text=context.getString(resId);
        } catch (Exception e){
            text = context.getText(R.string.nao_encontrado).toString();
        }
        return text;
    }

    public Partida getPartida(){
        return partidaModel.getPartidaAtual();
    }

    public Partida getPartidaOuCrie(){
        return partidaModel.getPartidaAtualOuCrie();
    }

    public void recuperePartida(String idPartida) {
        partidaModel.recuperePartida(idPartida);
    }

    public TimerState getTimerState(){
        return timerState;
    }

    public TimerState setTimerState(TimerState timerState){
        this.timerState = timerState;
        return timerState;
    }

    public void definaEstadoInicial() {
        if (timerState==null) {
            setTimerState(TimerStateFactory.crie(this, null));
        } else {
            timerState.recuperarDescanso(timerFragment);
        }
    }

    public TimerFragment getTimerFragment() {
        return timerFragment;
    }

    public String[] getArrayTiposEventosJogador() {
        List<String> listaEventos = new ArrayList<>();
        for (TipoEvento tipoEvento : TipoEvento.values()){
            listaEventos.add(getStringDeNomeRef(tipoEvento.getDescricao()));
        }
        String[] eventos = listaEventos.toArray(new String[0]);
        return eventos;
    }


    public void setSelectedTiposEventos(Set<Integer> selectedTiposEventos) {
        DialogEventosHelper.setTiposEventosSelecionadosPartida(getPartida(), selectedTiposEventos);
        jogadoresFragment.redesenhe();
    }

    public void setSelectedTiposEventos(Set<Integer> selectedTiposEventos, Jogador jogador) {
        DialogEventosHelper.setTiposEventosSelecionadosPartida(jogador, selectedTiposEventos);
        jogadoresFragment.redesenhe();
    }

    public Set<TipoEvento> getTiposEventosJogadoresSelecionados(Jogador jogador) {
        if (jogador.getTiposEventos()!=null){
            return jogador.getTiposEventos();
        }
        return getPartida().getTiposEventosSelecionados();
    }

    public void salvePartida() {
        partidaModel.salvePartidaAtual();
    }

    public void setListaPartidasMainFragment(ListaPartidasMainFragment listaPartidasMainFragment) {
        this.listaPartidasMainFragment = listaPartidasMainFragment;
        drawerPartida.postAttachChildren(listaPartidasMainFragment);
    }

    public void removeListaPartidasFragment() {
        this.listaPartidasMainFragment = null;
    }

    public List<Partida> getPartidasPassadas(){
        return partidaModel.getPartidasPassadas();
    }


    public void releaseResources() {
        partidaModel.releaseResources();
    }

    public void setPartidaMainFragment(PartidaMainFragment partidaMainFragment) {
        OlheiroController.partidaMainFragment = partidaMainFragment;
        drawerPartida.postAttachChildren(partidaMainFragment);
    }

    public void removePartidaMainFragment() {
        OlheiroController.partidaMainFragment=null;
    }

    public int getQuantidadeEventos(Jogador jogador, TipoEvento tipoEventoPassar) {
        return partidaModel.getQuantidadeEventos(jogador, tipoEventoPassar);
    }

    public void exportePartida(Activity activity, Partida partida){
        new EnviePartidaAsync(activity, getPartida()).execute();
    }

    public void removaPartidaDaLista(Partida partida) {
        partidaModel.remova(partida);
        listaPartidasMainFragment.refreshDisplay();
    }

    public void setPartidaDisplayMainFragment(PartidaDisplay partidaDisplayMainFragment) {
        this.partidaDisplayMainFragment = partidaDisplayMainFragment;
    }

    public void removePartidaDisplayMainFragment() {
        this.partidaDisplayMainFragment = null;
    }

    public void executeTarefaItemListaPartidas(Partida partida) {
        HashMap<ParametroTela, Object> params = new HashMap<>();
        params.put(ParametroTela.PARTIDA, partida);
        //if (this.partidaDisplayMainFragment==null){
            drawerPartida.vaParaTela(Telas.DISPLAY_PARTIDA, params);
        //}
        //TODO - Adicionar comportamento para tablets.
    }


    public Partida getPartidaDisplayPartida(){
        if (partidaDisplayMainFragment!=null){
            return partidaDisplayMainFragment.getPartida();
        }
        return null;
    }

    public Partida getPartida(String idPartida) {
        return partidaModel.getPartida(idPartida);
    }

    public void definaLocalidade(String descricao, Double latitude, Double longitude) {
        partidaModel.definaLocalidade(descricao, latitude, longitude);
    }

    public void definaLocalidadePartida(){
        DialogLocal dialogLocal = new DialogLocal();
        dialogLocal.show(drawerPartida.getFragmentManager(), "Local");

    }

    public void definaEventosPartida() {
        DialogEventos dialogEventos = new DialogEventos();
        dialogEventos.show(drawerPartida.getFragmentManager(),"Eventos");

    }

    public Set<Integer> getSelectedTiposEventos(Jogador jogador) {
        boolean[] listaTiposEventos = DialogEventosHelper.getTiposEventosJogadorSelecionados(getPartida(), jogador);
        return DialogEventosHelper.buildSelectedTiposEventos(listaTiposEventos);
    }

    public Set<Integer> getSelectedTiposEventos() {
        boolean[] listaTiposEventos = DialogEventosHelper.getTiposEventosJogadorSelecionados(getPartida());
        return DialogEventosHelper.buildSelectedTiposEventos(listaTiposEventos);
    }

    public boolean[] getTiposEventosJogadorSelecionados() {
        return DialogEventosHelper.getTiposEventosJogadorSelecionados(getPartida());
    }

    public boolean[] getTiposEventosJogadorSelecionados(Jogador jogador) {
        return DialogEventosHelper.getTiposEventosJogadorSelecionados(getPartida(), jogador);
    }

    public void alterarTiposPasse(Jogador jogador) {
        DialogEventosJogador dialogEventosJogador = new DialogEventosJogador(jogador);
        dialogEventosJogador.show(drawerPartida.getFragmentManager(), "EditarTiposPasseJogadorFragment");
    }

    public void apresentarVideo(Partida partida, Jogador jogador) {
        Map<ParametroTela, Object> params = new HashMap<>();
        params.put(ParametroTela.PARTIDA, partida);
        params.put(ParametroTela.JOGADOR, jogador);
        drawerPartida.vaParaTela(Telas.DISPLAY_VIDEO_PARTIDA, params);
    }

    public void salvePartida(Partida partida){
        partidaModel.insiraOuAtualize(partida);
    }

    public void apresenteResultadoPartidaAtual() {
        Map<ParametroTela, Object>  parametros = new HashMap<>();
        parametros.put(ParametroTela.PARTIDA, getPartida());
        drawerPartida.vaParaTela(Telas.DISPLAY_PARTIDA, parametros);
    }

    public void toast(String message, int duration){
        Toast.makeText(getContext(),message,duration).show();
    }

    public void toast(int message, int duration){
        Toast.makeText(getContext(), message, duration).show();
    }

    public void toast(final Activity activity, final int message, int duration){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void atualizeDisplayPartida() {
        partidaDisplayMainFragment.refreshDisplay();
    }

    public Date getDataInicioTempoAtual() {
        return getPartida().getTempoPartida().getDataInicio();
    }

    public void transiteProximoTempoPartida(Date date) {
        partidaModel.transiteProximoTempoPartida(date);
    }

    public void transiteFimDePartida(Date date) {
        partidaModel.transiteFimDePartida(date);
    }

    public TempoPartida getTempoPartidaAtual(){
        return getPartidaOuCrie().getTempoPartida();
    }

    public void removaJogador(Jogador jogador) {
        partidaModel.removaJogador(jogador);
    }

    public int getCorJogador1() {
        return partidaModel.getCorJogador1();
    }

    public int getCorJogador2() {
        return partidaModel.getCorJogador2();
    }

    public void exportePartidaAtual(Activity activity){
        new EnviePartidaAsync(activity, getPartida()).execute();
    }

    private class EnviePartidaAsync extends AsyncTask<String,Integer,Boolean> {

        private final Activity activity;
        private final Partida partida;
        private boolean resultado=false;

        public EnviePartidaAsync(Activity activity, Partida partida){
            super();
            this.activity = activity;
            this.partida = partida;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (partidaModel.exportePartida(partida)) {
                    resultado = true;
                    return true;
                }
            } catch (ExporterException e) {
                Log.e("CadernoOlheiro", "Problema ao exportar", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (resultado){
                toast(activity, R.string.partida_enviada, Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(context,context.getText(R.string.erro_exportacao),Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aBoolean);
            atualizeDisplayPartida();
        }
    }


}
