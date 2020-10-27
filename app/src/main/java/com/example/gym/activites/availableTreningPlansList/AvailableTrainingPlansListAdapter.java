package com.example.gym.activites.availableTreningPlansList;

import android.content.Context;
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

import com.example.gym.BlurAsyncTask;
import com.example.gym.R;

import java.util.List;

public class AvailableTrainingPlansListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> trainingPlansListNames;
    private List<Integer> trainingPlansListDifficulty;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter
    private TextView textViewName;
    private ImageView imageViewStars;
    private ImageView imageViewAddTrainingPlan;

    public AvailableTrainingPlansListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> names, @NonNull List<Integer> difficulty, Fragment fragment) {
        super(context, R.layout.available_training_plans_list_one_line, names);
        this.context=context;
        this.trainingPlansListNames=names;
        this.trainingPlansListDifficulty=difficulty;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;

        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.available_training_plans_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewName = line.findViewById(R.id.textViewTrainingPlanName);
        imageViewStars= line.findViewById(R.id.imageViewTrainingPlanDifficultyStars);
        imageViewAddTrainingPlan=line.findViewById(R.id.imageViewAddTrainingPlan);
        textViewName.setText(trainingPlansListNames.get(position));
        int height = textViewName.getLayoutParams().height;
        setDifficultyStarImage(imageViewStars, position, height);
        imageViewAddTrainingPlan.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
        imageViewAddTrainingPlan.requestLayout();
      //  imageViewStars.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
      //  imageViewStars.requestLayout();

       /* textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                ((AvailableTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansListNames.get(position),"30 minut","150", "Bardzo prosty, a zarazem ciekawy i niezwykły plan treningowy, służący rozwojowi fizycznemu całego ciała.");
            }
        });
        imageViewStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AvailableTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansListNames.get(position),"30 minut","150", "Bardzo prosty, a zarazem ciekawy i niezwykły plan treningowy, służący rozwojowi fizycznemu całego ciała.");
            }
        });

        imageViewAddTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
       setOnClickListeners(line, position, (AppCompatActivity)line.getContext(), line.getContext());
        return line;
    }

    private void setOnClickListeners(View line, final int position, final AppCompatActivity activity, final Context context){

        View.OnClickListener forUpdateDetailslistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AvailableTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansListNames.get(position),"30 minut","150", "Bardzo prosty, a zarazem ciekawy i niezwykły plan treningowy, służący rozwojowi fizycznemu całego ciała.");
            }
        };


        textViewName.setOnClickListener(forUpdateDetailslistener);
        imageViewStars.setOnClickListener(forUpdateDetailslistener);
        line.setOnClickListener(forUpdateDetailslistener);

        imageViewAddTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //komunikat o dodaniu planu
                addTrainingPlanAlertDialog(activity, context);
            }
        });



    }

    private void setDifficultyStarImage(ImageView imageViewStars, int position, int height){
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
        imageViewStars.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
        imageViewStars.requestLayout();
    }

    private void addTrainingPlanAlertDialog(AppCompatActivity activity, Context context) {
        /*AlertDialog dialog;
        dialog = new AlertDialog.Builder(getContext())
                .setTitle("tytul")
                .setMessage("t")
                .show();
        dialog.getWindow().addFlags(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);


         */
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
        builder.setTitle("Lets Blur");
        builder.setMessage("This is Blur Demo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog;
        dialog = builder.create();

// this position alert in the CENTER
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
*/
       new BlurAsyncTask(activity, context).execute();

    }
}

