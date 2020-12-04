package com.example.gym.activites.dieticianList;

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

public class DieticiansListFragment extends Fragment {

    protected ListView listView;
    DieticiansListAdapter dieticiansListAdapter;
    private ArrayList<String> dieticiansListNames = new ArrayList<String>();
    private ArrayList<String> dieticiansListSurnames = new ArrayList<String>();

    private DieticiansListFragment.DieticiansListFragmentActivityListener listener;

    private ArrayList<GymWorker> dieticiansArrayList = new ArrayList<>();

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_DIETICIANS="getDieticians";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dietician_list,container,false);

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_DIETICIANS); //dodanie akcji od pobierania informacji o użytkownikach
        listView=view.findViewById(R.id.listViewDietician);


       // if(dieticiansListNames.size()==0)
       //     dieticianListInit();
        //       listView=container.findViewById(R.id.listViewTrainers);
     //   dieticiansListAdapter = new DieticiansListAdapter((AppCompatActivity)view.getContext(), dieticiansArrayList, this);
       // listView.setAdapter(dieticiansListAdapter);

        if(dieticiansArrayList.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getDieticians();
            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            dieticiansListAdapter=new DieticiansListAdapter(appContext, dieticiansArrayList, fragment);
            listView.setAdapter(dieticiansListAdapter);
        }



        landscapeConfiguration(view);

        return view;
    }

    public interface DieticiansListFragmentActivityListener{
        public void onItemSelected(GymWorker dietician);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DieticiansListFragment.DieticiansListFragmentActivityListener) {
            listener = (DieticiansListFragment.DieticiansListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: DieticiansListFragment.DieticiansListFragmentActivityListener");
        }
    }

    public void updateDetail(GymWorker dietician) {
        listener.onItemSelected(dietician);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void getDieticians(){
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        //Log.e()
        params.put("gymId", String.valueOf(SharedPreferencesOperations.getGymId(getContext())));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_DIETICIANS,params, Constants.CODE_POST_REQUEST, getContext(), GET_DIETICIANS);
        request.execute();
        Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_DIETICIANS)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("dieticians");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        GymWorker trainer = new GymWorker(jsonArray.getJSONObject(i));
                       dieticiansArrayList.add(trainer);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());
                    Log.e("gymsArrayList: ",dieticiansArrayList.toString());

                    dieticiansListAdapter=new DieticiansListAdapter(appContext, dieticiansArrayList, fragment);
                    listView.setAdapter(dieticiansListAdapter);
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



    private void dieticianListInit(){
        dieticiansListNames.add(0,"Janek");
        dieticiansListNames.add(1,"Dzbanek");
        dieticiansListNames.add(2,"Michau");
        dieticiansListNames.add(3,"Rafał");

        dieticiansListSurnames.add(0,"Michalak");
        dieticiansListSurnames.add(1,"Waska");
        dieticiansListSurnames.add(2, "Jormund");
        dieticiansListSurnames.add(3,"Kowal");
    }

}
