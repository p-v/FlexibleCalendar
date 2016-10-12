package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.CircularEventCellView;

import java.util.List;

/**
 * @author p-v
 */
public class ExampleCellView extends CircularEventCellView {

    public ExampleCellView(Context context) {
        super(context);
    }

    public ExampleCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (7*width)/8;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
