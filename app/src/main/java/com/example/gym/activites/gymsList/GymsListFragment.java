package com.example.gym.activites.gymsList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Address;
import com.example.gym.R;

import java.util.ArrayList;

public class GymsListFragment extends Fragment {

    protected ListView listView;
    GymsListAdapter gymsListAdapter;
    private ArrayList<String> gymsListNames= new ArrayList<String>();
    private ArrayList<Address> gymListAdresses= new ArrayList<Address>();
    private GymsListFragmentActivityListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gyms_list,container,false);

        if(gymsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            gymListsInit();

        listView=view.findViewById(R.id.listViewGyms);
        gymsListAdapter = new GymsListAdapter((AppCompatActivity)view.getContext(),gymsListNames, gymListAdresses, this);
        listView.setAdapter(gymsListAdapter);
        landscapeConfiguration(view);

        return view;
    }

    public interface GymsListFragmentActivityListener{
        public void onItemSelected(String name, Address address);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof GymsListFragmentActivityListener) {
            listener = (GymsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: GymsListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(String name, Address address) {
        listener.onItemSelected(name, address);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
            Button buttonFindGymUsingLocalisation = view.findViewById(R.id.buttonFindGymUsingLocalisation);
            buttonFindGymUsingLocalisation.setPadding(0,0,0,0);
        }
    }

    //metoda do usuniecia jak bedzie baza danych
    private void gymListsInit(){

        gymsListNames.add(0,"Siłownia Rycha");
        gymsListNames.add(1, "Siłka+");
        gymsListNames.add(2,"Zwykła siłownia");
        gymsListNames.add(3,"Silnia");

        gymListAdresses.add(0, new Address("Lublin", "Nadbystrzycka","80"));
        gymListAdresses.add(1, new Address("Lublin", "Poniatowskiego","3A"));
        gymListAdresses.add(2, new Address("Zamość", "Lubelska","25"));
        gymListAdresses.add(3, new Address("Chełm", "Kwiatowa","17"));
    }

}
