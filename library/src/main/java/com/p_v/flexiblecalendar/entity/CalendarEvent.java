package com.p_v.flexiblecalendar.entity;

/**
 * @author p-v
 */
public class CalendarEvent implements Event {

    private int color;

    public CalendarEvent() {

    }

    public CalendarEvent(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
