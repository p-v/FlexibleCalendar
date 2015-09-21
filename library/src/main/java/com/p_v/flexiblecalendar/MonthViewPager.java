package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.antonyt.infiniteviewpager.InfiniteViewPager;

/**
 * @author p-v
 */
class MonthViewPager extends InfiniteViewPager {

    private int rowHeight = 0;
    private int numOfRows;

    public MonthViewPager(Context context) {
        super(context);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean wrapHeight =
                MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST;

        int height = getMeasuredHeight();

        if(wrapHeight && rowHeight == 0){
            int width = getMeasuredWidth();

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    MeasureSpec.EXACTLY);

            if (getChildCount() > 0) {
                View firstChild = getChildAt(0);

                firstChild.measure(widthMeasureSpec, MeasureSpec
                        .makeMeasureSpec(height, MeasureSpec.AT_MOST));

                height = firstChild.getMeasuredHeight();
                rowHeight = numOfRows == 6? height : (int)Math.ceil(((float)height*6)/5);
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(rowHeight,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    void setNumOfRows(int numOfRows){
        this.numOfRows = numOfRows;
    }
}
