package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.CircularEventCellView;

import java.util.List;

/**
 * @author p-v
 */
public class ExampleCellView4 extends CircularEventCellView {

    public ExampleCellView4(Context context) {
        super(context);
    }

    public ExampleCellView4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleCellView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = width/2;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
