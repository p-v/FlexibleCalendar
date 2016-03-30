1.2.1
=====
- Add a flag `disableAutoDateSelection` for disabling auto date selection on month change
- Fixed all dates not getting displayed when `showDatesOutsideMonth` was disabled

1.1.5
=====
- Support library 23.2 fixes
- Fix SELECTED_TODAY state in drawDateCell()

1.1.4
=====
- Add a flag `decorateDatesOutsideMonth` to decorate dates with events outside month.

1.1.3
=====
- Fix showDatesOutsideMonth when start day of the week is set to some other value then SUNDAY
- Fix the extra height below the calendar

1.1.2
=====
- Limit the number of pages to 20000 to avoid freezing.

1.1.1
=====
- Add method `selectDate`.

1.1.0
=====
- Add method `goToCurrentDay`
- Add customization to set start day of the week.

1.0.0
=====
- Customize date cell by adding custom events. Events are now customizable
- Fixed broken `goToCurrentMonth` method.

0.5.1
=====
- Added `refresh` method to refresh the views

0.5.0
======
- Added cellType for customizing the cell based on type

0.1.1
======
- Showing dates outside month method
- Customizing day of week display value
- Select the last date of previous month on moveToPreviousDate
