package com.example.gym.activites.classesPlan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.activites.availableTreningPlansList.AvailableTrainingPlansListActivity;
import com.example.gym.activites.trainingPlansList.MyTrainingPlansListAdapter;
import com.example.gym.activites.trainingPlansList.MyTrainingPlansListFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChooseClassesDayFragment extends Fragment {


    private ChooseClassesDayFragmentActivityListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_classes_day_fragment,container,false);

        CalendarView calendarView = view.findViewById(R.id.calendarViewChooseClassesDay);


        Calendar calendar = Calendar.getInstance();

        calendarView.setMinDate(calendar.getTimeInMillis());
        setMaxDate(calendar, calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                updateDetail(dayOfMonth, month, year);
            }
        });


        landscapeConfiguration(view);

        return view;
    }

    public interface ChooseClassesDayFragmentActivityListener{
        public void onItemSelected(int day, int month, int year);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChooseClassesDayFragmentActivityListener) {
            listener = (ChooseClassesDayFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: ChoseClassesDayFragment.ChooseClassesDayFragmentActivityListener");
        }
    }

    public void updateDetail(int day, int month, int year) {
        listener.onItemSelected(day, month, year);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    public static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public static void setMaxDate(Calendar mCalendar, CalendarView calendarView) {
        int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);
        int monday_offset;
        if (day_of_week == 1) {//jeśli niedziela
            monday_offset = -6;//przejście do poniedziałku
        } else
            monday_offset = (2 - day_of_week); // jeśli inny dzień tygodnia, to powrót o określoną liczbę dni do poniedziałku
        mCalendar.add(Calendar.DAY_OF_YEAR, monday_offset); //powrót do poniedziałku bieżącego miesiąca

        mCalendar.add(Calendar.DAY_OF_YEAR, 13);//przeniesienie się do niedzieli przyszłego tygodnia

        Date Week_Sunday_Date = mCalendar.getTime();//pobranie daty niedzieli z przyszłego tygodnia

        calendarView.setMaxDate(Week_Sunday_Date.getTime());
    }

}
