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

import com.example.gym.dataClasses.Meal;
import com.example.gym.R;

import java.util.List;

public class DietMealsListAdapter extends ArrayAdapter<Meal> {

    private AppCompatActivity context;
    //private List<String> mealsListNames;
    //private List<String> mealsListCalories;
    private List<Meal> mealsList;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    public DietMealsListAdapter(@NonNull AppCompatActivity context,@NonNull List<Meal> mealsList, Fragment fragment) {
        super(context, R.layout.meals_list_one_line, mealsList);
        this.context=context;
        //this.mealsListNames=mealsListNames;
        //this.mealsListCalories=mealsListCalories;
        this.mealsList=mealsList;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewMealName;
        TextView textViewMealCalories;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.meals_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewMealName = line.findViewById(R.id.textViewMealName);
      //  textViewMealCalories= line.findViewById(R.id.textViewMealCalories);
        textViewMealName.setText(mealsList.get(position).getName());
      //  textViewMealCalories.setText(mealsList.get(position).getCalories());

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, mealsListNames.get(position)+" "+mealsListCalories.get(position),Toast.LENGTH_SHORT).show();
                ((MealsListFragment) fragmentParrent).updateDetail(mealsList.get(position));
            }
        });
        return line;
    }

}
