package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalendarActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FlexibleCalendarView calendarView = (FlexibleCalendarView)findViewById(R.id.month_view);
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, int direction) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1);
                Toast.makeText(CalendarActivity2.this, cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH) + " " + year, Toast.LENGTH_SHORT).show();
            }
        });
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity2.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar2_date_cell_view, null);
                }
                if (cellType == BaseCellView.TODAY) {
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    cellView.setTextSize(15);
                } else {
                    cellView.setTextColor(getResources().getColor(android.R.color.white));
                    cellView.setTextSize(12);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity2.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar2_week_cell_view, null);
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
            public List<? extends Event> getEventsForTheDay(int year, int month, int day) {
                if(year==2015 && month == 7 && day == 25){
                    List<CustomEvent> colorLst1 = new ArrayList<>();
                    colorLst1.add(new CustomEvent(android.R.color.holo_green_dark));
                    colorLst1.add(new CustomEvent(android.R.color.holo_blue_light));
                    colorLst1.add(new CustomEvent(android.R.color.holo_purple));
                    return colorLst1;
                }
                if(year==2015 && month == 7 && day == 8){
                    List<CustomEvent> colorLst1 = new ArrayList<>();
                    colorLst1.add(new CustomEvent(android.R.color.holo_green_dark));
                    colorLst1.add(new CustomEvent(android.R.color.holo_blue_light));
                    colorLst1.add(new CustomEvent(android.R.color.holo_purple));
                    return colorLst1;
                }
                if(year==2015 && month == 7 && day == 5){
                    List<CustomEvent> colorLst1 = new ArrayList<>();
                    colorLst1.add(new CustomEvent(android.R.color.holo_purple));
                    return colorLst1;
                }
                return null;
            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calendarView.isShown()){
                    calendarView.collapse();
                }else{
                    calendarView.expand();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
