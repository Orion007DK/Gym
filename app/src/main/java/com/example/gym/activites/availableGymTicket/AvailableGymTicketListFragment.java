package com.example.gym.activites.availableGymTicket;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
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

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class AvailableGymTicketListFragment extends Fragment {

    protected ListView listView;
    AvailableGymTicketsListAdapter availableGymTicketsListAdapter;

    private ArrayList<Ticket> gymTicketsArrayList = new ArrayList<>();

    private AvailableGymTicketsListFragmentActivityListener listener;

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_AVAILABLE_TICKETS ="getAvailableTickets";

    ArrayList<Ticket> ticketsList = new ArrayList<>();

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;
    private TextView emptyListViewText;
    private FloatingActionButton floatingActionButtonShowAvailableTickets;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_gym_tickets_list_fragment,container,false);

        // if(trainersListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
        //    trainersListsNamesAndSurnamesInit();
        listView=view.findViewById(R.id.listViewGymTickets);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        emptyListViewText=view.findViewById(R.id.emptyListViewText);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_AVAILABLE_TICKETS); //dodanie akcji od pobierania informacji o użytkownikach

        if(ticketsList.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getTickets();
          //  availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
           // listView.setAdapter(availableGymTicketsListAdapter);
            View header_view =getLayoutInflater().inflate(R.layout.available_gym_tickets_header, null);
            if(listView.getHeaderViewsCount()==0)
                listView.addHeaderView(header_view);
            listView.setEmptyView(emptyListViewText);

            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            View header_view =getLayoutInflater().inflate(R.layout.available_gym_tickets_header, null);
            if(listView.getHeaderViewsCount()==0)
                listView.addHeaderView(header_view);
            listView.setEmptyView(emptyListViewText);
            availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, ticketsList, fragment);
            listView.setAdapter(availableGymTicketsListAdapter);
        }



        //       listView=container.findViewById(R.id.listViewTrainers);
        //  trainersListAdapter = new TrainersListAdapter((AppCompatActivity)view.getContext(),trainersList, this);
        //  listView.setAdapter(trainersListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    private void floatingActionButtonInit(View view){
        //floatingActionButtonShowAvailableTickets=view.findViewById(R.id.floatingActionButtonShowAvailablesTickets);
        floatingActionButtonShowAvailableTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public interface AvailableGymTicketsListFragmentActivityListener {
        public void onItemSelected(Ticket ticket);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AvailableGymTicketsListFragmentActivityListener) {
            listener = (AvailableGymTicketsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: GymTicketListFragment.GymTicketsListFragmentActivityListener");
        }
    }

    public void updateDetail(Ticket ticket) {
        listener.onItemSelected(ticket);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void getTickets(){
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        Date date = new Date();
        String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,date).toString();
        params.put("gymId", String.valueOf(SharedPreferencesOperations.getGymId(getContext())));
        params.put("date", stringDate);
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_AVAILABLE_TICKETS, params, Constants.CODE_POST_REQUEST, getContext(), GET_AVAILABLE_TICKETS);
        request.execute();
        Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_AVAILABLE_TICKETS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("ticketsList");
                    Log.e("TICKET LIST:", jsonArray.toString());

                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        ticketsList.add(new Ticket(jsonArray.getJSONObject(i)));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                 //       JSONArray jsonPossibilities = jsonObject.getJSONArray("possibilities");
                    /*    for(int j=0;j<jsonPossibilities.length();j++){
                            JSONObject jsPossibility = jsonPossibilities.getJSONObject(j);

                            Log.e("name:", jsPossibility.getString("possibilityName"));
                            Log.e("description:", jsPossibility.getString("possibilityDescription"));
                        }*/

                        //GymWorker trainer = new GymWorker(jsonArray.getJSONObject(i));
                        //trainersArrayList.add(trainer);
                  //      Log.e("Obiekt"+String.valueOf(i), jsonObject.toString());
                    }
                    availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, ticketsList, fragment);
                    listView.setAdapter(availableGymTicketsListAdapter);
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                    // Log.e("gymsArrayList: ",trainersArrayList.toString());

                    //  trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    // listView.setAdapter(trainersListAdapter);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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


    //metoda do usuniecia jak bedzie baza danych
    private void ticketsListInit(){

        gymTicketsArrayList.add(new Ticket(1,"Standard", "10","30"));
        gymTicketsArrayList.add(new Ticket(2,"Standard+", "20","50"));
        gymTicketsArrayList.add(new Ticket(3,"Standard + Fitness", "30","80"));
        gymTicketsArrayList.add(new Ticket(4,"Premium", "365", "120"));

}


}
