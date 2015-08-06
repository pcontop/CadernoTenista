package br.com.pcontop.CadernoTenista.view.partidaDisplay;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.EventoPartida;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.bean.TipoTempoPartida;
import br.com.pcontop.CadernoTenista.bean.TipoEvento;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.helper.TempoHelper;
import br.com.pcontop.CadernoTenista.model.TextTranslator;
import br.com.pcontop.CadernoTenista.view.LanguageFormatter;
import br.com.pcontop.CadernoTenista.view.ParametroTela;
import br.com.pcontop.CadernoTenista.view.TelaPrincipal;
import br.com.pcontop.CadernoTenista.view.Telas;

/**
 * Created by PauloBruno on 12/12/13.
 */
public class PartidaDisplayVideoFragment extends Fragment implements TelaPrincipal, PartidaDisplay {
    private static final String POSITION = "Position" ;
    private static final String PATH = "Path";
    OlheiroController olheiroController;
    private static Partida partida;
    private static Jogador jogador;
    private View mainView;
    private static final String PARTIDA = "Partida";
    private static final String JOGADOR = "Jogador";
    private static final int REQUEST_CHOOSER_PRIMEIRO = 1234;
    private static final int REQUEST_CHOOSER_SEGUNDO = 1235;
    public static final int RESULT_OK = -1;
    private MediaController mediaController;
    private EventoPartida eventoPartidaAtual;
    private VideoView videoView;
    private String currentVideoPath;

    public static PartidaDisplayVideoFragment getInstance(){
        return new PartidaDisplayVideoFragment();
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
        if (savedInstanceState!=null){
            recupereVideo(savedInstanceState);
        }

    }

    private void recupereVideo(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(PATH)){
            String pathVideo = savedInstanceState.getString(PATH);
            int posicao = savedInstanceState.getInt(POSITION);
            inicializeVideo(pathVideo);
            pulePlayerParaDecorridos(posicao);
            inicieVideo();
        }
    }

    private void recuperePartida(Bundle savedInstanceState) {
        String idPartida = savedInstanceState.getString(PARTIDA);
        partida = olheiroController.getPartida(idPartida);
        String idJogador = savedInstanceState.getString(JOGADOR);
        jogador = partida.busqueJogadorPorId(idJogador);
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
        mainView = inflater.inflate(R.layout.partida_display_video_layout,null,false);
        return mainView;
    }


    @Override
    public Telas getTela() {
        return Telas.DISPLAY_VIDEO_PARTIDA;
    }

    @Override
    public void setParams(Map<ParametroTela, Object> params) {
        for (ParametroTela param:params.keySet()){
            switch (param){
                case PARTIDA:
                    partida = (Partida) params.get(param);
                    break;
                case JOGADOR:
                    jogador = (Jogador) params.get(param);
                    break;
                default:

            }
            if (jogador!=null && partida!=null){
                refreshDisplay();
            }
        }
    }

    @Override
    public void refreshDisplay(){
        if (mainView!=null && partida!=null){
            apresenteDatas();
            prepareVideo();
            apresentePassesJogador();
        }
    }

    private void prepareVideo() {
        mediaController = new MediaController(getActivity());
        videoView = (VideoView) mainView.findViewById(R.id.partida_video_surface);
        MediaControl mediaControl = new MediaControl();
        mediaController.setMediaPlayer(mediaControl);
        mediaController.setAnchorView(videoView);
    }

    private void apresentePassesJogador() {
        TextView apresentacaoPasses = (TextView) mainView.findViewById(R.id.partida_video_texto_passes);
        StringBuilder builder = new StringBuilder();
        builder.append(getText(R.string.passes_de));
        builder.append(" ");
        builder.append(jogador.getNome());
        apresentacaoPasses.setText(builder.toString());
        ListView listaPasses = (ListView) mainView.findViewById(R.id.partida_video_passes);
        final PassesAdapter adapter = new PassesAdapter();
        listaPasses.setAdapter(adapter);
        listaPasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventoPartida eventoPartida = (EventoPartida) adapterView.getItemAtPosition(i);
                puleVideoParaEvento(eventoPartida);
            }
        });
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
    }
    
   private void setTextViewDate(int idTextView, Date data, int idTextoDataVazia){
        TextView textData = (TextView) mainView.findViewById(idTextView);
        String strData = LanguageFormatter.getDateFormat(data, idTextoDataVazia, getActivity());
        textData.setText(strData);

    }

    @Override
    public Partida getPartida() {
        return partida;
    }


    private class PassesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return jogador.getEventos().size();
        }

        @Override
        public Object getItem(int i) {
            return jogador.getEventos().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            EventoPartida eventoPartida = jogador.getEventos().get(i);
            LinearLayout layoutItem = (LinearLayout) view.inflate(getActivity(),R.layout.lista_passes_item,null);
            montaItem(eventoPartida, layoutItem);
            definaCorItem(eventoPartida, layoutItem);
            return layoutItem;
        }

        private void definaCorItem(EventoPartida eventoPartida, LinearLayout layoutItem) {
            int corTipoEvento = getCorTipoEvento(eventoPartida.getTipoEvento());
            int corComAlpha = adicioneAlpha(corTipoEvento);
            layoutItem.setBackgroundColor(corComAlpha);
        }

        private int adicioneAlpha(int corTipoEvento) {
            int alpha = (int) Math.round(Color.alpha(corTipoEvento) * 0.5);
            int cor = Color.argb(alpha,Color.red(corTipoEvento),Color.green(corTipoEvento),Color.blue(corTipoEvento));
            return cor;
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

        private void montaItem(EventoPartida eventoPartida, LinearLayout layoutItem) {
            TextView tempo = (TextView) layoutItem.findViewById(R.id.lista_passes_tempo);
            String tempoRaw = TempoHelper.getDescricaoTempo(partida, eventoPartida.getHora());
            String tempoDescrito = TextTranslator.translateFromStringName(getActivity(), tempoRaw);

            TextView decorridos = (TextView) layoutItem.findViewById(R.id.lista_passes_horario);
            Date tempoDecorridos = TempoHelper.getTempoTranscorridoDesdeUltimoInicio(partida, eventoPartida.getHora());
            String descDecorridos = TempoHelper.getTextoDiferencaTempo(tempoDecorridos);
            decorridos.setText(descDecorridos);

            TextView tipoEvento = (TextView) layoutItem.findViewById(R.id.lista_passes_tipo_passe);
            String tipoEventoRaw = eventoPartida.getTipoEvento().getDescricao();
            String tipoEventoTraduzido = TextTranslator.translateFromStringName(getActivity(), tipoEventoRaw);
            tipoEvento.setText(tipoEventoTraduzido);

            tempo.setText(tempoDescrito);
        }
    }

    private void chequeVideos(Partida partida, TipoTempoPartida tempoJogo){
        if (tempoJogo.equals(TipoTempoPartida.PRIMEIRO_SET)){
            if (partida.getPathVideoPrimeiroTempo()==null){
                Intent getContentIntent = FileUtils.createGetContentIntent();
                Intent intent = Intent.createChooser(getContentIntent, getText(R.string.selecione_video_primeiro_set));
                startActivityForResult(intent, REQUEST_CHOOSER_PRIMEIRO);
            }
        }
        if (tempoJogo.equals(TipoTempoPartida.SEGUNDO_SET)){
            if (partida.getPathVideoSegundoTempo()==null){
                Intent getContentIntent = FileUtils.createGetContentIntent();
                Intent intent = Intent.createChooser(getContentIntent, getText(R.string.selecione_video_segundo_set));
                startActivityForResult(intent, REQUEST_CHOOSER_SEGUNDO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER_PRIMEIRO:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(getActivity(), uri);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                        partida.setPathVideoPrimeiroTempo(path);
                        olheiroController.salvePartida(partida);
                        vaParaEventoJogoSelecionado();
                    }
                }
                break;
            case REQUEST_CHOOSER_SEGUNDO:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(getActivity(), uri);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                        partida.setPathVideoSegundoTempo(path);
                        olheiroController.salvePartida(partida);
                        vaParaEventoJogoSelecionado();
                    }
                }
                break;
        }
    }

    private void vaParaEventoJogoSelecionado() {
        puleVideoParaEvento(eventoPartidaAtual);
    }

    public void puleVideoParaEvento(EventoPartida eventoPartida) {
        TipoTempoPartida tempoJogo = TempoHelper.getTipoTempoPartida(partida, eventoPartida.getHora());
        eventoPartidaAtual = eventoPartida;
        chequeVideos(partida, tempoJogo);
        try {
            if (inicializePlayer(tempoJogo)){
                Date tempoTranscorrido = TempoHelper.getTempoTranscorridoDesdeUltimoInicio(partida, eventoPartida.getHora());
                Toast.makeText(getActivity(),"Indo para momento " + TempoHelper.getTextoDiferencaTempo(tempoTranscorrido),Toast.LENGTH_SHORT).show();
                pulePlayerParaDecorridos(tempoTranscorrido);
                inicieVideo();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

    private void inicieVideo() {
        videoView.start();

    }

    private void pulePlayerParaDecorridos(Date tempoTranscorrido) {
        int tempoTranscorridoMilisec = (int)tempoTranscorrido.getTime();
        pulePlayerParaDecorridos(tempoTranscorridoMilisec);

    }

    private void pulePlayerParaDecorridos(int tempoTranscorridoMilisec) {
        videoView.seekTo(tempoTranscorridoMilisec);
    }

    private boolean inicializePlayer(TipoTempoPartida tempoJogo) throws IOException {

        String pathVideo=null;
        if (tempoJogo.equals(TipoTempoPartida.PRIMEIRO_SET)){
            pathVideo = partida.getPathVideoPrimeiroTempo();
        }

        if (tempoJogo.equals(TipoTempoPartida.SEGUNDO_SET)){
            pathVideo = partida.getPathVideoSegundoTempo();
        }

        if (pathVideo!=null) {
            inicializeVideo(pathVideo);
            return true;
        }
        return false;

    }

    private void inicializeVideo(String pathVideo) {
        videoView.setVideoPath(pathVideo);
        setCurrentVideoPath(pathVideo);
        videoView.setMediaController(mediaController);
    }

    private class MediaControl implements MediaController.MediaPlayerControl {

        @Override
        public void start() {
            videoView.start();
        }

        @Override
        public void pause() {
            videoView.pause();
        }

        @Override
        public int getDuration() {
            return videoView.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return videoView.getCurrentPosition();
        }

        @Override
        public void seekTo(int i) {
            videoView.seekTo(i);
        }

        @Override
        public boolean isPlaying() {
            return videoView.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return videoView.getBufferPercentage();
        }

        @Override
        public boolean canPause() {
            return videoView.canPause();
        }

        @Override
        public boolean canSeekBackward() {
            return videoView.canSeekBackward();
        }

        @Override
        public boolean canSeekForward() {
            return videoView.canSeekForward();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (partida!=null){
            outState.putString(PARTIDA,partida.getId());
            outState.putString(JOGADOR, jogador.getId());
        }

        if (videoView!=null && videoView.isPlaying()){
            outState.putInt(POSITION, videoView.getCurrentPosition());
            outState.putString(PATH, getCurrentVideoPath());
        }
    }

    public String getCurrentVideoPath() {
        return currentVideoPath;
    }

    public void setCurrentVideoPath(String currentVideoPath) {
        this.currentVideoPath = currentVideoPath;
    }

}
