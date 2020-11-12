package com.example.gym;

import org.json.JSONException;
import org.json.JSONObject;

public class Meal {
    String name;
    String calories;
    int id;

    public Meal(String name, String calories, int id) {
        this.name = name;
        this.calories = calories;
        this.id = id;
    }

    public Meal(JSONObject jsonObject) throws JSONException {
        this.name=jsonObject.getString("name");
        this.calories=jsonObject.getString("calories");
        this.id=jsonObject.getInt("mealId");
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
