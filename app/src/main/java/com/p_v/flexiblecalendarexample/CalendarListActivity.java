package com.p_v.flexiblecalendarexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CalendarListActivity extends ActionBarActivity implements CalendarListAdapter.OnCalendarTypeClickListener {

    private List<String> calendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);

        RecyclerView calendarRecyclerView = (RecyclerView)findViewById(R.id.calendar_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        calendarRecyclerView.setLayoutManager(layoutManager);

        calendarList = new ArrayList<>();
        calendarList.add("Calendar 1");
        calendarList.add("Calendar 2");
        calendarList.add("Calendar 3");
        calendarList.add("Calendar 4");

        CalendarListAdapter adatper = new CalendarListAdapter(calendarList);
        adatper.setCalendarTypeClickListener(this);
        calendarRecyclerView.setAdapter(adatper);
    }

    @Override
    public void onCalendarTypeClick(String calendarType, int position) {
        switch(position){
            case 0:
                Intent calActivity1 = new Intent(this,CalendarActivity.class);
                startActivity(calActivity1);
                break;
            case 1:
                Intent calActivity2 = new Intent(this,CalendarActivity2.class);
                startActivity(calActivity2);
                break;

            case 2:
                Intent calActivity3 = new Intent(this,CalendarActivity3.class);
                startActivity(calActivity3);
                break;

            default:


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
