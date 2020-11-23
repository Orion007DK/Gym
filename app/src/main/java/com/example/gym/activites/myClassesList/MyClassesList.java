package com.example.gym.activites.myClassesList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gym.Classes;
import com.example.gym.activites.classesPlan.ClassesPlanActivity;
import com.example.gym.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyClassesList extends AppCompatActivity {

    ListView myClasseslistView;
    ArrayList<String> classNames = new ArrayList<>();
    ArrayList<Date> classesDates = new ArrayList<>();
    ArrayList<Time> classesStartTime = new ArrayList<>();
    ArrayList<Time> classesEndTime = new ArrayList<>();
    Button buttonAddClasses;

    ArrayList<Classes> classesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes_list);
        myClassesListInits();
        myClasseslistView=findViewById(R.id.listViewMyClasses);
        MyClassesListAdapter myClassesListAdapter = new MyClassesListAdapter(this, classesArrayList);
        myClasseslistView.setAdapter(myClassesListAdapter);
        View header_view =getLayoutInflater().inflate(R.layout.my_classes_list_header, null);
        myClasseslistView.addHeaderView(header_view);

        landscapeConfiguration();
        buttonAddClassesInit();
    }

    private void buttonAddClassesInit(){
        buttonAddClasses = findViewById(R.id.buttonAddClassses);
        buttonAddClasses.setOnClickListener(new View.OnClickListener() {
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
        classesArrayList.add(new Classes("Fitness",new Date(2004-1900, 1, 20),new Time(10,15,0).toString(),new Time(15,30,0).toString()));
        classesArrayList.add(new Classes("Przypadkowe ćwiczenia",new Date(2019-1900, 5, 12),new Time(10,15,0).toString(),new Time(15,30,0).toString()));

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


}
