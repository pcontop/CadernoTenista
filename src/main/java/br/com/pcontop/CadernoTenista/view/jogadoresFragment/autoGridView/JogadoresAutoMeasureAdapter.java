package br.com.pcontop.CadernoTenista.view.jogadoresFragment.autoGridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.pcontop.CadernoTenista.R;
import br.com.pcontop.CadernoTenista.bean.Jogador;
import br.com.pcontop.CadernoTenista.control.OlheiroController;
import br.com.pcontop.CadernoTenista.view.components.AutoMeasureGridViewAdapter;
import br.com.pcontop.CadernoTenista.view.components.GridViewItemLayout;

/**
 * Created by PauloBruno on 09/01/14.
 */
public class JogadoresAutoMeasureAdapter extends AutoMeasureGridViewAdapter {
    private static Context context;
    private static LayoutInflater inflater;
    private static OlheiroController olheiroController;

    public JogadoresAutoMeasureAdapter(Context context, LayoutInflater inflater, OlheiroController olheiroController){
        this.context=context;
        this.inflater=inflater;
        this.olheiroController = olheiroController;
    }

    @Override
    public void measureItems(int columnWidth) {
        // Inflate temp layout object for measuring
        JogadorGridItem jogadorGridItem = (JogadorGridItem)inflater.inflate(R.layout.jogador_layout, null);

        // Create measuring specs
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(columnWidth, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        // Loop through each data object
        for(int index = 0; index < olheiroController.getJogadoresPartida().size(); index++) {
            Jogador jogador = olheiroController.getJogadoresPartida().get(index);

            // Set position and data
            jogadorGridItem.setPosition(index);
            jogadorGridItem.updateItemDisplay(jogador);

            // Force measuring
            jogadorGridItem.requestLayout();
            jogadorGridItem.measure(widthMeasureSpec, heightMeasureSpec);
        }

    }


    @Override
    public int getCount() {
        return olheiroController.getJogadoresPartida().size();
    }

    @Override
    public Object getItem(int i) {
        return olheiroController.getJogadoresPartida().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Jogador jogador = olheiroController.getJogadoresPartida().get(i);
        JogadorGridItem jogadorGridItem = new JogadorGridItem(context, inflater, olheiroController);
        jogadorGridItem.updateItemDisplay(jogador);
        return jogadorGridItem;
    }

    @Override
    public void notifyDataSetChanged() {
        initJogadorGridItem();
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        //initJogadorGridItem();
        super.notifyDataSetInvalidated();
    }

    private void initJogadorGridItem() {
        int numColumns = context.getResources().getInteger(R.integer.list_num_columns);
        int count = getCount();
        GridViewItemLayout.initItemLayout(numColumns, count);
    }
}
