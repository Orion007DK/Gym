package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;

import java.util.Date;
import java.util.HashMap;

//tylko wygląd zrobiony

public class AddDimensionsActivity extends AppCompatActivity {

    private final String CREATE_DIMENSIONS="createDimensions";
    IntentFilter filter;

    Button buttonSaveChanges;

    EditText editTextHeight;
    EditText editTextWeight;
    EditText editTextAdiposeTissue;
    EditText editTextMuscleTissue;
    EditText editTextBodyWaterPercentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dimensions);
        editTextInit();

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(CREATE_DIMENSIONS); //dodanie akcji tworzenia konta dla użytkownika

        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDimensions();
            }
        });

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (intent.getAction().equals(CREATE_DIMENSIONS)) {
                Log.e("AddDimensionsActivity", bundle.getString("JSON"));
                unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
                addedDimensionsDialog(true);
            }
        }
    };


    private void createDimensions() {
        registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        HashMap<String, String> params = new HashMap<>();
        params.put("height", editTextHeight.getText().toString());
        params.put("weight", editTextWeight.getText().toString());
        params.put("adiposeTissue", editTextAdiposeTissue.getText().toString());
        params.put("muscleTissue", editTextMuscleTissue.getText().toString());
        params.put("bodyWaterPercentage", editTextBodyWaterPercentage.getText().toString());
        Log.e("params",params.toString());
        int userId=getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID,-1);
        Date date = new Date(System.currentTimeMillis());
        String dateString = DateFormat.format(Constants.DATABASE_DATA_FORMAT, date).toString();
        Log.e("Date",dateString);
        params.put("date", dateString);
        params.put("userId", String.valueOf(userId));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_CREATE_DIMENSIONS, params, Constants.CODE_POST_REQUEST, getApplicationContext(), CREATE_DIMENSIONS);
        request.execute();
    }


    private void editTextInit(){
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAdiposeTissue = findViewById(R.id.editTextAdiposeTissue);
        editTextMuscleTissue=findViewById(R.id.editTextMuscleTissue);
        editTextBodyWaterPercentage=findViewById(R.id.editTextBodyWaterPercentage);
    }

    private void editTextValidation(){
    }

    private void addedDimensionsDialog(boolean status){
        if(status) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Pomyślnie dodano wymiary!")
                    .setCancelable(false)
                    .setTitle("Dodano wymiary")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myProfileActivityIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                            startActivity(myProfileActivityIntent);
                           // nextActivity();
                        }
                    });

            AlertDialog dialog = builder.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Wystąpił błąd podczas dodawania wymiarów")
                    .setCancelable(false)
                    .setTitle("Nieudane dodanie wymiarow")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog dialog = builder.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }
}
