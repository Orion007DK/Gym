package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainersListActivity;

public class TrainerHomePageActivity extends OptionsMenuActivity {

    Button buttonShowMyTrainersProfile;
    Button buttonFindTrainer;
    Button buttonUnsubsribeFromTrainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home_page);
        idInit();
        buttonsOnClickListenersInit();
    }

    private void idInit(){
        buttonShowMyTrainersProfile=findViewById(R.id.buttonShowTrainersProfil);
        buttonFindTrainer=findViewById(R.id.buttonFindTrainer);
        buttonUnsubsribeFromTrainer=findViewById(R.id.buttonUnsubscribeFromTrainer);
    }

    private void buttonsOnClickListenersInit(){
        buttonShowMyTrainersProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myTrainersProfileActivity = new Intent(getApplicationContext(), TrainerDetailActivity.class);
                myTrainersProfileActivity.putExtra("name", "Janek");
                myTrainersProfileActivity.putExtra("surname", "Kos");
                startActivity(myTrainersProfileActivity);
            }
        });
        buttonFindTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainerIntent = new Intent(getApplicationContext(), TrainersListActivity.class);
                startActivity(trainerIntent);
            }
        });
        buttonUnsubsribeFromTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void goToTrainerHomePageActivity() {
    }
}
