# FlexibleCalendar

A customizable calendar for android. Now, customize your calendar your way.

![Demo](demo/demo.gif)

**Gradle**

Add dependencies in `build.gradle`,

```groovy
dependencies {
    compile 'com.p_v:flexiblecalendar:0.5.1'
}
```

Customize <b>FlexibleCalendar</b> using the <b>ICalendarView</b> interface

```java
calendarView.setCalendarView(new FlexibleCalendarView.ICalendarView() {
    @Override
    public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
        //customize the date cells
        BaseCellView cellView = (BaseCellView) convertView;
        if (cellView == null) {
            LayoutInflater inflater = LayoutInflater.from(CalendarActivity4.this);
            cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_date_cell_view, null);
        }
        if (cellType == BaseCellView.TODAY){
            cellView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            cellView.setTextSize(15);
        } else {
            cellView.setTextColor(getResources().getColor(android.R.color.white));
            cellView.setTextSize(12);
        }
        return cellView;
    }
    @Override
    public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
        //customize the weekday header cells
        BaseCellView cellView = (BaseCellView) convertView;
        if (cellView == null) {
            LayoutInflater inflater = LayoutInflater.from(CalendarActivity4.this);
            cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_week_cell_view, null);
            cellView.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
            cellView.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            cellView.setTextSize(18);
        }
        return cellView;
    }
    @Override
    public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
        return String.valueOf(defaultValue.charAt(0));
    }
});
```

Display events for a day using the <b>EventDataProvider</b>

```java
calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
    @Override
    public List<Integer> getEventsForTheDay(int year, int month, int day) {
        return getEventColorList(year,month,day);
    }
});
```

Track the month change event by setting the <b>OnMonthChangeListener</b>
```java
calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
    @Override
    public void onMonthChange(int year, int month, int direction) {
        //do things on month change
    }
});
```

Display circular events in different colors and sizes.

![Events](demo/screenshot-1.jpg)

Choose whether to show dates outside month or not by setting the `showDatesOutsideMonth` flag,
 using `FlexibleCalendarView#showDatesOutsideMonth()` method.

![Outside Month](demo/screenshot-2.jpg) &nbsp; ![Without Outside Month](demo/screenshot-3.jpg)

<b>Navigate the calendar:</b>

`goToCurrentMonth` - Go the the current month <br/>
`moveToNextDate` - Select the date after the current selection <br/>
`moveToPreviousDate` - Select the date before the current selection <br/>
`moveToNextMonth` - Move the view to the next month from the current visible month <br/>
`moveToPreviousMonth` - Move the view to the previous month from the current visible month <br/>

<b>Customizable display cells with different states:</b>

`state_date_regular` - Regular date<br/>
`state_date_today` - Today's date<br/>
`state_date_selected` - Selected date<br/>
`state_date_outside_month` - Date lying outside month but in current month view<br/>

<b>Sample cell background:</b>

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:flexible="http://schemas.android.com/apk/res-auto">
    <item flexible:state_date_regular="true"
        android:drawable="@drawable/cell_purple_background"/>
    <item flexible:state_date_today="true"
        android:drawable="@drawable/cell_gray_background"/>
    <item flexible:state_date_selected="true"
        android:drawable="@drawable/cell_red_background"/>
    <item flexible:state_date_outside_month="true"
        android:drawable="@drawable/cell_blue_background"/>
</selector>
```
