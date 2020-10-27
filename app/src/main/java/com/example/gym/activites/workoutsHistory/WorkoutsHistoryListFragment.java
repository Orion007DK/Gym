package com.example.gym.activites.workoutsHistory;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
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

import com.example.gym.R;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutsHistoryListFragment extends Fragment {

    protected ListView listView;
    WorkoutsHistoryListAdapter workoutsHistoryListAdapter;
    private ArrayList<Date> workoutsHistoryDates= new ArrayList<Date>();
    private ArrayList<String> workoutsHistoryNames= new ArrayList<String>();
    private WorkoutsHistoryListFragment.WorkoutHistoryListFragmentActivityListener listFragmentActivityListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_history_list, container, false);

        if(workoutsHistoryDates.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            workoutsListsInit();

        listView = view.findViewById(R.id.listViewWorkoutsHistory);
        workoutsHistoryListAdapter = new WorkoutsHistoryListAdapter((AppCompatActivity)view.getContext(),workoutsHistoryDates,workoutsHistoryNames,this);
        listView.setAdapter(workoutsHistoryListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    public interface WorkoutHistoryListFragmentActivityListener{
        public void onItemSelected(Date date, String name);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof WorkoutHistoryListFragmentActivityListener) {
            listFragmentActivityListener = (WorkoutHistoryListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(Date date, String name) {
        listFragmentActivityListener.onItemSelected(date, name);
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
        workoutsHistoryDates.add(new Date(2003-1900,1,2));
        workoutsHistoryDates.add(new Date(2007-1900,7,5));
        workoutsHistoryDates.add(new Date(2012-1900,9,1));
        workoutsHistoryDates.add(new Date(2020-1900,8,15));

        workoutsHistoryNames.add("Trening nr I");
        workoutsHistoryNames.add("Trening na barki II");
        workoutsHistoryNames.add("Trening nr I");
        workoutsHistoryNames.add("Ambitna nazwa treningu");
    }
}
