package com.example.gym.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.gym.QRActivity;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.gymsList.GymsListActivity;
import com.example.gym.activites.myClassesList.MyClassesListActivity;
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
    private Button buttonQr;

    private int gymId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        idInit();
        onClickListenersInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gymId= SharedPreferencesOperations.getGymId(getApplicationContext());
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
        buttonQr=findViewById(R.id.buttonQR);


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
                Intent myClassesIntent = new Intent(getApplicationContext(), MyClassesListActivity.class);
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
            Intent myDieticianIntent = new Intent(getApplicationContext(), DieticianHomePageActivity.class);
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

        buttonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QrIntent =  new Intent(getApplicationContext(), QRActivity.class);
                startActivity(QrIntent);
            }
        });
    }


    //nadpisanie metody pustą metodą, żeby nie dało się przejś do tej samej aktywności
    @Override
    protected void goToHomePageActivity() {
    }

    private void noGymDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        builder.setMessage("Nie masz jeszcze wybranej siłowni, czy chcesz ją teraz wybrać?")
                .setCancelable(true)
                .setTitle("Brak Siłowni")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent gymListActivityIntent = new Intent(getApplicationContext(), GymsListActivity.class);
                        startActivity(gymListActivityIntent);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}
