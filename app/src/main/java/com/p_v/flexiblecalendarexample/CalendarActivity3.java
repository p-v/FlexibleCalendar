package com.p_v.flexiblecalendarexample;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.exception.HighValueException;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity3 extends ActionBarActivity implements DatePickerDialog.OnDateSetListener{

    private Map<Integer,List<CustomEvent>> eventMap;
    private FlexibleCalendarView calendarView;
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

        calendarView = (FlexibleCalendarView)findViewById(R.id.calendar_view);
        calendarView.setMonthViewHorizontalSpacing(10);
        calendarView.setMonthViewVerticalSpacing(10);
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                Toast.makeText(CalendarActivity3.this,""+year+" "+ (month+1),Toast.LENGTH_SHORT).show();
            }
        });

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
                return getEvents(year, month, day);
            }
        });

        findViewById(R.id.update_events_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CustomEvent> colorLst1 = new ArrayList<>();
                colorLst1.add(new CustomEvent(android.R.color.holo_red_dark));
                colorLst1.add(new CustomEvent(android.R.color.holo_blue_light));
                colorLst1.add(new CustomEvent(android.R.color.holo_purple));
                eventMap.put(2, colorLst1);
                calendarView.refresh();
            }
        });

        findViewById(R.id.date_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog= new DatePickerDialog(CalendarActivity3.this,CalendarActivity3.this,
                        calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        try{
            calendarView.selectDate(year,monthOfYear,dayOfMonth);
        }catch(HighValueException e){
            e.printStackTrace();
        }
    }
}
