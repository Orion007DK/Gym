package com.example.gym.activites.availableTreningPlansList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.UIUtils;
import com.example.gym.activites.trainingPlansList.ExercisesListAdapter;

import java.util.ArrayList;

public class AvailableTrainingPlanDetailsFragment extends Fragment {

    TextView textViewTitle;
    TextView textViewEstimatedDurationValue;
    TextView textViewBurnedCaloriesValue;
    TextView textViewTrainingPlanDescription;
    ListView listViewExercises;
    ArrayList<String> exercisesNames= new ArrayList<>();
    ArrayList<String> exercisesRepetitions= new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_training_plan_details, container, false);
        idInit(view);
        landscapeConfiguration(view); // działa, nie trzeba zmniejszać paddingów tylko rozmiar.
        listViewInit(view);
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void setText(String textName, String textEstimatedDuration, String textBurnedCalories, String trainingPlanDescription){
        textViewTitle.setText(textName);
        textViewEstimatedDurationValue.setText(textEstimatedDuration);
        textViewBurnedCaloriesValue.setText(textBurnedCalories);
        textViewTrainingPlanDescription.setText(trainingPlanDescription);
    }

    private void idInit(View view){
        textViewEstimatedDurationValue= view.findViewById(R.id.textViewEstimatedDurationValue);
        textViewBurnedCaloriesValue=view.findViewById(R.id.textViewBurnedCaloriesValue);
        listViewExercises=view.findViewById(R.id.listViewExercises);
        textViewTitle=view.findViewById(R.id.textViewTitle);
        textViewTrainingPlanDescription=view.findViewById(R.id.textViewTrainingPlanDescription);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(0,0,0,0);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutTrainingPlanDetails);
            linearLayout.setPadding(0, 0, 0, 0);
        }
    }

    private void listViewInit(View view){
        exercisesListInit();
        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter((AppCompatActivity)view.getContext(), exercisesNames, exercisesRepetitions, this);
        listViewExercises.setAdapter(exercisesListAdapter);
        // UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName);
        UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName, view.getContext());

    }

    private void exercisesListInit(){
        exercisesNames.add("Brzuszki");
        exercisesNames.add("Pompki");
        exercisesNames.add("Podnoszenie ciężarów");
        exercisesNames.add("Jakieś ćwiczenie o bardzo długiej nazwie i dość wielu powtórzeniach, serio");

        exercisesRepetitions.add("3x15");
        exercisesRepetitions.add("2x10");
        exercisesRepetitions.add("10x6");
        exercisesRepetitions.add("100x100");
    }



}


