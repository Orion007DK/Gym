package com.example.gym.activites.trainingPlansList;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.dataClasses.TrainingPlan;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

public class MyTrainingPlansListAdapter extends ArrayAdapter<TrainingPlan> {

    private AppCompatActivity context;
    private List<String> trainingPlansListNames;
    private List<Integer> trainingPlansListDifficulty;
    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter

    private List<TrainingPlan> trainingPlansList;


    public MyTrainingPlansListAdapter(@NonNull AppCompatActivity context, @NonNull List<TrainingPlan> trainingPlans, Fragment fragment) {
        super(context, R.layout.training_plans_list_one_line, trainingPlans);
        this.trainingPlansList=trainingPlans;
        this.context=context;
      //  this.trainingPlansListNames=names;
      //  this.trainingPlansListDifficulty=difficulty;
        this.fragmentParrent=fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewName;
        ImageView imageViewStars;
        SimpleRatingBar simpleRatingBarTrainingPlanDifficulty;
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
        //imageViewStars= line.findViewById(R.id.imageViewTrainingPlanDifficultyStars);
        //textViewName.setText(trainingPlansListNames.get(position));
        textViewName.setText(trainingPlansList.get(position).getTrainingPlanName());
        simpleRatingBarTrainingPlanDifficulty=line.findViewById(R.id.simpleRatingBarTrainingPlanDifficultyStars);
        simpleRatingBarTrainingPlanDifficulty.getAnimationBuilder()
                .setRepeatCount(ValueAnimator.INFINITE)
                .setRepeatCount(0)
                .setDuration(1000*trainingPlansList.get(position).getDifficultyLevel())
                .setRepeatMode(ValueAnimator.REVERSE)
                .setInterpolator(new LinearInterpolator())
                .setRatingTarget(trainingPlansList.get(position).getDifficultyLevel())
                .start();
        //simpleRatingBarTrainingPlanDifficulty.setRating(trainingPlansList.get(position).getDifficultyLevel());
        int height = textViewName.getLayoutParams().height;
        //ratingBarTrainingPlanDifficulty.setRating(trainingPlansList.get(position).getDifficultyLevel());
       /* switch(trainingPlansList.get(position).getDifficultyLevel())
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
        }*/
        //imageViewStars.setImageResource(R.drawable.one_star);
        //imageViewStars.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,height));
        //imageViewStars.requestLayout();
        //textViewSurname.setText(trainingPlansListDifficulty.get(position));
        //switch (trainingPlansListDifficulty.get(position))

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                ((MyTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansList.get(position));
            }
        });
        return line;
    }

}
