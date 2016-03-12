package com.p_v.flexiblecalendarexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar5);

        FlexibleCalendarView view = (FlexibleCalendarView)findViewById(R.id.calendar_view);
        view.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity5.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar5_date_cell_view, null);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        view.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<? extends Event> getEventsForTheDay(int year, int month, int day) {

                if(day%7==0){
                    List<EventW> eventList = new ArrayList<>();
                    eventList.add(new EventW());
                    return eventList;
                }
                return null;
            }
        });

    }
    public static class EventW implements Event{
        public EventW(){

        }

        @Override
        public int getColor() {
            return 0;
        }
    }
}
