package com.example.gym.activites.myDietsList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.gym.R;

import java.util.ArrayList;

public class MyDietsListActivity extends AppCompatActivity {
    ArrayList<String> dietNamesList = new ArrayList<>();
    DietsListAdapter dietsListAdapter;
    ListView dietListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diets_list);
        dietListInit();
        dietsListAdapter=new DietsListAdapter(MyDietsListActivity.this, dietNamesList);
        dietListView=findViewById(R.id.listViewDiets);
        dietListView.setAdapter(dietsListAdapter);

       // dietListAdapter= new ArrayAdapter<>(getApplicationContext(), R.layout.diets_list_one_line,dietNamesList);
    }

    private void dietListInit(){
        dietNamesList.add("Pierwsza dieta");
        dietNamesList.add("Dieta niskokaloryczna");
        dietNamesList.add("Dieta białkowa");
    }
}
