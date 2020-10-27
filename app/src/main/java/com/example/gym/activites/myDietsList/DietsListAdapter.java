package com.example.gym.activites.myDietsList;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.activites.myDietsList.DietDaysListActivity;
import com.example.gym.R;

import java.util.List;

public class DietsListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> dietsListNames;

    public DietsListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> dietsListNames) {
        super(context, R.layout.diets_list_one_line, dietsListNames);
        this.context = context;
        this.dietsListNames = dietsListNames;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewDietName;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.diets_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewDietName = line.findViewById(R.id.textViewDietName);
        textViewDietName.setText(dietsListNames.get(position));

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dietsListNames.get(position),Toast.LENGTH_SHORT).show();
               // ((DieticiansListFragment) fragmentParrent).updateDetail(dieticianListNames.get(position),dieticianListSurnames.get(position));
                Intent dietDaysListActivityIntent = new Intent(getContext(), DietDaysListActivity.class);
                dietDaysListActivityIntent.putExtra("dietName", dietsListNames.get(position));
                getContext().startActivity(dietDaysListActivityIntent);
            }
        });
        return line;
    }
}
