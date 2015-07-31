package com.p_v.flexiblecalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.ICellViewDrawer;
import com.p_v.fliexiblecalendar.R;

/**
 * @author p-v
 */
public class WeekdayNameDisplayAdapter extends ArrayAdapter<String>{

    private ICellViewDrawer cellViewDrawer;

    public WeekdayNameDisplayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId,FlexibleCalendarHelper.getWeekDaysList(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCellView cellView = cellViewDrawer.getCellView(position,convertView,parent);
        if(cellView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            cellView = (BaseCellView)inflater.inflate(R.layout.base_cell_layout,null);
        }
        String item = getItem(position);
        cellView.setText(item);
        return cellView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void setCellView(ICellViewDrawer cellView) {
        this.cellViewDrawer = cellView;
    }

    public ICellViewDrawer getCellViewDrawer(){
        return cellViewDrawer;
    }

}
