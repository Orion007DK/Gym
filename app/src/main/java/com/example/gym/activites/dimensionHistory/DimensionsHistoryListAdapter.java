package com.example.gym.activites.dimensionHistory;

import android.text.format.DateFormat;
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

import java.util.Date;
import java.util.List;

public class DimensionsHistoryListAdapter extends ArrayAdapter<Date> {

    private AppCompatActivity context;
    private List<Date> dimensionsHistoryListDates;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter


    public DimensionsHistoryListAdapter(@NonNull AppCompatActivity context, @NonNull List<Date> dates, Fragment fragment) {
        super(context, R.layout.dimensions_history_one_line, dates);
        this.context=context;
        this.dimensionsHistoryListDates=dates;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewDate;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.dimensions_history_one_line, null);

        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewDate = line.findViewById(R.id.textViewDate);
        String stringDate = DateFormat.format("dd.MM.yyyy",dimensionsHistoryListDates.get(position)).toString();
        textViewDate.setText(stringDate);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dimensionsHistoryListDates.get(position).toString(),Toast.LENGTH_SHORT).show();
                ((DimensionHistoryListFragment) fragmentParrent).updateDetail(dimensionsHistoryListDates.get(position));
            }
        });
        return line;
    }
}
