package com.example.gym.activites.workoutsHistory;

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
import com.example.gym.FinishedTrainingPlan;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class WorkoutsHistoryListFragment extends Fragment {

    protected ListView listView;
    WorkoutsHistoryListAdapter workoutsHistoryListAdapter;
    private ArrayList<Date> workoutsHistoryDates= new ArrayList<Date>();
    private ArrayList<String> workoutsHistoryNames= new ArrayList<String>();

    private ArrayList<FinishedTrainingPlan> arrayListFinishedTrainingPlans = new ArrayList<>();

    private SpotsDialog progressDialog;
    IntentFilter filter;
    private static final String GET_FINISHED_USER_TRAINING_PLANS ="getUserTrainingPlans";
    private TextView emptyListViewText;

    private AppCompatActivity appContext;
    private Fragment fragment;

    private WorkoutsHistoryListFragment.WorkoutHistoryListFragmentActivityListener listFragmentActivityListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_history_list, container, false);

        //if(workoutsHistoryDates.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
           // workoutsListsInit();

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_FINISHED_USER_TRAINING_PLANS); //dodanie akcji od pobierania informacji o zajęciach użytkownika

        listView = view.findViewById(R.id.listViewWorkoutsHistory);
        //workoutsHistoryListAdapter = new WorkoutsHistoryListAdapter((AppCompatActivity)view.getContext(), arrayListFinishedTrainingPlans,this);
        //listView.setAdapter(workoutsHistoryListAdapter);
        emptyListViewText=view.findViewById(R.id.emptyListViewText);
        View header_view =getLayoutInflater().inflate(R.layout.workouts_history_header_line, null);
        if(listView.getHeaderViewsCount()==0)
            listView.addHeaderView(header_view);
        listView.setEmptyView(emptyListViewText);


        Log.e("size", String.valueOf(arrayListFinishedTrainingPlans.size()));
        if(arrayListFinishedTrainingPlans.size()<=0){
            getUserTrainingPlans();
        } else {
            workoutsHistoryListAdapter = new WorkoutsHistoryListAdapter((AppCompatActivity)view.getContext(), arrayListFinishedTrainingPlans,this);
            listView.setAdapter(workoutsHistoryListAdapter);
        }
        landscapeConfiguration(view);

        return view;
    }

    public interface WorkoutHistoryListFragmentActivityListener{
        public void onItemSelected(FinishedTrainingPlan finishedTrainingPlan);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof WorkoutHistoryListFragmentActivityListener) {
            listFragmentActivityListener = (WorkoutHistoryListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(FinishedTrainingPlan finishedTrainingPlan) {
        listFragmentActivityListener.onItemSelected(finishedTrainingPlan);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout layout = view.findViewById(R.id.linearLayoutWorkoutsHistoryListTitle);
            layout.setPadding(0,0,0,0);
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }
    //metoda do usuniecia jak bedzie baza danych
    private void workoutsListsInit(){
        arrayListFinishedTrainingPlans.add(new FinishedTrainingPlan(new Date(2003-1900,1,2), "Trening 102"));
        arrayListFinishedTrainingPlans.add(new FinishedTrainingPlan(new Date(2003-1900,1,2), "Trening na barki II"));
        arrayListFinishedTrainingPlans.add(new FinishedTrainingPlan(new Date(2012-1900,9,1), "Trening nr I"));
        arrayListFinishedTrainingPlans.add(new FinishedTrainingPlan(new Date(2020-1900,8,15), "Ambitna nazwa treningu"));

        workoutsHistoryDates.add(new Date(2003-1900,1,2));
        workoutsHistoryDates.add(new Date(2007-1900,7,5));
        workoutsHistoryDates.add(new Date(2012-1900,9,1));
        workoutsHistoryDates.add(new Date(2020-1900,8,15));

        workoutsHistoryNames.add("Trening nr I");
        workoutsHistoryNames.add("Trening na barki II");
        workoutsHistoryNames.add("Trening nr I");
        workoutsHistoryNames.add("Ambitna nazwa treningu");
    }

    private void getUserTrainingPlans(){
        appContext.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getContext())));
        Log.e("get","userTrainingPlans");

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_FINISHED_TRAINING_PLANS, params, Constants.CODE_POST_REQUEST, getContext(), GET_FINISHED_USER_TRAINING_PLANS);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_FINISHED_USER_TRAINING_PLANS)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("trainingPlansList");
                    Log.e("TrainingPlansList:", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        arrayListFinishedTrainingPlans.add(new FinishedTrainingPlan(jsonArray.getJSONObject(i)));

                    }
                    WorkoutsHistoryListAdapter workoutsHistoryListAdapter = new WorkoutsHistoryListAdapter(appContext, arrayListFinishedTrainingPlans, fragment);
                    listView.setAdapter(workoutsHistoryListAdapter);

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                Log.e("KONIEC","BroadcatAvailableTickets");
                appContext.unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
               // progressDialog.dismiss();
            }
        }
    };
}
