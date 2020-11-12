package com.example.gym.activites.gymsList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.gym.Address;
import com.example.gym.Gym;
import com.example.gym.R;

import java.util.List;

public class GymsListAdapter extends ArrayAdapter<Gym> {

    private AppCompatActivity context;
   // private List<String> gymsListNames;
    //private List<Address> gymsListAddresses;
    private List<Gym> gymsList;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    public GymsListAdapter(@NonNull AppCompatActivity context, List<Gym> gymsList, Fragment fragment) {
        super(context, R.layout.gyms_list_one_line_layout, gymsList);
        this.context=context;
        this.gymsList=gymsList;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewGymName;
        TextView textViewGymAdress;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.gyms_list_one_line_layout, null);
        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewGymName = line.findViewById(R.id.textViewGymName);
        textViewGymAdress= line.findViewById(R.id.textViewGymAddress);
        textViewGymName.setText(gymsList.get(position).getName());
        //Address address=gymsListAddresses.get(position);
       // final String stringAddress = address.getCityName()+", ul. "+address.getStreetName()+" "+address.getStreetNumber();
        textViewGymAdress.setText(gymsList.get(position).getStringAddress());

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, gymsListNames.get(position),Toast.LENGTH_SHORT).show();
                ((GymsListFragment) fragmentParrent).updateDetail(gymsList.get(position));
            }
        });
        return line;
    }

}

