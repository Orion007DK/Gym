package com.example.gym.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.Address;
import com.example.gym.activites.gymsList.GymDetailsActivity;
import com.example.gym.activites.gymsList.GymsListActivity;
import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainersListActivity;

public class GymHomePageActivity extends OptionsMenuActivity {

    Button buttonShowGymProfile;
    Button buttonFindGym;
    Button buttonShowTrainersList;
    Button buttonShowDieticiansList;
    Button buttonUnsubscribeFromGym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_home_page);
        idInit();
        buttonOnClickListenersInit();
    }

    private void idInit(){
       buttonShowGymProfile=findViewById(R.id.buttonShowGymProfile);
       buttonFindGym=findViewById(R.id.buttonFindGym);
       buttonShowTrainersList=findViewById(R.id.buttonShowTrainersList);
       buttonShowDieticiansList=findViewById(R.id.buttonShowDieticiansList);
       buttonUnsubscribeFromGym=findViewById(R.id.buttonUnsubscribeFromGym);
    }

    private void buttonOnClickListenersInit(){
        buttonShowGymProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showGymProfileIntent = new Intent(getApplicationContext(), GymDetailsActivity.class);
                showGymProfileIntent.putExtra("name","Siłownik");
                showGymProfileIntent.putExtra("address", new Address("Lublin","Ponikwody","3"));
                showGymProfileIntent.putExtra("aboutGym","Całkiem dobra siłownia w całkiem dobrej lokalizacji");
                startActivity(showGymProfileIntent);
            }
        });

        buttonFindGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findGymIntent = new Intent(getApplicationContext(), GymsListActivity.class);
                startActivity(findGymIntent);
            }
        });

        buttonShowTrainersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showTrainersListIntent = new Intent(getApplicationContext(), TrainersListActivity.class);
                startActivity(showTrainersListIntent);
            }
        });

        buttonShowDieticiansList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDieticianListActivity = new Intent(getApplicationContext(),TrainersListActivity.class);
                startActivity(showDieticianListActivity);
            }
        });

        buttonUnsubscribeFromGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void goToGymHomeActivity() {
    }
}
