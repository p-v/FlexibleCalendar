package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.ICellViewDrawer;
import com.p_v.flexiblecalendar.view.IDateCellViewDrawer;
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
    private IDateCellViewDrawer cellViewDrawer;
    private boolean showDatesOutsideMonth;

    private static final int SIX_WEEK_DAY_COUNT = 42;


    public FlexibleCalendarGridAdapter(Context context, int year, int month, boolean showDatesOutsideMonth ){
        this.context = context;
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        initialize(year,month);
    }

    public void initialize(int year, int month){
        this.year = year;
        this.month = month;
        this.monthDisplayHelper = new MonthDisplayHelper(year,month);
        this.calendar = FlexibleCalendarHelper.getLocalizedCalendar(context);
    }

    @Override
    public int getCount() {
        return showDatesOutsideMonth? SIX_WEEK_DAY_COUNT
                :monthDisplayHelper.getNumberOfDaysInMonth() + monthDisplayHelper.getFirstDayOfMonth() - 1;
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
        int row = position/7;
        int col = position%7;

        //checking if is within current month
        boolean isWithinCurrentMonth = monthDisplayHelper.isWithinCurrentMonth(row,col);

        BaseCellView cellView = cellViewDrawer.getCellView(position, convertView, parent, isWithinCurrentMonth);
        if(cellView==null){
            cellView = (BaseCellView) convertView;
            if(cellView == null){
                LayoutInflater inflater = LayoutInflater.from(context);
                cellView = (BaseCellView)inflater.inflate(R.layout.base_cell_layout,null);
            }
        }
        drawDateCell(cellView,position, isWithinCurrentMonth, row, col);
        return cellView;
    }

    private void drawDateCell(BaseCellView cellView, int position, boolean isWithinCurrentMonth,
                              int row, int col){

        if(isWithinCurrentMonth){
            int day = monthDisplayHelper.getDayAt(row,col);
            cellView.setText(String.valueOf(day));
            cellView.setOnClickListener(new DateClickListener(day, month, year, position));
            cellView.clearAllStates();
            if(monthEventFetcher!=null){
                cellView.setEvents(monthEventFetcher.getEventsForTheDay(year, month, day));
            }
            //select item
            if(selectedItem!= null && selectedItem.getYear()==year
                    && selectedItem.getMonth()==month
                    && selectedItem.getDay() ==day){
                cellView.addState(BaseCellView.STATE_SELECTED);
            }else if(calendar.get(Calendar.YEAR)==year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DAY_OF_MONTH) == day){
                cellView.addState(BaseCellView.STATE_TODAY);
            }else{
                cellView.addState(BaseCellView.STATE_REGULAR);
            }
            cellView.refreshDrawableState();
        }else{
            if(showDatesOutsideMonth){
                int day = monthDisplayHelper.getDayAt(row,col);
                cellView.setText(String.valueOf(day));
                int[] temp = new int[2];
                //date outside month and less than equal to 12 means it belongs to next month otherwise previous
                if(day<=12){
                    FlexibleCalendarHelper.nextMonth(year,month,temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0], position));
                }else{
                    FlexibleCalendarHelper.previousMonth(year, month, temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0], position));
                }

                cellView.clearAllStates();
                //select item
                if(selectedItem!= null && selectedItem.getYear()==temp[0]
                        && selectedItem.getMonth()==temp[1]
                        && selectedItem.getDay() ==day){
                    cellView.addState(BaseCellView.STATE_SELECTED);
                }else{
                    cellView.addState(BaseCellView.STATE_OUTSIDE_MONTH);
                }
                cellView.refreshDrawableState();
            } else{
                cellView.setBackgroundResource(android.R.color.transparent);
            }

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
            if(selectedItem==null){
                selectedItem = new SelectedDateItem(iYear,iMonth,iDay);
            }

            selectedItem.setDay(iDay);
            selectedItem.setMonth(iMonth);
            selectedItem.setYear(iYear);

            notifyDataSetChanged();

            if(onDateCellItemClickListener !=null){
                onDateCellItemClickListener.onDateClick(selectedItem);
            }

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

    public void setCellViewDrawer(IDateCellViewDrawer cellViewDrawer){
        this.cellViewDrawer = cellViewDrawer;
    }

}
