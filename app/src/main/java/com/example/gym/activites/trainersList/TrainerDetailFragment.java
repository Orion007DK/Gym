package com.example.gym.activites.trainersList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.GymWorker;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class TrainerDetailFragment extends Fragment{

    GymWorker trainer=null;
    IntentFilter filter;
    Button buttonSign;
    private final static String SUBSCRIBE_TRAINER="subscribeTrainer";
    private final static String UNSUBSCRIBE_TRAINER="unsubscribeTrainer";
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.trainer_details, container, false);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_TRAINER); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_TRAINER);


     //   landscapeConfiguration(view); nie działa, nie trzeba zmniejszać paddingów tylko rozmiar.

         buttonSign = view.findViewById(R.id.buttonSign);
        //if(getActivity().getClass().getSimpleName().equals(TrainerDetailActivity.class.getSimpleName())) {


        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void subscribeTrainer(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        if(id!=-1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("trainerId", String.valueOf(trainer.getWorkerId()));
            params.put("userId", String.valueOf(id));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_TRAINER, params, Constants.CODE_POST_REQUEST, context, SUBSCRIBE_TRAINER);
            request.execute();
            Log.e("try subcsibe","subcribeToTrainer()");
        }
    }

    private void unsubscribeTrainer(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        if(id!=-1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(id));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_TRAINER, params, Constants.CODE_POST_REQUEST, context, UNSUBSCRIBE_TRAINER);
            request.execute();
            Log.e("try subcsibe","subcribeToTrainer()");
        }
    }
/*
    private void clearTrainerData(){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(Constants.SP_TRAINER_ID);
        editor.apply();


        SharedPreferences dataTrainer = context.getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorTrainer = dataTrainer.edit();
        editorTrainer.clear();
        editorTrainer.apply();
    }*/

/*
    @Override
    public void onResume() {
        RoundedImageView roundedImageView = getView().findViewById(R.id.roundedViewTrainer);
       // roundedImageView.measure(null, null);
        ViewGroup.LayoutParams layoutParams = roundedImageView.getLayoutParams();
        Log.e("tal","sda");
        int height=layoutParams.height;
        // layoutParams.width=height;
        roundedImageView.getViewTreeObserver().removeOnPreDrawListener(null);
        Log.e("aa",String.valueOf(roundedImageView.getMeasuredHeight()));
        Log.e("sv",String.valueOf(roundedImageView.getMeasuredWidth()));
       // roundedImageView.setLayoutParams(new LinearLayout.LayoutParams(height, height));
        roundedImageView.setCornerRadius(height);
        Log.e("koi","koniec");
        super.onResume();
    }

*/
    public void setData(GymWorker trainer){
        TextView textViewNameSurname = getView().findViewById(R.id.textViewNameSurnameValue);
        TextView textViewEmail = getView().findViewById(R.id.textViewEmailValue);
        TextView textViewPhoneNumber = getView().findViewById(R.id.textViewPhoneNumberValue);
        TextView textViewAboutTrainer = getView().findViewById(R.id.textViewAboutTrainerValue);
        if(textViewNameSurname!=null && trainer.getSurname()!=null)
        textViewNameSurname.setText(trainer.getName()+" "+trainer.getSurname());
        if(textViewEmail!=null  && trainer.getEmail()!=null)
        textViewEmail.setText(trainer.getEmail());
        if(textViewPhoneNumber!=null && trainer.getPhoneNumber()!=null)
        textViewPhoneNumber.setText(trainer.getPhoneNumber());
        Log.e("trainer",trainer.getPhoneNumber());
        if(textViewAboutTrainer!=null && trainer.getDescription()!=null)
        textViewAboutTrainer.setText(aboutTrainer);//trainer.getDescription()aboutTrainer
        this.trainer=trainer;
        buttonSignInit();
    }

    private void buttonSignInit() {
        super.onResume();
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        final int idTrainer = data.getInt(Constants.SP_TRAINER_ID, -1);

        if(idTrainer==trainer.getWorkerId()) {
            buttonSign.setText("Wypisz się");
            buttonSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unsubscribeDialog();
                }
            });
        }
        else {
            buttonSign.setText("Zapisz się");
            buttonSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscribeDialog(idTrainer);
                }
            });
        }
    }


    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewAboutTrainer);
            textView.setPadding(0,0,0,0);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), SUBSCRIBE_TRAINER)) {
                //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    Boolean isSubscribed = json.getBoolean("subscribed");
                    if(isSubscribed){
                    Log.e("isSubscribed: ", json.getString("subscribed"));
                      //  Log.e("Context:","on: "+context.toString());
                    SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.putInt(Constants.SP_TRAINER_ID,trainer.getWorkerId());
                    editor.apply();
                    /*SharedPreferences dataTrainer = context.getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorTrainer = dataTrainer.edit();
                    editorTrainer.clear();
                    editorTrainer.apply();*/
                    SharedPreferencesOperations.clearTrainerData(context);
                    informationConfirmDialog("Zapisano!","Pomyślnie zapisałeś się do danego trenera");
                    }
                    else {
                    informationConfirmDialog("Nieudane zapisanie", "Niesety wystąpiły pewne problemy przy zapisywaniu Cie do trenera, spróbuj ponownie poźniej");
                    }
                    //editor.putString(Constants.SP_USER_SURNAME, userJson.getString("surname"));
                    //Log.e("gymId", String.valueOf(userJson.getInt("gymId")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.unregisterReceiver(broadcastReceiver);
            } else if(Objects.equals(intent.getAction(), UNSUBSCRIBE_TRAINER)){
                String jsonstr =bundle.getString("JSON");
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonstr);
                    Boolean isSubscribed = json.getBoolean("unsubscribed");
                    if(isSubscribed) {
                        informationConfirmDialog("Wypisany", "Zostałeś wypisany od swojego trenera");
                        SharedPreferencesOperations.clearTrainerData(context);
                        SharedPreferencesOperations.removeTrainerId(context);
                    } else {
                        informationConfirmDialog("Błąd", "Wystąpił błąd, nie udało się wypisać Cię od Twojego trenera, spróbuj później");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };


    private void subscribeDialog(int idTrainer) {
        String message  ="";
        if (idTrainer==-1) {
            message="Czy na pewno chcesz zapisać się do tego trenera?";
        } else {
            message="Jesteś już zapisany do innego trenera! Czy na pewno chcesz zapisać się do tego trenera? Będzie to skutkowało wypisaniem od obecnego trenera";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle("Potwierdzenie chęci zapisania")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subscribeTrainer();

                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void unsubscribeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Czy na pewno chcesz się wypisać od danego trenera?")
                .setCancelable(true)
                .setTitle("Potwierdzenie chęci wypisania")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unsubscribeTrainer();

                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
        //AlertDialog dialog = builder.show();
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void informationConfirmDialog(String title, String message){
        if(getActivity()!=null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setCancelable(false)
                    .setTitle(title)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            buttonSignInit();
                        }
                    })
                    .create();

            AlertDialog dialog = builder.show();}
    }






    private static String aboutTrainer="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fringilla rutrum neque sit amet viverra. Integer neque purus, tristique eget ante ac, pharetra rhoncus felis. Vestibulum lacinia tellus condimentum lobortis congue. In hac habitasse platea dictumst. Nullam est nulla, cursus eget pretium sed, congue non ante. Vivamus ac ligula et nisl varius vestibulum. Vestibulum vitae tellus vitae ligula ultricies blandit vitae at ligula. Integer faucibus fringilla eleifend. Morbi vehicula aliquet consectetur. ";

}
