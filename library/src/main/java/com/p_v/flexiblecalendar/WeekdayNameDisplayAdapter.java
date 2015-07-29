package com.p_v.flexiblecalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author p-v
 */
public class WeekdayNameDisplayAdapter extends ArrayAdapter<String>{

    public WeekdayNameDisplayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId,FlexibleCalendarHelper.getWeekDaysList(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        String item = getItem(position);
        textView.setText(item);
        return textView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
