package com.example.gym.activites.availableTreningPlansList;

import android.app.Activity;
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

import com.example.gym.R;

import java.util.ArrayList;

public class AvailableTrainingPlansListFragment extends Fragment {

    protected ListView listView;
    //TrainersListAdapter trainersListAdapter;
    private ArrayList<String> trainingPlansNames = new ArrayList<String>();
    private ArrayList<Integer> trainingPlansDifficulty = new ArrayList<>();

    private AvailableTrainingPlansListFragment.AvailableTrainingPlansListFragmentActivityListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_training_plans_list_fragment, container, false);

        if (trainingPlansNames.size() == 0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            trainingPlansNamesAndDifficultyListsInit();

        listView = view.findViewById(R.id.listViewAvailableTrainingPlans);
        //       listView=container.findViewById(R.id.listViewTrainers);
        AvailableTrainingPlansListAdapter availableTrainingPlansListAdapter = new AvailableTrainingPlansListAdapter((AppCompatActivity) view.getContext(), trainingPlansNames, trainingPlansDifficulty, this);
        listView.setAdapter(availableTrainingPlansListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    public interface AvailableTrainingPlansListFragmentActivityListener {
        public void onItemSelected(String textName, String textEstimatedDuration, String textBurnedCalories, String trainingPlanDescription);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AvailableTrainingPlansListFragment.AvailableTrainingPlansListFragmentActivityListener) {
            listener = (AvailableTrainingPlansListFragment.AvailableTrainingPlansListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + "musi implementować interfejs: AvailableTrainingPlansListFragment.AvailableTrainingPlansListFragmentActivityListener");
        }
    }

    public void updateDetail(String textName, String textEstimatedDuration, String textBurnedCalories, String trainingPlanDescription) {
        listener.onItemSelected(textName, textEstimatedDuration, textBurnedCalories, trainingPlanDescription);
    }

    private void landscapeConfiguration(View view) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10, 20, 10, 0);
        }
    }

    //metoda do usuniecia jak bedzie baza danych
    private void trainingPlansNamesAndDifficultyListsInit() {
        trainingPlansNames.add(0, "Trening podstawowy");
        trainingPlansNames.add(1, "Treing na ramiona+");
        trainingPlansNames.add(2, "Trening łatwy");
        trainingPlansNames.add(3, "Ostatni");

        trainingPlansDifficulty.add(0, 1);
        trainingPlansDifficulty.add(1, 2);
        trainingPlansDifficulty.add(2, 3);
        trainingPlansDifficulty.add(3, 0);

    }

}
