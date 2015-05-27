package br.com.pcontop.CadernoOlheiro.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import br.com.pcontop.CadernoOlheiro.R;

/**
 * Custom subclass of grid view to measure all view cells
 * in order to determine the max height of the row
 *
 * @author Chase Colburn
 */
public class AutoMeasureGridView extends GridView {

    public AutoMeasureGridView(Context context) {
        super(context);
    }

    public AutoMeasureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoMeasureGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {
            AutoMeasureGridViewAdapter adapter = (AutoMeasureGridViewAdapter)getAdapter();

            int numColumns = getContext().getResources().getInteger(R.integer.list_num_columns);
            int count = adapter.getCount();
            GridViewItemLayout.initItemLayout(numColumns, count);

            if(numColumns > 1) {
                int columnWidth = getMeasuredWidth() / numColumns;
                adapter.measureItems(columnWidth);
            }
        }
        super.onLayout(changed, l, t, r, b);
    }
}