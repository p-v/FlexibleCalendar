package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendarexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author p-v
 */
public class ExampleCellView2 extends BaseCellView {
        private int eventCircleY;
        private int radius;
        private int padding;
        private int leftMostPosition = Integer.MIN_VALUE;
        private List<Paint> paintList;

        public ExampleCellView2(Context context) {
            super(context);
            init();
        }

        public ExampleCellView2(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public ExampleCellView2(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init(){
            radius = (int)getResources().getDimension(R.dimen.example_cell_view_event_radius);
            padding = (int)getResources().getDimension(R.dimen.example_cell_view_event_spacing);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            if(paintList!=null) {
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
            if(paintList!=null) {
                int num = paintList.size();
                for (int i=0;i<num;i++) {
                    canvas.drawCircle(calculateStartPoint(i), eventCircleY, radius, paintList.get(i));
                }
            }
        }

        private int calculateStartPoint(int offset){
            return leftMostPosition + offset *(2*(radius+padding)) ;
        }

        @Override
        public void setEvents(List<? extends Event> colorList){
            if(colorList!=null){
                paintList = new ArrayList<>(colorList.size());
                for(Event e: colorList){
                    Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    eventPaint.setStyle(Paint.Style.FILL);
                    eventPaint.setColor(getContext().getResources().getColor(e.getColor()));
                    paintList.add(eventPaint);
                }
                invalidate();
                requestLayout();
            }
        }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //for a square cell
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
