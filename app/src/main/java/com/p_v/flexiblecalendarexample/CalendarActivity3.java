package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity3 extends ActionBarActivity {

    private Map<Integer,List<Integer>> eventColorMap;
    private boolean addColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_activity3);

        initDataSet();

        final FlexibleCalendarView calendarView = (FlexibleCalendarView)findViewById(R.id.calendar_view);
        calendarView.setMonthViewHorizontalSpacing(10);
        calendarView.setMonthViewVerticalSpacing(10);

        calendarView.setCalendarView(new FlexibleCalendarView.ICalendarView() {
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
            public List<Integer> getEventsForTheDay(int year, int month, int day) {
                return getEvents(year,month,day);
            }
        });

        Button changeEventsBtn = (Button)findViewById(R.id.change_events);
        changeEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addColor){
                    List<Integer> colorLst = new ArrayList<>();
                    colorLst.add(android.R.color.holo_orange_dark);
                    eventColorMap.put(15, colorLst);
                    addColor = false;
                }else{
                    eventColorMap.remove(15);
                    addColor = true;
                }
                calendarView.refresh();
            }
        });
        addColor = true;

    }

    private void initDataSet(){
        eventColorMap = new HashMap<>();

        List<Integer> colorLst = new ArrayList<>();
        colorLst.add(android.R.color.holo_red_dark);
        colorLst.add(android.R.color.holo_blue_light);
        colorLst.add(android.R.color.white);
        eventColorMap.put(25, colorLst);

        List<Integer> colorLst1 = new ArrayList<>();
        colorLst1.add(android.R.color.holo_red_dark);
        colorLst1.add(android.R.color.white);
        eventColorMap.put(12, colorLst1);


    }

    private List<Integer> getEvents(int year, int month, int day){
        return eventColorMap.get(day);
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
