package com.example.gym.activites.myClassesList;

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

import com.example.gym.R;
import com.example.gym.activites.workoutsHistory.WorkoutsHistoryListFragment;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class MyClassesListAdapter extends ArrayAdapter<String> {

    AppCompatActivity context;
    List<String> classesNames;
    List<Date> classesDates;
    List<Time> classesStartTime;
    List<Time> classesEndTime;


    MyClassesListAdapter(@NonNull AppCompatActivity context, List<String> classesNames, List<Date> classesDates, List<Time> classesStartTime, List<Time> classesEndTime) {
        super(context, R.layout.classes_list_one_line, classesNames);
        this.context=context;
        this.classesNames=classesNames;
        this.classesDates=classesDates;
        this.classesStartTime=classesStartTime;
        this.classesEndTime=classesEndTime;
        //Calendar cal =Calendar.getInstance();
       // cal.set(2004,10,5,5,30);
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
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
        String stringClassesDate= DateFormat.format("dd.MM.yyyy", classesDates.get(position)).toString();
        String time = classesStartTime.get(position).getHours()+":"+classesStartTime.get(position).getMinutes()+" - "+classesEndTime.get(position).getHours()+":"+classesEndTime.get(position).getMinutes();
        textViewClassesName.setText(classesNames.get(position));
        textViewClassesDate.setText(stringClassesDate +"\n"+ time);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, dimensionsHistoryListDates.get(position).toString(),Toast.LENGTH_SHORT).show();
                //((WorkoutsHistoryListFragment) fragmentParrent).updateDetail(workoutsHistoryListDates.get(position), workoutsHistoryListNames.get(position));
            }
        });
        return line;
    }
}
