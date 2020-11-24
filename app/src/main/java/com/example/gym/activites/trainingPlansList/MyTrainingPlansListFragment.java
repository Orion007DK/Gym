package com.example.gym.activites.trainingPlansList;

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

import com.example.gym.Classes;
import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.TrainingPlan;
import com.example.gym.activites.availableTreningPlansList.AvailableTrainingPlansListActivity;
import com.example.gym.R;
import com.example.gym.activites.myClassesList.MyClassesListAdapter;
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

public class MyTrainingPlansListFragment extends Fragment {

    //private Button buttonAddTrainingPlan;
    private FloatingActionButton floatingActionButtonAddTrainingPlan;
    protected ListView listView;
    //TrainersListAdapter trainersListAdapter;
    private ArrayList<String> trainingPlansNames= new ArrayList<String>();
    private ArrayList<Integer> trainingPlansDifficulty = new ArrayList<>();

    private ArrayList<TrainingPlan> arrayListTrainingPlans = new ArrayList<>();

    private SpotsDialog progressDialog;
    IntentFilter filter;
    private static final String GET_USER_TRAINING_PLANS ="getUserTrainingPlans";
    private TextView emptyListViewText;

    private AppCompatActivity appContext;
    private Fragment fragment;

    private MyTrainingPlansListFragmentActivityListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_plans_list_fragment,container,false);

        if(arrayListTrainingPlans.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            trainingPlansNamesAndDifficultyListsInit();

        appContext=(AppCompatActivity)view.getContext();
        fragment=this;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_TRAINING_PLANS); //dodanie akcji od pobierania informacji o zajęciach użytkownika

        listView=view.findViewById(R.id.listViewMyTrainingPlans);
        //       listView=container.findViewById(R.id.listViewTrainers);
      //  MyTrainingPlansListAdapter trainingPlansListAdapter= new MyTrainingPlansListAdapter((AppCompatActivity)view.getContext(), arrayListTrainingPlans, this);
     //   listView.setAdapter(trainingPlansListAdapter);
        landscapeConfiguration(view);
        floatingActionButtonAddTrainingPlan =view.findViewById(R.id.buttonAddTrainingPlan);
        floatingActionButtonAddTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent availableTrainingPlansIntent = new Intent(getActivity(), AvailableTrainingPlansListActivity.class);
                startActivity(availableTrainingPlansIntent);
            }
        });
        emptyListViewText=view.findViewById(R.id.emptyListViewText);

        View header_view =getLayoutInflater().inflate(R.layout.my_classes_list_header, null);
        if(listView.getHeaderViewsCount()==0)
            listView.addHeaderView(header_view);
        listView.setEmptyView(emptyListViewText);

        if(arrayListTrainingPlans.size()==0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getUserTrainingPlans();
            //  availableGymTicketsListAdapter=new AvailableGymTicketsListAdapter(appContext, gymTicketsArrayList, fragment);
            // listView.setAdapter(availableGymTicketsListAdapter);


            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            arrayListTrainingPlans.clear();
            getUserTrainingPlans();

            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();

            //MyClassesListAdapter myClassesListAdapter = new MyClassesListAdapter(this, arrayListClasses);
            //myClasseslistView.setAdapter(myClassesListAdapter);
        }


        return view;
    }

    public interface MyTrainingPlansListFragmentActivityListener{
        public void onItemSelected(String textName, String textEstimatedDuration, String textBurnedCalories, String textCompletedTrainings, String trainingPlanDescription);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MyTrainingPlansListFragmentActivityListener) {
            listener = (MyTrainingPlansListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: MyTrainingPlansListFragment.MyTrainingPlansListFragmentActivityListener");
        }
    }

    public void updateDetail(String textName, String textEstimatedDuration, String textBurnedCalories, String textCompletedTrainings, String trainingPlanDescription) {
        listener.onItemSelected(textName, textEstimatedDuration, textBurnedCalories, textCompletedTrainings, trainingPlanDescription);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void getUserTrainingPlans(){
        appContext.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        //Date date = new Date();
        //Date date =

        //String stringDate = DateFormat.format(Constants.DATABASE_DATA_FORMAT,calendarSelectedDate).toString();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getContext())));

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_TRAINING_PLANS, params, Constants.CODE_POST_REQUEST, getContext(), GET_USER_TRAINING_PLANS);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_TRAINING_PLANS)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("trainingPlansList");
                    Log.e("TrainingPlansList:", jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        arrayListTrainingPlans.add(new TrainingPlan(jsonArray.getJSONObject(i)));

                    }
                    MyTrainingPlansListAdapter trainingPlansListAdapter = new MyTrainingPlansListAdapter(appContext, arrayListTrainingPlans, fragment);
                    listView.setAdapter(trainingPlansListAdapter);

                    Log.e("js",jsonstr);

                    Log.e("jsonArray: ",jsonArray.toString());

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                Log.e("KONIEC","BroadcatAvailableTickets");
                appContext.unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };

    //metoda do usuniecia jak bedzie baza danych
    private void trainingPlansNamesAndDifficultyListsInit(){
        arrayListTrainingPlans.add(new TrainingPlan("Trening podstawowy",1));
        arrayListTrainingPlans.add(new TrainingPlan("Treing na ramiona+",1));
        arrayListTrainingPlans.add(new TrainingPlan("Trening łatwy",2));
        arrayListTrainingPlans.add(new TrainingPlan("Ostatni samuraj",3));

        trainingPlansNames.add(0, "Trening podstawowy");
        trainingPlansNames.add(1, "Treing na ramiona+");
        trainingPlansNames.add(2, "Trening łatwy");
        trainingPlansNames.add(3, "Ostatni");

        trainingPlansDifficulty.add(0,1);
        trainingPlansDifficulty.add(1,2);
        trainingPlansDifficulty.add(2,3);
        trainingPlansDifficulty.add(3,0);

    }
}
