package com.p_v.flexiblecalendarexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author p-v
 */
public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder> {

    private List<String> calendarList;
    private OnCalendarTypeClickListener onCalendarTypeClickListener;

    public CalendarListAdapter(List<String> calendarList){
        this.calendarList = calendarList;
    }

    @Override
    public CalendarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarListAdapter.ViewHolder holder, int position) {
        ((TextView)holder.itemView).setText(calendarList.get(position));
    }

    @Override
    public int getItemCount() {
        return calendarList == null? 0 : calendarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onCalendarTypeClickListener!=null){
                onCalendarTypeClickListener
                        .onCalendarTypeClick(calendarList.get(getPosition()),getPosition());
            }

        }
    }

    public void setCalendarTypeClickListener(OnCalendarTypeClickListener onCalendarTypeClickListener){
        this.onCalendarTypeClickListener = onCalendarTypeClickListener;
    }

    public interface OnCalendarTypeClickListener {
        void onCalendarTypeClick(String calendarType, int position);
    }


}
