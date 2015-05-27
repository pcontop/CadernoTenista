package br.com.pcontop.CadernoOlheiro.control;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.*;
import br.com.pcontop.CadernoOlheiro.control.dialogs.DialogEventosHelper;
import br.com.pcontop.CadernoOlheiro.control.disabler.Disabler;
import br.com.pcontop.CadernoOlheiro.control.disabler.Enabler;
import br.com.pcontop.CadernoOlheiro.control.disabler.ExecuteEnablerDisabler;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerState;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerStateFactory;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerStateType;
import br.com.pcontop.CadernoOlheiro.control.timer.TimerWatcher;
import br.com.pcontop.CadernoOlheiro.model.JogoModel;
import br.com.pcontop.CadernoOlheiro.model.export.ExporterException;
import br.com.pcontop.CadernoOlheiro.view.*;
import br.com.pcontop.CadernoOlheiro.view.dialogs.*;
import br.com.pcontop.CadernoOlheiro.view.jogadoresFragment.JogadoresFragment;
import br.com.pcontop.CadernoOlheiro.view.partidaDisplay.PartidaDisplay;

import java.util.*;


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
    static JogoModel jogoModel;
    private ListaPartidasMainFragment listaPartidasMainFragment;
    private static List<TimerWatcher> timerWatcherList = new ArrayList<>();

    static {
        Map<TimerStateType, ExecuteEnablerDisabler> actionMap = new HashMap<>();
        actionMap.put(TimerStateType.INICIAL, new Disabler());
        actionMap.put(TimerStateType.PRIMEIRO_TEMPO_EM_ANDAMENTO, new Enabler());
        actionMap.put(TimerStateType.FIM_PRIMEIRO_TEMPO, new Disabler());
        actionMap.put(TimerStateType.SEGUNDO_TEMPO_EM_ANDAMENTO, new Enabler());
        actionMap.put(TimerStateType.FIM_SEGUNDO_TEMPO, new Disabler());
        habilitacaoPassesEmTempos = actionMap;
    }

    private PartidaDisplay partidaDisplayMainFragment;

    protected OlheiroController(Context context){
        this.context=context;
        jogoModel = new JogoModel(context);
    }

    public Context getContext(){
        return context;
    }

    public void criePartida(){
        jogoModel.criePartida(getLocal(), getOlheiro());
        if (jogadoresFragment!=null){
            jogadoresFragment.redesenhe();
        }
    }

    public void setDrawerPartida(DrawerPartida drawerPartida){
        OlheiroController.drawerPartida = drawerPartida;
    }

    private Olheiro getOlheiro(){
        return jogoModel.getOlheiro();
    }

    public Localidade getLocal() {
        if (getPartida()==null){
            return null;
        }
        return getPartida().getLocal();
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
        List<Jogador> jogadorList = jogoModel.getJogadoresPartida();
        if (jogadorList ==null) {
            jogadorList = new ArrayList<>();
        }
        return jogadorList;
    }

    private Jogador crieJogador(String nome){
        Jogador jogador = jogoModel.crieJogador(nome);
        return jogador;
    }

    public void addJogador(Jogador jogador, Time time){
        jogoModel.addJogadorTime(jogador, time);
        jogadoresFragment.redesenhe();
    }

    public void addNewJogador(){
        DialogJogador dialogJogador = new DialogJogador(ActionDialogJogador.ADICIONAR,null);
        dialogJogador.show(drawerPartida.getFragmentManager(), "AdicioneJogadorFragment");

    }

    public void addNewJogador(Time time){
        String nomeJogador = getProximoNomeJogador(time);
        addNewJogador(nomeJogador, time);
    }

    public String getProximoNomeJogador(Time time){
        return "Jogador " + (time.getJogadores().size()+1);
    }

    public void addNewJogador(String nome, Time time){
        if (nome==null||nome.trim().equals("")){
            addNewJogador(time);
            return;
        }
        Jogador jogador = crieJogador(nome);
        addJogador(jogador, time);
    }

    public void removaJogador(Jogador jogador){
        jogoModel.remova(jogador);
        jogadoresFragment.redesenhe();
    }

    public Map<TimerStateType, ExecuteEnablerDisabler> getHabilitacaoPassesEmTempos(){
        return habilitacaoPassesEmTempos;
    }

    public Time getTime1(){
        Time time1 = jogoModel.getTime1();
        return time1;
    }

    public Time getTime2(){
        Time time2 = jogoModel.getTime2();
        return time2;
    }

    public int getCorTime(Jogador jogador){
        return jogoModel.getCorTime(jogador);
    }

    public int getCorTime(Partida partida, Jogador jogador){
        return jogoModel.getCorTime(partida, jogador);
    }

    public void editarJogador(Jogador jogador, String nomeJogador, Time time){
        jogador.setNome(nomeJogador);
        jogoModel.addJogadorTime(jogador, time);
        jogadoresFragment.redesenhe();
    }

    public void alterarJogador(Jogador jogador) {
        DialogJogador dialogJogador = new DialogJogador(ActionDialogJogador.EDITAR,jogador);
        dialogJogador.show(drawerPartida.getFragmentManager(), "AdicioneJogadorFragment");
    }

    public void addEvento(TipoEvento tipoEvento, Jogador jogador, Date date) {
        jogoModel.crieEventoEInsira(tipoEvento, jogador, date);
    }

    public String getStringDeNomeRef(String name) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(name, "string", packageName);
        String text;
        try {
            text=context.getString(resId);
        } catch (Exception e){
            text = "Não encontrado";
        }
        return text;
    }

    public Partida getPartida(){
        return jogoModel.getPartidaAtual();
    }


    public void recuperePartida(String idPartida) {
        jogoModel.recuperePartida(idPartida);
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
            timerState.recuperar(timerFragment);
        }
    }

    public void setDataInicioPrimeiroTempo(Date dataInicioPrimeiroTempo) {
        getPartida().setDataInicio(dataInicioPrimeiroTempo);
        salvePartida();
    }

    public void setDataInicioSegundoTempo(Date dataInicioSegundoTempo) {
        getPartida().setDataInicioSegundoTempo(dataInicioSegundoTempo);
        salvePartida();
    }

    public Date getDataInicioPrimeiroTempo() {
        return getPartida().getDataInicio();
    }

    public Date getDataInicioSegundoTempo() {
        return getPartida().getDataInicioSegundoTempo();
    }

    public Date getDataFimPrimeiroTempo() {
        return getPartida().getDataFimPrimeiroTempo();
    }

    public void setDataFimPrimeiroTempo(Date dataFimPrimeiroTempo) {
        getPartida().setDataFimPrimeiroTempo(dataFimPrimeiroTempo);
        salvePartida();
    }

    public Date getDataFimSegundoTempo() {
        return getPartida().getDataFimSegundoTempo();
    }

    public void setDataFimSegundoTempo(Date dataFimSegundoTempo) {
        getPartida().setDataFimSegundoTempo(dataFimSegundoTempo);
        salvePartida();
    }

    public TimerFragment getTimerFragment() {
        return timerFragment;
    }

    public String[] getArrayTiposEventosJogador() {
        List<String> listaEventos = new ArrayList<>();
        for (TipoEvento tipoEvento: TipoEvento.values()){
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
        jogoModel.salvePartida();
    }

    public void setListaPartidasMainFragment(ListaPartidasMainFragment listaPartidasMainFragment) {
        this.listaPartidasMainFragment = listaPartidasMainFragment;
        drawerPartida.postAttachChildren(listaPartidasMainFragment);
    }

    public void removeListaPartidasFragment() {
        this.listaPartidasMainFragment = null;
    }

    public List<Partida> getPartidasPassadas(){
        return jogoModel.getPartidasPassadas();
    }


    public void releaseResources() {
        jogoModel.releaseResources();
    }

    public void setPartidaMainFragment(PartidaMainFragment partidaMainFragment) {
        OlheiroController.partidaMainFragment = partidaMainFragment;
        drawerPartida.postAttachChildren(partidaMainFragment);
    }

    public void removePartidaMainFragment() {
        OlheiroController.partidaMainFragment=null;
    }

    public int getQuantidadeEventos(Jogador jogador, TipoEvento tipoEventoPassar) {
        return jogoModel.getQuantidadeEventos(jogador, tipoEventoPassar);
    }

    public boolean exportePartida(Activity activity, Partida partida){
        int contador=0;
        while (contador < 3){

            try {
                if (jogoModel.exportePartida(partida)) {
                    toast(activity, R.string.partida_enviada, Toast.LENGTH_SHORT);
                    return true;
                }
            } catch (ExporterException e) {
                Log.e("CadernoOlheiro","Problema ao exportar",e);
            }
            contador++;
            toast(R.string.reenviando_partida, Toast.LENGTH_SHORT);
        }
        toast(activity, R.string.erro_exportacao, Toast.LENGTH_SHORT);
        return false;
    }

    public void removaPartidaDaLista(Partida partida) {
        jogoModel.remova(partida);
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
        return jogoModel.getPartida(idPartida);
    }

    public void definaLocalidade(String descricao, Double latitude, Double longitude) {
        jogoModel.definaLocalidade(descricao, latitude, longitude);
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
        drawerPartida.vaParaTela(Telas.DISPLAY_VIDEO_PARTIDA,params);
    }

    public void salvePartida(Partida partida){
        jogoModel.insiraOuAtualize(partida);
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
}
