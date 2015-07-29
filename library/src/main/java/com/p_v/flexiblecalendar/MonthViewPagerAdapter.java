package com.p_v.flexiblecalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-v
 */
public class MonthViewPagerAdapter extends PagerAdapter {

    private static final int VIEWS_IN_PAGER = 4;

    private Context context;
    private List<FlexibleCalendarGridAdapter> dateAdapters;
    private FlexibleCalendarGridAdapter.OnDateCellItemClickListener onDateCellItemClickListener;
    private FlexibleCalendarGridAdapter.MonthEventFetcher monthEventFetcher;

    public MonthViewPagerAdapter(Context context, int year, int month, FlexibleCalendarGridAdapter.OnDateCellItemClickListener onDateCellItemClickListener){
        this.context = context;
        this.dateAdapters = new ArrayList<>(VIEWS_IN_PAGER);
        this.onDateCellItemClickListener = onDateCellItemClickListener;
        initializeDateAdapters(year, month);
    }

    private void initializeDateAdapters(int year, int month){
        int pYear;
        int pMonth;
        if(month==0){
            pYear = year-1;
            pMonth = 11;
        }else{
            pYear = year;
            pMonth = month - 1;
        }

        for(int i=0;i<VIEWS_IN_PAGER - 1;i++){
            dateAdapters.add(new FlexibleCalendarGridAdapter(context,year,month));
            if(month==11){
                year++;
                month =0;
            }else{
                month++;
            }
        }
        dateAdapters.add(new FlexibleCalendarGridAdapter(context, pYear, pMonth));
    }

    public void refreshDateAdapters(int position){
        FlexibleCalendarGridAdapter currentAdapter = dateAdapters.get(position);
        //selecting the first date of the month
        currentAdapter.setSelectedItem(new SelectedDateItem(currentAdapter.getYear(),currentAdapter.getMonth(),1),true);

        int[] nextDate = new int[2];
        FlexibleCalendarHelper.nextMonth(currentAdapter.getYear(), currentAdapter.getMonth(), nextDate);

        dateAdapters.set((position + 1) % 4, new FlexibleCalendarGridAdapter(context, nextDate[0], nextDate[1]));

        FlexibleCalendarHelper.nextMonth(nextDate[0], nextDate[1], nextDate);
        dateAdapters.set((position + 2) % 4, new FlexibleCalendarGridAdapter(context, nextDate[0], nextDate[1]));

        FlexibleCalendarHelper.previousMonth(currentAdapter.getYear(), currentAdapter.getMonth(), nextDate);
        dateAdapters.set((position + 3) % 4, new FlexibleCalendarGridAdapter(context, nextDate[0], nextDate[1]));

        notifyDataSetChanged();
    }

    public FlexibleCalendarGridAdapter getMonthAdapterAtPosition(int position){
        FlexibleCalendarGridAdapter gridAdapter = null;
        if(dateAdapters!=null && position >= 0 && position < dateAdapters.size()){
            gridAdapter = dateAdapters.get(position);
        }
        return gridAdapter;
    }


    @Override
    public int getCount() {
        return VIEWS_IN_PAGER;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        FlexibleCalendarGridAdapter adapter = dateAdapters.get(position);
        adapter.setOnDateClickListener(onDateCellItemClickListener);
        adapter.setMonthEventFetcher(monthEventFetcher);

        GridView view = (GridView)inflater.inflate(R.layout.month_grid_layout,null);
        view.setAdapter(adapter);

        layout.addView(view);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void setSelectedItem(SelectedDateItem selectedItem){
        for(FlexibleCalendarGridAdapter f : dateAdapters){
            f.setSelectedItem(selectedItem,true);
        }
        this.notifyDataSetChanged();
    }

    public void setMonthEventFetcher(FlexibleCalendarGridAdapter.MonthEventFetcher monthEventFetcher){
        this.monthEventFetcher = monthEventFetcher;
    }

}
