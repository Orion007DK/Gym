package com.example.gym.activites.trainersList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.dataClasses.GymWorker;
import com.example.gym.R;

import java.util.List;

import ss.anoop.avtarview.AvtarView;

public class TrainersListAdapter extends ArrayAdapter<GymWorker> {

    private AppCompatActivity context;
    private List<String> trainersListNames;
    private List<String> trainersListSurnames;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter
    private List<GymWorker> trainersList;


    public TrainersListAdapter(@NonNull AppCompatActivity context, @NonNull List<GymWorker> trainersList, Fragment fragment) {
        super(context, R.layout.trainers_one_line_layout, trainersList);
        this.context=context;
        this.trainersList=trainersList;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewName;
       // TextView textViewSurname;
        AvtarView avtarView;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.trainers_one_line_layout, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }


        textViewName = line.findViewById(R.id.textViewName);
     //   textViewSurname= line.findViewById(R.id.textViewSurname);
        avtarView=line.findViewById(R.id.avtarViewTrainer);
        textViewName.setText(trainersList.get(position).getName()+" "+trainersList.get(position).getSurname());
     //   textViewSurname.setText(trainersListSurnames.get(position));
        avtarView.setText(trainersList.get(position).getName()+" "+trainersList.get(position).getSurname());
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                ((TrainersListFragment) fragmentParrent).updateDetail(trainersList.get(position));
            }
        });
        return line;
    }

    }
