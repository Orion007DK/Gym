package com.example.gym.activites.availableDietsList;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Constants;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.myDietsList.DietDaysListActivity;
import com.example.gym.activites.myDietsList.MyDietsListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class AvailableDietsListAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private List<String> dietsListNames;
    private List<Integer> dietsListId;
    IntentFilter filter;
    private SpotsDialog progressDialog;

    final private static String SUBSCRIBE_DIET = "subscribeDiet";

    public AvailableDietsListAdapter(@NonNull AppCompatActivity context, @NonNull List<String> dietsListNames, List<Integer>dietsListId) {
        super(context, R.layout.diets_list_one_line, dietsListNames);
        this.context = context;
        this.dietsListNames = dietsListNames;
        this.dietsListId=dietsListId;
        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_DIET); //dodanie akcji od pobierania informacji o użytkownikach
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View line;
        TextView textViewDietName;
        //jeśli nie ma linii do przetworzenia(ponownego uzycia)
        if(convertView==null) {
            //tworzenie tzw. "pompki" do tworzenia listy ocen
            LayoutInflater inflater = context.getLayoutInflater();
            //tworzenie pojedyńczej linii na podstawie XML-a
            line = inflater.inflate(R.layout.available_diets_list_one_line, null);


        } else {
            //jeśli jest linia, to użycie jej
            line=convertView;
        }
        textViewDietName = line.findViewById(R.id.textViewDietName);
        ImageView addDiet = line.findViewById(R.id.imageViewAddDiet);
        textViewDietName.setText(dietsListNames.get(position));

        /*textViewDietName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dietsListNames.get(position),Toast.LENGTH_SHORT).show();
               // ((DieticiansListFragment) fragmentParrent).updateDetail(dieticianListNames.get(position),dieticianListSurnames.get(position));
                Intent dietDaysListActivityIntent = new Intent(getContext(), DietDaysListActivity.class);
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_NAME, dietsListNames.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_ID, dietsListId.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_SUB_STATUS, true);
                getContext().startActivity(dietDaysListActivityIntent);
            }
        });*/

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dietsListNames.get(position),Toast.LENGTH_SHORT).show();
                // ((DieticiansListFragment) fragmentParrent).updateDetail(dieticianListNames.get(position),dieticianListSurnames.get(position));
                Intent dietDaysListActivityIntent = new Intent(getContext(), DietDaysListActivity.class);
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_NAME, dietsListNames.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_ID, dietsListId.get(position));
                dietDaysListActivityIntent.putExtra(Constants.BUNDLE_DIET_SUB_STATUS, true);
                getContext().startActivity(dietDaysListActivityIntent);
            }
        });

        addDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeDialog(dietsListId.get(position));
            }
        });


        return line;
    }


    private void subscribeDialog(final int dietId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.AvailableDietsAddDietDialogMessage)
                .setCancelable(true)
                .setTitle(R.string.AvailableDietsAddDietDialogTitle)
                .setPositiveButton(R.string.DialogPositiveButtonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subscribeDiet(dietId);
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

    private void subscribeDiet(int dietId){
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);
        progressDialog.show();
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(getContext())));
        params.put("dietId", String.valueOf(dietId));
        PerformNetworkRequest request;
        request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_DIET, params, Constants.CODE_POST_REQUEST, getContext(), SUBSCRIBE_DIET);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), SUBSCRIBE_DIET)) {
                try {
                    Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    // JSONArray jsonArray = json.getJSONArray("dietsList");
                    //jsonArray.getJSONObject(1);
                    //for(int i=0;i<jsonArray.length();i++){
                    // JSONObject js = jsonArray.getJSONObject(i);
                    // dietNamesList.add(js.getString("name"));
                    //dietIdList.add(js.getInt("id"));

                    //dietNamesList.add((JSONObject)jsonArray.get(i).get("name"));
                    // dietNamesList.get(i);
                    //Log.e("diet:", jsonArray.get(i).toString());
                    //}
                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    //Log.e("jsonArray: ",jsonArray.toString());
                    //Log.e("gymsArrayList: ",trainersArrayList.toString());

                    // trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    //listView.setAdapter(trainersListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getContext().unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
                Intent myDietsListActivityIntent = new Intent(getContext(), MyDietsListActivity.class);
                getContext().startActivity(myDietsListActivityIntent);
            }
        }
    };

}
