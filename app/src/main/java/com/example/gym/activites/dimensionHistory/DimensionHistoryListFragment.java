package com.example.gym.activites.dimensionHistory;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.Dimensions;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class DimensionHistoryListFragment extends Fragment {

    protected ListView listView;
    DimensionsHistoryListAdapter dimensionsHistoryListAdapter;
    private ArrayList<Date> dimensionsHistoryDates= new ArrayList<Date>();
    private DimensionsHistoryListFragmentActivityListener listFragmentActivityListener;
    private ArrayList<Dimensions> dimensionsArrayList = new ArrayList<>();

    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_USER_ALL_DIMENSIONS="getUserAllDimensions";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dimensions_history_list, container, false);
        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        //if(dimensionsHistoryDates.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
        //    dimensionHistoryListsInit();



        listView = view.findViewById(R.id.listViewDimensionsHistory);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_ALL_DIMENSIONS); //dodanie akcji od pobierania informacji o użytkownikach

        if(dimensionsArrayList.size()==0){
            getUserAllDimensions();
            /*dialog= new ProgressDialog(getActivity());
            dialog.setMessage("Trwa ładowanie listy wymiarów");
            dialog.show();*/
            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();

// later dismiss
           // dialog.hide()
        } else {
            dimensionsHistoryListAdapter=new DimensionsHistoryListAdapter(appContext, dimensionsArrayList, fragment);
            listView.setAdapter(dimensionsHistoryListAdapter);
        }

        //ArrayList<Dimensions> dimensionsArrayList = new ArrayList<>();
        //dimensionsArrayList.add(new Dimensions(new Date(2003-1900,1,2)));//month -1(1 oznacza luty)
        //dimensionsArrayList.add(new Dimensions("50", "40", "100", "", "10", new Date(2007-1900,7,5),1));


        //dimensionsHistoryListAdapter=new DimensionsHistoryListAdapter((AppCompatActivity)view.getContext(),dimensionsHistoryDates,this);
        //dimensionsHistoryListAdapter=new DimensionsHistoryListAdapter((AppCompatActivity)view.getContext(),dimensionsArrayList,this);
       // listView.setAdapter(dimensionsHistoryListAdapter);


        landscapeConfiguration(view);
        return view;
    }



    public interface DimensionsHistoryListFragmentActivityListener{
        public void onItemSelected(Dimensions dimensions);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DimensionsHistoryListFragmentActivityListener) {
            listFragmentActivityListener = (DimensionsHistoryListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(Dimensions dimensions) {
        listFragmentActivityListener.onItemSelected(dimensions);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout layout = view.findViewById(R.id.linearLayoutDimensionsHistoryListTitle);
            layout.setPadding(0,0,0,0);
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    //metoda do usuniecia jak bedzie baza danych
    private void dimensionHistoryListsInit(){
        dimensionsHistoryDates.add(new Date(2003-1900,1,2));
        dimensionsHistoryDates.add(new Date(2007-1900,7,5));
        dimensionsHistoryDates.add(new Date(2012-1900,9,1));
        dimensionsHistoryDates.add(new Date(2020-1900,8,15));
    }

    private void getUserAllDimensions(){
            getContext().registerReceiver(broadcastReceiver, filter);
        if(getContext().getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE)!=null && getContext().getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID,-1)!=-1){
            int userId=getContext().getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID,-1);
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(userId));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_ALL_DIMENSIONS, params, Constants.CODE_POST_REQUEST, getContext(), GET_USER_ALL_DIMENSIONS);
            request.execute();
    } else {
        Log.e("getUserAllDimensions()","nullException");
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_ALL_DIMENSIONS)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("dimensionsData");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        Dimensions dimensions = new Dimensions(jsonArray.getJSONObject(i));
                        dimensionsArrayList.add(dimensions);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Dimensions dimensions = new Dimensions(jsonArray.getJSONObject(1));
                    Log.e("dimensions: ",dimensions.getStringDimension());
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());

                    dimensionsHistoryListAdapter=new DimensionsHistoryListAdapter(appContext, dimensionsArrayList, fragment);
                    listView.setAdapter(dimensionsHistoryListAdapter);

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


}
