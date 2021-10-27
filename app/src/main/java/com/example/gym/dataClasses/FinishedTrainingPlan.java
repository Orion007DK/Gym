package com.example.gym.dataClasses;

import android.text.format.DateFormat;

import com.example.gym.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FinishedTrainingPlan implements Serializable {

    int finishedTrainingPlanId;
    int trainingPlanId;
    String trainingPlanName;
    String trainingPlanDescription;
    Date date;
    String duration;

    public FinishedTrainingPlan(JSONObject jsonObjectTicket) throws JSONException, ParseException {
        this.finishedTrainingPlanId = jsonObjectTicket.getInt("finishedTrainingPlanId");
        this.trainingPlanId = jsonObjectTicket.getInt("trainingPlanId");
        if (!jsonObjectTicket.isNull("trainingPlanName"))
            this.trainingPlanName= jsonObjectTicket.getString("trainingPlanName");
        if (!jsonObjectTicket.isNull("trainingPlanDescription"))
            this.trainingPlanDescription = jsonObjectTicket.getString("trainingPlanDescription");
        if (!jsonObjectTicket.isNull("date")) {
            try {
                this.date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObjectTicket.getString("date"));
            } catch (ParseException e) {
                this.date = null;
                e.printStackTrace();
            };
        }
        if (!jsonObjectTicket.isNull("duration"))
            this.duration = jsonObjectTicket.getString("duration");
    }

    public FinishedTrainingPlan(Date date, String trainingPlanName) {
        this.trainingPlanName = trainingPlanName;
        this.date = date;
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

    public int getFinishedTrainingPlanId() {
        return finishedTrainingPlanId;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate(){
        String stringDate = DateFormat.format(Constants.DATA_FORMAT,date).toString();
        return stringDate;
    }

    public String getDuration() {
        return duration;
    }
}
