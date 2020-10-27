package com.example.gym.activites.classesPlan;

import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.ClassesDetailsActivity;
import com.example.gym.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class ClassesListAdapter extends ArrayAdapter<String> {

    AppCompatActivity context;
    List<String> classesNames;
    List<Time> classesStartTime;
    List<Time> classesEndTime;
    //Calendar calendarSelectedDate;
    Fragment parentFragment;

    public static String CLASSES_NAME="classesName";
    public static String CLASSES_START_TIME="classesStartTime";
    public static String CLASSES_END_TIME="classesEndTime";
    public static String CALENDAR_SELECTED_DATE="calendarSelectedDate";




    ClassesListAdapter(@NonNull AppCompatActivity context, List<String> classesNames, List<Time> classesStartTime, List<Time> classesEndTime, Fragment parentFragment) {
        super(context, R.layout.classes_list_one_line, classesNames);
        this.context=context;
        this.classesNames=classesNames;
        this.classesStartTime=classesStartTime;
        this.classesEndTime=classesEndTime;
        this.parentFragment=parentFragment;
  //      this.calendarSelectedDate=calendarSelectedDate;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View line;
        TextView textViewClassesName;
        TextView textViewClassesDate;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.classes_list_one_line, null);

        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewClassesName = line.findViewById(R.id.textViewClassesName);
        textViewClassesDate = line.findViewById(R.id.textViewClassesDate);
        DateFormat.format("hh-mm",classesStartTime.get(position));
        String time =DateFormat.format("HH:mm ",classesStartTime.get(position))+" - "+DateFormat.format("HH:mm ",classesEndTime.get(position));
        textViewClassesName.setText(classesNames.get(position));
        textViewClassesDate.setText(time);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarSelectedDate =((ClassesDayDetailsFragment)parentFragment).getDate();
                Intent classesDetailsActivityIntent = new Intent(getContext(), ClassesDetailsActivity.class);
                classesDetailsActivityIntent.putExtra(CLASSES_NAME, classesNames.get(position));
                classesDetailsActivityIntent.putExtra(CLASSES_START_TIME, classesStartTime.get(position));
                classesDetailsActivityIntent.putExtra(CLASSES_END_TIME, classesEndTime.get(position));
                classesDetailsActivityIntent.putExtra(CALENDAR_SELECTED_DATE, calendarSelectedDate);
                context.startActivity(classesDetailsActivityIntent);
                //  Toast.makeText(context, dimensionsHistoryListDates.get(position).toString(),Toast.LENGTH_SHORT).show();
                //((WorkoutsHistoryListFragment) fragmentParrent).updateDetail(workoutsHistoryListDates.get(position), workoutsHistoryListNames.get(position));
            }
        });
        return line;
    }
}
