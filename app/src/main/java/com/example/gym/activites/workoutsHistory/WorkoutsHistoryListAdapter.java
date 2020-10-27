package com.example.gym.activites.workoutsHistory;

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

import java.util.Date;
import java.util.List;

public class WorkoutsHistoryListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<Date> workoutsHistoryListDates;
    private List<String> workoutsHistoryListNames;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    public WorkoutsHistoryListAdapter(@NonNull AppCompatActivity context, List<Date> workoutDates, List<String> workoutNames, Fragment fragment) {
        super(context, R.layout.workouts_history_one_line,workoutNames);
        this.context=context;
        this.workoutsHistoryListDates =workoutDates;
        this.workoutsHistoryListNames=workoutNames;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewWorkoutDate;
        TextView textViewWorkoutName;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.workouts_history_one_line, null);

        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewWorkoutDate = line.findViewById(R.id.textViewWorkoutDate);
        textViewWorkoutName = line.findViewById(R.id.textViewWorkoutName);
        String stringWorkoutDate= DateFormat.format("dd.MM.yyyy", workoutsHistoryListDates.get(position)).toString();
        textViewWorkoutDate.setText(stringWorkoutDate);
        textViewWorkoutName.setText(workoutsHistoryListNames.get(position));
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, dimensionsHistoryListDates.get(position).toString(),Toast.LENGTH_SHORT).show();
                ((WorkoutsHistoryListFragment) fragmentParrent).updateDetail(workoutsHistoryListDates.get(position), workoutsHistoryListNames.get(position));
            }
        });
        return line;
    }
}
