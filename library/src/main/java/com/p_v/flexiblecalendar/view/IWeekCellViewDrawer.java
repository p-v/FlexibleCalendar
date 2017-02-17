package com.p_v.flexiblecalendar.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author p-v
 */
public interface IWeekCellViewDrawer extends ICellViewDrawer {
    /**
     * Week Cell view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    BaseCellView getCellView(int position, View convertView, ViewGroup parent);

    /**
     * Display value for the day of week
     *
     * @param dayOfWeek
     * @param defaultValue
     * @return
     */
    String getWeekDayName(int dayOfWeek, String defaultValue);
}
