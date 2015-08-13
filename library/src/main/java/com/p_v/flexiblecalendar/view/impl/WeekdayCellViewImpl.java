package com.p_v.flexiblecalendar.view.impl;

import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.IWeekCellViewDrawer;

/**
 * Default week cell view drawer
 *
 * @author p-v
 */
public class WeekdayCellViewImpl implements IWeekCellViewDrawer {

    private FlexibleCalendarView.ICalendarView calendarView;

    public WeekdayCellViewImpl(FlexibleCalendarView.ICalendarView calendarView){
        this.calendarView = calendarView;
    }

    @Override
    public void setCalendarView(FlexibleCalendarView.ICalendarView calendarView) {
        this.calendarView = calendarView;
    }

    @Override
    public BaseCellView getCellView(int position, View convertView, ViewGroup parent) {
        return calendarView.getWeekdayCellView(position, convertView, parent);
    }

    @Override
    public String getWeekDayName(int dayOfWeek, String defaultValue) {
        return calendarView.getDayOfWeekDisplayValue(dayOfWeek,defaultValue);
    }
}
