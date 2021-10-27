package com.example.gym.dataClasses;

import android.text.format.DateFormat;

import com.example.gym.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Classes implements Serializable {

    private int classesId;
    private String classesName;
    private String classesDescription;
    private Date date;
    private String classesStartTime;
    private String classesEndTime;
    private String trainerName;
    private String trainerSurname;
    private String availableEntries;
    private String requiredOption;

    public Classes(JSONObject jsonObject) throws JSONException {
        if(jsonObject.isNull("classesId"))
            this.classesId =-1;
        else
            this.classesId = jsonObject.getInt("classesId");

        if(jsonObject.isNull("classesName"))
            this.classesName =null;
        else
            this.classesName = jsonObject.getString("classesName");

        if(jsonObject.isNull("classesDescription"))
            this.classesDescription =null;
        else
            this.classesDescription = jsonObject.getString("classesDescription");

        if(jsonObject.isNull("date"))
            this.date =null;
        else
            try {
                this.date = new SimpleDateFormat(Constants.DATABASE_DATA_FORMAT).parse(jsonObject.getString("date"));
            } catch (ParseException e) {
                this.date =null;
                e.printStackTrace();
            };

        if(jsonObject.isNull("classesStartTime"))
            this.classesStartTime=null;
        else
            this.classesStartTime = jsonObject.getString("classesStartTime");

        if(jsonObject.isNull("classesEndTime"))
            this.classesEndTime=null;
        else
            this.classesEndTime = jsonObject.getString("classesEndTime");

        if(jsonObject.isNull("trainerName"))
            this.trainerName=null;
        else
            this.trainerName = jsonObject.getString("trainerName");

        if(jsonObject.isNull("trainerSurname"))
            this.trainerSurname=null;
        else
            this.trainerSurname = jsonObject.getString("trainerSurname");

        if(jsonObject.isNull("availableEntries"))
            this.availableEntries=null;
        else
            this.availableEntries = jsonObject.getString("availableEntries");

        if(jsonObject.isNull("requiredOption"))
            this.requiredOption=null;
        else
            this.requiredOption = jsonObject.getString("requiredOption");
    }

    public int getClassesId() {
        return classesId;
    }

    public String getStringClassesId() {
        return String.valueOf(classesId);
    }
    public String getClassesName() {
        return classesName;
    }

    public String getClassesDescription() {
        return classesDescription;
    }

    public Date getDate() {
        return date;
    }

    public String getClassesStartTime() {
        return classesStartTime;
    }

    public String getClassesEndTime() {
        return classesEndTime;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainerSurname() {
        return trainerSurname;
    }

    public String getAvailableEntries() {
        return availableEntries;
    }

    public String getRequiredOption() {
        return requiredOption;
    }

    public String getStringDate(){
        String stringDate = DateFormat.format(Constants.DATA_FORMAT,date).toString();
        return stringDate;
    }

    public String getStringDateInDatabaseFormat(){
        String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,date).toString();
        return stringDate;
    }

    public void setAvailableEntries(String availableEntries) {
        this.availableEntries = availableEntries;
    }

    public String getTime(){
        return classesStartTime+"-"+classesEndTime;
    }

    public Classes(String classesName, String classesStartTime, String classesEndTime) {
        this.classesName = classesName;
        this.classesStartTime = classesStartTime;
        this.classesEndTime = classesEndTime;
    }

    public Classes(String classesName, Date date, String classesStartTime, String classesEndTime) {
        this.classesName = classesName;
        this.date = date;
        this.classesStartTime = classesStartTime;
        this.classesEndTime = classesEndTime;
    }
}
