package com.example.gym.activites.gymsList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.gym.Constants;
import com.example.gym.dataClasses.Gym;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class GymDetailsActivity extends AppCompatActivity {

    private static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fringilla rutrum neque sit amet viverra. Integer neque purus, tristique eget ante ac, pharetra rhoncus felis. Vestibulum lacinia tellus condimentum lobortis congue. In hac habitasse platea dictumst. Nullam est nulla, cursus eget pretium sed, congue non ante. Vivamus ac ligula et nisl varius vestibulum. Vestibulum vitae tellus vitae ligula ultricies blandit vitae at ligula. Integer faucibus fringilla eleifend. Morbi vehicula aliquet consectetur. ";

    private Gym gym;

    IntentFilter filter;
    // private AppCompatActivity appContext;
    //  private Fragment fragment;
    private static final String GET_GYM_DATA = "getGymData";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;

    GymDetailFragment gymDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_details);
       // Bundle bundle = getIntent().getExtras();

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_GYM_DATA); //dodanie akcji od pobierania informacji o użytkownikach

        gymDetailFragment = (GymDetailFragment) getSupportFragmentManager().findFragmentById(R.id.gymDetailsFragment);

        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int gymId = data.getInt(Constants.SP_GYM_ID, -1);
        Log.e("gymId: ", String.valueOf(gymId));
        if(gymId!=-1){
            getGymData(gymId);
        }

        //if (bundle != null) {
            // String name=bundle.getString("name");
            // Address address=bundle.getParcelable("address");
            //String address=bundle.getString("address");
            //String aboutGym=bundle.getString("aboutGym");
            //String email = bundle.getString("email");
            //String phoneNumber = bundle.getString("phoneNumber");

            /*String name = "Silka";
            Address address = new Address("Lublin", "Nowa", "5");
            String aboutGym = loremIpsum;
            String email = "email@od.pl";
            String phoneNumber = "21323423";
            //Gym gym = new Gym(name, address, email, phoneNumber, aboutGym);
*/
       // }
      /*  progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        getGymData(1);*/

            //gymDetailFragment.setText(gym);

    }


    private void getGymData(int gymId) {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("gymId", String.valueOf(gymId));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_GYM_DATA, params, Constants.CODE_POST_REQUEST, this, GET_GYM_DATA);
        request.execute();
        //    Log.e("getGyms","gyms");
    }

    private void setData(){
        gymDetailFragment.setText(gym);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec", "Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_GYM_DATA)) {
                try {
                    Log.e("Reciever", "tre");
                    String jsonstr = bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    JSONObject jsonObject = json.getJSONObject("gymData");
                    gym = new Gym(jsonObject);
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js", jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonObject: ", jsonObject.toString());
                    setData();
                    progressDialog.dismiss();
                    }
                    else {
                        progressDialog.dismiss();
                        noNetworkDialog(context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}


            }

        }
    };

    private void noNetworkDialog(final Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.NoNetworkConnectionDialogMessage)
                .setCancelable(false)
                .setTitle(R.string.NoNetworkConnectionDialogTitle)
                .setPositiveButton(R.string.InformationDialogPositiveButtonOk, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .create();

        AlertDialog dialog = builder.show();
    }
}
