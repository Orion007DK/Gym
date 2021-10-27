package com.example.gym.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.R;
import com.example.gym.activites.workoutsHistory.WorkoutsHistoryActivity;
import com.example.gym.activites.dimensionHistory.DimensionHistoryActivity;

public class MyStatisticGeneralPageActivity extends OptionsMenuActivity {

    Button buttonDimensionsHistory;
    Button butttonTrainingHistory;
    Button buttonStatisticsOnDiagrams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_statistic_general_page);
        idInit();
        buttonsOnClickListenersInit();
    }

    private void idInit(){
        buttonDimensionsHistory=findViewById(R.id.buttonDimensionsHistory);
        butttonTrainingHistory=findViewById(R.id.buttonTrainingHistory);
        buttonStatisticsOnDiagrams=findViewById(R.id.buttonStatisticsOnDiagrams);
    }

    private void buttonsOnClickListenersInit(){
        buttonDimensionsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dimensionHistoryActivityIntent=new Intent(getApplicationContext(), DimensionHistoryActivity.class);
                startActivity(dimensionHistoryActivityIntent);
            }
        });
        butttonTrainingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workoutsHistoryActivityIntent = new Intent(getApplicationContext(), WorkoutsHistoryActivity.class);
                startActivity(workoutsHistoryActivityIntent);
            }
        });
        buttonStatisticsOnDiagrams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myDimensionChartActivityIntent = new Intent(getApplicationContext(), MyDimensionChartActivity.class);
                startActivity(myDimensionChartActivityIntent);
            }
        });
    }
}
