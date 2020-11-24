package com.example.gym;

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
import android.widget.TextView;

import com.example.gym.activites.classesPlan.ClassesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class ClassesDetailsActivity extends AppCompatActivity {

    private TextView textViewClassesTitle;
    private TextView textViewTrainerNameSurname;
    private TextView textViewClassesDateValue;
    private TextView textViewClassesTimeValue;
    private TextView textViewClassesFreeSlotsValue;
    private TextView textViewClassesDescriptionValue;
    private TextView textViewRequiredGymTicketTypeValue;
    private Button buttonCheckTicket;
    private Button buttonAddClassses;

    TextView textViewNoUnsubscribes;

    Classes classes;

    private SpotsDialog progressDialog;
    IntentFilter filter;
    private static final String CHECK_TICKET="checkTicket";
    private static final String SUBSCRIBE_CLASSES="subscribeClasses";
    private static final String UNSUBSCRIBE_CLASSES="subscribeClasses";
    private static final String CHECK_IS_USER_SUBSCRIBING="checkIsUserSubscribing";

Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_details);
        idInit();
        context = this;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(CHECK_TICKET); //dodanie akcji od sprawdzania karnetów
        filter.addAction(SUBSCRIBE_CLASSES); //dodanie akcji od zapisywania na zajęcia
        filter.addAction(UNSUBSCRIBE_CLASSES); //dodanie akcji od wypisywania z zajęć
        filter.addAction(CHECK_IS_USER_SUBSCRIBING); //dodanie akcji od sprawdzenia czy użytkownik jest zapisany na te zajęcia

        classes=(Classes)getIntent().getSerializableExtra("classes");
        textViewClassesTitle.setText(classes.getClassesName());
        //Calendar calendarSelectedDate = (Calendar)getIntent().getSerializableExtra(ClassesListAdapter.CALENDAR_SELECTED_DATE);
        textViewClassesDateValue.setText(classes.getStringDate());
        //String stringDate = getIntent().getStringExtra(ClassesListAdapter.CALENDAR_SELECTED_DATE);
        //textViewClassesDateValue.setText(stringDate);
        textViewNoUnsubscribes.setText("Z zajęć można wypisać się najpóźniej do 48 godzin przed rozpoczęciem zajęć, pozostały czas do rozpoczęcia zajęć: " + String.valueOf(countLeftHours()) + " godzin");
        //Time classesStartTime = (Time)getIntent().getSerializableExtra(ClassesListAdapter.CLASSES_START_TIME);
        //Time classesEndTime = (Time)getIntent().getSerializableExtra(ClassesListAdapter.CLASSES_END_TIME);
        checkIsUserSubscribing();
        String stringClassStartTime = getIntent().getStringExtra(ClassesListAdapter.CLASSES_START_TIME);
        String stringClassEndTime= getIntent().getStringExtra(ClassesListAdapter.CLASSES_END_TIME);

        //if(getIntent().getBooleanExtra(Constants.BUNDLE_CLASSES_IS_SUBSCRIBED, false)) {
        //    buttonsInit(true);

        //}
        buttonCheckTicket.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        checkTicket();
    }
});

        //String time =DateFormat.format("HH:mm",classesStartTime)+" - "+DateFormat.format("HH:mm",classesEndTime);
        textViewClassesTimeValue.setText(classes.getClassesStartTime()+" - "+classes.getClassesEndTime());
        textViewClassesFreeSlotsValue.setText(classes.getAvailableEntries());
        textViewClassesDescriptionValue.setText(classes.getClassesDescription());
        textViewRequiredGymTicketTypeValue.setText(classes.getRequiredOption());

    }

    private void checkTicket(){
        registerReceiver(broadcastReceiver, filter);
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("ticketOption", classes.getRequiredOption());
        params.put("date", classes.getStringDateInDatabaseFormat());
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_CHECK_TICKET, params, Constants.CODE_POST_REQUEST, getApplicationContext(), CHECK_TICKET);
        request.execute();
    }

    private void checkIsUserSubscribing(){
        registerReceiver(broadcastReceiver, filter);
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("classesId", classes.getStringClassesId());
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        Log.e("userId",String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        Log.e("classId", classes.getStringClassesId());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_CHECK_IS_USER_SUBSCRIBING, params, Constants.CODE_POST_REQUEST, getApplicationContext(), CHECK_IS_USER_SUBSCRIBING);
        request.execute();
    }

    private void subscribeClasses(){
        registerReceiver(broadcastReceiver, filter);
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("classesId", classes.getStringClassesId());
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        params.put("ticketOption", classes.getRequiredOption());
        params.put("date", classes.getStringDateInDatabaseFormat());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_CLASSES, params, Constants.CODE_POST_REQUEST, getApplicationContext(), SUBSCRIBE_CLASSES);
        request.execute();
    }

    private void unsubscribeClasses(){
        registerReceiver(broadcastReceiver, filter);
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("classesId", classes.getStringClassesId());
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        params.put("ticketOption", classes.getRequiredOption());
        params.put("date", classes.getStringDateInDatabaseFormat());
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_CLASSES, params, Constants.CODE_POST_REQUEST, getApplicationContext(), UNSUBSCRIBE_CLASSES);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), CHECK_TICKET)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
               //     unregisterReceiver(broadcastReceiver);
                    progressDialog.dismiss();
                    checkTicketDialog(json.getBoolean("checkTicket"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("KONIEC","BroadcatAvailableTickets");

                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}

            }
            if(Objects.equals(intent.getAction(), CHECK_IS_USER_SUBSCRIBING)){
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                 //   unregisterReceiver(broadcastReceiver);
                    progressDialog.dismiss();
                    buttonsInit(json.getBoolean("check"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(Objects.equals(intent.getAction(), SUBSCRIBE_CLASSES)){
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                  // unregisterReceiver(broadcastReceiver);
                    progressDialog.dismiss();
                    if(json.getBoolean("classesSubscription"))
                        classes.setAvailableEntries(String.valueOf(Integer.valueOf(classes.getAvailableEntries())-1));
                        textViewClassesFreeSlotsValue.setText(classes.getAvailableEntries());
                    buttonsInit((json.getBoolean("classesSubscription")));
                    subscribedClassesDialog(json.getBoolean("classesSubscription"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(Objects.equals(intent.getAction(), UNSUBSCRIBE_CLASSES)){
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                   // unregisterReceiver(broadcastReceiver);
                    progressDialog.dismiss();
                    boolean isUnsubscribed=json.getBoolean("classesUnsubscription");
                    if(isUnsubscribed)
                        classes.setAvailableEntries(String.valueOf(Integer.valueOf(classes.getAvailableEntries())+1));
                    textViewClassesFreeSlotsValue.setText(classes.getAvailableEntries());
                    buttonsInit(!isUnsubscribed);
                    if(isUnsubscribed)
                        informationDialog("Wypisano", "Pomyślnie udało Ci sie wypisać z zajęć");
                    else
                        informationDialog("Błąd", "Niestety nie udało się wypisać Cię z zajęć, spróbuj ponownie później");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            unregisterReceiver(broadcastReceiver);
        }
    };


    private void buttonsInit(boolean isSubscribed){
        if(isSubscribed) {
            buttonCheckTicket.setVisibility(View.GONE);
            buttonAddClassses.setText("Wypisz z zajęć");
            final int leftHours = countLeftHours();
            if (leftHours >= 48 ) {
                buttonAddClassses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            unsubscribeClasses();
                    }
                });
            } else {
                buttonAddClassses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        informationDialog("Jest zbyt późno", "Nie można już wypisać się z tych zajęć, ponieważ zostało tylko " + String.valueOf(countLeftHours()) + " do rozpoczęcia zajęć");
                    }
                });
            }
        } else {
            buttonCheckTicket.setVisibility(View.VISIBLE);
            buttonAddClassses.setText("Zapisz na zajęcia");
            if(Integer.valueOf(classes.getAvailableEntries())>0)
                buttonAddClassses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscribeClasses();
                    }
                });
            else
                informationDialog("Brak wolnych miejsc", "Niestety, nie ma już wolnych miejsc, sprawdź inne  zajęcia");
        }
    }

    private void checkTicketDialog(boolean ticketCheck) {
        String message  ="";
        String title = "";
        if (ticketCheck) {
            message="Posiadasz odpowiedni karnet, aby zapisać się na te zajęcia";
            title="Posiadasz karnet";
        } else {
            message="Niestety, nie posiadasz odpowiedniego karnetu, aby zapisać się na te zajęcia";
            title="Brak karnetu";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(title)
                .setPositiveButton("Zrozumiałem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void subscribedClassesDialog(boolean subscribed) {
        String message  ="";
        String title = "";
        if (subscribed) {
            message="Gratulujemy!, pomyślnie zapisałeś się na zajęcia";
            title="Zapisano";
        } else {
            message="Niestety, nie nie udało nam się zapisać Cie na dane zajęcia, sprawdź swoje karnety i spróbuj później";
            title="Nie zapisano";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(title)
                .setPositiveButton("Zrozumiałem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void informationDialog(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(title)
                .setPositiveButton("Zrozumiałem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void idInit(){
        textViewClassesTitle=findViewById(R.id.textViewClassesTitle);
        textViewTrainerNameSurname=findViewById(R.id.textViewTrainerNameSurname);
        textViewClassesDateValue=findViewById(R.id.textViewClassesDateValue);
        textViewClassesTimeValue=findViewById(R.id.textViewClassesTimeValue);
        textViewClassesFreeSlotsValue=findViewById(R.id.textViewClassesFreeSlotsValue);
        textViewClassesDescriptionValue=findViewById(R.id.textViewClassesDescriptionValue);
        textViewRequiredGymTicketTypeValue=findViewById(R.id.textViewRequiredGymTicketTypeValue);
        buttonCheckTicket=findViewById(R.id.buttonCheckTicket);
        buttonAddClassses=findViewById(R.id.buttonAddClassses);

        textViewNoUnsubscribes=findViewById(R.id.textViewNoUnsubscribes);
    }

    private int countLeftHours(){
        Date today = new Date();
        Date classesDate=classes.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date time = null;
        try {
            time = dateFormat.parse(classes.getClassesStartTime());
            classesDate.setHours(time.getHours());
            classesDate.setMinutes(time.getMinutes());
            Log.e("classDate", classesDate.toString());
            long diff = classesDate.getTime() - today.getTime();
            Log.e("Hours: ", String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)));
            return (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("hh,mm");
        try {
            time = dateFormat.parse(classes.getClassesStartTime());
            classesDate.setHours(time.getHours());
            classesDate.setMinutes(time.getMinutes());
            Log.e("classDate", classesDate.toString());
            long diff = classesDate.getTime() - today.getTime();
            Log.e("Hours: ", String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)));
            return (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("hh.mm");
        try {
            time = dateFormat.parse(classes.getClassesStartTime());
            classesDate.setHours(time.getHours());
            classesDate.setMinutes(time.getMinutes());
            Log.e("classDate", classesDate.toString());
            long diff = classesDate.getTime() - today.getTime();
            Log.e("Hours: ", String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)));
            return (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("hh/mm");
        try {
            time = dateFormat.parse(classes.getClassesStartTime());
            classesDate.setHours(time.getHours());
            classesDate.setMinutes(time.getMinutes());
            Log.e("classDate", classesDate.toString());
            long diff = classesDate.getTime() - today.getTime();
            Log.e("Hours: ", String.valueOf(TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)));
            return (int)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
