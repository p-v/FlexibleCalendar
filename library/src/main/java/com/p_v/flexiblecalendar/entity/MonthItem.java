package com.p_v.flexiblecalendar.entity;

public class MonthItem {

    private int month;
    private int year;

    public MonthItem(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public MonthItem clone() {
        return new MonthItem(year, month);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
