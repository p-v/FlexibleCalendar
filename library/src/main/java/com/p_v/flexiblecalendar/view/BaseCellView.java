package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.TextView;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.fliexiblecalendar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author p-v
 */
public abstract class BaseCellView extends TextView {

    public static final int TODAY = 0;
    public static final int SELECTED = 1;
    public static final int REGULAR = 3;
    public static final int SELECTED_TODAY = 4;
    public static final int OUTSIDE_MONTH = 5;

    @IntDef({TODAY,SELECTED,REGULAR,SELECTED_TODAY,OUTSIDE_MONTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CellType{}

    public static final int STATE_TODAY = R.attr.state_date_today;
    public static final int STATE_REGULAR = R.attr.state_date_regular;
    public static final int STATE_SELECTED = R.attr.state_date_selected;
    public static final int STATE_OUTSIDE_MONTH = R.attr.state_date_outside_month;

    private Set<Integer> stateSet;

    public BaseCellView(Context context) {
        super(context);
        stateSet = new HashSet<>(3);
    }

    public BaseCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        stateSet = new HashSet<>(3);
    }

    public BaseCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        stateSet = new HashSet<>(3);
    }

    public void addState(int state){
        stateSet.add(state);
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

    public abstract void setEvents(List<? extends Event> colorList);

    public Set<Integer> getStateSet(){
        return stateSet;
    }

}
