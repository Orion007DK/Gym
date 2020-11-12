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

import com.example.gym.Constants;
import com.example.gym.R;

import java.util.List;

public class DietsListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> dietsNamesList;
    private List<Integer> dietsIdList;

    public DietsListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> dietsNamesList, List<Integer> dietsIdList) {
        super(context, R.layout.diets_list_one_line, dietsNamesList);
        this.context = context;
        this.dietsNamesList = dietsNamesList;
        this.dietsIdList=dietsIdList;
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
        textViewDietName.setText(dietsNamesList.get(position));

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dietsNamesList.get(position),Toast.LENGTH_SHORT).show();
               // ((DieticiansListFragment) fragmentParrent).updateDetail(dieticianListNames.get(position),dieticianListSurnames.get(position));
                Intent dietDaysListActivityIntent = new Intent(getContext(), DietDaysListActivity.class);
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_NAME, dietsNamesList.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_ID, dietsIdList.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_SUB_STATUS, false);
                getContext().startActivity(dietDaysListActivityIntent);
            }
        });
        return line;
    }
}
