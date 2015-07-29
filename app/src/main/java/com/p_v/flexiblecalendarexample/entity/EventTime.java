package com.p_v.flexiblecalendarexample.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by p-v on 20/07/15.
 */
public class EventTime implements Parcelable {

    private int hour;
    private int minutes;
    private int year;
    private int month;

    public EventTime(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        hour = cal.get(Calendar.HOUR);
        minutes = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
    }

    public int getMonth(){
        return month;
    }

    public int getMinutes(){
        return minutes;
    }

    public int getYear(){
        return year;
    }

    public int getHour(){
        return hour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
