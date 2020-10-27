package com.example.gym.activites.trainingPlansList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

import java.util.List;

public class ExercisesListAdapter extends ArrayAdapter {


    private AppCompatActivity context;
    private List<String> exercisesListNames;
    private List<String> exercisesListRepetitions;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter


    public ExercisesListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> names, @NonNull List<String> repetitions, Fragment fragment) {
        super(context, R.layout.exercises_list_one_line, names);
        this.context=context;
        this.exercisesListNames=names;
        this.exercisesListRepetitions=repetitions;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewName;
        TextView textViewRepetitions;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.exercises_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewName = line.findViewById(R.id.textViewExerciseName);
        textViewRepetitions= line.findViewById(R.id.textViewExerciseRepetitions);
        textViewName.setText(exercisesListNames.get(position));
        textViewRepetitions.setText(exercisesListRepetitions.get(position));


        //textViewSurname.setText(trainingPlansListDifficulty.get(position));
        //switch (trainingPlansListDifficulty.get(position))

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                //((TrainersListFragment) fragmentParrent).updateDetail(trainersListNames.get(position),trainersListSurnames.get(position));
            }
        });
        return line;
    }
}
