package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.registrationWatchers.DataCorrectWatcher;
import com.example.gym.registrationWatchers.EmailTextEditWatcher;
import com.example.gym.registrationWatchers.NameTextEditWatcher;
import com.example.gym.registrationWatchers.PasswordTextEditWatcher;
import com.example.gym.registrationWatchers.PhoneNumberTextEditWatcher;
import com.example.gym.R;
import com.example.gym.registrationWatchers.RegulationsCheckBoxWatcher;
import com.example.gym.registrationWatchers.SournameTextEditWatcher;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmail;
    EditText editTextPhoneNumber;
    EditText editTextPassword;
    EditText editTextRepeatedPassword;
    CheckBox checkBoxRegulations;
    DataCorrectWatcher dataCorrectWatcher;
    Button buttonRegister;

    IntentFilter filter;

    private static String CREATE_USER="createUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        listenersInit();
    }

    protected void onResume(){
        RegistrationActivity.super.onResume();
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(CREATE_USER); //dodanie akcji tworzenia konta dla użytkownika
        // registerReceiver(broadcastReceiver, new IntentFilter(NetworkService.NOTIFICATION));
        //registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (intent.getAction().equals(CREATE_USER)) {
                Log.e("RegistrationActivity", bundle.getString("JSON"));
                unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
            }

        }
    };



    private void createUser() {
        registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        HashMap<String, String> params = new HashMap<>();
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());
        params.put("name", editTextName.getText().toString());
        params.put("surname", editTextSurname.getText().toString());
        params.put("phoneNumber", editTextPhoneNumber.getText().toString());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_CREATE_USER, params, Constants.CODE_POST_REQUEST, getApplicationContext(), CREATE_USER);
        request.execute();
    }



    private void init(){
        editTextName=findViewById(R.id.editTextName);
        editTextSurname=findViewById(R.id.editTextSurname);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextRepeatedPassword=findViewById(R.id.editTextRepeatedPassword);
        checkBoxRegulations=findViewById(R.id.checkBoxAcceptRegulations);
        buttonRegister=findViewById(R.id.buttonRegister);
    }

    private void listenersInit(){
        dataCorrectWatcher = new DataCorrectWatcher((Button)findViewById(R.id.buttonRegister));
        NameTextEditWatcher nameWatcher = new NameTextEditWatcher(editTextName, dataCorrectWatcher);
        editTextName.addTextChangedListener(nameWatcher);
        SournameTextEditWatcher surnameWatcher = new SournameTextEditWatcher(editTextSurname, dataCorrectWatcher);
        editTextSurname.addTextChangedListener(surnameWatcher);
        EmailTextEditWatcher emailTextEditWatcher = new EmailTextEditWatcher(editTextEmail, dataCorrectWatcher);
        editTextEmail.addTextChangedListener(emailTextEditWatcher);
        PhoneNumberTextEditWatcher phoneNumberTextEditWatcher = new PhoneNumberTextEditWatcher(editTextPhoneNumber, dataCorrectWatcher);
        editTextPhoneNumber.addTextChangedListener(phoneNumberTextEditWatcher);
        PasswordTextEditWatcher passwordTextEditWatcher = new PasswordTextEditWatcher(editTextPassword, editTextRepeatedPassword, dataCorrectWatcher);
        PasswordTextEditWatcher repeatedPasswordTextEditWatcher = new PasswordTextEditWatcher(editTextRepeatedPassword, editTextPassword, dataCorrectWatcher);
        editTextPassword.addTextChangedListener(passwordTextEditWatcher);
        editTextRepeatedPassword.addTextChangedListener(repeatedPasswordTextEditWatcher);
        RegulationsCheckBoxWatcher regulationsCheckBoxWatcher = new RegulationsCheckBoxWatcher(dataCorrectWatcher);
        checkBoxRegulations.setOnCheckedChangeListener(regulationsCheckBoxWatcher);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                registerDialog();
            }
        });
    }

    private void registerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Twoje konto zostało założone")
                .setCancelable(false)
                .setTitle("Zarejestrowano")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                    }
                });

        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
