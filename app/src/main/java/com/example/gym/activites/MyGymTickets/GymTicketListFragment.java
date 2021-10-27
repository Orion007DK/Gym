package com.example.gym.activites.MyGymTickets;

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

import com.example.gym.Dialogs;
import com.example.gym.activites.availableGymTicket.AvailableGymTickets;
import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.dataClasses.Ticket;
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

public class GymTicketListFragment extends Fragment {

    protected ListView listView;
    GymTicketsListAdapter gymTicketsListAdapter;

    private ArrayList<Ticket> gymTicketsArrayList = new ArrayList<>();

    private GymTicketsListFragmentActivityListener listener;

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_USER_TICKETS ="getUserActiveTickets";
    ArrayList<Ticket> ticketsList = new ArrayList<>();

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;
    private TextView emptyListViewText;
    private FloatingActionButton floatingActionButtonShowAvailableTickets;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gym_tickets_list_fragment,container,false);

        // if(trainersListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
        //    trainersListsNamesAndSurnamesInit();
        listView=view.findViewById(R.id.listViewGymTickets);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        emptyListViewText=view.findViewById(R.id.emptyListViewText);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_TICKETS); //dodanie akcji od pobierania informacji o użytkownikach

        floatingActionButtonInit(view);
        View header_view =getLayoutInflater().inflate(R.layout.gym_tickets_header, null);

        if(ticketsList.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            //getTickets();
            getTickets();
           // ticketsListInit();
            //gymTicketsListAdapter=new GymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
            //listView.setAdapter(gymTicketsListAdapter);
            if(listView.getHeaderViewsCount()==0)
                listView.addHeaderView(header_view);
            listView.setEmptyView(emptyListViewText);

            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            gymTicketsListAdapter=new GymTicketsListAdapter(appContext, ticketsList, fragment);
            listView.setAdapter(gymTicketsListAdapter);
            if(listView.getHeaderViewsCount()==0)
                listView.addHeaderView(header_view);
            listView.setEmptyView(emptyListViewText);
        }



        //       listView=container.findViewById(R.id.listViewTrainers);
        //  trainersListAdapter = new TrainersListAdapter((AppCompatActivity)view.getContext(),trainersList, this);
        //  listView.setAdapter(trainersListAdapter);
        landscapeConfiguration(view);

        return view;
    }


    private void floatingActionButtonInit(View view){
        floatingActionButtonShowAvailableTickets=view.findViewById(R.id.floatingActionButtonShowAvailablesTickets);
        floatingActionButtonShowAvailableTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvailableGymTickets.class);
                startActivity(intent);
            }
        });
    }


    public interface GymTicketsListFragmentActivityListener{
        public void onItemSelected(Ticket ticket);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof GymTicketsListFragmentActivityListener) {
            listener = (GymTicketsListFragmentActivityListener) activity;
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
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getContext())));
        params.put("date", stringDate);
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_TICKETS, params, Constants.CODE_POST_REQUEST, getContext(), GET_USER_TICKETS);
        request.execute();
        Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_TICKETS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    JSONArray jsonArray = json.getJSONArray("ticketsList");

                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        ticketsList.add(new Ticket(jsonArray.getJSONObject(i)));
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonPossibilities = jsonObject.getJSONArray("possibilities");
                        for(int j=0;j<jsonPossibilities.length();j++){
                            JSONObject jsPossibility = jsonPossibilities.getJSONObject(j);

                            Log.e("name:", jsPossibility.getString("possibilityName"));
                            Log.e("description:", jsPossibility.getString("possibilityDescription"));
                        }

                        //GymWorker trainer = new GymWorker(jsonArray.getJSONObject(i));
                        //trainersArrayList.add(trainer);
                        Log.e("Obiekt"+String.valueOf(i), jsonObject.toString());
                    }
                    gymTicketsListAdapter=new GymTicketsListAdapter(appContext, ticketsList, fragment);
                    listView.setAdapter(gymTicketsListAdapter);
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                   // Log.e("gymsArrayList: ",trainersArrayList.toString());

                  //  trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                   // listView.setAdapter(trainersListAdapter);*/
                    } else {
                        Dialogs.noNetworkFinishDialog(context, getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                requireContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }
    };


    //metoda do usuniecia jak bedzie baza danych
    private void ticketsListInit(){

        gymTicketsArrayList.add(new Ticket(1,"Standard", new Date(2020-1900,10,10), new Date(2020-1900,12,10)));
        gymTicketsArrayList.add(new Ticket(2,"Standard+", new Date(2020-1900,1,17), new Date(2020-1900,4,17)));
        gymTicketsArrayList.add(new Ticket(3,"Standard + Fitness", new Date(2020-1900,2,10), new Date(2020-1900,8,10)));
        gymTicketsArrayList.add(new Ticket(4,"Premium", new Date(2020-1900,10,10), new Date(2020-1900,12,10)));

}


}
