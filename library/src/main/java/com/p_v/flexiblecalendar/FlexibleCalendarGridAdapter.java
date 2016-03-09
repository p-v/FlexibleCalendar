package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p_v.flexiblecalendar.entity.DateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.entity.Event;
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
    private DateItem selectedItem;
    private MonthEventFetcher monthEventFetcher;
    private IDateCellViewDrawer cellViewDrawer;
    private boolean showDatesOutsideMonth;
	private boolean decorateDatesOutsideMonth;

    private static final int SIX_WEEK_DAY_COUNT = 42;


	public FlexibleCalendarGridAdapter(Context context, int year, int month,
                                       boolean showDatesOutsideMonth, boolean decorateDatesOutsideMonth, int startDayOfTheWeek){
        this.context = context;
        this.showDatesOutsideMonth = showDatesOutsideMonth;
		this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
        initialize(year,month,startDayOfTheWeek);
    }

    public void initialize(int year, int month, int startDayOfTheWeek){
        this.year = year;
        this.month = month;
        this.monthDisplayHelper = new MonthDisplayHelper(year,month,startDayOfTheWeek);
        this.calendar = FlexibleCalendarHelper.getLocalizedCalendar(context);
    }

    @Override
    public int getCount() {
        return showDatesOutsideMonth? SIX_WEEK_DAY_COUNT
                :monthDisplayHelper.getNumberOfDaysInMonth()
                + monthDisplayHelper.getWeekStartDay() + monthDisplayHelper.getFirstDayOfMonth() - 1;
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

        //compute cell type
        int cellType = BaseCellView.OUTSIDE_MONTH;
        //day at the current row and col
        int day = monthDisplayHelper.getDayAt(row,col);
        if(isWithinCurrentMonth){
            //set to REGULAR if is within current month
            cellType = BaseCellView.REGULAR;
            if(selectedItem!= null && selectedItem.getYear()==year
                    && selectedItem.getMonth()==month
                    && selectedItem.getDay() ==day){
                //selected
                cellType = BaseCellView.SELECTED;
            }
            if(calendar.get(Calendar.YEAR)==year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DAY_OF_MONTH) == day){
                if(cellType == BaseCellView.SELECTED){
                    //today and selected
                    cellType = BaseCellView.SELECTED_TODAY;
                }else{
                    //today
                    cellType = BaseCellView.TODAY;
                }
            }
        }

        BaseCellView cellView = cellViewDrawer.getCellView(position, convertView, parent, cellType);
        if(cellView==null){
            cellView = (BaseCellView) convertView;
            if(cellView == null){
                LayoutInflater inflater = LayoutInflater.from(context);
                cellView = (BaseCellView)inflater.inflate(R.layout.square_cell_layout,null);
            }
        }
        drawDateCell(cellView, day, cellType);
        return cellView;
    }

    private void drawDateCell(BaseCellView cellView,int day, int cellType){
        cellView.clearAllStates();
        if(cellType != BaseCellView.OUTSIDE_MONTH) {
            cellView.setText(String.valueOf(day));
            cellView.setOnClickListener(new DateClickListener(day, month, year));
            // add events
            if(monthEventFetcher!=null){
                cellView.setEvents(monthEventFetcher.getEventsForTheDay(year, month, day));
            }
            switch (cellType){
                case BaseCellView.SELECTED_TODAY:
        	    cellView.addState(BaseCellView.STATE_TODAY);
        	    cellView.addState(BaseCellView.STATE_SELECTED);
        	    break;
                case BaseCellView.TODAY:
                    cellView.addState(BaseCellView.STATE_TODAY);
                    break;
                case BaseCellView.SELECTED:
                    cellView.addState(BaseCellView.STATE_SELECTED);
                    break;
                default:
                    cellView.addState(BaseCellView.STATE_REGULAR);
            }
        }else{
            if(showDatesOutsideMonth){
                cellView.setText(String.valueOf(day));
                int[] temp = new int[2];
                //date outside month and less than equal to 12 means it belongs to next month otherwise previous
                if(day<=12){
                    FlexibleCalendarHelper.nextMonth(year,month,temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0]));
                }else{
                    FlexibleCalendarHelper.previousMonth(year, month, temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0]));
                }

				if(decorateDatesOutsideMonth && monthEventFetcher!=null){
					cellView.setEvents(monthEventFetcher.getEventsForTheDay(temp[0], temp[1], day));
				}

				cellView.addState(BaseCellView.STATE_OUTSIDE_MONTH);
            } else{
                cellView.setBackgroundResource(android.R.color.transparent);
                cellView.setText(null);
                cellView.setOnClickListener(null);
            }
        }
        cellView.refreshDrawableState();
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

        public DateClickListener(int day, int month, int year){
            this.iDay = day;
            this.iMonth = month;
            this.iYear = year;
        }

        @Override
        public void onClick(final View v) {
            selectedItem = new DateItem(iYear,iMonth,iDay);

            notifyDataSetChanged();

            if(onDateCellItemClickListener !=null){
                onDateCellItemClickListener.onDateClick(selectedItem);
            }

        }
    }

    public interface OnDateCellItemClickListener {
        void onDateClick(DateItem selectedItem);
    }

    interface MonthEventFetcher {
        List<? extends Event> getEventsForTheDay(int year,int month,int day);
    }

    public void setOnDateClickListener(OnDateCellItemClickListener onDateCellItemClickListener){
        this.onDateCellItemClickListener = onDateCellItemClickListener;
    }

    public void setSelectedItem(DateItem selectedItem, boolean notify){
        this.selectedItem = selectedItem;
        if(notify) notifyDataSetChanged();
    }

    public DateItem getSelectedItem(){
        return selectedItem;
    }

    void setMonthEventFetcher(MonthEventFetcher monthEventFetcher){
        this.monthEventFetcher = monthEventFetcher;
    }

    public void setCellViewDrawer(IDateCellViewDrawer cellViewDrawer){
        this.cellViewDrawer = cellViewDrawer;
    }

    public void setShowDatesOutsideMonth(boolean showDatesOutsideMonth){
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        this.notifyDataSetChanged();
    }

	public void setDecorateDatesOutsideMonth(boolean decorateDatesOutsideMonth) {
		this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
		this.notifyDataSetChanged();
	}

    public void setFirstDayOfTheWeek(int firstDayOfTheWeek){
        monthDisplayHelper = new MonthDisplayHelper(year,month,firstDayOfTheWeek);
        this.notifyDataSetChanged();
    }

}
