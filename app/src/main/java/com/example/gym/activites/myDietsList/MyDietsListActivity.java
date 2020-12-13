package com.example.gym.activites.myDietsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gym.activites.OptionsMenuActivity;
import com.example.gym.activites.availableDietsList.AvailableDietsList;
import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.activites.HomePageActivity;
import com.example.gym.SharedPreferencesOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class MyDietsListActivity extends OptionsMenuActivity {
    ArrayList<String> dietNamesList = new ArrayList<>();
    ArrayList<Integer> dietIdList = new ArrayList<>();
    DietsListAdapter dietsListAdapter;
    ListView dietListView;
    TextView emptyListViewText;
    private FloatingActionButton floatingActionButtonAddDiet;

    IntentFilter filter;

    private SpotsDialog progressDialog;

    final private static String GET_USER_DIETS = "getUserDiets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diets_list);
      //  dietListInit();

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_DIETS); //dodanie akcji od pobierania informacji o użytkownikach


        floatingActionButtonAddDiet=findViewById(R.id.floatingActionButtonAddDiet);
        emptyListViewText=findViewById(R.id.emptyListViewText);

        dietListView=findViewById(R.id.listViewDiets);
     //   dietsListAdapter=new DietsListAdapter(MyDietsListActivity.this, dietNamesList, dietIdList);
     //   dietListView.setAdapter(dietsListAdapter);

       // progressDialog = new SpotsDialog(MyDietsListActivity.this, R.style.Custom);
        //progressDialog.show();
        getDiets();

        floatingActionButtonAddDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDiets = new Intent(getApplicationContext(), AvailableDietsList.class);
                startActivity(addDiets);
            }
        });
       // dietListAdapter= new ArrayAdapter<>(getApplicationContext(), R.layout.diets_list_one_line,dietNamesList);
    }

    private void getDiets(){
        progressDialog = new SpotsDialog(MyDietsListActivity.this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_DIETS, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USER_DIETS);
        request.execute();
        Log.e("getDiets","diets");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_DIETS)) {
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

                    dietsListAdapter=new DietsListAdapter(MyDietsListActivity.this, dietNamesList, dietIdList);
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

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(MyDietsListActivity.this, HomePageActivity.class);
        startActivity(backIntent);
        //super.onBackPressed();
    }

    private void dietListInit(){
        dietNamesList.add("Pierwsza dieta");
        dietNamesList.add("Dieta niskokaloryczna");
        dietNamesList.add("Dieta białkowa");

        dietIdList.add(1);
        dietIdList.add(2);
        dietIdList.add(3);

    }

    @Override
    protected void goToMyDietsListActivity() {
    }
}
