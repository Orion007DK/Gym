package com.example.gym.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.dieticianList.DieticianListActivity;
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

    private int gymId;

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
   //    buttonUnsubscribeFromGym=findViewById(R.id.buttonUnsubscribeFromGym);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gymId = SharedPreferencesOperations.getGymId(getApplicationContext());
    }

    private void buttonOnClickListenersInit(){
        buttonShowGymProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(gymId!=-1) {
                   Intent showGymProfileIntent = new Intent(getApplicationContext(), GymDetailsActivity.class);
                   // showGymProfileIntent.putExtra("name","Siłownik");
                   // showGymProfileIntent.putExtra("address", new Address("Lublin","Ponikwody","3"));
                   // showGymProfileIntent.putExtra("aboutGym","Całkiem dobra siłownia w całkiem dobrej lokalizacji");
                   //showGymProfileIntent.putExtra("email", "silownia@sila.pl");
                   // showGymProfileIntent.putExtra("phoneNumber", "123456789");
                   startActivity(showGymProfileIntent);
               } else {
                   noGymDialog();
                }
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
                if(gymId!=-1) {
                Intent showTrainersListIntent = new Intent(getApplicationContext(), TrainersListActivity.class);
                startActivity(showTrainersListIntent);
                } else {
                    noGymDialog();
                }
            }
        });

        buttonShowDieticiansList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gymId!=-1) {
                Intent showDieticianListActivity = new Intent(getApplicationContext(), DieticianListActivity.class);
                startActivity(showDieticianListActivity);
                } else {
                    noGymDialog();
                }
            }
        });
/*
        buttonUnsubscribeFromGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override //nadpisanie żeby nie dało się z menu przejść do tej samej aktywności
    protected void goToGymHomeActivity() {
    }


    private void noGymDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GymHomePageActivity.this);
        builder.setMessage(R.string.GymHomePageWantGymSubscribeDialogMessage)
                .setCancelable(true)
                .setTitle(R.string.GymHomePageWantGymSubscribeDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent gymListActivityIntent = new Intent(getApplicationContext(),GymsListActivity.class);
                        startActivity(gymListActivityIntent);
                    }
                })
                .setNegativeButton(R.string.DialogNegativeButtonNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
