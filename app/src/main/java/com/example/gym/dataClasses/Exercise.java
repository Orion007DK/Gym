package com.example.gym.dataClasses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

public class Exercise implements Serializable {
    int exerciseNumber;
    String exerciseName;
    String image;
    String exerciseDescription;
    String repetitions;
    String sets;
    String load;

    public Exercise(JSONObject jsonObjectTicket) throws JSONException, ParseException {
        Log.e("jsTICKET:", jsonObjectTicket.toString());
        this.exerciseNumber = jsonObjectTicket.getInt("exerciseNumber");
        if (!jsonObjectTicket.isNull("exerciseName"))
            this.exerciseName = jsonObjectTicket.getString("exerciseName");
        if (!jsonObjectTicket.isNull("image"))
            this.image = jsonObjectTicket.getString("image");
        if (!jsonObjectTicket.isNull("exerciseDescription"))
            this.exerciseDescription = jsonObjectTicket.getString("exerciseDescription");
        if (!jsonObjectTicket.isNull("repetitions"))
            this.repetitions = jsonObjectTicket.getString("repetitions");
        if (!jsonObjectTicket.isNull("sets"))
            this.sets = jsonObjectTicket.getString("sets");
        if (!jsonObjectTicket.isNull("load"))
            this.load = jsonObjectTicket.getString("load");
        else load=null;
    }

    public Exercise(String exerciseName, String repetitions, String sets) {
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
        this.sets = sets;
    }

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getImage() {
        return image;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public String getSets() {
        return sets;
    }

    public String getLoad() {
        return load;
    }
}