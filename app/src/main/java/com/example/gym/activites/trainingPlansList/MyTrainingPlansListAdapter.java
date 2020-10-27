package com.example.gym.activites.trainingPlansList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

import java.util.List;

public class MyTrainingPlansListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> trainingPlansListNames;
    private List<Integer> trainingPlansListDifficulty;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter


    public MyTrainingPlansListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> names, @NonNull List<Integer> difficulty, Fragment fragment) {
        super(context, R.layout.training_plans_list_one_line, names);
        this.context=context;
        this.trainingPlansListNames=names;
        this.trainingPlansListDifficulty=difficulty;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewName;
        ImageView imageViewStars;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.training_plans_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewName = line.findViewById(R.id.textViewTrainingPlanName);
        imageViewStars= line.findViewById(R.id.imageViewTrainingPlanDifficultyStars);
        textViewName.setText(trainingPlansListNames.get(position));
        int height = textViewName.getLayoutParams().height;
        switch(trainingPlansListDifficulty.get(position))
        {
            case 1:
            {
                imageViewStars.setImageResource(R.drawable.star_1);
                break;
            }
            case 2:
            {
                imageViewStars.setImageResource(R.drawable.star_2);
                break;
            }
            case 3:
            {
                imageViewStars.setImageResource(R.drawable.star_3);
                break;
            }
            default:
            {
                imageViewStars.setImageResource(R.drawable.star_0);
                break;
            }
        }
        //imageViewStars.setImageResource(R.drawable.one_star);
        imageViewStars.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
        imageViewStars.requestLayout();
        //textViewSurname.setText(trainingPlansListDifficulty.get(position));
        //switch (trainingPlansListDifficulty.get(position))

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                ((MyTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansListNames.get(position),"30 minut","150","2", "Bardzo prosty, a zarazem ciekawy i niezwykły plan treningowy, służący rozwojowi fizycznemu całego ciała.");
            }
        });
        return line;
    }

}
