package com.example.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gym.activites.trainingPlansList.MyTrainingPlansListActivity;
import com.example.gym.dataClasses.Exercise;
import com.example.gym.dataClasses.TrainingPlan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import params.com.stepprogressview.StepProgressView;

public class InteractiveTrainingActivity extends AppCompatActivity {

    private Timer timer;
    TextView textViewTime;
    private int time = 0;
    TimerTask timerTask;

    ArrayList<Exercise> exerciseList;

    Button buttonStopTraining;
    Button buttonFinishExercise;

    StepProgressView stepProgressView;
    ArrayList<Integer> arrayListMarkers = new ArrayList<>();

    int performedExercises=0;
    int seconds;
    int minutes;
    int hours;

    IntentFilter filter;
    private final String INSERT_INTO_USER_FINISHED_TRAINING_PLANS ="insertIntoUserFinishedTrainingPlans";

    TextView textViewPerformedExerciseProgress;
    TextView textViewRepetitions;
    TextView textViewSets;
    TextView textViewTitle;
    TextView textViewDescription;
    TextView textViewLoad;
    TextView textViewLoadLabel;

    private int currentExerciseNumber=0;
    private int currentSet=0;

    TrainingPlan trainingPlan;
    private SpotsDialog progressDialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_training);
        Bundle bundle = getIntent().getExtras();
        trainingPlan = (TrainingPlan)bundle.getSerializable("trainingPlan");

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(INSERT_INTO_USER_FINISHED_TRAINING_PLANS); //dodanie akcji tworzenia konta dla użytkownika

        exerciseList = trainingPlan.getArrayListExercises();
        idInit();
        stepProgressViewInit();
        execiseDataSet();
        textViewPerformedExerciseProgress.setText(getString(R.string.InteractiveTrainingExercisePerformedInfo)+String.valueOf(performedExercises)+"/"+String.valueOf(exerciseList.size()));


        buttonStopTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //performedExercises++;
                //textViewPerformedExerciseProgress.setText("Wykonane ćwiczenia: "+String.valueOf(performedExercises)+"/"+String.valueOf(exerciseList.size()));
                //stepProgressView.setCurrentProgress(performedExercises);
                finish();

            }
        });

        buttonFinishExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSet++;
                textViewSets.setText(String.valueOf(currentSet)+"/"+exerciseList.get(currentExerciseNumber).getSets());
                if(currentSet==Integer.valueOf(exerciseList.get(currentExerciseNumber).getSets()))
                {
                    if(currentExerciseNumber+1==exerciseList.size()) {
                        timer.cancel();
                        insertIntoUserFinishedTrainingPlans();
                    } else {
                        dialogInit(15000);
                    }
                }
                if(currentSet+1==Integer.valueOf(exerciseList.get(currentExerciseNumber).getSets()))
                    buttonFinishExercise.setText(R.string.InteractiveTrainingExercisePerformedButton);
            }
        });

    }

    private void insertIntoUserFinishedTrainingPlans() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter); //zarejestrowanie odbiorcy ze stworzonym filtrem
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getApplicationContext())));
        params.put("trainingPlanId", String.valueOf(trainingPlan.getTrainingPlanId()));
        Date date = new Date(System.currentTimeMillis());
        String dateString = DateFormat.format(Constants.DATABASE_DATA_FORMAT, date).toString();
        Log.e("date", dateString);
        params.put("date", dateString);
        params.put("duration", String.format("%02d:%02d:%02d", hours, minutes, seconds));
        Log.e("Duration",String.format("%02d:%02d:%02d", hours, minutes, seconds));

        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_INSERT_INTO_USER_FINISHED_TRAINING_PLANS, params, Constants.CODE_POST_REQUEST, getApplicationContext(), INSERT_INTO_USER_FINISHED_TRAINING_PLANS);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (intent.getAction().equals(INSERT_INTO_USER_FINISHED_TRAINING_PLANS)) {
                String jsonstr =bundle.getString("JSON");
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                        finishedTrainingDialog();
                    } else {
                        Dialogs.noNetworkFinishDialog(context, InteractiveTrainingActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("InsertFinishedTraining:", bundle.getString("JSON"));

                unregisterReceiver(broadcastReceiver); //odrejestrowanie odbiorcy
                progressDialog.dismiss();


            }
        }
    };

    private void nextExercise(){
            currentExerciseNumber++;
            currentSet = 0;
            performedExercises++;
            textViewPerformedExerciseProgress.setText(getString(R.string.InteractiveTrainingExercisePerformedInfo) + String.valueOf(performedExercises) + "/" + String.valueOf(exerciseList.size()));
            stepProgressView.setCurrentProgress(performedExercises);
            execiseDataSet();
            buttonFinishExercise.setText(R.string.InteractiveTrainingSetPerformedInfo);
    }

    PieProgressDrawable pieProgressDrawable;
    ImageView timeProgress;
    TextView textViewRestTime;
    CountDownTimer countDownTimer;

    private void dialogInit(final int milisec){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.break_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        final Button buttonCancel = dialog.findViewById(R.id.buttonCancelDialog);

        pieProgressDrawable = new PieProgressDrawable();
        pieProgressDrawable.setColor(Color.parseColor("#6200EE")); //dla R.color.primary nie działa nie wiadomo czemu, wyświetla się szary kolor
        timeProgress = (ImageView) dialog.findViewById(R.id.time_progress);
        timeProgress.setImageDrawable(pieProgressDrawable);
        textViewRestTime=dialog.findViewById(R.id.textViewRestTime);

        pieProgressDrawable.setLevel(100);
        timeProgress.invalidate();


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Dialog) dialog).isShowing()) {
                dialog.dismiss();}
                if(countDownTimer!=null)
                countDownTimer.cancel();
                nextExercise();
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            //private static final int AUTO_DISMISS_MILLIS = 30000;

            @Override
            public void onShow(final DialogInterface dialog) {
                //final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                final CharSequence charSequenceRestTime = textViewRestTime.getText();
                countDownTimer = new CountDownTimer(milisec, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        pieProgressDrawable.setLevel((int)(100*millisUntilFinished/milisec));
                        timeProgress.invalidate();
                        Log.e(String.valueOf(millisUntilFinished),String.valueOf(milisec));

                        textViewRestTime.setText(String.format(
                                Locale.getDefault(), "%s %ds",
                                charSequenceRestTime,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                        ));
                    }

                    @Override
                    public void onFinish() {
                        if (((Dialog) dialog).isShowing()) {
                            dialog.dismiss();
                            nextExercise();
                        }
                    }
                }.start();
            }


        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void finishedTrainingDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(InteractiveTrainingActivity.this);
        builder.setMessage(getString(R.string.InteractiveTrainingTrainingFinishedDialogMessage)+String.format("%02d:%02d:%02d", hours, minutes, seconds))
                .setCancelable(true)
                .setTitle(R.string.InteractiveTrainingTrainingFinishedDialogTitle)
                .setPositiveButton(R.string.InteractiveTrainingTrainingFinishedDialogPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent finishTrainingIntent = new Intent(InteractiveTrainingActivity.this, MyTrainingPlansListActivity.class);
                        startActivity(finishTrainingIntent);
                        finish();
                    }
                });
        builder.show();
    }


    private void idInit(){
        textViewPerformedExerciseProgress=findViewById(R.id.textViewPerformedExerciseProgress);
        textViewRepetitions=findViewById(R.id.textViewRepetitionsValue);
        textViewSets=findViewById(R.id.textViewSetsValue);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewDescription=findViewById(R.id.textViewDescriptionValue);
        textViewTime=findViewById(R.id.textViewTime);
        textViewLoad=findViewById(R.id.textViewLoadValue);
        textViewLoadLabel=findViewById(R.id.textViewLoad);

        buttonStopTraining=findViewById(R.id.buttonStopTraining);
        buttonFinishExercise=findViewById(R.id.buttonFinishExercise);
    }

    private void execiseDataSet(){
        textViewTitle.setText(exerciseList.get(currentExerciseNumber).getExerciseName());
        textViewRepetitions.setText(exerciseList.get(currentExerciseNumber).getRepetitions());
        textViewSets.setText(String.valueOf(currentSet)+"/"+exerciseList.get(currentExerciseNumber).getSets());
        textViewDescription.setText(exerciseList.get(currentExerciseNumber).getExerciseDescription());
        if(exerciseList.get(currentExerciseNumber).getLoad()!=null){
            textViewLoadLabel.setVisibility(View.VISIBLE);
            textViewLoad.setVisibility(View.VISIBLE);
            textViewLoad.setText(exerciseList.get(currentExerciseNumber).getLoad());
        } else {
            textViewLoadLabel.setVisibility(View.GONE);
            textViewLoad.setVisibility(View.GONE);
        }
    }



    private void stepProgressViewInit(){
        stepProgressView = findViewById(R.id.stepProgressViewExercise);
        if(exerciseList.size()>=3) {
            arrayListMarkers.add((int) exerciseList.size()/3);
            arrayListMarkers.add((int)2*exerciseList.size()/3);
        } else {
            arrayListMarkers.add((int)exerciseList.size()/2);
        }
        stepProgressView.setMarkers(arrayListMarkers);
        stepProgressView.setCurrentProgress(0);
        stepProgressView.setTotalProgress(exerciseList.size());
    }

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        seconds = time;
                        minutes = seconds / 60;
                        hours = minutes /60;
                        minutes %= 60;
                        seconds %= 60;
                        textViewTime.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                            time += 1;
                    }
                });
            }

        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", time);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        time=savedInstanceState.getInt("time");
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timerTask.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onBackPressed() {
    }
}
