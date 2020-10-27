package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.R;
import com.example.gym.activites.dieticianList.DieticianDetailsActivity;
import com.example.gym.activites.dieticianList.DieticianListActivity;

public class DieticianHomePage extends OptionsMenuActivity {

    Button buttonShowMyDieticianProfile;
    Button buttonFindDietician;
    Button buttonUnsubsribeFromDietician;


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
        buttonUnsubsribeFromDietician=findViewById(R.id.buttonUnsubscribeFromDietician);
    }

    private void buttonsOnClickListenersInit(){
        buttonShowMyDieticianProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent myDieticianProfileActivity = new Intent(getApplicationContext(), DieticianDetailsActivity.class);
                myDieticianProfileActivity.putExtra("name", "Janek");
                myDieticianProfileActivity.putExtra("surname", "Kos");
                myDieticianProfileActivity.putExtra("landscapeOn",true);
                startActivity(myDieticianProfileActivity);
            }
        });
        buttonFindDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findDieticianIntent = new Intent(getApplicationContext(), DieticianListActivity.class);
                startActivity(findDieticianIntent);
            }
        });
        buttonUnsubsribeFromDietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void goToDieticianHomePageActivity() {
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
