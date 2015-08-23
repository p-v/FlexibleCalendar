package com.p_v.flexiblecalendarexample;

import com.p_v.flexiblecalendar.view.Event;

/**
 * @author p-v
 */
public class CalendarEvent implements Event {

    private int color;

    public CalendarEvent(int color){
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
