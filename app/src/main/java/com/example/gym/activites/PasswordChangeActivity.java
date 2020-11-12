package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

//do zrobienia

public class PasswordChangeActivity extends AppCompatActivity {

    EditText editTextPreviousPassword;
    EditText editTextNewPassword;
    EditText editTextRepeatedNewPassword;
    Button buttonChangePassword;

    private static String UPDATE_PASSWORD = "updatePassword";

    IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(UPDATE_PASSWORD); //dodanie akcji zmiany hasła

        idInit();
    }

    private void idInit() {
        editTextPreviousPassword = findViewById(R.id.editTextPreviousPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextRepeatedNewPassword = findViewById(R.id.editTextRepeatedNewPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String previousPassword = editTextPreviousPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String repeatedNewPassword = editTextRepeatedNewPassword.getText().toString();
                if (newPassword != null && repeatedNewPassword != null && previousPassword!=null && newPassword.equals(repeatedNewPassword)) {
                    Log.e("start","zmiana hasla");
                    if(newPassword.equals(previousPassword))
                    {
                        changePasswordDialog(false, "Nowe hasło musi różnić się od poprzedniego");
                    } else {
                    changePassword(previousPassword, newPassword);
                    }
                } else {
                    changePasswordDialog(false,"Podane hasła nie są takie same");
                }


            }
        });
    }

        private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("Brodcast","zyje");
                Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
                if (Objects.equals(intent.getAction(), UPDATE_PASSWORD)) {
                    //  Log.e("Main", bundle.getString("JSON"));
                    try {
                        String jsonstr =bundle.getString("JSON");
                        JSONObject json = new JSONObject(jsonstr);
                        Log.e("js",jsonstr);
                        String resp = json.getString("userData");
                        Log.e("response ", resp);
                        Boolean succes = json.getBoolean("userData");
                        if(succes)
                            changePasswordDialog(succes, null);
                        else{
                            int errorNo = json.getInt("errorNo");
                            if(errorNo == 1){
                            changePasswordDialog(succes, "Złe hasło, podaj dobre hasło");
                            } else {
                                changePasswordDialog(succes, "Coś poszło nie tak, spróbuj później");
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    unregisterReceiver(broadcastReceiver);
                }
                    //unregisterReceiver(broadcastReceiver);
                }

        };



    private void changePassword(String oldPassword, String newPassword) {
        registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(id));
        params.put("password", oldPassword);
        params.put("newPassword", newPassword);

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UPDATE_PASSWORD, params, Constants.CODE_POST_REQUEST, getApplicationContext(), UPDATE_PASSWORD);
        request.execute();
        Log.e("reqest","req");
    }

    private void changePasswordDialog(boolean status, String errorInfo){
        if(status) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Zmiana hasła")
                    .setCancelable(false)
                    .setTitle("Pomyślnie zmieniono hasło!")
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
            builder.setMessage(errorInfo)
                    .setCancelable(false)
                    .setTitle("Błąd przy zmianie hasła")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog dialog = builder.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }
}
