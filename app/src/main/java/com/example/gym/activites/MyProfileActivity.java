package com.example.gym.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.activites.MyGymTickets.MyGymTickets;
import com.example.gym.R;

public class MyProfileActivity extends OptionsMenuActivity {

    Button buttonMyPersonalData;
    Button buttonAddDimensions;
    Button buttonMyStatistics;
    Button buttonMyGymTickets;
    Button buttonDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        idInit();
        onClickListenersInit();
    }

    private void onClickListenersInit(){
        buttonMyPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPersonalDataIntent=new Intent(getApplicationContext(), MyPersonalDataActivity.class);
                startActivity(myPersonalDataIntent);
            }
        });

        buttonAddDimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDimensionsIntent = new Intent(getApplicationContext(), AddDimensionsActivity.class);
                startActivity(addDimensionsIntent);
            }
        });

        buttonMyStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myStatisticsGeneral=new Intent(getApplicationContext(), MyStatisticGeneralPageActivity.class);
                startActivity(myStatisticsGeneral);
            }
        });

        buttonMyGymTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myGymTicketsIntent = new Intent(getApplicationContext(), MyGymTickets.class);
                startActivity(myGymTicketsIntent);
            }
        });

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent homePageActivityIntent = new Intent(this, HomePageActivity.class);
        startActivity(homePageActivityIntent);
    }

    private void idInit(){
    buttonMyPersonalData=findViewById(R.id.buttonMyPersonalData);
    buttonAddDimensions=findViewById(R.id.buttonAddDimensions);
    buttonMyStatistics=findViewById(R.id.buttonMyStatistics);
    buttonMyGymTickets=findViewById(R.id.buttonMyGymTickets);
    buttonDeleteAccount=findViewById(R.id.buttonDeleteAccount);
    }

    @Override
    protected void goToMyProfileActivity() {
    }
}
