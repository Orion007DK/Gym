package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.gym.Constants;
import com.example.gym.GymWorker;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainerDetailFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class TrainerDetailActivity extends AppCompatActivity {

    IntentFilter filter;
    private static final String GET_TRAINER_DATA = "getTrainerData";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;
    private TrainerDetailFragment trainerDetailFragment;
    private GymWorker trainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_trainer_detail);
/*
        if (bundle != null) {
           // String name = bundle.getString("name");
           // String surname = bundle.getString("surname");
            TrainerDetailFragment detailFragment = (TrainerDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);
String name = "Michau";
String surname = "Kowal";
String email ="MichauKowal@toon.pl";
String phoneNumber = "991431131";
String description ="Michau dobrym trenerem jest";
            GymWorker trainer = new GymWorker(1, name, surname, phoneNumber, email, description);

            // ustawiamy tekst fragmentu w tej aktywności
            detailFragment.setText(trainer);
        }*/

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_TRAINER_DATA); //dodanie akcji od pobierania informacji o użytkownikach

        trainerDetailFragment = (TrainerDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);

        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int trainerId = data.getInt(Constants.SP_TRAINER_ID, -1);
        Log.e("trainerId: ", String.valueOf(trainerId));
        if(trainerId!=-1){
            if(isDataSetInPrefs()) {
                Log.e("Trainer data from: ","prefs");
                getDataFromPrefs(trainerId);
            }else {
                Log.e("Trainer data from: ", "database");
                getTrainerData(trainerId);
            }

        }
    }

    private void getTrainerData(int trainerId) {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("trainerId", String.valueOf(trainerId));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_TRAINER_DATA, params, Constants.CODE_POST_REQUEST, this, GET_TRAINER_DATA);
        request.execute();
    }

    private void setData(){
        trainerDetailFragment.setData(trainer);
    }

    private boolean isDataSetInPrefs() {
        SharedPreferences data = getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        if (data.getString(Constants.SP_GYM_WORKER_NAME, null) != null &&
                data.getString(Constants.SP_GYM_WORKER_SURNAME, null) != null &&
                data.getString(Constants.SP_GYM_WORKER_EMAIL, null) != null &&
                data.getString(Constants.SP_GYM_WORKER_PHONE_NUMBER, null) != null &&
                data.getString(Constants.SP_GYM_WORKER_DESCRIPTION, null) != null &&
                data.getInt(Constants.SP_GYM_WORKER_CLIENTS_NUMBER, -1) != -1 &&
                data.getInt(Constants.SP_GYM_WORKER_MAX_CLIENTS_NUMBER, -1) != -1)
            return true;
        return false;
    }

    private void getDataFromPrefs(int trainerId){
        SharedPreferences data = getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        String name = data.getString(Constants.SP_GYM_WORKER_NAME, null);
        String surname = data.getString(Constants.SP_GYM_WORKER_SURNAME, null);
        String email = data.getString(Constants.SP_GYM_WORKER_EMAIL, null);
        String phoneNumber = data.getString(Constants.SP_GYM_WORKER_PHONE_NUMBER, null);
        String description = data.getString(Constants.SP_GYM_WORKER_DESCRIPTION, null);
       // int clientsNumber = data.getInt(Constants.SP_GYM_WORKER_CLIENTS_NUMBER,-1);
       // int maxClientsNumber = data.getInt(Constants.SP_GYM_WORKER_MAX_CLIENTS_NUMBER,-1);
        Log.e("DATA from Prefs: ",name+" "+surname+" "+email+" "+ phoneNumber+" "+description);
        trainer = new GymWorker(trainerId, name, surname, email, phoneNumber, description);
        setData();
    }

    private void putDataIntoPref(){
        SharedPreferences data = getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        //if(trainer.getWorkerId()!=)
        //    editor.putString(Constants.SP_TRAINER_NAME,trainer.getName());
        if(trainer.getName()!=null)
            editor.putString(Constants.SP_GYM_WORKER_NAME,trainer.getName());
        if(trainer.getSurname()!=null)
            editor.putString(Constants.SP_GYM_WORKER_SURNAME,trainer.getSurname());
        if(trainer.getEmail()!=null)
            editor.putString(Constants.SP_GYM_WORKER_EMAIL,trainer.getEmail());
        if(trainer.getPhoneNumber()!=null)
            editor.putString(Constants.SP_GYM_WORKER_PHONE_NUMBER,trainer.getPhoneNumber());
        if(trainer.getDescription()!=null)
            editor.putString(Constants.SP_GYM_WORKER_DESCRIPTION,trainer.getDescription());
        if(trainer.getPhoto()!=null)
            editor.putString(Constants.SP_GYM_WORKER_PHOTO,trainer.getPhoto());
        /*if(trainer.getClientsNumber()>=0)
            editor.putInt(Constants.SP_GYM_WORKER_CLIENTS_NUMBER, trainer.getClientsNumber());
        if(trainer.getMaxClientsNumber()>=0)
            editor.putInt(Constants.SP_GYM_WORKER_MAX_CLIENTS_NUMBER, trainer.getMaxClientsNumber());*/
        editor.apply();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec", "Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_TRAINER_DATA)) {
                try {
                    String jsonstr = bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONObject jsonObject = json.getJSONObject("trainerData");
                    trainer= new GymWorker(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
                setData();
                putDataIntoPref();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //     dialog.dismiss();
                //}
            } }
    };


}
