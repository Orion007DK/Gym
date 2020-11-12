package com.example.gym.activites;

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

import com.example.gym.Gym;
import com.example.gym.GymWorker;
import com.example.gym.R;
import com.example.gym.activites.dieticianList.DieticiansListFragment;

import java.util.List;

public class DieticiansListAdapter extends ArrayAdapter<GymWorker> {

    private AppCompatActivity context;
    private List<String> dieticianListNames;
    private List<String> dieticianListSurnames;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    private List<GymWorker> dieticiansList;

    public DieticiansListAdapter(@NonNull AppCompatActivity context, @NonNull List<GymWorker> dieticiansList, Fragment fragment) {
        super(context, R.layout.dietician_list_one_line_layout, dieticiansList);
        this.context=context;
        this.dieticiansList=dieticiansList;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewName;
        TextView textViewSurname;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.dietician_list_one_line_layout, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewName = line.findViewById(R.id.textViewName);
        textViewSurname= line.findViewById(R.id.textViewSurname);
        textViewName.setText(dieticiansList.get(position).getName());
        textViewSurname.setText(dieticiansList.get(position).getSurname());

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, dieticianListNames.get(position)+" "+dieticianListSurnames.get(position),Toast.LENGTH_SHORT).show();
                ((DieticiansListFragment) fragmentParrent).updateDetail(dieticiansList.get(position));
            }
        });
        return line;
    }

}
