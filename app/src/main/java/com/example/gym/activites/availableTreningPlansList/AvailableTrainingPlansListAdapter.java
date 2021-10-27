package com.example.gym.activites.availableTreningPlansList;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.dataClasses.TrainingPlan;
import com.example.gym.activites.trainingPlansList.MyTrainingPlansListActivity;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AvailableTrainingPlansListAdapter extends ArrayAdapter<TrainingPlan> {

    private AppCompatActivity context;
    //private List<String> trainingPlansListNames;
    //private List<Integer> trainingPlansListDifficulty;
    private List<TrainingPlan> trainingPlansList;

    private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter
    private TextView textViewName;
    private ImageView imageViewStars;
    private ImageView imageViewAddTrainingPlan;
    private SimpleRatingBar simpleRatingBarTrainingPlanDifficulty;
    private final static String SUBSCRIBE_TRAINING_PLAN ="subscribeTrainingPlan";
    IntentFilter filter;

    public AvailableTrainingPlansListAdapter(@NonNull AppCompatActivity context, @NonNull List<TrainingPlan> trainingPlansList, Fragment fragment) {
        super(context, R.layout.available_training_plans_list_one_line, trainingPlansList);
        this.context=context;
       // this.trainingPlansListNames=names;
       // this.trainingPlansListDifficulty=difficulty;
        this.trainingPlansList=trainingPlansList;
        this.fragmentParrent=fragment;

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_TRAINING_PLAN); //dodanie akcji od zapisania do trenera
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
        //imageViewStars= line.findViewById(R.id.imageViewTrainingPlanDifficultyStars);
        imageViewAddTrainingPlan=line.findViewById(R.id.imageViewAddTrainingPlan);
        simpleRatingBarTrainingPlanDifficulty=line.findViewById(R.id.simpleRatingBarTrainingPlanDifficultyStars);
        textViewName.setText(trainingPlansList.get(position).getTrainingPlanName());
        int height = textViewName.getLayoutParams().height;
        setDifficultyStarImage(position);
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

        View.OnClickListener forUpdateDetailsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AvailableTrainingPlansListFragment) fragmentParrent).updateDetail(trainingPlansList.get(position));
            }
        };


        textViewName.setOnClickListener(forUpdateDetailsListener);
        simpleRatingBarTrainingPlanDifficulty.setOnClickListener(forUpdateDetailsListener);
        line.setOnClickListener(forUpdateDetailsListener);

        imageViewAddTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //komunikat o dodaniu planu
                //addTrainingPlanAlertDialog(activity, context);
                addTrainingPlanDialog(trainingPlansList.get(position).getTrainingPlanId());
            }
        });



    }

    private void setDifficultyStarImage(int position){
        simpleRatingBarTrainingPlanDifficulty.getAnimationBuilder()
                .setRepeatCount(ValueAnimator.INFINITE)
                .setRepeatCount(0)
                .setDuration(1000*trainingPlansList.get(position).getDifficultyLevel())
                .setRepeatMode(ValueAnimator.REVERSE)
                .setInterpolator(new LinearInterpolator())
                .setRatingTarget(trainingPlansList.get(position).getDifficultyLevel())
                .start();
        /*switch(trainingPlansList.get(position).getDifficultyLevel())
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
        imageViewStars.requestLayout();*/
    }

    private void subscribeTrainingPlan(int trainingPlanId){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);

            HashMap<String, String> params = new HashMap<>();
            params.put("trainingPlanId", String.valueOf(trainingPlanId));
            params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getContext())));
            Log.e("Params: ",params.toString());
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_TRAINING_PLAN, params, Constants.CODE_POST_REQUEST, context, SUBSCRIBE_TRAINING_PLAN);
            request.execute();
    }

        private void addTrainingPlanDialog(final int trainingPlanId) {
            String message  =context.getString(R.string.AvailableTrainingPlansAddTrainingDialogMessage);
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(true)
                    .setTitle(R.string.AvailableTrainingPlanAddTrainingDialogTitle)
                    .setPositiveButton(context.getString(R.string.DialogPositiveButtonYes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            subscribeTrainingPlan(trainingPlanId);

                        }
                    })
                    .setNegativeButton(context.getString(R.string.DialogNegativeButtonNo), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
            //AlertDialog dialog = builder.show();
            //   dialog.setCanceledOnTouchOutside(false);
        }

    public void informationConfirmDialog(String title, String message, Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setTitle(title)
                .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent myTrainingPlanListActivityIntent = new Intent(getContext(), MyTrainingPlansListActivity.class);
                        getContext().startActivity(myTrainingPlanListActivityIntent);
                    }
                })
                .create();
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), SUBSCRIBE_TRAINING_PLAN)) {
                //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    Boolean isSubscribed = json.getBoolean("subscribed");
                    if(isSubscribed){
                        Log.e("isSubscribed: ", json.getString("subscribed"));
                        //  Log.e("Context:","on: "+context.toString());
                        informationConfirmDialog("Dodano", "Dodano plan treningowy, do Twoich planów", getContext());
                    }
                    else {
                        Dialogs.informationConfirmDialog(context.getString(R.string.ErrorDialogTitle), context.getString(R.string.AddTrainingDialogErrorMessage), getContext());
                    }
                    //editor.putString(Constants.SP_USER_SURNAME, userJson.getString("surname"));
                    //Log.e("gymId", String.valueOf(userJson.getInt("gymId")));
                    } else {
                        Dialogs.noNetworkDialog(context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.unregisterReceiver(broadcastReceiver);
            }
        }
    };



        private void addTrainingPlanAlertDialog(AppCompatActivity activity, Context context) {


    }
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
       //new BlurAsyncTask(activity, context).execute();


}

