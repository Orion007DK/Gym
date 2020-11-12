package com.example.gym;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;

import java.util.HashMap;

public class SharedPreferencesOperations {

    static public void clearTrainerData(Context context) {
        SharedPreferences dataTrainer = context.getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorTrainer = dataTrainer.edit();
        editorTrainer.clear();
        editorTrainer.apply();
    }

    static public void removeTrainerId(Context context){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(Constants.SP_TRAINER_ID);
        editor.apply();
    }

    static public void clearDieticianData(Context context) {
        SharedPreferences dataDietician = context.getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorDietician = dataDietician.edit();
        editorDietician.clear();
        editorDietician.apply();
    }

    static public void removeDieteticianId(Context context){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(Constants.SP_DIETICIAN_ID);
        editor.apply();
    }

    static public void clearGymData(Context context){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(Constants.SP_GYM_ID);
        editor.apply();
    }

    static public int getGymId(Context context){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        return  data.getInt(Constants.SP_GYM_ID, -1);
    }

    static public int getUserId(Context context){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        return  data.getInt(Constants.SP_USER_ID, -1);
    }






}

