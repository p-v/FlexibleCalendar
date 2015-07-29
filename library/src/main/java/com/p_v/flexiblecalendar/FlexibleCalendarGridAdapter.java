package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.fliexiblecalendar.R;

import java.util.Calendar;
import java.util.List;

/**
 * @author p-v
 */
class FlexibleCalendarGridAdapter extends BaseAdapter {

    private int year;
    private int month;
    private Context context;
    private MonthDisplayHelper monthDisplayHelper;
    private Calendar calendar;
    private OnDateCellItemClickListener onDateCellItemClickListener;
    private SelectedDateItem selectedItem;
    private MonthEventFetcher monthEventFetcher;


    public FlexibleCalendarGridAdapter(Context context, int year, int month ){
        this.context = context;
        this.year = year;
        this.month = month;
        this.monthDisplayHelper = new MonthDisplayHelper(year,month);
        this.calendar = FlexibleCalendarHelper.getLocalizedCalendar(context);
    }

    @Override
    public int getCount() {
        return monthDisplayHelper.getNumberOfDaysInMonth() + monthDisplayHelper.getFirstDayOfMonth() - 1;
    }

    @Override
    public Object getItem(int position) {
        //TODO implement
        int row = position / 7;
        int col = position % 7 ;
        return monthDisplayHelper.getDayAt(row,col);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCellView cellView = (BaseCellView) convertView;
        if(cellView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            cellView = (BaseCellView)inflater.inflate(R.layout.square_cell_layout,null);
        }
        drawDateCell(cellView, position);
        return cellView;
    }

    private void drawDateCell(BaseCellView cellView, int position){
        int row = position/7;
        int col = position%7;

        // TODO make it flexible enough to handle out of month dates.
        // Currently it handles only dates in the month

        if(monthDisplayHelper.isWithinCurrentMonth(row,col)){
            int day = monthDisplayHelper.getDayAt(row,col);
            cellView.setText(String.valueOf(day));
            cellView.setBackgroundResource(R.drawable.cell_background);
            cellView.setOnClickListener(new DateClickListener(day, month, year, position));
            cellView.clearAllStates();
            if(monthEventFetcher!=null){
                cellView.setEvents(monthEventFetcher.getEventsForTheDay(year,month,day));
            }
            if(calendar.get(Calendar.YEAR)==year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DAY_OF_MONTH) == day){
                cellView.addState(R.attr.state_date_today);
            }
            //select item
            if(selectedItem!= null && selectedItem.getYear()==year
                    && selectedItem.getMonth()==month
                    && selectedItem.getDay() ==day){
                cellView.addState(R.attr.state_date_selected);
            }
            cellView.refreshDrawableState();
        }else{
            cellView.setBackgroundResource(android.R.color.transparent);
        }
    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    private class DateClickListener implements View.OnClickListener{

        private int iDay;
        private int iMonth;
        private int iYear;
        private int iPosition;

        public DateClickListener(int day, int month, int year, int position){
            this.iDay = day;
            this.iMonth = month;
            this.iYear = year;
            this.iPosition = position;
        }

        @Override
        public void onClick(final View v) {
            if(selectedItem!=null){
                notifyDataSetChanged();
            }else{
                selectedItem = new SelectedDateItem(iYear,iMonth,iDay);
            }

            selectedItem.setDay(iDay);
            selectedItem.setMonth(iMonth);
            selectedItem.setYear(iYear);

            if(onDateCellItemClickListener !=null){
                onDateCellItemClickListener.onDateClick(selectedItem);
            }

            v.post(new Runnable() {
                @Override
                public void run() {
                    ((BaseCellView) v).addState(R.attr.state_date_selected);
                    v.refreshDrawableState();
                }
            });

        }
    }

    public interface OnDateCellItemClickListener {
        void onDateClick(SelectedDateItem selectedItem);
    }

    interface MonthEventFetcher {
        List<Integer> getEventsForTheDay(int year,int month,int day);
    }

    public void setOnDateClickListener(OnDateCellItemClickListener onDateCellItemClickListener){
        this.onDateCellItemClickListener = onDateCellItemClickListener;
    }

    public void setSelectedItem(SelectedDateItem selectedItem, boolean notify){
        this.selectedItem = selectedItem;
        if(notify) notifyDataSetChanged();
    }

    public SelectedDateItem getSelectedItem(){
        return selectedItem;
    }

    void setMonthEventFetcher(MonthEventFetcher monthEventFetcher){
        this.monthEventFetcher = monthEventFetcher;
    }

}
