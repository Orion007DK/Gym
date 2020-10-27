package com.example.gym.activites.trainersList;

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

public class TrainersListFragment extends Fragment {

    protected ListView listView;
    TrainersListAdapter trainersListAdapter;
    private ArrayList<String> trainersListNames= new ArrayList<String>();
    private ArrayList<String> trainersListSurnames= new ArrayList<String>();

    private TrainersListFragmentActivityListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainers_list,container,false);

        if(trainersListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            trainersListsNamesAndSurnamesInit();

        listView=view.findViewById(R.id.listViewTrainers);
 //       listView=container.findViewById(R.id.listViewTrainers);
        trainersListAdapter = new TrainersListAdapter((AppCompatActivity)view.getContext(),trainersListNames, trainersListSurnames, this);
        listView.setAdapter(trainersListAdapter);
        landscapeConfiguration(view);

    return view;
    }

    public interface TrainersListFragmentActivityListener{
        public void onItemSelected(String name, String surname);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TrainersListFragmentActivityListener) {
            listener = (TrainersListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(String name, String surname) {
        listener.onItemSelected(name, surname);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    //metoda do usuniecia jak bedzie baza danych
    private void trainersListsNamesAndSurnamesInit(){

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
