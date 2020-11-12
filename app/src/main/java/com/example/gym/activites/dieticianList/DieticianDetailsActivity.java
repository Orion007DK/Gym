package com.example.gym.activites.dieticianList;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class DieticianDetailsActivity extends AppCompatActivity {

    IntentFilter filter;
    private static final String GET_DIETICIAN_DATA = "getDieticianData";



    //ProgressDialog dialog;
    private SpotsDialog progressDialog;
    private DieticiansDetailFragment dieticiansDetailFragment;
    private GymWorker dietician;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietician_details);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_DIETICIAN_DATA); //dodanie akcji od pobierania informacji o użytkownikach

        dieticiansDetailFragment = (DieticiansDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);

        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int dieticianId = data.getInt(Constants.SP_DIETICIAN_ID, -1);
        Log.e("dieticianId: ", String.valueOf(dieticianId));
        if(dieticianId!=-1){
            if(isDataSetInPrefs()) {
                Log.e("Dietician data from: ","prefs");
                getDataFromPrefs(dieticianId);
            }else {
                Log.e("Dietician data from: ", "database");
                getDieticianData(dieticianId);
            }

        }

}

        private void getDieticianData(int dieticianId) {
            progressDialog = new SpotsDialog(this, R.style.Custom);
            progressDialog.show();
            registerReceiver(broadcastReceiver, filter);
            HashMap<String, String> params = new HashMap<>();
            params.put("dieticianId", String.valueOf(dieticianId));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_DIETICIAN_DATA, params, Constants.CODE_POST_REQUEST, this, GET_DIETICIAN_DATA);
            request.execute();
    }

        private void setData(){
            dieticiansDetailFragment.setText(dietician);
        }

       private boolean isDataSetInPrefs() {
            SharedPreferences data = getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
            if (data.getString(Constants.SP_GYM_WORKER_NAME, null) != null &
                    data.getString(Constants.SP_GYM_WORKER_SURNAME, null) != null &
                    data.getString(Constants.SP_GYM_WORKER_EMAIL, null) != null &
                    data.getString(Constants.SP_GYM_WORKER_PHONE_NUMBER, null) != null &
                    data.getString(Constants.SP_GYM_WORKER_DESCRIPTION, null) != null)
                return true;
            return false;
        }

        private void getDataFromPrefs(int dieticianId){
            SharedPreferences data = getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
            String name = data.getString(Constants.SP_GYM_WORKER_NAME, null);
            String surname = data.getString(Constants.SP_GYM_WORKER_SURNAME, null);
            String email = data.getString(Constants.SP_GYM_WORKER_EMAIL, null);
            String phoneNumber = data.getString(Constants.SP_GYM_WORKER_PHONE_NUMBER, null);
            String description = data.getString(Constants.SP_GYM_WORKER_DESCRIPTION, null);
            Log.e("DATA from Prefs: ",name+" "+surname+" "+email+" "+ phoneNumber+" "+description);
            dietician = new GymWorker(dieticianId, name, surname, email, phoneNumber, description);
            setData();
        }

        private void putDataIntoPref(){
            SharedPreferences data = getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = data.edit();
            //if(dietician.getWorkerId()!=)
            //    editor.putString(Constants.SP_DIETICIAN_NAME,dietician.getName());
            if(dietician.getName()!=null)
                editor.putString(Constants.SP_GYM_WORKER_NAME,dietician.getName());
            if(dietician.getSurname()!=null)
                editor.putString(Constants.SP_GYM_WORKER_SURNAME,dietician.getSurname());
            if(dietician.getEmail()!=null)
                editor.putString(Constants.SP_GYM_WORKER_EMAIL,dietician.getEmail());
            if(dietician.getPhoneNumber()!=null)
                editor.putString(Constants.SP_GYM_WORKER_PHONE_NUMBER,dietician.getPhoneNumber());
            if(dietician.getDescription()!=null)
                editor.putString(Constants.SP_GYM_WORKER_DESCRIPTION,dietician.getDescription());
            if(dietician.getPhoto()!=null)
                editor.putString(Constants.SP_GYM_WORKER_PHOTO,dietician.getPhoto());
            editor.apply();
        }

        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("rec", "Start");
                Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
                if (Objects.equals(intent.getAction(), GET_DIETICIAN_DATA)) {
                    try {
                        String jsonstr = bundle.getString("JSON");
                        JSONObject json = new JSONObject(jsonstr);
                        JSONObject jsonObject = json.getJSONObject("dieticianData");
                        dietician= new GymWorker(jsonObject);

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



                }

            }
        };
}
