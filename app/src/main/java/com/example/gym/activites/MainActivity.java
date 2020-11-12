package com.example.gym.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView register;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;

    IntentFilter filter;

    private final static String GET_USERS = "getUsers";
    private final static String GET_USER_ID = "getUserID";
    private final static String LOGIN = "logIn";
    private final static String GET_USER_DATA = "getUserData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kenBurnsViewInit();

        if(getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID, -1)!=-1){
            Intent intent  = new Intent(getApplicationContext(),HomePageActivity.class);
            startActivity(intent);
        }
        //showSoftKeyboard(findViewById(R.id.editTextEmail));
        //   Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        register = findViewById(R.id.textViewRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
            }
        });
        idInit();
        buttonLogin = findViewById(R.id.buttonLogIn);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                //startActivity(homePageIntent);
                //Background background = new Background(MainActivity.this, editTextEmail, editTextPassword);
                //background.execute(email,password);
                /// registerReceiver(broadcastReceiver, filter);
                //Log.e("klik","klik");
               //  readUsers();
               //  getUSerId();
                SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
                int id = data.getInt(Constants.SP_USER_ID, -1);
                if(id!=(-1)){
                    //Log.e("MM:","Uzytkownik jest już zalogowany");
                    //nextActivity();
                    loginDialog(true);
                } else {
                logIn();}
               // Log.e("password", hashed);
            //    if (BCrypt.checkpw(candidate, hashed))

            }
        });
        landscapeConfiguration();

    }

    @Override
    public void onBackPressed() {
    }

    protected void onResume() {
        MainActivity.super.onResume();
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_DATA); //dodanie akcji od pobierania informacji o użytkownikach
        filter.addAction(GET_USER_ID); //akcja od pobierania id użytkownika
        filter.addAction(LOGIN);
        // registerReceiver(broadcastReceiver, new IntentFilter(NetworkService.NOTIFICATION));
        //registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_DATA)) {
              //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONObject userJson = json.getJSONObject("userData");
                    Log.e("js",jsonstr);
                    Log.e("userjs: ",userJson.toString());
                    SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putString(Constants.SP_USER_NAME,userJson.getString("name"));
                    editor.putString(Constants.SP_USER_SURNAME, userJson.getString("surname"));
                    //Log.e("gymId", String.valueOf(userJson.getInt("gymId")));
                    Log.e("gymIdS", userJson.getString("gymId"));
                    Log.e("dieticianId:","id"+userJson.getString("dieticianId"));
                    if(!userJson.isNull("gymId"))
                    editor.putInt(Constants.SP_GYM_ID, userJson.getInt("gymId"));
                    if(!userJson.isNull("trainerId"))
                    editor.putInt(Constants.SP_TRAINER_ID, userJson.getInt("trainerId"));
                    if(!userJson.isNull("dieticianId"))
                    editor.putInt(Constants.SP_DIETICIAN_ID, userJson.getInt("dieticianId"));
                    Log.e("jsonName: ",userJson.getString("name"));
                    editor.apply();
                    Log.e("name111: ",getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getString(Constants.SP_USER_NAME, null));
                    Log.e("dieticianIDDD: ","t"+String.valueOf(getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_DIETICIAN_ID, -1)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
            } else if (Objects.equals(intent.getAction(), GET_USER_ID)) {
                Log.e("Main", bundle.getString("JSON"));
                unregisterReceiver(broadcastReceiver);
            } else if(intent.getAction().equals(LOGIN)){
                Log.e("Broad","cast");
                try {
                    JSONObject json = new JSONObject(bundle.getString("JSON"));
                    int userId = json.getInt("userId");


                    if(userId!=0){
                        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = data.edit();
                        editor.putInt(Constants.SP_USER_ID,userId);
                        editor.apply();
                        loginDialog(true);
                        getUserData(userId);
                     //   getUser+Data(userId);
                    }
                    else{
                        loginDialog(false);}

                    Log.e("userID:", String.valueOf(userId));
                } catch (JSONException e) {
                    loginDialog(false);
                    e.printStackTrace();
                }
                Log.e("Main", bundle.getString("JSON"));
                //unregisterReceiver(broadcastReceiver);
            }

        }
    };


    private void loginDialog(boolean status){
        if(status) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Zalogowałeś się pomyślnie")
                    .setCancelable(false)
                    .setTitle("Zalogowano")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            nextActivity();
                        }
                    });

            AlertDialog dialog = builder.show();
            dialog.setCanceledOnTouchOutside(false);
        } else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Podany został błędny login lub hasło, spróbuj ponownie")
                    .setCancelable(false)
                    .setTitle("Nieudane logowanie")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog dialog = builder.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    private void getUserData(int id) {
        //registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_DATA, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USER_DATA);
        request.execute();
    }

    private void getUSerId(){
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
       // String hashed = BCrypt.hashpw(editTextPassword.getText().toString(), BCrypt.gensalt(10));
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_ID, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USERS);
        request.execute();
    }

    private void logIn(){
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_LOG_IN, params, Constants.CODE_POST_REQUEST, getApplicationContext(), LOGIN);
        request.execute();
    }

    private void nextActivity(){
        Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(homePageIntent);
    }


    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void landscapeConfiguration(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void idInit(){
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
    }

    private void kenBurnsViewInit(){
        KenBurnsView kbv = findViewById(R.id.kenBurnsView);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(5000, ACCELERATE_DECELERATE);
//duration = 10000ms = 10s and interpolator = ACCELERATE_DECELERATE
        kbv.setTransitionGenerator(generator); //set new transition on kbv
    }
}
