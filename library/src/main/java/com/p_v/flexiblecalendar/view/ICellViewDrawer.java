package com.p_v.flexiblecalendar.view;

import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;

/**
 * @author p-v
 **/
public interface ICellViewDrawer {
    /**
     * Set the calendar view
     *
     * @param calendarView calendar view
     */
    void setCalendarView(FlexibleCalendarView.ICalendarView calendarView);

    /**
     * Cell view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    BaseCellView getCellView(int position, View convertView, ViewGroup parent);
}
