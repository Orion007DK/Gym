package com.example.gym.activites.myDietsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gym.R;
import com.example.gym.activites.myDietsList.dietsDay.DietsDayActivity;

public class DietDaysListActivity extends AppCompatActivity {
    Button buttonDaysOfWeek[] = new Button[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_days_list);
        idInit();
        buttonsListenersInit();
    }

    private void idInit() {
        buttonDaysOfWeek[0] = findViewById(R.id.buttonMonday);
        buttonDaysOfWeek[1] = findViewById(R.id.buttonTuesday);
        buttonDaysOfWeek[2] = findViewById(R.id.buttonWednsday);
        buttonDaysOfWeek[3] = findViewById(R.id.buttonThursday);
        buttonDaysOfWeek[4] = findViewById(R.id.buttonFriday);
        buttonDaysOfWeek[5] = findViewById(R.id.buttonSaturday);
        buttonDaysOfWeek[6] = findViewById(R.id.buttonSunday);
    }

    private void buttonsListenersInit() {
        for (final Button button : buttonDaysOfWeek) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String weekDayName = button.getText().toString();
                    String dietName = findViewById(R.id.textViewDietName).toString();
                    Intent dietsDayIntent = new Intent(getApplicationContext(), DietsDayActivity.class);
                    dietsDayIntent.putExtra("dietName",dietName);
                    dietsDayIntent.putExtra("weekDayName", weekDayName);
                    startActivity(dietsDayIntent);
                }
            });

        }
    }
}

