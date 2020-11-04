package com.example.gym;

import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dimensions {

    String height;
    String weight;
    String adiposeTissue;
    String muscleTissue;
    String bodyWaterPercentage;
    Date date;
    int dimensionId;

    public Dimensions(int height, int weight, int adiposeTissue, int muscleTissue, int bodyWaterPercentage, Date date, int dimensionId) {
        this.height = String.valueOf(height);
        this.weight = String.valueOf(weight);
        this.adiposeTissue = String.valueOf(adiposeTissue);
        this.muscleTissue = String.valueOf(muscleTissue);
        this.bodyWaterPercentage = String.valueOf(bodyWaterPercentage);
        this.date = date;
        this.dimensionId = dimensionId;
    }

    public Dimensions(String height, String weight, String adiposeTissue, String muscleTissue, String bodyWaterPercentage, Date date, int dimensionId) {
        this.height = String.valueOf(height);
        this.weight = String.valueOf(weight);
        this.adiposeTissue = String.valueOf(adiposeTissue);
        this.muscleTissue = String.valueOf(muscleTissue);
        this.bodyWaterPercentage = String.valueOf(bodyWaterPercentage);
        this.date = date;
        this.dimensionId = dimensionId;
    }

    public Dimensions(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getString("height").equals("null"))
            this.height =null;
        else
            this.height = jsonObject.getString("height");

        if(jsonObject.getString("weight").equals("null"))
            this.weight =null;
        else
            this.weight = jsonObject.getString("weight");

        if(jsonObject.getString("adiposeTissue").equals("null"))
            this.adiposeTissue =null;
        else
            this.adiposeTissue = jsonObject.getString("adiposeTissue");

        if(jsonObject.getString("muscleTissue").equals("null"))
            this.muscleTissue =null;
        else
            this.muscleTissue = jsonObject.getString("muscleTissue");

        if(jsonObject.getString("bodyWaterPercentage").equals("null"))
            this.bodyWaterPercentage =null;
        else
            this.bodyWaterPercentage = jsonObject.getString("bodyWaterPercentage");



        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date"));
        } catch (ParseException e) {
            this.date =null;
            e.printStackTrace();
        };
        this.dimensionId = jsonObject.getInt("dimensionId");
    }

    public Dimensions() {
    }

    public String getStringDimension(){
        return height+" "+weight+" "+adiposeTissue+" "+muscleTissue+" "+bodyWaterPercentage+" "+date.toString()+" "+String.valueOf(dimensionId)+"\n";
    }

    public Dimensions(Date date) {
        this.date = date;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getAdiposeTissue() {
        return adiposeTissue;
    }

    public String getMuscleTissue() {
        return muscleTissue;
    }

    public String getBodyWaterPercentage() {
        return bodyWaterPercentage;
    }

    public Date getDate() {
        return date;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public String getStringDate(){
        String stringDate = DateFormat.format(Constants.DATA_FORMAT,date).toString();
        return stringDate;
    }

    public String getHeightWithoutNull() {
        if(height==null || height=="")
            return "Nie podano wartości";
        return height;
    }

    public String getWeightWithoutNull() {
        if(weight==null || weight=="")
            return "Nie podano wartości";
        return weight;
    }

    public String getAdiposeTissueWithoutNull() {
        if(adiposeTissue==null || adiposeTissue=="")
            return "Nie podano wartości";
        return adiposeTissue;
    }

    public String getMuscleTissueWithoutNull() {
        if(muscleTissue==null || muscleTissue=="")
            return "Nie podano wartości";
        return muscleTissue;
    }

    public String getBodyWaterPercentageWithoutNull() {
        if(bodyWaterPercentage==null || bodyWaterPercentage=="")
            return "Nie podano wartości";
        return bodyWaterPercentage;
    }



}
