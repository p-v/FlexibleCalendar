package com.p_v.flexiblecalendar.entity;

/**
 * @author p-v 
 */
public class SelectedDateItem {

    private int day;
    private int month;
    private int year;

    public SelectedDateItem(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public SelectedDateItem clone() {
        return new SelectedDateItem(year,month,day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
