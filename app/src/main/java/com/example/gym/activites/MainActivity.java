package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView register;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;

    IntentFilter filter;

    private final static String GET_USERS = "getUsers";
    private final static String GET_USER_ID= "getUserID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kenBurnsViewInit();
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
                Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(homePageIntent);
                //Background background = new Background(MainActivity.this, editTextEmail, editTextPassword);
                //background.execute(email,password);
               /// registerReceiver(broadcastReceiver, filter);
               // readUsers();
               // getUSerId();



            }
        });
        landscapeConfiguration();

    }

    protected void onResume(){
        MainActivity.super.onResume();
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USERS); //dodanie akcji od pobierania informacji o użytkownikach
        filter.addAction(GET_USER_ID); //akcja od pobierania id użytkownika
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
            if(intent.getAction().equals(GET_USERS)){
                Log.e("Main", bundle.getString("JSON"));
                //rozmiar pliku przeliczony na kB
                unregisterReceiver(broadcastReceiver);
            } else if(intent.getAction().equals(GET_USER_ID)){
                Log.e("Main", bundle.getString("JSON"));
                unregisterReceiver(broadcastReceiver);
            }

        }
    };



    private void readUsers() {
        registerReceiver(broadcastReceiver, filter);
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_READ_USERS, null, Constants.CODE_GET_REQUEST, getApplicationContext(), GET_USERS);
        request.execute();
    }

    private void getUSerId(){
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_ID, params, Constants.CODE_POST_REQUEST, getApplicationContext(), GET_USERS);
        request.execute();
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
