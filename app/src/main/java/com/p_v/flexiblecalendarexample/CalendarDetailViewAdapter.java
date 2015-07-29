package com.p_v.flexiblecalendarexample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-v
 */
public class CalendarDetailViewAdapter extends PagerAdapter{

    private Context context;

    private List<CalendarEventsAdapter> calendarEventsAdapterList;

    public CalendarDetailViewAdapter(Context context){
        this.context = context;
        calendarEventsAdapterList = new ArrayList<>(4);
    }

    public void initializeAdapters(List<CalendarEventsAdapter> calendarEventsAdapterList){
        this.calendarEventsAdapterList = calendarEventsAdapterList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return calendarEventsAdapterList==null?0:calendarEventsAdapterList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView view = (RecyclerView)
                inflater.inflate(R.layout.calendar_pager_item, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setAdapter(calendarEventsAdapterList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RecyclerView)object);

    }
}