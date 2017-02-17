package com.p_v.flexiblecalendar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.IWeekCellViewDrawer;
import com.p_v.fliexiblecalendar.R;

import java.text.DateFormatSymbols;

/**
 * @author p-v
 */
public class WeekdayNameDisplayAdapter extends ArrayAdapter<WeekdayNameDisplayAdapter.WeekDay> {

    private IWeekCellViewDrawer cellViewDrawer;
    private WeekDay[] weekDayArray;

    public WeekdayNameDisplayAdapter(Context context, int textViewResourceId, int startDayOfTheWeek) {
        super(context, textViewResourceId);
        initializeWeekDays(startDayOfTheWeek);
    }

    @Override
    public WeekDay getItem(int position) {
        return weekDayArray[position];
    }

    @Override
    public int getCount() {
        return weekDayArray.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCellView cellView = cellViewDrawer.getCellView(position, convertView, parent);
        if (cellView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            cellView = (BaseCellView) inflater.inflate(R.layout.square_cell_layout, null);
        }
        WeekDay weekDay = getItem(position);
        String weekdayName = cellViewDrawer.getWeekDayName(weekDay.index, weekDay.displayValue); //adding 1 as week days starts from 1 in Calendar
        if (TextUtils.isEmpty(weekdayName)) {
            weekdayName = weekDay.displayValue;
        }
        cellView.setText(weekdayName);
        return cellView;
    }

    private void initializeWeekDays(int startDayOfTheWeek) {
        DateFormatSymbols symbols = new DateFormatSymbols(FlexibleCalendarHelper.getLocale(getContext()));
        String[] weekDayList = symbols.getShortWeekdays(); // weekday list has 8 elements
        weekDayArray = new WeekDay[7];
        //reordering array based on the start day of the week
        for (int i = 1; i < weekDayList.length; i++) {
            WeekDay weekDay = new WeekDay();
            weekDay.index = i;
            weekDay.displayValue = weekDayList[i];
            int tempVal = i - startDayOfTheWeek;
            weekDayArray[tempVal < 0 ? 7 + tempVal : tempVal] = weekDay;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void setCellView(IWeekCellViewDrawer cellView) {
        this.cellViewDrawer = cellView;
    }

    public IWeekCellViewDrawer getCellViewDrawer() {
        return cellViewDrawer;
    }

    public void setStartDayOfTheWeek(int startDayOfTheWeek) {
        initializeWeekDays(startDayOfTheWeek);
        this.notifyDataSetChanged();
    }

    public class WeekDay {
        int index;
        String displayValue;
    }

}
