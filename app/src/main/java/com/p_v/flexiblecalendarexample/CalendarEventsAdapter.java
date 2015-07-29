package com.p_v.flexiblecalendarexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p_v.flexiblecalendarexample.entity.Event;
import com.p_v.fliexiblecalendar.R;

import java.util.List;

/**
 * Created by p-v on 20/07/15.
 */
public class CalendarEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Event> eventList;

    private boolean isMoreDetailed = false;
    private static final int HOURS_IN_A_DAY = 24;

    private Context context;

    public CalendarEventsAdapter(Context context){
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i){
            case 0:
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.day_list_item, null);
                return new ViewHolder(view);
            case 1:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(getItemViewType(i) == 0){
            Event event = eventList.get(i);
            ((ViewHolder)viewHolder).eventTitleTextView.setText(event.getTitle());
            //TODO think for l10n here
            if(event.getEventTime()!=null) {
                String dateFormat = event.getEventTime().getHour() + ":" + event.getEventTime().getMinutes();
                ((ViewHolder) viewHolder).timeTextView.setText(dateFormat);
            }
        }else{

        }
    }

    @Override
    public int getItemViewType(int position) {
        return isMoreDetailed?1:0;
    }

    @Override
    public int getItemCount() {
        if(isMoreDetailed){
            return HOURS_IN_A_DAY;
        }
        return eventList==null?0:eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitleTextView;
        TextView timeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = (TextView)itemView.findViewById(R.id.time);
            eventTitleTextView = (TextView)itemView.findViewById(R.id.event_title);
        }
    }

    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
    }
}
