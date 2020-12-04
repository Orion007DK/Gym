package com.example.gym.activites.gymsList;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.gym.Address;
import com.example.gym.Constants;
import com.example.gym.Dimensions;
import com.example.gym.Gym;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.activites.dimensionHistory.DimensionsHistoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class GymsListFragment extends Fragment {

    protected ListView listView;
    GymsListAdapter gymsListAdapter;
    private ArrayList<String> gymsListNames = new ArrayList<String>();
    private ArrayList<Address> gymListAdresses = new ArrayList<Address>();
    private GymsListFragmentActivityListener listener;

    //private ArrayList<Gym> gymsList=new ArrayList<>();

    private ArrayList<Gym> gymsArrayList = new ArrayList<>();


    IntentFilter filter;
    private AppCompatActivity appContext;
    private Fragment fragment;
    private static final String GET_GYMS = "getGyms";
    private static final String GET_GYMS_BY_LOCALISATION = "getGymsByLocalisation";

    //ProgressDialog dialog;
    private SpotsDialog progressDialog;
    Button buttonFindGymUsingLocalisation;

    LocationManager locationManager;
    LocationListener locationListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gyms_list, container, false);
        listView = view.findViewById(R.id.listViewGyms);

        appContext = (AppCompatActivity) view.getContext();
        fragment = this;

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_GYMS); //dodanie akcji od pobierania informacji o użytkownikach
        filter.addAction(GET_GYMS_BY_LOCALISATION); //dodanie akcji od pobierania informacji o użytkownikach


        if (gymsArrayList.size() == 0) {//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            getGyms();
            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
            progressDialog.show();
        } else {
            gymsListAdapter = new GymsListAdapter(appContext, gymsArrayList, fragment);
            listView.setAdapter(gymsListAdapter);
        }
        //   gymsListAdapter = new GymsListAdapter((AppCompatActivity)view.getContext(), gymsList, this);
        //    listView.setAdapter(gymsListAdapter);
        buttonFindGymUsingLocalisation = view.findViewById(R.id.buttonFindGymByLocalisation);
        buttonFindGymUsingLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        location.getAccuracy();
                        getGymsByLocalisation(location.getLongitude(), location.getLatitude());
                        Log.e("cokolwiek", location.toString());
                   //     locationManager.removeUpdates(locationListener);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
                locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
               // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(appContext, permissions ,1 );
                    return;
                }
                boolean gps_enabled = false;
                boolean network_enabled = false;
                try {
                    gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch(Exception ex) {}

                try {
                    network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch(Exception ex) {}

                if(!gps_enabled && !network_enabled) {
                    // notify user
                    new AlertDialog.Builder(appContext)
                            .setMessage("Funkcja lokalizacji jest wyłączona")
                            .setPositiveButton("Przejdź do ustawień", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    appContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setNegativeButton("Anuluj",null)
                            .show();
                } else {
                    progressDialog = new SpotsDialog(appContext, R.style.Custom);
                    progressDialog.show();
                    locationManager.removeUpdates(locationListener);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }



            //   getGymsByLocalisation();

            }
        });
        landscapeConfiguration(view);

        return view;
    }

    public interface GymsListFragmentActivityListener{
        public void onItemSelected(Gym gym);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof GymsListFragmentActivityListener) {
            listener = (GymsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: GymsListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(Gym gym) {
        listener.onItemSelected(gym);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
            buttonFindGymUsingLocalisation.setPadding(0,0,0,0);
        }
    }

    private void getGyms(){
            getContext().registerReceiver(broadcastReceiver, filter);
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_GYMS,null, Constants.CODE_GET_REQUEST, getContext(), GET_GYMS);
            request.execute();
            Log.e("getGyms","gyms");
    }

    private void getGymsByLocalisation(double longitude, double latitude){
        appContext.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
       // params.put("longtitude", String.valueOf(50.9802563490014));
        params.put("longitude", String.valueOf(longitude));
        //params.put("latitude", String.valueOf(23.161349317333087));
        params.put("latitude", String.valueOf(latitude));
      //  locationManager.removeUpdates(locationListener);
        Log.e("Localisation", String.valueOf(longitude)+"/"+String.valueOf(latitude));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_GYMS_BY_LOCALISATION,params, Constants.CODE_POST_REQUEST, getContext(), GET_GYMS_BY_LOCALISATION);
        request.execute();
        Log.e("getGyms","gyms");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_GYMS)) {
                try {
                    //Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("gyms");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        Gym gym = new Gym(jsonArray.getJSONObject(i));
                        gymsArrayList.add(gym);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                   // Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    //Log.e("jsonArray: ",jsonArray.toString());
                    //Log.e("gymsArrayList: ",gymsArrayList.toString());

                    gymsListAdapter=new GymsListAdapter(appContext, gymsArrayList, fragment);
                    listView.setAdapter(gymsListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            } else if (Objects.equals(intent.getAction(), GET_GYMS_BY_LOCALISATION)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    JSONArray jsonArray = json.getJSONArray("gyms");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    //jsonArray.getJSONObject(1);
                    gymsArrayList.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        Log.e(jsonArray.getJSONObject(i).getString("gymName"), jsonArray.getJSONObject(i).getString("distance"));
                        Gym gym = new Gym(jsonArray.getJSONObject(i));
                        gymsArrayList.add(gym);
                    }
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    //Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    //Log.e("jsonArray: ",jsonArray.toString());
                    //Log.e("gymsArrayList: ",gymsArrayList.toString());

                    gymsListAdapter=new GymsListAdapter(appContext, gymsArrayList, fragment);
                    listView.setAdapter(gymsListAdapter);
                    listView.requestLayout();
                    locationManager.removeUpdates(locationListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };





    //metoda do usuniecia jak bedzie baza danych
 /*   private void gymListsInit(){

        gymsList.add(new Gym("Siłownia Rycha",new Address("Lublin", "Nadbystrzycka","80")));
        gymsList.add(new Gym("Siłka+", new Address("Lublin", "Poniatowskiego","3A")));
        gymsList.add(new Gym("Zwykła siłownia",new Address("Zamość", "Lubelska","25")));
        gymsList.add(new Gym("Silnia", new Address("Chełm", "Kwiatowa","17")));

    */


    /*    gymsListNames.add(0,"Siłownia Rycha");
        gymsListNames.add(1, "Siłka+");
        gymsListNames.add(2,"Zwykła siłownia");
        gymsListNames.add(3,"Silnia");

        gymListAdresses.add(0, new Address("Lublin", "Nadbystrzycka","80"));
        gymListAdresses.add(1, new Address("Lublin", "Poniatowskiego","3A"));
        gymListAdresses.add(2, new Address("Zamość", "Lubelska","25"));
        gymListAdresses.add(3, new Address("Chełm", "Kwiatowa","17"));*/
    }

