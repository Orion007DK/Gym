package com.example.gym.activites.availableDietsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class AvailableDietsList extends AppCompatActivity {

    AvailableDietsListAdapter dietsListAdapter;

    ArrayList<String> dietNamesList = new ArrayList<>();
    ArrayList<Integer> dietIdList = new ArrayList<>();

    ListView dietListView;
    IntentFilter filter;
    private SpotsDialog progressDialog;
    TextView emptyListViewText;

    final private static String GET_USER_AVAILABLE_DIETS = "getUserAvailableDiets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_diets_list);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_AVAILABLE_DIETS); //dodanie akcji od pobierania informacji o użytkownikach


        //dietsListAdapter=new AvailableDietsListAdapter(AvailableDietsList.this, dietNamesList);
        dietListView=findViewById(R.id.listViewAvailableDiets);
        emptyListViewText=findViewById(R.id.emptyListViewText);
        //dietListInit();
       // dietListView.setAdapter(dietsListAdapter);
        getDiets();
    }

    private void dietListInit(){
        dietNamesList.add("Pierwsza dieta");
        dietNamesList.add("Dieta niskokaloryczna");
        dietNamesList.add("Dieta białkowa");
    }

    private void getDiets(){
        progressDialog = new SpotsDialog(AvailableDietsList.this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_AVAILABLE_DIETS, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USER_AVAILABLE_DIETS);
        request.execute();
        Log.e("getDiets","diets");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_AVAILABLE_DIETS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("dietsList");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        dietNamesList.add(js.getString("name"));
                        dietIdList.add(js.getInt("id"));

                        //dietNamesList.add((JSONObject)jsonArray.get(i).get("name"));
                        // dietNamesList.get(i);
                        //Log.e("diet:", jsonArray.get(i).toString());
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                    //Log.e("gymsArrayList: ",trainersArrayList.toString());

                    // trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    //listView.setAdapter(trainersListAdapter);

                    dietsListAdapter=new AvailableDietsListAdapter(AvailableDietsList.this, dietNamesList, dietIdList);
                    dietListView.setAdapter(dietsListAdapter);
                    dietListView.setEmptyView(emptyListViewText);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };


}
