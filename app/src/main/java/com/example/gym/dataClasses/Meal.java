package com.example.gym.dataClasses;

import android.util.Log;

import com.example.gym.dataClasses.Component;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Meal {
    private String name;
    private String calories;
    private String protein;
    private String carbohydrates;
    private int id;
    private String preparation;
    private ArrayList<Component> components;



    public Meal(String name, String calories, int id) {
        this.name = name;
        this.calories = calories;
        this.id = id;
    }

    public Meal(JSONObject jsonObject) throws JSONException {
        Log.e("MealJSON", jsonObject.toString());
        if(!jsonObject.isNull("name"))
             this.name=jsonObject.getString("name");
        if(!jsonObject.isNull("calories"))
             this.calories=jsonObject.getString("calories");
        if(!jsonObject.isNull("protein"))
            this.protein=jsonObject.getString("protein");
        if(!jsonObject.isNull("carbohydrates"))
            this.carbohydrates=jsonObject.getString("carbohydrates");
        if(!jsonObject.isNull("mealId"))
             this.id=jsonObject.getInt("mealId");
        if(!jsonObject.isNull("preparation")){
            this.preparation=jsonObject.getString("preparation");
        }

        if(!jsonObject.isNull("components")) {
            components=new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("components");
            for(int i=0;i<jsonArray.length();i++)
                components.add(new Component(jsonArray.getJSONObject(i)));
        }


        }

    public String getProtein() {
        return protein;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public String getPreparation() {
        return preparation;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public String getName() {
        return name;
    }

    public String getCalories() {
        return calories;
    }

    public int getId() {
        return id;
    }
}
