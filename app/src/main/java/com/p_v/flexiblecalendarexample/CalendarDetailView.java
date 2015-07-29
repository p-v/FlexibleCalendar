package com.p_v.flexiblecalendarexample;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

/**
 * Created by p-v on 21/07/15.
 */
public class CalendarDetailView extends ViewPager {

    private CalendarDetailViewAdapter detailViewAdapter;

    public CalendarDetailView(Context context) {
        super(context);
    }

    public CalendarDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        detailViewAdapter = new CalendarDetailViewAdapter(getContext());
        setAdapter(new InfinitePagerAdapter(detailViewAdapter));
    }

    public CalendarDetailViewAdapter getDetailViewAdapter(){
        return detailViewAdapter;
    }

}
