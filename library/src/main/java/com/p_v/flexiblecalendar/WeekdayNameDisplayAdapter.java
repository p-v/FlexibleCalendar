package com.p_v.flexiblecalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.p_v.flexiblecalendar.view.SquareCellView;
import com.p_v.fliexiblecalendar.R;

/**
 * @author p-v
 */
public class WeekdayNameDisplayAdapter extends ArrayAdapter<String>{

    public WeekdayNameDisplayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId,FlexibleCalendarHelper.getWeekDaysList(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        SquareCellView textView = (SquareCellView)inflater.inflate(R.layout.square_cell_layout,null);
        String item = getItem(position);
        textView.setText(item);
        return textView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
