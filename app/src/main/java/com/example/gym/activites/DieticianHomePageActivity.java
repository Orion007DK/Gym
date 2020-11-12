package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.Constants;
import com.example.gym.R;
import com.example.gym.activites.dieticianList.DieticianDetailsActivity;
import com.example.gym.activites.dieticianList.DieticianListActivity;
import com.example.gym.activites.trainersList.TrainersListActivity;

public class DieticianHomePageActivity extends OptionsMenuActivity {

    Button buttonShowMyDieticianProfile;
    Button buttonFindDietician;
    Button buttonUnsubsribeFromDietician;

    int idDietician=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietician_home_page);
        idInit();
        buttonsOnClickListenersInit();
    }

    private void idInit(){
        buttonShowMyDieticianProfile=findViewById(R.id.buttonShowDieticianProfil);
        buttonFindDietician=findViewById(R.id.buttonFindDietician);
       // buttonUnsubsribeFromDietician=findViewById(R.id.buttonUnsubscribeFromDietician);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        idDietician =data.getInt(Constants.SP_DIETICIAN_ID, -1);
    }

    private void buttonsOnClickListenersInit(){
        buttonShowMyDieticianProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idDietician!=(-1)) {
                    Intent myDieticianProfileActivity = new Intent(getApplicationContext(), DieticianDetailsActivity.class);
                   // myDieticianProfileActivity.putExtra("name", "Janek");
                    //myDieticianProfileActivity.putExtra("surname", "Kos");
                    //myDieticianProfileActivity.putExtra("landscapeOn", true);
                    startActivity(myDieticianProfileActivity);
                } else {
                    noDieticianDialog();
                }
            }
        });
        buttonFindDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findDieticianIntent = new Intent(getApplicationContext(), DieticianListActivity.class);
                startActivity(findDieticianIntent);
            }
        });
        /*buttonUnsubsribeFromDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    protected void goToDieticianHomePageActivity() {
    }

    private void noDieticianDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DieticianHomePageActivity.this);
        builder.setMessage("Nie masz jeszcze wybranego dietetyka, czy chcesz teraz go wybrać?")
                .setCancelable(true)
                .setTitle("Brak dietetyka")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent trainerListActivityIntent = new Intent(getApplicationContext(), DieticianListActivity.class);
                        startActivity(trainerListActivityIntent);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }


    //do przemyślenia!
    //zamiast cofać do ostatniej strony, to cofać do menu głównego, wtedy np. po przejściu od trenera
    //za pomocą menu opcji do dietetyka, po naciśnięciu przycisku BACK wracamy do menu głównego zamiast
    // z powrotem do trenera
 /*   @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(homeIntent);
    }
  */
}
