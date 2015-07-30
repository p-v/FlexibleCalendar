package com.p_v.flexiblecalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.fliexiblecalendar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.List;

/**
 * A Flexible calendar view
 *
 * @author p-v
 */
public class FlexibleCalendarView extends LinearLayout implements
        FlexibleCalendarGridAdapter.OnDateCellItemClickListener,
        FlexibleCalendarGridAdapter.MonthEventFetcher {

    /**
     * Event Data Provider used for displaying events for a particular date
     */
    public interface EventDataProvider {
        List<Integer> getEventsForTheDay(int year,int month, int day);
    }

    /**
     * Listener for month change.
     */
    public interface OnMonthChangeListener{
        /**
         * Called whenever there is a month change
         * @param year the selected month's year
         * @param month the selected month
         * @param direction  LEFT or RIGHT
         */
        void onMonthChange(int year, int month, @Direction int direction);
    }

    /**
     * Click listener for date cell
     */
    public interface OnDateClickListener{
        /**
         * Called whenever a date cell is clicked
         * @param day selected day
         * @param month selected month
         * @param year selected year
         */
        void onDateClick(int year,int month, int day);
    }

    /*
     * Direction Constants
     */
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    private InfinitePagerAdapter monthInfPagerAdapter;

    /**
     * Direction for movement of FlexibleCalendarView left or right
     */
    @IntDef({LEFT,RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction{}

    private Context context;
    /**
     * View pager for the month view
     */
    private MonthViewPager monthViewPager;

    private OnMonthChangeListener onMonthChangeListener;
    private OnDateClickListener onDateClickListener;

    private MonthViewPagerAdapter monthViewPagerAdapter;
    private EventDataProvider eventDataProvider;

    private int startDisplayYear;
    private int startDisplayMonth;
    private int startDisplayDay;

    /**
     * Currently selected date item
     */
    private SelectedDateItem selectedDateItem;

    public FlexibleCalendarView(Context context){
        super(context);
        this.context = context;
    }

    public FlexibleCalendarView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        init(attrs);
    }

    public FlexibleCalendarView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs){
        setAttributes(attrs);
        setOrientation(VERTICAL);

        //create week view header
        GridView weekDisplayView = new GridView(context);
        weekDisplayView.setLayoutParams(
                new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.WRAP_CONTENT));
        weekDisplayView.setNumColumns(7);
        weekDisplayView.setColumnWidth(GridView.STRETCH_COLUMN_WIDTH);
        weekDisplayView.setAdapter(new WeekdayNameDisplayAdapter(getContext(), android.R.layout.simple_list_item_1));
        this.addView(weekDisplayView);

        //setup month view
        monthViewPager = new MonthViewPager(context);
        monthViewPager.setNumOfRows(FlexibleCalendarHelper.getNumOfRowsForTheMonth(startDisplayYear,startDisplayMonth));
        monthViewPagerAdapter = new MonthViewPagerAdapter(context, startDisplayYear, startDisplayMonth, this);
        monthViewPagerAdapter.setMonthEventFetcher(this);
        monthInfPagerAdapter = new InfinitePagerAdapter(monthViewPagerAdapter);
        monthViewPager.setAdapter(monthInfPagerAdapter);
        monthViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        monthViewPager.addOnPageChangeListener(new MonthChangeListener());

        //initialize with the current selected item
        selectedDateItem = new SelectedDateItem(startDisplayYear,startDisplayMonth,startDisplayDay);
        monthViewPagerAdapter.setSelectedItem(selectedDateItem);

        this.addView(monthViewPager);
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FlexibleCalendarView);
        try {
            Calendar cal = Calendar.getInstance(FlexibleCalendarHelper.getLocale(context));
            startDisplayMonth = a.getInteger(R.styleable.FlexibleCalendarView_startDisplayMonth,cal.get(Calendar.MONTH));
            startDisplayYear = a.getInteger(R.styleable.FlexibleCalendarView_startDisplayYear,cal.get(Calendar.YEAR));
            startDisplayDay = cal.get(Calendar.DAY_OF_MONTH);
        } finally {
            a.recycle();
        }
    }

    private class MonthChangeListener implements ViewPager.OnPageChangeListener{

        //Initializing with the offset value
        private int lastPosition = monthInfPagerAdapter.getRealCount() * 100;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int direction = position>lastPosition? RIGHT : LEFT;

            //refresh the previous adapter and deselect the item
            monthViewPagerAdapter.getMonthAdapterAtPosition(lastPosition % 4).setSelectedItem(null,true);

            //the month view pager adater will update here again
            monthViewPagerAdapter.refreshDateAdapters(position%4);

            //update last position
            lastPosition = position;

            //update the currently selected date item
            FlexibleCalendarGridAdapter adapter = monthViewPagerAdapter.getMonthAdapterAtPosition(position%4);
            selectedDateItem = adapter.getSelectedItem();

            if(onMonthChangeListener!=null){
                //fire on month change event
                startDisplayYear = adapter.getYear();
                startDisplayMonth = adapter.getMonth();
                onMonthChangeListener.onMonthChange(startDisplayYear, startDisplayMonth,direction);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    /**
     * Expand the view with animation
     */
    public void expand() {
        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = getMeasuredHeight();

        getLayoutParams().height = 0;
        setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(((int)(targetHeight / getContext().getResources().getDisplayMetrics().density)));
        startAnimation(a);
    }


    /**
     * Collapse the view with animation
     */
    public void collapse(){
        final int initialHeight = this.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    setVisibility(View.GONE);
                }else{
                    getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(((int)(initialHeight / getContext().getResources().getDisplayMetrics().density)));
        startAnimation(a);
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener){
        this.onMonthChangeListener = onMonthChangeListener;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener){
        this.onDateClickListener = onDateClickListener;
    }

    public void setEventDataProvider(EventDataProvider eventDataProvider){
        this.eventDataProvider = eventDataProvider;
    }

   /* /**
     * Set the start display year and month
     * @param year  start year to display
     * @param month  start month to display
     *//*
    public void setStartDisplay(int year,int month){
        //TODO revisit there is something wrong going here things are not selected
        this.startDisplayYear = year;
        this.startDisplayMonth = month;
        invalidate();
        requestLayout();
    }
*/
    @Override
    public void onDateClick(SelectedDateItem selectedItem) {
        this.selectedDateItem = selectedItem;
        if(onDateClickListener!=null) {
            onDateClickListener.onDateClick(selectedItem.getYear(), selectedItem.getMonth(), selectedItem.getDay());
        }
    }

    /**
     * @return currently selected date
     */
    public SelectedDateItem getSelectedDateItem(){
        return selectedDateItem;
    }

    /**
     * Move the selection to the next day
     */
    public void moveToPreviousDate(){
        if(selectedDateItem!=null){
            Calendar cal = Calendar.getInstance();
            cal.set(selectedDateItem.getYear(), selectedDateItem.getMonth(), selectedDateItem.getDay());
            cal.add(Calendar.DATE, -1);

            if(selectedDateItem.getMonth()!=cal.get(Calendar.MONTH)){
                monthViewPager.setCurrentItem(monthViewPager.getCurrentItem()-1,true);
                monthViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar cal = FlexibleCalendarHelper.getLocalizedCalendar(context);
                        cal.set(selectedDateItem.getYear(), selectedDateItem.getMonth(), selectedDateItem.getDay());
                        selectedDateItem.setDay(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        monthViewPagerAdapter.setSelectedItem(selectedDateItem);
                    }
                });
            }else{
                selectedDateItem.setDay(cal.get(Calendar.DAY_OF_MONTH));
                selectedDateItem.setMonth(cal.get(Calendar.MONTH));
                selectedDateItem.setYear(cal.get(Calendar.YEAR));
                monthViewPagerAdapter.setSelectedItem(selectedDateItem);
            }
        }
    }

    /**
     * Move the selection to the previous day
     */
    public void moveToNextDate(){
        if(selectedDateItem!=null){
            Calendar cal = Calendar.getInstance();
            cal.set(selectedDateItem.getYear(), selectedDateItem.getMonth(), selectedDateItem.getDay());
            cal.add(Calendar.DATE, 1);

            if(selectedDateItem.getMonth()!=cal.get(Calendar.MONTH)){
                monthViewPager.setCurrentItem(monthViewPager.getCurrentItem() + 1, true);
            }else{
                selectedDateItem.setDay(cal.get(Calendar.DAY_OF_MONTH));
                selectedDateItem.setMonth(cal.get(Calendar.MONTH));
                selectedDateItem.setYear(cal.get(Calendar.YEAR));
                monthViewPagerAdapter.setSelectedItem(selectedDateItem);
            }
        }
    }

    @Override
    public List<Integer> getEventsForTheDay(int year, int month, int day) {
        return eventDataProvider == null?
                null : eventDataProvider.getEventsForTheDay(year,month,day);
    }
}
