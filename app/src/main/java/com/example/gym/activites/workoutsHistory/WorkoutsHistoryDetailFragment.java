package com.example.gym.activites.workoutsHistory;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

public class WorkoutsHistoryDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_history_details, container, false);
        landscapeConfiguration(view);
        return  view;
    }


    public void setText(String trainingPlanName, String date, String duration, String burnedCalories){
        TextView textViewTrainingPlanNameValue= getView().findViewById(R.id.textViewTrainingPlanNameValue);
        TextView textViewWorkoutDateValue= getView().findViewById(R.id.textViewWorkoutDateValue);
        TextView textViewWorkoutDurationValue= getView().findViewById(R.id.textViewWorkoutDurationValue);
        TextView textViewBurnedCaloriesValue= getView().findViewById(R.id.textViewBurnedCaloriesValue);
        textViewTrainingPlanNameValue.setText(trainingPlanName);
        textViewWorkoutDateValue.setText(date);
        textViewWorkoutDurationValue.setText(duration);
        textViewBurnedCaloriesValue.setText(burnedCalories);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewTitle.setPadding(10, 20, 10, 20);
        }
    }
}
