package com.example.gym.activites.dieticianList;

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
import com.example.gym.Dialogs;
import com.example.gym.dataClasses.GymWorker;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.RequestHandler;
import com.example.gym.SharedPreferencesOperations;
import com.rishabhharit.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class DieticiansDetailFragment extends Fragment {

    GymWorker dietician=null;
    IntentFilter filter;
    Button buttonSign;
    private final static String SUBSCRIBE_DIETICIAN ="subscribeDietician";
    private final static String UNSUBSCRIBE_DIETICIAN ="unsubscribeDietician";
    Context context;
    RoundedImageView roundedImageView;
    TextView textViewClientsNumber;
    TextView textViewClientsNumberLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dietician_details, container, false);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_DIETICIAN); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_DIETICIAN);
        roundedImageView=view.findViewById(R.id.roundedViewDietician);
        buttonSign = view.findViewById(R.id.buttonSign);
        return view;
    }

    private void subscribeDietician(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        if(id!=-1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("dieticianId", String.valueOf(dietician.getWorkerId()));
            params.put("userId", String.valueOf(id));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_DIETICIAN, params, Constants.CODE_POST_REQUEST, context, SUBSCRIBE_DIETICIAN);
            request.execute();
            Log.e("try subcsibe","subcribeTDeitician()");
        }
    }

    private void unsubscribeDietician(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        if(id!=-1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(id));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_DIETICIAN, params, Constants.CODE_POST_REQUEST, context, UNSUBSCRIBE_DIETICIAN);
            request.execute();
            Log.e("try subcsibe","subcribeToTrainer()");
        }
    }

    private void clearDieticianData(){
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(Constants.SP_DIETICIAN_ID);
        editor.apply();

        SharedPreferences dataDietician = context.getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorDietician = dataDietician.edit();
        editorDietician.clear();
        editorDietician.apply();
    }

    private void getImage(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("dieticianId", String.valueOf(dietician.getWorkerId()));
        DieticiansDetailFragment.PerformNetworkRequestForImage request = new DieticiansDetailFragment.PerformNetworkRequestForImage(Constants.URL_GET_DIETICIAN_IMAGE, params, Constants.CODE_POST_REQUEST, context);
        request.execute();
    }



    public void setText(GymWorker dietician){
        TextView textViewNameSurname = getView().findViewById(R.id.textViewNameSurnameValue);
        TextView textViewEmail = getView().findViewById(R.id.textViewEmailValue);
        TextView textViewPhoneNumber = getView().findViewById(R.id.textViewPhoneNumberValue);
        TextView textViewAboutDietician = getView().findViewById(R.id.textViewAboutDieticianValue);
        textViewClientsNumber = getView().findViewById(R.id.textViewClientsNumberValue);
        textViewClientsNumberLabel = getView().findViewById(R.id.textViewClientsNumber);
        //textViewNameSurname.setText(dietician.getName()+" "+dietician.getSurname());
        if(textViewNameSurname!=null && dietician.getSurname()!=null)
            textViewNameSurname.setText(dietician.getName()+" "+dietician.getSurname());
        if(textViewEmail!=null  && dietician.getEmail()!=null)
            textViewEmail.setText(dietician.getEmail());
        if(textViewPhoneNumber!=null && dietician.getPhoneNumber()!=null)
            textViewPhoneNumber.setText(dietician.getPhoneNumber());
        if(textViewAboutDietician!=null && dietician.getDescription()!=null)
            textViewAboutDietician.setText(dietician.getDescription());//trainer.getDescription()aboutTrainer
        if(textViewClientsNumber!=null && dietician.getClientsNumber()>=0 && dietician.getMaxClientsNumber()>=0)
            textViewClientsNumber.setText(dietician.getClientsNumber()+"/"+dietician.getMaxClientsNumber());
        this.dietician=dietician;
        buttonSignInit();
        getImage();
    }

    private void buttonSignInit() {
        super.onResume();
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        final int idDietician = data.getInt(Constants.SP_DIETICIAN_ID, -1);

        if(idDietician==dietician.getWorkerId()) {
            buttonSign.setText(R.string.DieticiansDetailSignOutButton);
            if(getActivity().getClass().getSimpleName().equals("DieticianDetailActivity")){
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
            buttonSign.setText(R.string.DieticiansDetailSignInButton);
            buttonSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dietician.getClientsNumber()<dietician.getMaxClientsNumber())
                        subscribeDialog(idDietician);
                    else
                        informationConfirmDialog(getString(R.string.DieticiansDetailsNoFreeSlotsDialogTitle), getString(R.string.DieticiansDetailsNoFreeSlotsDialogMessage));

                }
            });
        }
    }


    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewAboutDietician);
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
            if (Objects.equals(intent.getAction(), SUBSCRIBE_DIETICIAN)) {
                //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)) {
                        Boolean isSubscribed = json.getBoolean("subscribed");
                        if (isSubscribed) {
                            Log.e("isSubscribed: ", json.getString("subscribed"));
                            //  Log.e("Context:","on: "+context.toString());
                            SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = data.edit();
                            editor.putInt(Constants.SP_DIETICIAN_ID, dietician.getWorkerId());
                            editor.apply();
                            dietician.setClientsNumber(dietician.getClientsNumber() + 1);
                            textViewClientsNumber.setText(dietician.getClientsNumber() + "/" + dietician.getMaxClientsNumber());
                            SharedPreferences dataDietician = context.getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorDietician = dataDietician.edit();
                            editorDietician.clear();
                            editorDietician.apply();
                            informationConfirmDialog(getString(R.string.DieticiansDetailsSuccessfulDieteticianSubscribeDialogTitle), getString(R.string.DieticiansDetailsSuccessfulDieteticianSubscribeDialogMessage));
                        } else {
                            informationConfirmDialog(getString(R.string.DieticiansDetailsUnsuccessfulDieteticianSubscribeDialogTitle), getString(R.string.DieticiansDetailsUnsuccessfulDieteticianSubscribeDialogMessage));
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
            } else if(Objects.equals(intent.getAction(), UNSUBSCRIBE_DIETICIAN)){
                String jsonstr =bundle.getString("JSON");
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)) {
                        Boolean isSubscribed = json.getBoolean("unsubscribed");
                        if (isSubscribed) {
                            dietician.setClientsNumber(dietician.getClientsNumber() - 1);
                            textViewClientsNumber.setText(dietician.getClientsNumber() + "/" + dietician.getMaxClientsNumber());
                            informationConfirmDialog(getString(R.string.DieticiansDetailsSuccessfulDieteticianUnsubscribedDialogTitle), getString(R.string.DieticiansDetailsSuccessfulDieteticianUnsubscribedDialogMessage));
                            SharedPreferencesOperations.clearDieticianData(context);
                            SharedPreferencesOperations.removeDieteticianId(context);
                        } else {
                            informationConfirmDialog(getString(R.string.DieticiansDetailsUnsuccessfulDieteticianUnsubscribedDialogTitle), getString(R.string.DieticiansDetailsUnsuccessfulDieteticianUnsubscribedDialogMessage));
                        }
                    }else {
                            Dialogs.noNetworkDialog(context);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };


    private void subscribeDialog(int idDietician) {
        String message  ="";
        if (idDietician==-1) {
            message=getString(R.string.DieticiansDetailsIfSubcribeDialogMessage);
        } else {
            message=getString(R.string.DieticiansDetailsAlreadySubscribingDialogMessage);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle(R.string.DieticiansDetailsIfSubscribeDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subscribeDietician();

                    }
                })
                .setNegativeButton(R.string.DialogNegativeButtonNo, new DialogInterface.OnClickListener() {
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
        builder.setMessage(R.string.DieticiansDetailsIfUnsubcribeDialogMessage)
                .setTitle(R.string.DieticiansDetailsIfUnsubscribeDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unsubscribeDietician();

                    }
                })
                .setNegativeButton(R.string.DialogNegativeButtonNo, new DialogInterface.OnClickListener() {
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
                    .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            buttonSignInit();
                        }
                    })
                    .create();

            AlertDialog dialog = builder.show();}
    }

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
            if(s!=null) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (!object.getBoolean("error")) {
                        //   Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        //refreshing the herolist after every operation
                        //so we get an updated list
                        //we will create this method right now it is commented
                        JSONObject trainerData = object.getJSONObject("dieticianData");
                        String image = trainerData.getString("image");
                        if (!image.equals("null")) {
                            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            if (roundedImageView != null)
                                roundedImageView.setImageBitmap(decodedByte);
                        }
                        // sendBroadcastJSON(object, action);
                        //because we haven't created it yet

                    } else {
                        Log.e("error: ", object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
