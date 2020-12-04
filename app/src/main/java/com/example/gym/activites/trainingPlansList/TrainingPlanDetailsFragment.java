package com.example.gym.activites.trainingPlansList;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.Exercise;
import com.example.gym.InteractiveTrainingActivity;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.TrainingPlan;
import com.example.gym.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class TrainingPlanDetailsFragment extends Fragment {

    TextView textViewTitle;
    TextView textViewEstimatedDurationValue;
    TextView textViewBurnedCaloriesValue;
    TextView textViewCompletedTrainingsValue;
    TextView textViewTrainingPlanDescription;
    ListView listViewExercises;

    Button buttonStartTraining;
    Button buttonDeleteTraining;

    ArrayList<String> exercisesNames= new ArrayList<>();
    ArrayList<String> exercisesRepetitions= new ArrayList<>();

    ArrayList<Exercise> exercisesArrayList = new ArrayList<>();

    private AppCompatActivity context;

    TrainingPlan trainingPlan;

    IntentFilter filter;
    private final static String UNSUBSCRIBE_TRAINING_PLAN ="unsubscribeTrainingPlan";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_plan_details, container, false);
        idInit(view);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(UNSUBSCRIBE_TRAINING_PLAN);

        buttonStartTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInteractiveTraining = new Intent(requireActivity(), InteractiveTrainingActivity.class);
                intentInteractiveTraining.putExtra("trainingPlan", trainingPlan);
                startActivity(intentInteractiveTraining);
            }
        });
        buttonDeleteTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsubscribeDialog();
            }
        });

        context =(AppCompatActivity)view.getContext();
           landscapeConfiguration(view); // działa, nie trzeba zmniejszać paddingów tylko rozmiar.
        listViewInit(view);
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void unsubscribeTrainingPlan(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);

            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(context)));
            params.put("trainingPlanId", String.valueOf(trainingPlan.getTrainingPlanId()));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_TRAINING_PLAN, params, Constants.CODE_POST_REQUEST, context, UNSUBSCRIBE_TRAINING_PLAN);
            request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), UNSUBSCRIBE_TRAINING_PLAN)) {
                //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    Boolean isUnsubscribed = json.getBoolean("unsubscribed");
                    if(isUnsubscribed){
                        Dialogs.informationConfirmDialog("Usunięto", "Plan treningowy został usunięty z listy Twoich planów treningowych", context);
                    }
                    else {
                        Dialogs.informationConfirmDialog("Błąd", "Wystąpił błąd, nie udało się usunąć planu treningowego",context );
                    }
                    //editor.putString(Constants.SP_USER_SURNAME, userJson.getString("surname"));
                    //Log.e("gymId", String.valueOf(userJson.getInt("gymId")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.unregisterReceiver(broadcastReceiver);
            }

        }
    };

    private void unsubscribeDialog() {
            String message="Czy na pewno chcesz usunąć ten plan treningowy?";

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle("Potwierdzenie chęci usunięcia planu")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unsubscribeTrainingPlan();

                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }


    public void setText(TrainingPlan trainingPlan){
        textViewTitle.setText(trainingPlan.getTrainingPlanName());
        textViewEstimatedDurationValue.setText(trainingPlan.getEstimatedDuration());
        textViewBurnedCaloriesValue.setText(trainingPlan.getBurnedCalories());
        textViewCompletedTrainingsValue.setText("Brak");
        textViewTrainingPlanDescription.setText(trainingPlan.getTrainingPlanDescription());
        //Log.e("array", trainingPlan.getExercises().toString());
        exercisesArrayList=new ArrayList<>(Arrays.asList(trainingPlan.getExercises()));
        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(context, exercisesArrayList, this);
        listViewExercises.setAdapter(exercisesListAdapter);
        this.trainingPlan=trainingPlan;
    }

    private void idInit(View view){
        textViewEstimatedDurationValue= view.findViewById(R.id.textViewEstimatedDurationValue);
        textViewBurnedCaloriesValue=view.findViewById(R.id.textViewBurnedCaloriesValue);
        textViewCompletedTrainingsValue=view.findViewById(R.id.textViewCompletedTrainingsValue);
        listViewExercises=view.findViewById(R.id.listViewExercises);
        textViewTitle=view.findViewById(R.id.textViewTitle);
        textViewTrainingPlanDescription=view.findViewById(R.id.textViewTrainingPlanDescription);
        buttonStartTraining=view.findViewById(R.id.buttonStartTraining);
        buttonDeleteTraining=view.findViewById(R.id.buttonDeleteTrainingPlan);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(0,0,0,0);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutTrainingPlanDetails);
            linearLayout.setPadding(0, 0, 0, 0);
        }
    }

    private void listViewInit(View view){
        //exercisesListInit();
        View header_view =getLayoutInflater().inflate(R.layout.exercises_list_header_line, null);
        if(listViewExercises.getHeaderViewsCount()==0)
            listViewExercises.addHeaderView(header_view);
        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter((AppCompatActivity)view.getContext(), exercisesArrayList, this);
        listViewExercises.setAdapter(exercisesListAdapter);
       // UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName);
        UIUtils.setListViewHeightBasedOnItems(listViewExercises, R.id.textViewExerciseName, view.getContext());

    }

    private void exercisesListInit(){
        exercisesArrayList.add(new Exercise("Brzuszki","2","5"));
        exercisesArrayList.add(new Exercise("Pompki","2","10"));
        exercisesArrayList.add(new Exercise("Podnoszenie ciężarów","10","6"));
        exercisesArrayList.add(new Exercise("Jakieś ćwiczenie o bardzo długiej nazwie i dość wielu powtórzeniach, serio","100","101"));


        exercisesNames.add("Brzuszki");
        exercisesNames.add("Pompki");
        exercisesNames.add("Podnoszenie ciężarów");
        exercisesNames.add("Jakieś ćwiczenie o bardzo długiej nazwie i dość wielu powtórzeniach, serio");

        exercisesRepetitions.add("3x15");
        exercisesRepetitions.add("2x10");
        exercisesRepetitions.add("10x6");
        exercisesRepetitions.add("100x100");
    }



}
