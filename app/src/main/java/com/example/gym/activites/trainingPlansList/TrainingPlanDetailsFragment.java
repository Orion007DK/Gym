package com.example.gym.activites.trainingPlansList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import com.example.gym.Exercise;
import com.example.gym.R;
import com.example.gym.TrainingPlan;
import com.example.gym.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainingPlanDetailsFragment extends Fragment {

    TextView textViewTitle;
    TextView textViewEstimatedDurationValue;
    TextView textViewBurnedCaloriesValue;
    TextView textViewCompletedTrainingsValue;
    TextView textViewTrainingPlanDescription;
    ListView listViewExercises;
    ArrayList<String> exercisesNames= new ArrayList<>();
    ArrayList<String> exercisesRepetitions= new ArrayList<>();

    ArrayList<Exercise> exercisesArrayList = new ArrayList<>();

    private AppCompatActivity appContext;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_plan_details, container, false);
        idInit(view);
        appContext=(AppCompatActivity)view.getContext();
           landscapeConfiguration(view); // działa, nie trzeba zmniejszać paddingów tylko rozmiar.
        listViewInit(view);
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void setText(TrainingPlan trainingPlan){
        textViewTitle.setText(trainingPlan.getTrainingPlanName());
        textViewEstimatedDurationValue.setText(trainingPlan.getEstimatedDuration());
        textViewBurnedCaloriesValue.setText(trainingPlan.getBurnedCalories());
        textViewCompletedTrainingsValue.setText("Brak danych");
        textViewTrainingPlanDescription.setText(trainingPlan.getTrainingPlanDescription());
        //Log.e("array", trainingPlan.getExercises().toString());
       exercisesArrayList=new ArrayList<>(Arrays.asList(trainingPlan.getExercises()));
        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(appContext, exercisesArrayList, this);
        listViewExercises.setAdapter(exercisesListAdapter);
    }

    private void idInit(View view){
        textViewEstimatedDurationValue= view.findViewById(R.id.textViewEstimatedDurationValue);
        textViewBurnedCaloriesValue=view.findViewById(R.id.textViewBurnedCaloriesValue);
        textViewCompletedTrainingsValue=view.findViewById(R.id.textViewCompletedTrainingsValue);
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
        //exercisesListInit();
        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter((AppCompatActivity)view.getContext(), exercisesArrayList, this);
        listViewExercises.setAdapter(exercisesListAdapter);
       // UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName);
        UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName, view.getContext());

    }

    private void exercisesListInit(){
        exercisesArrayList.add(new Exercise("Brzuszki","2","5"));
        exercisesArrayList.add(new Exercise("Pompki","2","10"));
        exercisesArrayList.add(new Exercise("Podnoszenie ciężarów","10","6"));
        exercisesArrayList.add(new Exercise("Jakieś ćwiczenie o bardzo długiej nazwie i dość wielu powtórzeniach, serio","100","101"));


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
