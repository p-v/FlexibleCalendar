package com.p_v.flexiblecalendarexample;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity3 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_activity3);

        FlexibleCalendarView calendarView = (FlexibleCalendarView)findViewById(R.id.calendar_view);
        calendarView.setMonthViewHorizontalSpacing(10);
        calendarView.setMonthViewVerticalSpacing(10);

        calendarView.setCalendarView(new FlexibleCalendarView.ICalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, boolean isWithinCurrentMonth) {
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
        });

        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<Integer> getEventsForTheDay(int year, int month, int day) {
                if(year==2015 && month== 7 && day ==25){
                    List<Integer> colorLst = new ArrayList<Integer>();
                    colorLst.add(android.R.color.holo_red_dark);
                    colorLst.add(android.R.color.holo_blue_light);
                    colorLst.add(android.R.color.holo_purple);
                    return colorLst;
                }
                return null;
            }
        });

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
