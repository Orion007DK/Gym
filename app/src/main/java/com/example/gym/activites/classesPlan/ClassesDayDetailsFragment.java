package com.example.gym.activites.classesPlan;

import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClassesDayDetailsFragment extends Fragment {


    private ListView listViewClasses;
    private TextView textViewTitle;
    private TextView textViewDate;
    Calendar calendarSelectedDate = Calendar.getInstance();

    private ArrayList<String> arrayListClassesNames = new ArrayList<>();
    private ArrayList<Time> arrayListClassesStartTime = new ArrayList<>();
    private ArrayList<Time> arrayListClassesEndTime = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classes_day_details_fragment, container, false);

        idInit(view);
        arrayListsInit();
        listViewClassesInit(inflater);
        landscapeConfiguration(view);

        return  view;
    }

private void listViewClassesInit(LayoutInflater inflater){
        ClassesListAdapter classesListAdapter = new ClassesListAdapter((AppCompatActivity)getActivity(), arrayListClassesNames, arrayListClassesStartTime, arrayListClassesEndTime, this);
        listViewClasses.setAdapter(classesListAdapter);
        View header_view =inflater.inflate(R.layout.classes_list_header, null);
        listViewClasses.addHeaderView(header_view);
}

    public void setText(int day, int month, int year){
        calendarSelectedDate.set(year, month, day);
        String date = String.valueOf(DateFormat.format("dd.MM.yyyy",calendarSelectedDate));
        //String date = String.valueOf(day)+"."+String.valueOf(month)+"."+String.valueOf(year);
        String dayOfWeekName =calendarSelectedDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        dayOfWeekName=dayOfWeekName.substring(0, 1).toUpperCase() + dayOfWeekName.substring(1);//zmiana pierwszej litery na dużą
        textViewTitle.setText(dayOfWeekName);
        textViewDate.setText(date);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textViewTitle  = view.findViewById(R.id.textViewTitle);
            textViewTitle.setPadding(0,0,0,0);
            TextView textViewDate = view.findViewById(R.id.textViewDate);
            textViewDate.setPadding(0, 0, 0, 0);
        }
    }

    private void idInit(View view){
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewDate=view.findViewById(R.id.textViewDate);
        listViewClasses=view.findViewById(R.id.listViewClasses);
    }

    //do usunięcia jak będzie baza danych
    private void arrayListsInit(){

        arrayListClassesNames.add("Podnoszenie czegokolwiek");
        arrayListClassesNames.add("Fitness");
        arrayListClassesNames.add("Ćwiczenia");

        arrayListClassesStartTime.add(new Time(12,30,0));
        arrayListClassesStartTime.add(new Time(15,30,0));
        arrayListClassesStartTime.add(new Time(17,15,0));

        arrayListClassesEndTime.add(new Time(13,30,0));
        arrayListClassesEndTime.add(new Time(17,00,0));
        arrayListClassesEndTime.add(new Time(20,15,0));
    }

    public Calendar getDate(){
        return calendarSelectedDate;
    }
}
