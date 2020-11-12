package com.example.gym.activites.trainingPlansList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.activites.availableTreningPlansList.AvailableTrainingPlansListActivity;
import com.example.gym.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyTrainingPlansListFragment extends Fragment {

    //private Button buttonAddTrainingPlan;
    private FloatingActionButton floatingActionButtonAddTrainingPlan;
    protected ListView listView;
    //TrainersListAdapter trainersListAdapter;
    private ArrayList<String> trainingPlansNames= new ArrayList<String>();
    private ArrayList<Integer> trainingPlansDifficulty = new ArrayList<>();


    private MyTrainingPlansListFragmentActivityListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_plans_list_fragment,container,false);

        if(trainingPlansNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            trainingPlansNamesAndDifficultyListsInit();

        listView=view.findViewById(R.id.listViewMyTrainingPlans);
        //       listView=container.findViewById(R.id.listViewTrainers);
        MyTrainingPlansListAdapter trainingPlansListAdapter= new MyTrainingPlansListAdapter((AppCompatActivity)view.getContext(),trainingPlansNames, trainingPlansDifficulty, this);
        listView.setAdapter(trainingPlansListAdapter);
        landscapeConfiguration(view);
        floatingActionButtonAddTrainingPlan =view.findViewById(R.id.buttonAddTrainingPlan);
        floatingActionButtonAddTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent availableTrainingPlansIntent = new Intent(getActivity(), AvailableTrainingPlansListActivity.class);
                startActivity(availableTrainingPlansIntent);
            }
        });

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

    //metoda do usuniecia jak bedzie baza danych
    private void trainingPlansNamesAndDifficultyListsInit(){
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
