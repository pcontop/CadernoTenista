package br.com.pcontop.CadernoTenista.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Partida;
import br.com.pcontop.CadernoTenista.control.FabricaController;
import br.com.pcontop.CadernoTenista.control.OlheiroController;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 19/11/13
 * time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class ListaPartidasMainFragment extends Fragment implements TelaPrincipal {
    private LayoutInflater inflater;
    private OlheiroController olheiroController;
    ListaPartidasAdapter listaPartidasAdapter;
    ListView listaPartidas;
    ViewGroup listaPartidasLayout;
    private static Telas tela = Telas.LISTA_PARTIDAS;

    private static List<Partida> partidas = new ArrayList<>();

    public Telas getTela(){
        return tela;
    }

    @Override
    public void setParams(Map<ParametroTela, Object> params) {

    }


    private static ListaPartidasMainFragment instance =  new ListaPartidasMainFragment();
    public ListaPartidasMainFragment(){

    }

    public static ListaPartidasMainFragment getInstance(){
        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerOlheiroController();
        buildListasPartidas();
        fetchPartidas();
    }

    private void registerOlheiroController() {
        olheiroController = FabricaController.getOlheiroController(getActivity());
        olheiroController.setListaPartidasMainFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        listaPartidasLayout = (ViewGroup) inflater.inflate(R.layout.lista_partidas_layout,null,false);
        return listaPartidasLayout;
    }

    private void fetchPartidas(){
        Thread partidasFetcher = new Thread(new PartidasFetcher());
        partidasFetcher.start();
    }

    private void buildListasPartidas() {
        listaPartidas = (ListView) listaPartidasLayout.findViewById(R.id.lista_partidas_lista);
        listaPartidasAdapter = new ListaPartidasAdapter();
        listaPartidas.setAdapter(listaPartidasAdapter);
        listaPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                executeTarefaItem(i);
            }
        });
        this.registerForContextMenu(listaPartidas);
    }

    private void executeTarefaItem(int i) {
        Partida partida = partidas.get(i);
        olheiroController.executeTarefaItemListaPartidas(partida);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (olheiroController!=null){
            olheiroController.removeListaPartidasFragment();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void remover(Partida partida){
        //Toast.makeText(getActivity(),"Removendo partida " + partida,Toast.LENGTH_SHORT).show();
        olheiroController.removaPartidaDaLista(partida);
    }

    public void refreshDisplay(){
        listaPartidasAdapter.notifyDataSetInvalidated();
    }

    /**
     * Adapter da Lista
     *
     */
    private class ListaPartidasAdapter extends BaseAdapter implements ListAdapter {

        @Override
        public void notifyDataSetInvalidated() {
            partidas = olheiroController.getPartidasPassadas();
            super.notifyDataSetInvalidated();
        }

        @Override
        public int getCount() {
            return partidas.size();
        }

        @Override
        public Object getItem(int i) {
            return partidas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RelativeLayout layout;
            if (i % 2 ==0) {
                layout = (RelativeLayout) inflater.inflate(R.layout.item_lista_partidas,null,false);
            } else {
                layout = (RelativeLayout) inflater.inflate(R.layout.item_lista_partidas_2,null,false);
            }
            Partida partida = partidas.get(i);
            TextView local = (TextView) layout.findViewById(R.id.item_lista_partidas_local);
            local.setText(partida.getLocal()==null?"":partida.getLocal().getDescricao());
            TextView passesAnotados = (TextView) layout.findViewById(R.id.item_lista_partidas_passes_anotados);
            passesAnotados.setText(partida.getEventos().size()+"");
            TextView dataInicio = (TextView) layout.findViewById(R.id.item_lista_partidas_data_inicio);
            String textoData = LanguageFormatter.getDateFormat(partida.getDataInicio(), R.string.sem_data_inicio_definida, getActivity());
            dataInicio.setText(textoData);

            TextView enviadoEm = (TextView) layout.findViewById(R.id.item_lista_partidas_enviado_em);
            String textoEnviadoEm = LanguageFormatter.getDateFormat(partida.getUltimoEnvio(), R.string.nao_enviado, getActivity());
            enviadoEm.setText(textoEnviadoEm);

            return layout;
        }
    }


    private class PartidasFetcher implements Runnable {
        private final Handler handler = new Handler();

        @Override
        public void run() {
            List<Partida> partidas = olheiroController.getPartidasPassadas();
            ListaPartidasMainFragment.this.partidas = partidas;
            handler.post(new Runnable() {
                public void run() {
                    refreshDisplay();
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.escolha_acao);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_lista_partidas, menu);
    }

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        Partida partida = partidas.get(info.position);
        switch (menuItemIndex){
            case R.id.context_menu_lista_partidas_enviar:
                olheiroController.exportePartida(getActivity(), partida);
                return true;
            case R.id.context_menu_lista_partidas_remover:
                remover(partida);
                return true;
            default:
                //
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        setRetainInstance(true);
        super.onSaveInstanceState(outState);
    }

}
