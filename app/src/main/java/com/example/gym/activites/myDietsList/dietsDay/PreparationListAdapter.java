package com.example.gym.activites.myDietsList.dietsDay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

import java.util.List;

public class PreparationListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> preparationSteps;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    public PreparationListAdapter(@NonNull AppCompatActivity context, List<String> preparationSteps, Fragment fragment) {
        super(context, R.layout.preparation_list_one_line, preparationSteps);
        this.context=context;
        this.preparationSteps=preparationSteps;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewPreparationStep;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.preparation_list_one_line, null);
        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewPreparationStep = line.findViewById(R.id.textViewPreparationStep);
        textViewPreparationStep.setText((position+1)+". "+preparationSteps.get(position));
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
