package com.example.gym.activites.classesPlan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.AvailableGymTicketsListAdapter;
import com.example.gym.Classes;
import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ClassesDayDetailsFragment extends Fragment {


    private ListView listViewClasses;
    private TextView textViewTitle;
    private TextView textViewDate;
    Calendar calendarSelectedDate = Calendar.getInstance();

    private SpotsDialog progressDialog;
    IntentFilter filter;
    private static final String GET_ONE_DAY_CLASSES="getOneDayClasses";
    private ArrayList<Classes> arrayListClasses=new ArrayList<>();

    private AppCompatActivity appContext;
    private Fragment fragment;
    private TextView emptyListViewText;

    private ArrayList<String> arrayListClassesNames = new ArrayList<>();
    private ArrayList<Time> arrayListClassesStartTime = new ArrayList<>();
    private ArrayList<Time> arrayListClassesEndTime = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classes_day_details_fragment, container, false);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_ONE_DAY_CLASSES); //dodanie akcji od pobierania informacji o użytkownikach

        emptyListViewText=view.findViewById(R.id.emptyListViewText);

        idInit(view);
        //arrayListsInit();
        //listViewClassesInit(inflater);
        landscapeConfiguration(view);
/*
        if(arrayListClasses.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getOneDayClasses();
            //  availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
            // listView.setAdapter(availableGymTicketsListAdapter);
            View header_view =getLayoutInflater().inflate(R.layout.classes_list_header, null);
            if(listViewClasses.getHeaderViewsCount()==0)
                listViewClasses.addHeaderView(header_view);
            listViewClasses.setEmptyView(emptyListViewText);

            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            View header_view =getLayoutInflater().inflate(R.layout.classes_list_header, null);
            if(listViewClasses.getHeaderViewsCount()==0)
                listViewClasses.addHeaderView(header_view);
            listViewClasses.setEmptyView(emptyListViewText);
            ClassesListAdapter classesListAdapter=new ClassesListAdapter(appContext, arrayListClasses, fragment);
            listViewClasses.setAdapter(classesListAdapter);
        }
*/




        return  view;
    }

private void listViewClassesInit(LayoutInflater inflater){
        //ClassesListAdapter classesListAdapter = new ClassesListAdapter((AppCompatActivity)getActivity(), arrayListClassesNames, arrayListClassesStartTime, arrayListClassesEndTime, this);
        ClassesListAdapter classesListAdapter = new ClassesListAdapter((AppCompatActivity)getActivity(), arrayListClasses, this);
        listViewClasses.setAdapter(classesListAdapter);
        View header_view =inflater.inflate(R.layout.classes_list_header, null);
        listViewClasses.addHeaderView(header_view);
}

    public void setText(int day, int month, int year){
        calendarSelectedDate.set(year, month, day);
        String date = String.valueOf(DateFormat.format("dd.MM.yyyy",calendarSelectedDate));
        String stringDateForDatabase = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        //String date = String.valueOf(day)+"."+String.valueOf(month)+"."+String.valueOf(year);
        String dayOfWeekName =calendarSelectedDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        dayOfWeekName=dayOfWeekName.substring(0, 1).toUpperCase() + dayOfWeekName.substring(1);//zmiana pierwszej litery na dużą
        textViewTitle.setText(dayOfWeekName);
        textViewDate.setText(date);


        if(arrayListClasses.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getOneDayClasses(stringDateForDatabase);
            //  availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
            // listView.setAdapter(availableGymTicketsListAdapter);
            View header_view =getLayoutInflater().inflate(R.layout.classes_list_header, null);
            if(listViewClasses.getHeaderViewsCount()==0)
                listViewClasses.addHeaderView(header_view);
            listViewClasses.setEmptyView(emptyListViewText);

            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            View header_view =getLayoutInflater().inflate(R.layout.classes_list_header, null);
            if(listViewClasses.getHeaderViewsCount()==0)
                listViewClasses.addHeaderView(header_view);
            listViewClasses.setEmptyView(emptyListViewText);
            ClassesListAdapter classesListAdapter=new ClassesListAdapter(appContext, arrayListClasses, fragment);
            listViewClasses.setAdapter(classesListAdapter);
        }



    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textViewTitle  = view.findViewById(R.id.textViewTitle);
            textViewTitle.setPadding(0,0,0,0);
            TextView textViewDate = view.findViewById(R.id.textViewDate);
            textViewDate.setPadding(0, 0, 0, 0);
        }
    }

    private void idInit(View view){
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewDate=view.findViewById(R.id.textViewDate);
        listViewClasses=view.findViewById(R.id.listViewClasses);
    }

    //do usunięcia jak będzie baza danych
    private void arrayListsInit(){


        arrayListClasses.add(new Classes("Podnoszenie czegokolwiek", "12:30", "13:30"));
        arrayListClasses.add(new Classes("Fitness", "12:30", "13:30"));
        arrayListClasses.add(new Classes("Podnoszenie czegokolwiek", "17:30", "19:30"));
        arrayListClasses.add(new Classes("Ćwiczenia", "2:30", "12:30"));

        arrayListClassesNames.add("Podnoszenie czegokolwiek");
        arrayListClassesNames.add("Fitness");
        arrayListClassesNames.add("Ćwiczenia");

        arrayListClassesStartTime.add(new Time(12,30,0));
        arrayListClassesStartTime.add(new Time(15,30,0));
        arrayListClassesStartTime.add(new Time(17,15,0));

        arrayListClassesEndTime.add(new Time(13,30,0));
        arrayListClassesEndTime.add(new Time(17,00,0));
        arrayListClassesEndTime.add(new Time(20,15,0));
    }

    private void getOneDayClasses(String stringDate){
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("gymId", String.valueOf(SharedPreferencesOperations.getGymId(getContext())));
        params.put("date", stringDate);
        Log.e("gymId ",String.valueOf(SharedPreferencesOperations.getGymId(getContext())));
        Log.e("date ", stringDate);
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_ONE_DAY_CLASSES, params, Constants.CODE_POST_REQUEST, getContext(), GET_ONE_DAY_CLASSES);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_ONE_DAY_CLASSES)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("classesList");

                    for(int i=0;i<jsonArray.length();i++){
                        arrayListClasses.add(new Classes(jsonArray.getJSONObject(i)));

                    }
                    ClassesListAdapter classesListAdapter = new ClassesListAdapter(appContext, arrayListClasses, fragment);
                    listViewClasses.setAdapter(classesListAdapter);

                    Log.e("js",jsonstr);

                    Log.e("jsonArray: ",jsonArray.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("KONIEC","BroadcatAvailableTickets");
                requireContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };



    public Calendar getDate(){
        return calendarSelectedDate;
    }
}
