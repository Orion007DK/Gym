package com.example.gym.activites.trainersList;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.gym.RequestHandler;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.TrainerDetailActivity;
import com.rishabhharit.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class TrainerDetailFragment extends Fragment{

    GymWorker trainer=null;
    IntentFilter filter;
    Button buttonSign;
    private final static String SUBSCRIBE_TRAINER="subscribeTrainer";
    private final static String UNSUBSCRIBE_TRAINER="unsubscribeTrainer";
    RoundedImageView roundedImageView;
    Context context;
    TextView textViewClientsNumber;
    TextView textViewClientsNumberLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.trainer_details, container, false);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_TRAINER); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_TRAINER);
        roundedImageView=view.findViewById(R.id.roundedViewTrainer);


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

    private void getImage(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
            HashMap<String, String> params = new HashMap<>();
            params.put("trainerId", String.valueOf(trainer.getWorkerId()));
            PerformNetworkRequestForImage request = new PerformNetworkRequestForImage(Constants.URL_GET_TRAINER_IMAGE, params, Constants.CODE_POST_REQUEST, context);
            request.execute();
    }

    public void setData(GymWorker trainer){
        TextView textViewNameSurname = getView().findViewById(R.id.textViewNameSurnameValue);
        TextView textViewEmail = getView().findViewById(R.id.textViewEmailValue);
        TextView textViewPhoneNumber = getView().findViewById(R.id.textViewPhoneNumberValue);
        TextView textViewAboutTrainer = getView().findViewById(R.id.textViewAboutTrainerValue);
        textViewClientsNumber = getView().findViewById(R.id.textViewClientsNumberValue);
        textViewClientsNumberLabel = getView().findViewById(R.id.textViewClientsNumber);
        if(textViewNameSurname!=null && trainer.getSurname()!=null)
        textViewNameSurname.setText(trainer.getName()+" "+trainer.getSurname());
        if(textViewEmail!=null  && trainer.getEmail()!=null)
        textViewEmail.setText(trainer.getEmail());
        if(textViewPhoneNumber!=null && trainer.getPhoneNumber()!=null)
        textViewPhoneNumber.setText(trainer.getPhoneNumber());
        Log.e("trainer",trainer.getPhoneNumber());
        if(textViewAboutTrainer!=null && trainer.getDescription()!=null)
        textViewAboutTrainer.setText(aboutTrainer);//trainer.getDescription()aboutTrainer
        if(textViewClientsNumber!=null && trainer.getClientsNumber()>=0 && trainer.getMaxClientsNumber()>=0)
            textViewClientsNumber.setText(trainer.getClientsNumber()+"/"+trainer.getMaxClientsNumber());
        this.trainer=trainer;
        buttonSignInit();
        getImage();
    }

    private void buttonSignInit() {
        super.onResume();
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        final int idTrainer = data.getInt(Constants.SP_TRAINER_ID, -1);

        if(idTrainer==trainer.getWorkerId()) {
            buttonSign.setText("Wypisz się");
            if(getActivity().getClass().getSimpleName().equals("TrainerDetailActivity")){
            textViewClientsNumber.setVisibility(View.GONE);
            textViewClientsNumberLabel.setVisibility(View.GONE);}
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
                    if(trainer.getClientsNumber()<trainer.getMaxClientsNumber())
                        subscribeDialog(idTrainer);
                    else
                        informationConfirmDialog("Brak miejsc", "Ten trener osiągnął już limit klientów, musisz poszukać innego trenera");
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
                    SharedPreferencesOperations.clearTrainerData(context);
                    trainer.setClientsNumber(trainer.getClientsNumber()+1);
                    textViewClientsNumber.setText(String.valueOf(trainer.getClientsNumber())+"/"+trainer.getMaxClientsNumber());
                    informationConfirmDialog("Zapisano!","Pomyślnie zapisałeś się do danego trenera");
                    }
                    else {
                    informationConfirmDialog("Nieudane zapisanie", "Niesety wystąpiły pewne problemy przy zapisywaniu Cie do trenera, spróbuj ponownie poźniej");
                    }
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
                        trainer.setClientsNumber(trainer.getClientsNumber()-1);
                        textViewClientsNumber.setText(String.valueOf(trainer.getClientsNumber())+"/"+trainer.getMaxClientsNumber());
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

    public class PerformNetworkRequestForImage extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        private String url;

        //the parameters
        private HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        private int requestCode;

        //context for sendBroadcast;\

        private Context context;


        //constructor to initialize values
        public PerformNetworkRequestForImage(String url, HashMap<String, String> params, int requestCode, Context context) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            this.context=context;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("exec","Eex");
            // progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);

                if (!object.getBoolean("error")) {
                    //   Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    JSONObject trainerData = object.getJSONObject("trainerData");
                    String image = trainerData.getString("image");
                    if(!image.equals("null")) {
                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        if (roundedImageView != null)
                            roundedImageView.setImageBitmap(decodedByte);
                    }
                   // sendBroadcastJSON(object, action);
                    //because we haven't created it yet

                } else {
                    Log.e("error: ",object.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            if (requestCode == Constants.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == Constants.CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }

    }



}
