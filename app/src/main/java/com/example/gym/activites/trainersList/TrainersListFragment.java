package com.example.gym.activites.trainersList;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.GymWorker;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class TrainersListFragment extends Fragment {

    protected ListView listView;
    TrainersListAdapter trainersListAdapter;
    private ArrayList<String> trainersListNames= new ArrayList<String>();
    private ArrayList<String> trainersListSurnames= new ArrayList<String>();

    private ArrayList<GymWorker> trainersArrayList = new ArrayList<>();

    private TrainersListFragmentActivityListener listener;

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_TRAINERS="getTrainers";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainers_list,container,false);

       // if(trainersListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
        //    trainersListsNamesAndSurnamesInit();
        listView=view.findViewById(R.id.listViewTrainers);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_TRAINERS); //dodanie akcji od pobierania informacji o użytkownikach

        if(trainersArrayList.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getTrainers();
            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
            listView.setAdapter(trainersListAdapter);
        }



 //       listView=container.findViewById(R.id.listViewTrainers);
      //  trainersListAdapter = new TrainersListAdapter((AppCompatActivity)view.getContext(),trainersList, this);
      //  listView.setAdapter(trainersListAdapter);
        landscapeConfiguration(view);

    return view;
    }

    public interface TrainersListFragmentActivityListener{
        public void onItemSelected(GymWorker trainer);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TrainersListFragmentActivityListener) {
            listener = (TrainersListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(GymWorker trainer) {
        listener.onItemSelected(trainer);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void getTrainers(){
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("gymId", String.valueOf(SharedPreferencesOperations.getGymId(getContext())));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_TRAINERS, params, Constants.CODE_POST_REQUEST, getContext(), GET_TRAINERS);
        request.execute();
        Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_TRAINERS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("trainers");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        GymWorker trainer = new GymWorker(jsonArray.getJSONObject(i));
                        trainersArrayList.add(trainer);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                    Log.e("gymsArrayList: ",trainersArrayList.toString());

                    trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    listView.setAdapter(trainersListAdapter);
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
    private void trainersListsNamesAndSurnamesInit(){

        trainersArrayList.add(new GymWorker("Janek","Michalak"));
        trainersArrayList.add(new GymWorker("Dzbanek","Waska"));
        trainersArrayList.add(new GymWorker("Michau","Jormund"));
        trainersArrayList.add(new GymWorker("Rafał","Kowal"));

        trainersListNames.add(0, "Janek");
        trainersListNames.add(1, "Dzbanek");
        trainersListNames.add(2, "Michau");
        trainersListNames.add(3, "Rafał");

        trainersListSurnames.add(0,"Michalak");
        trainersListSurnames.add(1,"Waska");
        trainersListSurnames.add(2, "Jormund");
        trainersListSurnames.add(3,"Kowal");
    }

}
