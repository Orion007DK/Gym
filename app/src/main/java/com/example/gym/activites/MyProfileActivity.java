package com.example.gym.activites;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.MyGymTickets.MyGymTickets;
import com.example.gym.R;

import net.glxn.qrgen.android.QRCode;

public class MyProfileActivity extends OptionsMenuActivity {

    Button buttonMyPersonalData;
    Button buttonAddDimensions;
    Button buttonMyStatistics;
    Button buttonMyGymTickets;
    Button buttonDeleteAccount;
    private Button buttonQr;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
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
}
