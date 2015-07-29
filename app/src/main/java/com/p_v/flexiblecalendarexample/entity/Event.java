package com.p_v.flexiblecalendarexample.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by p-v on 20/07/15.
 */
public class Event implements Parcelable{

    private String title;
    private long time = -1;
    private EventTime eventTime;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTime(long time){
        this.time = time;
        eventTime = new EventTime(time);
    }

    public EventTime getEventTime(){
        return eventTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
