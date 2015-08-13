package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;
import com.p_v.flexiblecalendarexample.entity.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarActivityFragment extends Fragment implements FlexibleCalendarView.OnMonthChangeListener,
        FlexibleCalendarView.OnDateClickListener {

    private FlexibleCalendarView calendarView;
    private CalendarDetailView detailViewPager;
    private TextView someTextView;

  public CalendarActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (FlexibleCalendarView)view.findViewById(R.id.calendar_view);
        calendarView.setCalendarView(new FlexibleCalendarView.ICalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, boolean isWithinCurrentMonth) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if(cellView == null){
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    cellView = (SquareCellView)inflater.inflate(R.layout.calendar1_week_cell_view,null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return String.valueOf(defaultValue.charAt(0));
            }
        });
        calendarView.setOnMonthChangeListener(this);
        calendarView.setOnDateClickListener(this);
        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<Integer> getEventsForTheDay(int year, int month, int day) {
                if (year == 2015 && month == 8 && day == 12) {
                    List<Integer> eventColors = new ArrayList<Integer>(2);
                    eventColors.add(android.R.color.holo_blue_light);
                    eventColors.add(android.R.color.holo_purple);
                    return eventColors;
                }
                if (year == 2015 && month == 8 && day == 7 ||
                        year == 2015 && month == 8 && day == 29 ||
                        year == 2015 && month == 8 && day == 5 ||
                        year == 2015 && month == 8 && day == 9) {
                    List<Integer> eventColors = new ArrayList<Integer>(1);
                    eventColors.add(android.R.color.holo_blue_light);
                    return eventColors;
                }

                if(year == 2015 && month == 8 && day == 31 ||
                        year == 2015 && month == 8 && day == 22 ||
                        year == 2015 && month == 8 && day == 18 ||
                        year == 2015 && month == 9 && day == 11 ){
                    List<Integer> eventColors = new ArrayList<Integer>(3);
                    eventColors.add(android.R.color.holo_red_dark);
                    eventColors.add(android.R.color.holo_orange_light);
                    eventColors.add(android.R.color.holo_purple);
                    return eventColors;
                }

                return null;
            }
        });

        detailViewPager = (CalendarDetailView)view.findViewById(R.id.detail_view_pager);
        detailViewPager.getDetailViewAdapter().initializeAdapters(getEventAdapters());
        detailViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int lastPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (lastPos > position) {
                    calendarView.moveToPreviousDate();
                } else {
                    calendarView.moveToNextDate();
                }
                lastPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupToolBar(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateTitle(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth());
    }

    public void setupToolBar(View mainView){
        Toolbar toolbar = (Toolbar) mainView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);

        someTextView = new TextView(getActivity());
        someTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.isShown()) {
                    calendarView.collapse();
                } else {
                    calendarView.expand();
                }
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(someTextView);
    }

    private void updateTitle(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        someTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                this.getResources().getConfiguration().locale)+" "+year);
    }

    private List<CalendarEventsAdapter> getEventAdapters(){
        List<CalendarEventsAdapter> list = new ArrayList<>();
        list.add(getSomeEventAdapter());
        list.add(getSomeEventAdapter());
        list.add(getSomeEventAdapter());
        list.add(getSomeEventAdapter());
        return list;
    }

    private CalendarEventsAdapter getSomeEventAdapter(){
        Event event = new Event();
        event.setTitle("Your birthday event");
        event.setTime(1442985300000L);
        Event aws = new Event();
        aws.setTitle("Awesome event");
        aws.setTime(1442978100000L);
        CalendarEventsAdapter calendarEventsAdapter = new CalendarEventsAdapter(getActivity());
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add(aws);
        calendarEventsAdapter.setEventList(eventList);
        return calendarEventsAdapter;
    }

    @Override
    public void onMonthChange(int year, int month, int direction) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        updateTitle(year,month);
    }

    @Override
    public void onDateClick(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        Toast.makeText(getActivity(),cal.getTime().toString(),Toast.LENGTH_SHORT).show();
    }
}
