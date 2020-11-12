package com.example.gym.activites.gymsList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Address;
import com.example.gym.Constants;
import com.example.gym.Dimensions;
import com.example.gym.Gym;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.activites.dimensionHistory.DimensionsHistoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class GymsListFragment extends Fragment {

    protected ListView listView;
    GymsListAdapter gymsListAdapter;
    private ArrayList<String> gymsListNames= new ArrayList<String>();
    private ArrayList<Address> gymListAdresses= new ArrayList<Address>();
    private GymsListFragmentActivityListener listener;

    //private ArrayList<Gym> gymsList=new ArrayList<>();

    private ArrayList<Gym> gymsArrayList = new ArrayList<>();

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_GYMS="getGyms";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gyms_list,container,false);
        listView=view.findViewById(R.id.listViewGyms);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_GYMS); //dodanie akcji od pobierania informacji o użytkownikach



        if(gymsArrayList.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getGyms();
            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
        gymsListAdapter=new GymsListAdapter(appContext, gymsArrayList, fragment);
        listView.setAdapter(gymsListAdapter);
        }
     //   gymsListAdapter = new GymsListAdapter((AppCompatActivity)view.getContext(), gymsList, this);
    //    listView.setAdapter(gymsListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    public interface GymsListFragmentActivityListener{
        public void onItemSelected(Gym gym);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof GymsListFragmentActivityListener) {
            listener = (GymsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: GymsListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(Gym gym) {
        listener.onItemSelected(gym);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
            Button buttonFindGymUsingLocalisation = view.findViewById(R.id.buttonFindGymUsingLocalisation);
            buttonFindGymUsingLocalisation.setPadding(0,0,0,0);
        }
    }

    private void getGyms(){
            getContext().registerReceiver(broadcastReceiver, filter);
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_GYMS,null, Constants.CODE_GET_REQUEST, getContext(), GET_GYMS);
            request.execute();
            Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_GYMS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("gyms");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        Gym gym = new Gym(jsonArray.getJSONObject(i));
                        gymsArrayList.add(gym);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                    Log.e("gymsArrayList: ",gymsArrayList.toString());

                    gymsListAdapter=new GymsListAdapter(appContext, gymsArrayList, fragment);
                    listView.setAdapter(gymsListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };





    //metoda do usuniecia jak bedzie baza danych
 /*   private void gymListsInit(){

        gymsList.add(new Gym("Siłownia Rycha",new Address("Lublin", "Nadbystrzycka","80")));
        gymsList.add(new Gym("Siłka+", new Address("Lublin", "Poniatowskiego","3A")));
        gymsList.add(new Gym("Zwykła siłownia",new Address("Zamość", "Lubelska","25")));
        gymsList.add(new Gym("Silnia", new Address("Chełm", "Kwiatowa","17")));

    */


    /*    gymsListNames.add(0,"Siłownia Rycha");
        gymsListNames.add(1, "Siłka+");
        gymsListNames.add(2,"Zwykła siłownia");
        gymsListNames.add(3,"Silnia");

        gymListAdresses.add(0, new Address("Lublin", "Nadbystrzycka","80"));
        gymListAdresses.add(1, new Address("Lublin", "Poniatowskiego","3A"));
        gymListAdresses.add(2, new Address("Zamość", "Lubelska","25"));
        gymListAdresses.add(3, new Address("Chełm", "Kwiatowa","17"));*/
    }

