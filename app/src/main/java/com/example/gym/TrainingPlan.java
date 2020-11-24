package com.example.gym;

import android.util.Log;

import com.example.gym.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TrainingPlan {
    int trainingPlanId;
    String trainingPlanName;
    String trainingPlanDescription;
    int difficultyLevel;
    String estimatedDuration;
    String burnedCalories;
    String trainerName;
    String trainerSurname;
    Exercise exercises[];


    public TrainingPlan(JSONObject jsonObjectTicket) throws JSONException, ParseException {
        this.trainingPlanId = jsonObjectTicket.getInt("trainingPlanId");
        if (!jsonObjectTicket.isNull("trainingPlanName"))
            this.trainingPlanName= jsonObjectTicket.getString("trainingPlanName");
        if (!jsonObjectTicket.isNull("trainingPlanDescription"))
            this.trainingPlanDescription = jsonObjectTicket.getString("trainingPlanDescription");
        if (!jsonObjectTicket.isNull("difficultyLevel"))
            this.difficultyLevel = jsonObjectTicket.getInt("difficultyLevel");
        if (!jsonObjectTicket.isNull("estimatedDuration"))
            this.estimatedDuration = jsonObjectTicket.getString("estimatedDuration");
        if (!jsonObjectTicket.isNull("burnedCalories"))
            this.burnedCalories= jsonObjectTicket.getString("burnedCalories");
        if (!jsonObjectTicket.isNull("trainerName"))
            this.trainerName= jsonObjectTicket.getString("trainerName");
        if (!jsonObjectTicket.isNull("trainerSurname"))
            this.trainerSurname= jsonObjectTicket.getString("trainerSurname");

        JSONArray jsonExercises = jsonObjectTicket.getJSONArray("exercises");
        exercises = new Exercise[jsonExercises.length()];
        for (int j = 0; j < jsonExercises.length(); j++) {
            JSONObject jsExercise = jsonExercises.getJSONObject(j);
            this.exercises[j]=new Exercise(jsExercise);
        }
    }

    public TrainingPlan(String trainingPlanName, int difficultyLevel) {
        this.trainingPlanName = trainingPlanName;
        this.difficultyLevel = difficultyLevel;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public String getTrainingPlanName() {
        return trainingPlanName;
    }

    public String getTrainingPlanDescription() {
        return trainingPlanDescription;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getBurnedCalories() {
        return burnedCalories;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainerSurname() {
        return trainerSurname;
    }

    public Exercise[] getExercises() {
        return exercises;
    }
}
