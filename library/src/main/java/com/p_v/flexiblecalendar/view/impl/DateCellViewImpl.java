package com.p_v.flexiblecalendar.view.impl;

import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.IDateCellViewDrawer;

/**
 * Default date cell view drawer
 * @author p-v
 */
public class DateCellViewImpl implements IDateCellViewDrawer {

    private FlexibleCalendarView.ICalendarView calendarView;

    public DateCellViewImpl(FlexibleCalendarView.ICalendarView calendarView){
        this.calendarView = calendarView;
    }

    @Override
    public void setCalendarView(FlexibleCalendarView.ICalendarView calendarView) {
        this.calendarView = calendarView;
    }

    @Override
    public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
        return calendarView.getCellView(position,convertView,parent,cellType);
    }
}
