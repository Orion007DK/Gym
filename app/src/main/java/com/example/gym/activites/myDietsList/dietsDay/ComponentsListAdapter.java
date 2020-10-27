package com.example.gym.activites.myDietsList.dietsDay;

import android.content.Context;
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

import com.example.gym.R;

import java.util.List;

public class ComponentsListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> componentsListNames;
    private List<String> componentsListQuantity;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    public ComponentsListAdapter(@NonNull AppCompatActivity context, List<String> componentsListNames, List<String> componentsListQuantity, Fragment fragment) {
        super(context, R.layout.component_list_one_line,componentsListNames);
        this.context=context;
        this.componentsListNames=componentsListNames;
        this.componentsListQuantity=componentsListQuantity;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewComponentName;
        TextView textViewComponentQuantity;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.component_list_one_line, null);
        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewComponentName = line.findViewById(R.id.textViewComponentName);
        textViewComponentQuantity= line.findViewById(R.id.textViewComponentQuantity);
        textViewComponentName.setText(componentsListNames.get(position));
        textViewComponentQuantity.setText(componentsListQuantity.get(position));
        /*
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, componentsListNames.get(position)+" "+componentsListQuantity.get(position),Toast.LENGTH_SHORT).show();
                //((TrainersListFragment) fragmentParrent).updateDetail(trainersListNames.get(position),trainersListSurnames.get(position));
            }
        });

         */
        return line;
    }

}
