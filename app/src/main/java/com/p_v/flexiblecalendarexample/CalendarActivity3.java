package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity3 extends ActionBarActivity {

    private Map<Integer,List<CustomEvent>> eventMap;

    private void  initializeEvents(){
        eventMap = new HashMap<>();
        List<CustomEvent> colorLst = new ArrayList<>();
        colorLst.add(new CustomEvent(android.R.color.holo_red_dark));
        eventMap.put(25,colorLst);

        List<CustomEvent> colorLst1 = new ArrayList<>();
        colorLst1.add(new CustomEvent(android.R.color.holo_red_dark));
        colorLst1.add(new CustomEvent(android.R.color.holo_blue_light));
        colorLst1.add(new CustomEvent(android.R.color.holo_purple));
        eventMap.put(22,colorLst1);

        List<CustomEvent> colorLst2= new ArrayList<>();
        colorLst2.add(new CustomEvent(android.R.color.holo_red_dark));
        colorLst2.add(new CustomEvent(android.R.color.holo_blue_light));
        colorLst2.add(new CustomEvent(android.R.color.holo_purple));
        eventMap.put(28,colorLst1);

        List<CustomEvent> colorLst3 = new ArrayList<>();
        colorLst3.add(new CustomEvent(android.R.color.holo_red_dark));
        colorLst3.add(new CustomEvent(android.R.color.holo_blue_light));
        eventMap.put(29,colorLst1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_activity3);

        initializeEvents();

        final FlexibleCalendarView calendarView = (FlexibleCalendarView)findViewById(R.id.calendar_view);
        calendarView.setMonthViewHorizontalSpacing(10);
        calendarView.setMonthViewVerticalSpacing(10);

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity3.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_date_cell_view, null);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity3.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CustomEvent> getEventsForTheDay(int year, int month, int day) {
                return getEvents(year,month,day);
            }
        });

        findViewById(R.id.update_events_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CustomEvent> colorLst1 = new ArrayList<>();
                colorLst1.add(new CustomEvent(android.R.color.holo_red_dark));
                colorLst1.add(new CustomEvent(android.R.color.holo_blue_light));
                colorLst1.add(new CustomEvent(android.R.color.holo_purple));
                eventMap.put(2,colorLst1);
                calendarView.refresh();
            }
        });

    }

    public List<CustomEvent> getEvents(int year, int month, int day){
        return eventMap.get(day);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
