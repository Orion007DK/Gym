package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.registrationWatchers.DataCorrectWatcher;
import com.example.gym.registrationWatchers.EmailTextEditWatcher;
import com.example.gym.registrationWatchers.NameTextEditWatcher;
import com.example.gym.registrationWatchers.PasswordTextEditWatcher;
import com.example.gym.registrationWatchers.PhoneNumberTextEditWatcher;
import com.example.gym.R;
import com.example.gym.registrationWatchers.RegulationsCheckBoxWatcher;
import com.example.gym.registrationWatchers.SournameTextEditWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmail;
    EditText editTextPhoneNumber;
    EditText editTextPassword;
    EditText editTextRepeatedPassword;
    CheckBox checkBoxRegulations;
    TextView textViewRegulations;
    DataCorrectWatcher dataCorrectWatcher;
    Button buttonRegister;

    IntentFilter filter;
    Context context;

    private static String CREATE_USER="createUser";
    private static String CHECK_EMAIL="checkEmail";
    private static String GET_REGULATIONS="getRegulations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context=this;
        init();
        listenersInit();
    }

    protected void onResume(){
        RegistrationActivity.super.onResume();
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(CREATE_USER); //dodanie akcji tworzenia konta dla użytkownika
        filter.addAction(CHECK_EMAIL); //dodanie akcji sprawdzania maila
        filter.addAction(GET_REGULATIONS); //dodanie akcji pobierania regulaminu
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

                    try {
                        String jsonstr =bundle.getString("JSON");
                        JSONObject json = new JSONObject(jsonstr);
                        if(json.isNull(Constants.NETWORK_ERROR_TAG)) {
                        } else {
                            Dialogs.noNetworkDialog(context);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("RegistrationActivity", bundle.getString("JSON"));
                unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
            } else if(intent.getAction().equals(CHECK_EMAIL)){
                String jsonstr =bundle.getString("JSON");
                try {
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    boolean check = json.getBoolean("check");
                    if(check){
                        createUser();
                        registerDialog();
                    } else {
                        editTextEmail.setError(getString(R.string.RegistrationEmailAlreadyExistsError));
                        Dialogs.informationConfirmDialog(getString(R.string.RegistrationEmailAlreadyExistsDialogTitle), getString(R.string.RegistrationEmailAlreadyExistsDialogMessage), context);
                    }
                    } else {
                        Dialogs.noNetworkDialog(context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                unregisterReceiver(broadcastReceiver);
            } else if(intent.getAction().equals(GET_REGULATIONS)){
                String jsonstr =bundle.getString("JSON");
                try {
                    JSONObject json = new JSONObject(jsonstr);
                    //json.getString("regulations");
                    dialogInit(json.getString("regulations"));
                } catch (JSONException e) {
                    Dialogs.informationConfirmDialog(getString(R.string.ErrorDialogTitle), getString(R.string.RegistrationUnsuccesfulGetRegulationsDialogMessage), context);
                    e.printStackTrace();
                }



                unregisterReceiver(broadcastReceiver);
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

    private void checkEmail(){

        registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        HashMap<String, String> params = new HashMap<>();
        params.put("email", editTextEmail.getText().toString());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_CHECK_EMAIL, params, Constants.CODE_POST_REQUEST, getApplicationContext(), CHECK_EMAIL);
        request.execute();
    }

    private void getRegulations() {
        registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        HashMap<String, String> params = new HashMap<>();
        params.put("regulations", "regulations_pl");
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_REGULATIONS, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_REGULATIONS);
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
        textViewRegulations=findViewById(R.id.textViewRegulations);
    }

    private void listenersInit(){
        dataCorrectWatcher = new DataCorrectWatcher((Button)findViewById(R.id.buttonRegister));
        NameTextEditWatcher nameWatcher = new NameTextEditWatcher(editTextName, dataCorrectWatcher, this);
        editTextName.addTextChangedListener(nameWatcher);
        SournameTextEditWatcher surnameWatcher = new SournameTextEditWatcher(editTextSurname, dataCorrectWatcher, this);
        editTextSurname.addTextChangedListener(surnameWatcher);
        EmailTextEditWatcher emailTextEditWatcher = new EmailTextEditWatcher(editTextEmail, dataCorrectWatcher, this);
        editTextEmail.addTextChangedListener(emailTextEditWatcher);
        PhoneNumberTextEditWatcher phoneNumberTextEditWatcher = new PhoneNumberTextEditWatcher(editTextPhoneNumber, dataCorrectWatcher, this);
        editTextPhoneNumber.addTextChangedListener(phoneNumberTextEditWatcher);
        PasswordTextEditWatcher passwordTextEditWatcher = new PasswordTextEditWatcher(editTextPassword, editTextRepeatedPassword, dataCorrectWatcher, this);
        PasswordTextEditWatcher repeatedPasswordTextEditWatcher = new PasswordTextEditWatcher(editTextRepeatedPassword, editTextPassword, dataCorrectWatcher, this);
        editTextPassword.addTextChangedListener(passwordTextEditWatcher);
        editTextRepeatedPassword.addTextChangedListener(repeatedPasswordTextEditWatcher);
        RegulationsCheckBoxWatcher regulationsCheckBoxWatcher = new RegulationsCheckBoxWatcher(dataCorrectWatcher);
        checkBoxRegulations.setOnCheckedChangeListener(regulationsCheckBoxWatcher);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createUser();
                //registerDialog();
            checkEmail();
            }


        });
        textViewRegulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegulations();
            }
        });
    }

    private void registerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.RegistrationSucessfulAcoutCreateDialogMessage)
                .setCancelable(false)
                .setTitle(R.string.RegistrationSucessfulAcoutCreateDialogTitle)
                .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }
                });

        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void dialogInit(String regulations){
        final Dialog dialog = new Dialog(this);
        //String regulations="Nic nie można!";
        dialog.setContentView(R.layout.regulations_dialog);
        final Button buttonCancel = dialog.findViewById(R.id.buttonCancelDialog);
        final TextView textViewRegulations = dialog.findViewById(R.id.textViewRegulations);
        textViewRegulations.setText(regulations);


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Dialog) dialog).isShowing()) {
                    dialog.dismiss();}
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
