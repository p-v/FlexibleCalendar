package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author p-v
 */
public class BaseCellView extends TextView {


    public static final int STATE_TODAY = R.attr.state_date_today;
    public static final int STATE_REGULAR = R.attr.state_date_regular;
    public static final int STATE_SELECTED = R.attr.state_date_selected;
    public static final int STATE_OUTSIDE_MONTH = R.attr.state_date_outside_month;

    private Set<Integer> stateSet;
    private Paint eventPaint;

    private int eventCircleY;
    private int radius;
    private int padding;
    private int leftMostPosition = Integer.MIN_VALUE;
    private List<Paint> paintList;

    public BaseCellView(Context context) {
        super(context);
    }

    public BaseCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.BaseCellView);
        try{
            radius = (int)a.getDimension(R.styleable.BaseCellView_event_radius,5);
            padding = (int)a.getDimension(R.styleable.BaseCellView_event_circle_padding,1);
        }finally {
            a.recycle();
        }
        stateSet = new HashSet<>(3);
        eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        eventPaint.setStyle(Paint.Style.FILL);
    }

    public void addState(int state){
        stateSet.add(state);
    }

    public void removeState(int state){
        stateSet.remove(state);
    }

    public void clearAllStates(){
        stateSet.clear();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if(stateSet==null) stateSet = new HashSet<>(3);
        if(!stateSet.isEmpty()){
            final int[] drawableState = super.onCreateDrawableState(extraSpace + stateSet.size());
            int[] states = new int[stateSet.size()];
            int i = 0;
            for(Integer s : stateSet){
                states[i++] = s;
            }
            mergeDrawableStates(drawableState,states);
            return drawableState;
        }else{
            return super.onCreateDrawableState(extraSpace);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //initialize paint objects only if there is no state or just one state i.e. the regular day state
        if((stateSet==null || stateSet.isEmpty()
                ||(stateSet.size() ==1 && stateSet.contains(STATE_REGULAR))) && paintList!=null) {
            int num = paintList.size();

            Paint p = new Paint();
            p.setTextSize(getTextSize());

            Rect rect = new Rect();
            p.getTextBounds("31", 0, 1, rect); // measuring using fake text

            eventCircleY = (3 * getHeight() + rect.height()) / 4;

            //calculate left most position for the circle
            if (leftMostPosition == Integer.MIN_VALUE) {
                leftMostPosition = (getWidth() / 2) - (num / 2) * 2 * (padding + radius);
                if (num % 2 == 0) {
                    leftMostPosition = leftMostPosition + radius + padding;
                }
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw only if there is no state or just one state i.e. the regular day state
        if((stateSet==null || stateSet.isEmpty() || (stateSet.size() ==1
                && stateSet.contains(STATE_REGULAR))) && paintList!=null) {
            int num = paintList.size();
            for (int i=0;i<num;i++) {
                canvas.drawCircle(calculateStartPoint(i), eventCircleY, radius, paintList.get(i));
            }
        }
    }

    private int calculateStartPoint(int offset){
        return leftMostPosition + offset *(2*(radius+padding)) ;
    }

    public void setEvents(List<Integer> colorList){
        if(colorList!=null){
            paintList = new ArrayList<>(colorList.size());
            for(Integer e : colorList){
                Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                eventPaint.setStyle(Paint.Style.FILL);
                eventPaint.setColor(getContext().getResources().getColor(e));
                paintList.add(eventPaint);
            }
            invalidate();
            requestLayout();
        }
    }
}
