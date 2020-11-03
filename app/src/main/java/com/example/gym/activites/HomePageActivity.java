package com.example.gym.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.activites.myClassesList.MyClassesList;
import com.example.gym.activites.trainingPlansList.MyTrainingPlansListActivity;
import com.example.gym.activites.myDietsList.MyDietsListActivity;
import com.example.gym.R;
import com.manojbhadane.QButton;

public class HomePageActivity extends OptionsMenuActivity {

    private Button buttonGym;
    private Button buttonTrainingPlans;
    private Button buttonTrainer;
    private Button buttonClasses;
    private Button buttonDiets;
    private Button buttonDietician;
    private Button buttonMyProfile;
    private QButton buttonGym2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        idInit();
        onClickListenersInit();
    }

    @Override
    public void onBackPressed() {
    }

    private void idInit(){
        buttonGym = findViewById(R.id.buttonGym);
        buttonTrainingPlans=findViewById(R.id.buttonTrainingPlans);
        buttonTrainer =findViewById(R.id.buttonTrainer);
        buttonClasses=findViewById(R.id.buttonClasses);
        buttonDiets=findViewById(R.id.buttonDiets);
        buttonDietician=findViewById(R.id.buttonDietician);
        buttonMyProfile=findViewById(R.id.buttonMyProfile);

    }

    private void onClickListenersInit(){
        buttonGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gymIntent = new Intent(getApplicationContext(), GymHomePageActivity.class);
                startActivity(gymIntent);
            }
        });

        buttonTrainingPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTrainingPlansIntent = new Intent(getApplicationContext(), MyTrainingPlansListActivity.class);
                startActivity(myTrainingPlansIntent);
            }
        });

        buttonTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainerIntent = new Intent(getApplicationContext(), TrainerHomePageActivity.class);
                startActivity(trainerIntent);
            }
        });

        buttonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myClassesIntent = new Intent(getApplicationContext(), MyClassesList.class);
                startActivity(myClassesIntent);
            }
        });

        buttonDiets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent myDietsIntent = new Intent(getApplicationContext(), MyDietsListActivity.class);
            startActivity(myDietsIntent);
            }
        });

        buttonDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent myDieticianIntent = new Intent(getApplicationContext(), DieticianHomePage.class);
            startActivity(myDieticianIntent);
            }
        });

        buttonMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(myProfileIntent);
            }
        });
    }


    //nadpisanie metody pustą metodą, żeby nie dało się przejś do tej samej aktywności
    @Override
    protected void goToHomePageActivity() {
    }
}
