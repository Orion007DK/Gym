package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.changePersonalDataWatchers.ChangeDataCorrectWatcher;
import com.example.gym.changePersonalDataWatchers.ChangeEmailTextEditWatcher;
import com.example.gym.changePersonalDataWatchers.ChangeNameTextEditWatcher;
import com.example.gym.changePersonalDataWatchers.ChangePhoneNumberTextEditWatcher;
import com.example.gym.changePersonalDataWatchers.ChangeSurnameTextEditWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MyPersonalDataActivity extends OptionsMenuActivity {

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmail;
    EditText editTextPhoneNumber;
    TextView textViewChangePassword;
    Button buttonSaveChanges;
    ChangeNameTextEditWatcher changeNameTextEditWatcher;
    ChangeSurnameTextEditWatcher changeSurnameTextEditWatcher;
    ChangeEmailTextEditWatcher changeEmailTextEditWatcher;
    ChangePhoneNumberTextEditWatcher changePhoneNumberTextEditWatcher;
    ChangeDataCorrectWatcher changeDataCorrectWatcher;

    IntentFilter filter;

    private final static String GET_USER_DATA = "getUserData";
    private final static String UPDATE_USER_DATA = "updateUserData";

    private static int ID=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_data);
        idInit();

        //tymczasowe dane, powinny byc odczytywane z bazy danych
        String previousName="Janek";
        String previousSurname="Kos";
        String previousEmail="janekKos@gmail.com";
        String previousPhoneNumber="732274875";


        //editTextInit(previousName,previousSurname,previousEmail,previousPhoneNumber);
        //watchersInit(previousName,previousSurname,previousEmail,previousPhoneNumber);
        //buttonSaveChangesInit();
        changePasswordListenerInit();
    }

    protected void onResume(){
       MyPersonalDataActivity.super.onResume();
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_DATA); //dodanie akcji od pobierania informacji o użytkowniku
        filter.addAction(UPDATE_USER_DATA); //dodanie akcji aktualizowania informacji o użytkowniku w bazie danych
        // registerReceiver(broadcastReceiver, new IntentFilter(NetworkService.NOTIFICATION));
        //registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        getUserData(ID);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if(intent.getAction().equals(GET_USER_DATA)){
                try {
                    JSONObject jsonObject = new JSONObject(bundle.getString("JSON"));
                    JSONObject jsonUserData = jsonObject.getJSONObject("userData");
                    String email = jsonUserData.getString("email");
                    String name = jsonUserData.getString("name");
                    String surname = jsonUserData.getString("surname");
                    String phoneNumber = jsonUserData.getString("phoneNumber");
                    editTextInit(name,surname,email,phoneNumber);
                    watchersInit(name,surname,email,phoneNumber);
                    buttonSaveChangesInit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //rozmiar pliku przeliczony na kB
                unregisterReceiver(broadcastReceiver);
            } else if(intent.getAction().equals(UPDATE_USER_DATA)){
                Log.e("MyPersonalDataActivity", bundle.getString("JSON"));
                //rozmiar pliku przeliczony na kB
                unregisterReceiver(broadcastReceiver);
            }
        }
    };



    private void getUserData(int id) {
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_DATA, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USER_DATA);
        request.execute();
    }

    private void updateUserData(int id, String email, String name, String surname, String phoneNumber) {
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("email", email);
        params.put("name", name);
        params.put("surname", surname);
        params.put("phoneNumber", phoneNumber);
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UPDATE_USER, params, Constants.CODE_POST_REQUEST, getApplicationContext(), UPDATE_USER_DATA);
        Log.e("reqest","req");
        request.execute();
    }




    private void idInit(){
        editTextName=findViewById(R.id.editTextName);
        editTextSurname=findViewById(R.id.editTextSurname);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);
        textViewChangePassword=findViewById(R.id.textViewChangePassword);
        buttonSaveChanges=findViewById(R.id.buttonSaveChanges);
    }

    private void editTextInit(String previusName, String previousSurname, String previousEmail, String previousPhoneNumber){
        editTextName.setText(previusName);
        editTextSurname.setText(previousSurname);
        editTextEmail.setText(previousEmail);
        editTextPhoneNumber.setText(previousPhoneNumber);
    }

    private void watchersInit(String previusName, String previousSurname, String previousEmail, String previousPhoneNumber){
        changeDataCorrectWatcher = new ChangeDataCorrectWatcher(buttonSaveChanges);
        changeNameTextEditWatcher = new ChangeNameTextEditWatcher(editTextName,changeDataCorrectWatcher, previusName);
        changeSurnameTextEditWatcher = new ChangeSurnameTextEditWatcher(editTextSurname, changeDataCorrectWatcher, previousSurname);
        changeEmailTextEditWatcher = new ChangeEmailTextEditWatcher(editTextEmail, changeDataCorrectWatcher, previousEmail);
        changePhoneNumberTextEditWatcher = new ChangePhoneNumberTextEditWatcher(editTextPhoneNumber, changeDataCorrectWatcher, previousPhoneNumber);
        editTextName.addTextChangedListener(changeNameTextEditWatcher);
        editTextSurname.addTextChangedListener(changeSurnameTextEditWatcher);
        editTextEmail.addTextChangedListener(changeEmailTextEditWatcher);
        editTextPhoneNumber.addTextChangedListener(changePhoneNumberTextEditWatcher);
    }

    private void buttonSaveChangesInit(){
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeDataCorrectWatcher.isChanged() && changeDataCorrectWatcher.isDataCorrect()){
                    setNewData(editTextName.getText().toString(), editTextSurname.getText().toString(), editTextEmail.getText().toString(), editTextPhoneNumber.getText().toString());
                    buttonSaveChanges.setBackgroundColor(Color.BLUE);
                    buttonSaveChanges.setEnabled(false);
                    changeDataCorrectWatcher.setDataChanged(false);
                    updateUserData(ID, editTextEmail.getText().toString(), editTextName.getText().toString(),  editTextSurname.getText().toString(), editTextPhoneNumber.getText().toString());
                }
            }
        });
    }

    private void changePasswordListenerInit(){
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PasswordChangeIntent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(PasswordChangeIntent);
            }
        });
    }

    private void setNewData(String newName, String newSurname, String newEmail, String newPhoneNumber){
        changeNameTextEditWatcher.setPreviousName(newName);
        changeSurnameTextEditWatcher.setPreviousSurname(newSurname);
        changeEmailTextEditWatcher.setPreviousEmail(newEmail);
        changePhoneNumberTextEditWatcher.setPreviousPhoneNumber(newPhoneNumber);
    }
}
