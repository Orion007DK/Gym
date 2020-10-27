package com.example.gym.activites.dieticianList;

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
import com.example.gym.activites.DieticiansListAdapter;

import java.util.ArrayList;

public class DieticiansListFragment extends Fragment {

    protected ListView listView;
    DieticiansListAdapter dieticiansListAdapter;
    private ArrayList<String> dieticiansListNames = new ArrayList<String>();
    private ArrayList<String> dieticiansListSurnames = new ArrayList<String>();

    private DieticiansListFragment.DieticiansListFragmentActivityListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dietician_list,container,false);
        if(dieticiansListNames.size()==0)
            dieticianListInit();

        listView=view.findViewById(R.id.listViewDietician);
        //       listView=container.findViewById(R.id.listViewTrainers);
        dieticiansListAdapter = new DieticiansListAdapter((AppCompatActivity)view.getContext(), dieticiansListNames, dieticiansListSurnames, this);
        listView.setAdapter(dieticiansListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    public interface DieticiansListFragmentActivityListener{
        public void onItemSelected(String name, String surname);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DieticiansListFragment.DieticiansListFragmentActivityListener) {
            listener = (DieticiansListFragment.DieticiansListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: DieticiansListFragment.DieticiansListFragmentActivityListener");
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
