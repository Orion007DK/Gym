package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gym.Constants;
import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainersListActivity;

public class TrainerHomePageActivity extends OptionsMenuActivity {

    Button buttonShowMyTrainersProfile;
    Button buttonFindTrainer;
    Button buttonUnsubsribeFromTrainer;

    int idTrainer=-1;

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
     //   buttonUnsubsribeFromTrainer=findViewById(R.id.buttonUnsubscribeFromTrainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        idTrainer=data.getInt(Constants.SP_TRAINER_ID, -1);
    }

    private void buttonsOnClickListenersInit(){
       // SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        //final int idTrainer = data.getInt(Constants.SP_TRAINER_ID, -1);
        buttonShowMyTrainersProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idTrainer!=(-1)) {
                    Log.e("idTrainerNieMA: ", String.valueOf(idTrainer));
                    Intent myTrainersProfileActivity = new Intent(getApplicationContext(), TrainerDetailActivity.class);
         //           myTrainersProfileActivity.putExtra("name", "Janek");
         //           myTrainersProfileActivity.putExtra("surname", "Kos");
                    startActivity(myTrainersProfileActivity);
                } else {
                    noTrainerDialog();
                }
            }
        });
        buttonFindTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainerIntent = new Intent(getApplicationContext(), TrainersListActivity.class);
                startActivity(trainerIntent);
            }
        });
    }

    @Override
    protected void goToTrainerHomePageActivity() {
    }

    private void noTrainerDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TrainerHomePageActivity.this);
        builder.setMessage(R.string.TrainerHomePageWantTrainerSubscribeDialogMessage)
                .setCancelable(true)
                .setTitle(R.string.TrainerHomePageWantTrainerSubscribeDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent trainerListActivityIntent = new Intent(getApplicationContext(), TrainersListActivity.class);
                        startActivity(trainerListActivityIntent);
                    }
                })
                .setNegativeButton(R.string.DialogNegativeButtonNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }
}
