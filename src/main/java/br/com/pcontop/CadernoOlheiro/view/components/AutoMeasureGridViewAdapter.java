package br.com.pcontop.CadernoOlheiro.view.components;

import android.widget.BaseAdapter;

/**
 * Created by PauloBruno on 09/01/14.
 */
public abstract class AutoMeasureGridViewAdapter extends BaseAdapter{
    public abstract void measureItems(int columnWidth);
}
