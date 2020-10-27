package com.example.gym.Niepotrzebne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.gym.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChooseWeekActivity extends AppCompatActivity {

    Button buttonPlanForCurrentWeekClasses;
    Button buttonPlanForNextWeekClasses;
    TextView textViewCurrentWeekStartDate;
    TextView textViewCurrentWeekEndDate;
    TextView textViewNextWeekStartDate;
    TextView textViewNextWeekEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_week);
        idInit();


        CalendarView calendarView = findViewById(R.id.calendarView);


        Calendar c1 = Calendar.getInstance();
        //Calendar c2 = Calendar.getInstance();

        calendarView.setMinDate(c1.getTimeInMillis());
        setMaxDate(c1, calendarView);


  /*      //first day of week
        c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//poniedziałek

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        String date1=String.valueOf(day1)+"."+String.valueOf(month1)+"."+String.valueOf(year1);
*/

  /*
        //last day of week
        c1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//niedziela

        int year7 = c1.get(Calendar.YEAR);
        int month7 = c1.get(Calendar.MONTH)+1;
        int day7 = c1.get(Calendar.DAY_OF_MONTH);
        String date2=String.valueOf(day7)+"."+String.valueOf(month7)+"."+String.valueOf(year7);

        calendarView.setMaxDate(c1.getTimeInMillis());

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 9);
    textViewCurrentWeekStartDate.setText(date1);
    textViewCurrentWeekEndDate.setText(date2);
    Calendar c3 = Calendar.getInstance();
    textViewNextWeekStartDate.setText(getNextWeek(c3));
*/

        //  calendarView.setMaxDate(endOfMonth);
        //  calendarView.setMinDate(startOfMonth);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
    }

    private void idInit() {
        buttonPlanForCurrentWeekClasses = findViewById(R.id.buttonPlanForCurrentWeekClasses);
        buttonPlanForNextWeekClasses = findViewById(R.id.buttonPlanForNextWeekClasses);
        textViewCurrentWeekStartDate = findViewById(R.id.textViewCurrentWeekStartDate);
        textViewCurrentWeekEndDate = findViewById(R.id.textViewCurrentWeekEndDate);
        textViewNextWeekStartDate = findViewById(R.id.textViewNextWeekStartDate);
        textViewNextWeekEndDate = findViewById(R.id.textViewNextWeekEndDate);
    }



//ustawianie maksymalnej daty na ostatni dzień przyszłego tygodnia
    public static void setMaxDate(Calendar mCalendar, CalendarView calendarView) {
        mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateMonday = mCalendar.getTime();


        calendarView.setMaxDate(mDateMonday.getTime());
    }


    public static String getNextWeek(Calendar mCalendar) {
        // Monday
        mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        Date mDateMonday = mCalendar.getTime();

        int year1 = mCalendar.get(Calendar.YEAR);
        int month1 = mCalendar.get(Calendar.MONTH) + 1;
        int day1 = mCalendar.get(Calendar.DAY_OF_MONTH);
        String date1 = String.valueOf(day1) + "." + String.valueOf(month1) + "." + String.valueOf(year1);

        // Sunday
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date Week_Sunday_Date = mCalendar.getTime();

        int year2 = mCalendar.get(Calendar.YEAR);
        int month2 = mCalendar.get(Calendar.MONTH) + 1;
        int day2 = mCalendar.get(Calendar.DAY_OF_MONTH);
        String date2 = String.valueOf(day2) + "." + String.valueOf(month2) + "." + String.valueOf(year2);

        // Date format
        String strDateFormat = "dd MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(Week_Sunday_Date);

        // Sub string
        if ((MONDAY.substring(3, 6)).equals(SUNDAY.substring(3, 6))) {
            MONDAY = MONDAY.substring(0, 2);
        }

        return date1 + " - " + date2;
    }

}
