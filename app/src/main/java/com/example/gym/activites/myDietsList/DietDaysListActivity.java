package com.example.gym.activites.myDietsList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.myDietsList.dietsDay.DietsDayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class DietDaysListActivity extends AppCompatActivity {
    Button buttonDaysOfWeek[] = new Button[7];

    TextView textViewDietName;
    Button buttonSubUnsubDiet;
    String dietName;
    int dietId;
    int buttonCounter=1; //in method will have to be final

    boolean buttonIsSub;
    IntentFilter filter;
    private SpotsDialog progressDialog;

    final private static String SUBSCRIBE_OR_UNSUBSCRIBE_DIET = "subscribeOrUnsubscribeDiet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_days_list);
        idInit();

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_OR_UNSUBSCRIBE_DIET); //dodanie akcji od pobierania informacji o użytkownikach

        Bundle bundle = getIntent().getExtras();
        dietName=bundle.getString(Constants.BUNDLE_DIET_NAME);
        dietId=bundle.getInt(Constants.BUNDLE_DIET_ID);
        textViewDietName.setText(dietName);
        buttonIsSub=bundle.getBoolean(Constants.BUNDLE_DIET_SUB_STATUS);
        buttonSubUnsubDietInit();
        buttonsListenersInit();
    }

    private void buttonSubUnsubDietInit(){
        buttonSubUnsubDiet=findViewById(R.id.buttonSubUnsubDiet);
        if(buttonIsSub)
            buttonSubUnsubDiet.setText("Dodaj dietę");
        else
            buttonSubUnsubDiet.setText("Usuń dietę");

            buttonSubUnsubDiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscribeDialog();
                }
            });
    }

    private void subscribeOrUnsubscribeDiet(){
        progressDialog = new SpotsDialog(DietDaysListActivity.this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        params.put("dietId", String.valueOf(dietId));
        PerformNetworkRequest request;
        if(buttonIsSub)
            request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_DIET, params, Constants.CODE_POST_REQUEST, getApplicationContext(), SUBSCRIBE_OR_UNSUBSCRIBE_DIET);
        else
            request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_DIET, params, Constants.CODE_POST_REQUEST, getApplicationContext(), SUBSCRIBE_OR_UNSUBSCRIBE_DIET);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), SUBSCRIBE_OR_UNSUBSCRIBE_DIET)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                   // JSONArray jsonArray = json.getJSONArray("dietsList");
                    //jsonArray.getJSONObject(1);
                    //for(int i=0;i<jsonArray.length();i++){
                       // JSONObject js = jsonArray.getJSONObject(i);
                       // dietNamesList.add(js.getString("name"));
                        //dietIdList.add(js.getInt("id"));

                        //dietNamesList.add((JSONObject)jsonArray.get(i).get("name"));
                        // dietNamesList.get(i);
                        //Log.e("diet:", jsonArray.get(i).toString());
                    //}
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    //Log.e("jsonArray: ",jsonArray.toString());
                    //Log.e("gymsArrayList: ",trainersArrayList.toString());

                    // trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    //listView.setAdapter(trainersListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
                Intent myDietsListActivityIntent = new Intent(DietDaysListActivity.this, MyDietsListActivity.class);
                startActivity(myDietsListActivityIntent);
            }
        }
    };





    private void idInit() {
        textViewDietName=findViewById(R.id.textViewDietName);
        buttonDaysOfWeek[0] = findViewById(R.id.buttonMonday);
        buttonDaysOfWeek[1] = findViewById(R.id.buttonTuesday);
        buttonDaysOfWeek[2] = findViewById(R.id.buttonWednsday);
        buttonDaysOfWeek[3] = findViewById(R.id.buttonThursday);
        buttonDaysOfWeek[4] = findViewById(R.id.buttonFriday);
        buttonDaysOfWeek[5] = findViewById(R.id.buttonSaturday);
        buttonDaysOfWeek[6] = findViewById(R.id.buttonSunday);
    }

    private void buttonsListenersInit() {

        for (final Button button : buttonDaysOfWeek) {
            final int day = buttonCounter;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String weekDayName = button.getText().toString();
                    //String dietName = findViewById(R.id.textViewDietName).toString();
                    Intent dietsDayIntent = new Intent(getApplicationContext(), DietsDayActivity.class);
                    dietsDayIntent.putExtra(Constants.BUNDLE_DIET_ID, dietId);
                    dietsDayIntent.putExtra(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID, day);
                    dietsDayIntent.putExtra(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME, weekDayName);
                    startActivity(dietsDayIntent);
                }
            });
        buttonCounter++;
        }
    }

    private void subscribeDialog() {
        String message  ="";
        String title ="";
        if (buttonIsSub) {
            message="Czy na pewno chcesz dodać tą dietę?";
            title="Dodawanie diety";
        } else {
            message="Czy na pewno chcesz usunąć tą dietę";
            title="Usuwanie diety";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(DietDaysListActivity.this);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(title)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subscribeOrUnsubscribeDiet();
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

}

