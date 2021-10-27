package com.example.gym.activites;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.MyGymTickets.MyGymTickets;
import com.example.gym.R;
import com.example.gym.activites.gymsList.GymDetailFragment;

import net.glxn.qrgen.android.QRCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MyProfileActivity extends OptionsMenuActivity {

    Button buttonMyPersonalData;
    Button buttonAddDimensions;
    Button buttonMyStatistics;
    Button buttonMyGymTickets;
    Button buttonDeleteAccount;
    private Button buttonQr;
    Context context;
    private final static String DELETE_USER ="deleteUser";
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(DELETE_USER); //dodanie akcji od zapisania do trenera

        context=this;
        idInit();
        onClickListenersInit();
    }

    private void onClickListenersInit(){
        buttonMyPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPersonalDataIntent=new Intent(getApplicationContext(), MyPersonalDataActivity.class);
                startActivity(myPersonalDataIntent);
            }
        });

        buttonAddDimensions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDimensionsIntent = new Intent(getApplicationContext(), AddDimensionsActivity.class);
                startActivity(addDimensionsIntent);
            }
        });

        buttonMyStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myStatisticsGeneral=new Intent(getApplicationContext(), MyStatisticGeneralPageActivity.class);
                startActivity(myStatisticsGeneral);
            }
        });

        buttonMyGymTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myGymTicketsIntent = new Intent(getApplicationContext(), MyGymTickets.class);
                startActivity(myGymTicketsIntent);
            }
        });

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountDialog();
            }
        });

        buttonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap myBitmap = QRCode.from("{\"userId\":" + String.valueOf(SharedPreferencesOperations.getUserId(context))+"}").withSize(350, 350).bitmap();
                //ImageView myImage = (ImageView) findViewById(R.id.imageView);
                //myImage.setImageBitmap(myBitmap);


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.qr_dialog);

                ImageView imageViewQR = dialog.findViewById(R.id.imageViewQR);
                imageViewQR.setImageBitmap(myBitmap);

                final Button buttonCancel = dialog.findViewById(R.id.buttonCancelDialog);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

    }

    private void deleteAccount(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(SharedPreferencesOperations.getUserId(context)));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SOFT_DELETE_USER, params, Constants.CODE_POST_REQUEST, context, DELETE_USER);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), DELETE_USER)) {
                //  Log.e("Main", bundle.getString("JSON"));
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    Log.e("json", jsonstr);
                    if(!json.getBoolean("error")){
                        informationDialog(getString(R.string.MyProfileSuccesfulAccountDeleteDialogTitle), getString(R.string.MyProfileSuccesfulAccountDeleteDialogMessage));
                    }
                    else {
                        informationDialog(getString(R.string.MyProfileUnsuccesfulAccountDeleteDialogTitle), getString(R.string.MyProfileUnsuccesfulAccountDeleteDialogMessage));
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

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent homePageActivityIntent = new Intent(this, HomePageActivity.class);
        startActivity(homePageActivityIntent);
    }

    private void idInit(){
    buttonMyPersonalData=findViewById(R.id.buttonMyPersonalData);
    buttonAddDimensions=findViewById(R.id.buttonAddDimensions);
    buttonMyStatistics=findViewById(R.id.buttonMyStatistics);
    buttonMyGymTickets=findViewById(R.id.buttonMyGymTickets);
    buttonDeleteAccount=findViewById(R.id.buttonDeleteAccount);
    buttonQr=findViewById(R.id.buttonQR);
    }

    @Override
    protected void goToMyProfileActivity() {
    }

    private void deleteAccountDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.MyProfileWantAccountDeleteDialogMessage)
                .setCancelable(true)
                .setTitle(R.string.MyProfileWantAccountDeleteDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAccount();
                    }
                })
                .setNegativeButton(R.string.DialogNegativeButtonNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void informationDialog(String title, String message){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setTitle(title)
                    .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            SharedPreferencesOperations.clearAllData(context);
                            Intent loginIntent = new Intent(MyProfileActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                        }
                    })
                    .create();

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
    }





}
