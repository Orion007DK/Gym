package com.example.gym.activites.myClassesList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gym.Classes;
import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.OptionsMenuActivity;
import com.example.gym.activites.classesPlan.ClassesPlanActivity;
import com.example.gym.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class MyClassesListActivity extends OptionsMenuActivity {

    ListView myClasseslistView;
    ArrayList<String> classNames = new ArrayList<>();
    ArrayList<Date> classesDates = new ArrayList<>();
    ArrayList<Time> classesStartTime = new ArrayList<>();
    ArrayList<Time> classesEndTime = new ArrayList<>();
    //Button buttonAddClasses;
    FloatingActionButton floatingActionButtonAddClasses;
    ArrayList<Classes> arrayListClasses = new ArrayList<>();

    private TextView emptyListViewText;
    private AppCompatActivity appContext;
    private SpotsDialog progressDialog;
    IntentFilter filter;
    private static final String GET_USER_CLASSES="getUserClasses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes_list);


        appContext=this;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_CLASSES); //dodanie akcji od pobierania informacji o zajęciach użytkownika
        myClasseslistView=findViewById(R.id.listViewMyClasses);
        emptyListViewText=findViewById(R.id.emptyListViewText);
        //myClassesListInits();

        View header_view =getLayoutInflater().inflate(R.layout.my_classes_list_header, null);
        if(myClasseslistView.getHeaderViewsCount()==0)
            myClasseslistView.addHeaderView(header_view);
        myClasseslistView.setEmptyView(emptyListViewText);

        if(arrayListClasses.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getUserClasses();
            //  availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
            // listView.setAdapter(availableGymTicketsListAdapter);


            progressDialog = new SpotsDialog(appContext, R.style.Custom);
            progressDialog.show();
        } else {
            arrayListClasses.clear();
            getUserClasses();

            progressDialog = new SpotsDialog(appContext, R.style.Custom);
            progressDialog.show();

            //MyClassesListAdapter myClassesListAdapter = new MyClassesListAdapter(this, arrayListClasses);
            //myClasseslistView.setAdapter(myClassesListAdapter);
        }
        //MyClassesListAdapter myClassesListAdapter = new MyClassesListAdapter(this, arrayListClasses);
        //myClasseslistView.setAdapter(myClassesListAdapter);
        //View header_view =getLayoutInflater().inflate(R.layout.my_classes_list_header, null);
        //myClasseslistView.addHeaderView(header_view);

        landscapeConfiguration();
        buttonAddClassesInit();
    }





    private void buttonAddClassesInit(){
        //floatingActionButtonAddClasses = findViewById(R.id.buttonAddClassses);
        floatingActionButtonAddClasses=findViewById(R.id.floatingActionButtonAddClasses);
        floatingActionButtonAddClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent chooseWeekActivityIntent = new Intent(getApplicationContext(), ChooseWeekActivity.class);
                //startActivity(chooseWeekActivityIntent);
                Intent classesActivityIntent = new Intent(getApplicationContext(), ClassesPlanActivity.class);
                startActivity(classesActivityIntent);
            }
        });
    }

    private void myClassesListInits(){
        arrayListClasses.add(new Classes("Fitness",new Date(2004-1900, 1, 20),new Time(10,15,0).toString(),new Time(15,30,0).toString()));
        arrayListClasses.add(new Classes("Przypadkowe ćwiczenia",new Date(2019-1900, 5, 12),new Time(10,15,0).toString(),new Time(15,30,0).toString()));

        classNames.add("Fitness");
        classNames.add("Przypadkowe ćwiczenia");
        //classNames.add("Trening jakis");

        Calendar cal = Calendar.getInstance();
        cal.set(2017,2,20);
        classesDates.add(cal.getTime());

        cal.set(2019,5,12);
        classesDates.add(cal.getTime());

        classesStartTime.add(new Time(10,15,0));
        classesStartTime.add(new Time(12,15,0));

        classesEndTime.add(new Time(12,15,0));
        classesEndTime.add(new Time(15,30,0));

    }

    private void landscapeConfiguration(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = findViewById(R.id.textViewTitle);
            textView.setPadding(0,0,0,10);
        }
    }

    private void getUserClasses(){
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);


        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        params.put("date", DateFormat.format(Constants.DATABASE_DATA_FORMAT,date).toString());

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_CLASSES, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USER_CLASSES);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_CLASSES)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("classesList");
                    Log.e("classesList:", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        arrayListClasses.add(new Classes(jsonArray.getJSONObject(i)));

                    }
                    MyClassesListAdapter classesListAdapter = new MyClassesListAdapter(appContext, arrayListClasses);
                    myClasseslistView.setAdapter(classesListAdapter);

                    Log.e("js",jsonstr);

                    Log.e("jsonArray: ",jsonArray.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("KONIEC","BroadcatAvailableTickets");
                unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };

    @Override
    protected void goToMyClassesListActivity() {
    }
}
