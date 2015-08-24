package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by p-v on 15/07/15.
 */
public class SquareCellView extends CircularEventCellView {

    public SquareCellView(Context context) {
        super(context);
    }

    public SquareCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //making sure the cell view is a square
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
