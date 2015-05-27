package br.com.pcontop.CadernoOlheiro.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.pcontop.CadernoOlheiro.R;
import br.com.pcontop.CadernoOlheiro.bean.Partida;
import br.com.pcontop.CadernoOlheiro.control.FabricaController;
import br.com.pcontop.CadernoOlheiro.control.OlheiroController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: PauloBruno
 * Date: 11/11/13
 * time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class DrawerPartida extends Activity {
    public static final String APPLICATION = "Application";
    public static final String TELA_ATIVA = "TelaAtiva";
    private MediaPlayer mMediaPlayer;
    private boolean played=false;
    private OlheiroController olheiroController;
    private ActionBarDrawerToggle mDrawerToggle;
    private static String mTitle;
    private static String mDrawerTitle;
    private static DrawerLayout mDrawerLayout;
    private static Telas telaAtual = Telas.PARTIDA;
    //private static Map<Telas, Fragment> telasCarregadas = new HashMap<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        registreOlheiroController();
        inicializeLayout();
        adicioneMenuDrawer();
        adicioneBotaoActionBar();
        if (savedInstanceState != null){
            recuperePartida(savedInstanceState);
        }  else {
            adicioneViewPrincipal();
        }
        //playMp3();
    }

    private void inicializeLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void adicioneBotaoActionBar() {
        mTitle = getString(R.string.app_name);
        mDrawerTitle=getString(R.string.drawer_open);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    private void adicioneViewPrincipal() {
        vaParaTela(Telas.PARTIDA);
    }

    public TelaPrincipal vaParaTela(Telas novaTela){
        Fragment fragment = novaTela.getTela();
        if (novaTela.equals(this.telaAtual)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit()
            ;
        }  else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack("anterior")
                    .commit()
                    ;
        }
        mDrawerLayout.closeDrawers();
        this.telaAtual = novaTela;
        return (TelaPrincipal) fragment;
    }

    public TelaPrincipal vaParaTela(Telas novaTela, Map<ParametroTela, Object> params){
        TelaPrincipal telaPrincipal = vaParaTela(novaTela);
        telaPrincipal.setParams(params);
        return telaPrincipal;
    }

    private void adicioneMenuDrawer() {
        String [] entries = getResources().getStringArray(R.array.opcoes_menu_principal);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, entries));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void recuperePartida(Bundle savedInstanceState) {
        String idPartida = savedInstanceState.getString(APPLICATION);
        olheiroController.recuperePartida(idPartida);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(APPLICATION,olheiroController.getPartida().getId());
        outState.putString(TELA_ATIVA, telaAtual.toString());
    }

    private void registreOlheiroController() {
        this.olheiroController = FabricaController.getOlheiroController(this);
        olheiroController.setDrawerPartida(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       olheiroController.releaseResources();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()){
            case R.id.menu_partida_escolher_eventos:
                olheiroController.definaEventosPartida();
                return true;
            case R.id.menu_partida_enviar_partida:

                //olheiroController.exportePartidaDisplayPartida();
                try {
                    Partida partida = olheiroController.getPartidaDisplayPartida();
                    Boolean result = new EnviePartidaAsync(this, olheiroController).execute(partida).get();
                    if (result){
                        olheiroController.atualizeDisplayPartida();
                    }
                } catch (InterruptedException e) {
                    Log.e("DrawerPartida","Erro ao enviar partida.",e);
                } catch (ExecutionException e) {
                    Log.e("DrawerPartida","Erro ao enviar partida.",e);
                }
                return true;
            case R.id.menu_partida_escolher_local:
                olheiroController.definaLocalidadePartida();
                return true;
            default:
                return false;
        }
    }

    public void postAttachChildren(TelaPrincipal telaPrincipal){
        this.telaAtual = telaPrincipal.getTela();
        invalidateOptionsMenu();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position){
                case 0:
                    vaParaTela(Telas.PARTIDA);
                    break;
                case 1:
                    vaParaTela(Telas.LISTA_PARTIDAS);
                    break;
                case 2:
                    vaParaTela(Telas.SOBRE);
                    break;
                default:
                    //do nothing;
            }
        }
    }


}
