package com.example.gym.activites.classesPlan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Classes;
import com.example.gym.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class ClassesListAdapter extends ArrayAdapter<Classes> {

    AppCompatActivity context;
    List<Classes> classesList;
    List<String> classesNames;
    List<Time> classesStartTime;
    List<Time> classesEndTime;
    //Calendar calendarSelectedDate;
    Fragment parentFragment;

    public static String CLASSES_NAME="classesName";
    public static String CLASSES_START_TIME="classesStartTime";
    public static String CLASSES_END_TIME="classesEndTime";
    public static String CALENDAR_SELECTED_DATE="calendarSelectedDate";




    ClassesListAdapter(@NonNull AppCompatActivity context, List<Classes> classesList, Fragment parentFragment) {
        super(context, R.layout.classes_list_one_line, classesList);
        this.context=context;
        this.classesList=classesList;
       // this.classesNames=classesNames;
        //this.classesStartTime=classesStartTime;
       // this.classesEndTime=classesEndTime;
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
        //DateFormat.format("hh-mm",classesStartTime.get(position));
        //String time =DateFormat.format("HH:mm ",classesStartTime.get(position))+" - "+DateFormat.format("HH:mm ",classesEndTime.get(position));

        //textViewClassesName.setText(classesNames.get(position));
        textViewClassesName.setText(classesList.get(position).getClassesName());
        textViewClassesDate.setText(classesList.get(position).getClassesStartTime()+"-"+classesList.get(position).getClassesEndTime());

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarSelectedDate =((ClassesDayDetailsFragment)parentFragment).getDate();
                Intent classesDetailsActivityIntent = new Intent(getContext(), ClassesDetailsActivity.class);
                //classesDetailsActivityIntent.putExtra(CLASSES_NAME, classesList.get(position).getClassesName());
               // classesDetailsActivityIntent.putExtra(CLASSES_START_TIME, classesList.get(position).getClassesStartTime());
                //classesDetailsActivityIntent.putExtra(CLASSES_END_TIME, classesList.get(position).getClassesEndTime());
               // classesDetailsActivityIntent.putExtra(CALENDAR_SELECTED_DATE, calendarSelectedDate);
                classesDetailsActivityIntent.putExtra("classes", classesList.get(position));
                context.startActivity(classesDetailsActivityIntent);

                //  Toast.makeText(context, dimensionsHistoryListDates.get(position).toString(),Toast.LENGTH_SHORT).show();
                //((WorkoutsHistoryListFragment) fragmentParrent).updateDetail(workoutsHistoryListDates.get(position), workoutsHistoryListNames.get(position));
            }
        });
        return line;
    }
}
