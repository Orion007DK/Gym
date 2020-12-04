package com.example.gym.activites.gymsList;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.gym.Constants;
import com.example.gym.Gym;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.RequestHandler;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.activites.MapsActivity;
import com.example.gym.activites.trainersList.TrainerDetailFragment;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class GymDetailFragment extends Fragment {


    CarouselView carouselView;

    Gym gym=null;
    IntentFilter filter;
    Button buttonSign;
    ImageView imageViewMap;

    private final static String SUBSCRIBE_GYM ="subscribeGym";
    private final static String UNSUBSCRIBE_GYM ="unsubscribeGym";
    Context context;

   // String bitmapImages[];
    Bitmap bitmapImages[];

/*
    @SuppressLint("StaticFieldLeak")
    private AsyncTask loadImages = new AsyncTask() {
        @SuppressLint("WrongThread")
        @Override
        protected Object doInBackground(Object[] objects) {

            final int[] sampleImages = {
                    R.drawable.gym1,
                    R.drawable.gym2,
                    R.drawable.gym3,
                    R.drawable.gym4,
                    R.drawable.gym5
            };
            carouselView.setPageCount(sampleImages.length);
            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    imageView.setImageResource(sampleImages[position]);

                }
            };
            carouselView.setImageListener(imageListener);
            return null;
        }
    };*/

ImageListener imageListener;
    final int[] sampleImages = {
            R.drawable.gym1,
            R.drawable.gym2,
            R.drawable.gym3,
            R.drawable.gym4,
            R.drawable.gym5
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gym_details, container, false);

       carouselView = (CarouselView) view.findViewById(R.id.carouselView);
       //loadImages.execute((Object) null);
        /*
        carouselView.setPageCount(sampleImages.length);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);

            }
        };
        carouselView.setImageListener(imageListener);*/
      //  carouselView.setPageCount(sampleImages.length);

       // carouselView.setImageListener(imageListener);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_GYM); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_GYM);

        buttonSign = view.findViewById(R.id.buttonSign);
        imageViewMap = view.findViewById(R.id.imageViewMap);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(context, MapsActivity.class);
                Log.e(Constants.BUNDLE_MAPS_LONGITUDE, String.valueOf(gym.getLongitude()));
                Log.e(Constants.BUNDLE_MAPS_LATITUDE, String.valueOf(gym.getLatitude()));
                mapsIntent.putExtra(Constants.BUNDLE_MAPS_LONGITUDE, gym.getLongitude());
                mapsIntent.putExtra(Constants.BUNDLE_MAPS_LATITUDE, gym.getLatitude());
                startActivity(mapsIntent);
            }
        });


        landscapeConfiguration(view);
        return view;
    };


  //  private ImageListener imageListener;

   /*
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };*/
   private void subscribeGym(){
       //progressDialog = new SpotsDialog(this, R.style.Custom);
       //progressDialog.show();

       context.registerReceiver(broadcastReceiver, filter);
       SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
       int id = data.getInt(Constants.SP_USER_ID, -1);
       if(id!=-1) {
           HashMap<String, String> params = new HashMap<>();
           params.put("gymId", String.valueOf(gym.getGymId()));
           params.put("userId", String.valueOf(id));
           Log.e("Params: ",params.toString());
           PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_SUBSCRIBE_GYM, params, Constants.CODE_POST_REQUEST, context, SUBSCRIBE_GYM);
           request.execute();
       }
   }

    private void unsubscribeGym(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        int id = data.getInt(Constants.SP_USER_ID, -1);
        if(id!=-1) {
            Log.e("userid: ", String.valueOf(id));
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(id));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_UNSUBSCRIBE_GYM, params, Constants.CODE_POST_REQUEST, context, UNSUBSCRIBE_GYM);
            request.execute();
        } else {
            informationConfirmDialog("Błąd", "Wystąpił bład, wyloguj się i zaloguj, a następnie spróbuj ponownie");
        }
    }

    private void getImage(){
        //progressDialog = new SpotsDialog(this, R.style.Custom);
        //progressDialog.show();

        context.registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("gymId", String.valueOf(gym.getGymId()));
        GymDetailFragment.PerformNetworkRequestForImage request = new GymDetailFragment.PerformNetworkRequestForImage(Constants.URL_GET_GYM_IMAGES, params, Constants.CODE_POST_REQUEST, context);
        request.execute();
    }

    private static String loremIpsum="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fringilla rutrum neque sit amet viverra. Integer neque purus, tristique eget ante ac, pharetra rhoncus felis. Vestibulum lacinia tellus condimentum lobortis congue. In hac habitasse platea dictumst. Nullam est nulla, cursus eget pretium sed, congue non ante. Vivamus ac ligula et nisl varius vestibulum. Vestibulum vitae tellus vitae ligula ultricies blandit vitae at ligula. Integer faucibus fringilla eleifend. Morbi vehicula aliquet consectetur. ";


    public void setText(Gym gym) {
        TextView textViewName = getView().findViewById(R.id.textViewGymNameValue);
        TextView textViewSurname = getView().findViewById(R.id.textViewGymAddressValue);
        TextView textViewAboutGym = getView().findViewById(R.id.textViewAboutGymValue);
        TextView textViewEmail = getView().findViewById(R.id.textViewGymEmailValue);
        TextView textViewPhoneNumber = getView().findViewById(R.id.textViewGymPhoneNumberValue);
        textViewName.setText(gym.getName());
        textViewSurname.setText(gym.getStringAddress());
        textViewAboutGym.setText(gym.getDescription());
        textViewEmail.setText(gym.getEmail());
        textViewPhoneNumber.setText(gym.getPhoneNumber());
        Log.e("gym", String.valueOf(gym.getLatitude()));
        this.gym=gym;
        buttonSignInit();
        getImage();
    }

    private void landscapeConfiguration(View view) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView = view.findViewById(R.id.textViewAboutGym);
            textView.setPadding(0, 0, 0, 0);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutLabels);
           // linearLayoutCompat
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    4.7f
            );
            linearLayout.setLayoutParams(param);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5.6f
            );
            TextView textViewAboutGym = view.findViewById(R.id.textViewAboutGym);
            textViewAboutGym.setLayoutParams(param2);
            LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3.0f
            );
           // ScrollView scrollViewAboutGymValue=view.findViewById(R.id.scrollViewAboutGymValue);
            //scrollViewAboutGymValue.setLayoutParams(param3);
        }
    }

    private void buttonSignInit() {
        super.onResume();
        SharedPreferences data = context.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        final int idGym = data.getInt(Constants.SP_GYM_ID, -1);
        Log.e("buttonSignInit idGym:", String.valueOf(idGym));
        if(idGym==gym.getGymId()) {
            Log.e("buttonSignInit: ", "Wypisz");
            buttonSign.setText("Wypisz się");
            buttonSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unsubscribeDialog();
                }
            });
        }
        else {
            Log.e("buttonSignInit: ", "Zapisz");
            buttonSign.setText("Zapisz się");
            buttonSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscribeDialog(idGym);
                }
            });
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
            if (Objects.equals(intent.getAction(), SUBSCRIBE_GYM)) {
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
                        editor.putInt(Constants.SP_GYM_ID, gym.getGymId());
                        editor.apply();
                        SharedPreferencesOperations.removeTrainerId(context);
                        SharedPreferencesOperations.removeDieteticianId(context);
                        SharedPreferencesOperations.clearTrainerData(context);
                        SharedPreferencesOperations.clearDieticianData(context);
                        informationConfirmDialog("Zapisano!","Pomyślnie zapisałeś się do siłowni!");
                    }
                    else {
                        informationConfirmDialog("Nieudane zapisanie", "Niesety wystąpiły pewne problemy przy zapisywaniu Cie do siłowni, spróbuj ponownie poźniej");
                    }
                    //editor.putString(Constants.SP_USER_SURNAME, userJson.getString("surname"));
                    //Log.e("gymId", String.valueOf(userJson.getInt("gymId")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.unregisterReceiver(broadcastReceiver);
            } else if(Objects.equals(intent.getAction(), UNSUBSCRIBE_GYM)){
                String jsonstr =bundle.getString("JSON");
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonstr);
                    Boolean isSubscribed = json.getBoolean("unsubscribed");
                    if(isSubscribed) {
                        informationConfirmDialog("Wypisany", "Zostałeś wypisany ze swojej siłowni");
                        SharedPreferencesOperations.removeTrainerId(context);
                        SharedPreferencesOperations.removeDieteticianId(context);
                        SharedPreferencesOperations.clearTrainerData(context);
                        SharedPreferencesOperations.clearDieticianData(context);
                        SharedPreferencesOperations.clearGymData(context);
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
            message="Czy na pewno chcesz zapisać się do tej siłowni?";
        } else {
            message="Jesteś już zapisany do innej siłowni! Czy na pewno chcesz zapisać się do tej siłowni? Będzie to skutkowało wypisaniem z obecnej siłowni, oraz wypisaniem od trenera i dietetyka";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setTitle("Potwierdzenie chęci zapisania")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        subscribeGym();

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
        builder.setMessage("Czy na pewno chcesz się wypisać z danej siłowni? Poskutkuje to również wypisaniem od trenera i dietetyka")
                .setCancelable(true)
                .setTitle("Potwierdzenie chęci wypisania")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unsubscribeGym();

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
                    JSONArray jsonArrayGymImages = object.getJSONArray("gymImages");
                    bitmapImages = new Bitmap[jsonArrayGymImages.length()];
                    for(int i=0;i<jsonArrayGymImages.length();i++){
                        JSONObject jsonObjectImage = jsonArrayGymImages.getJSONObject(i);
                        byte[] decodedString = Base64.decode(jsonObjectImage.getString("image"), Base64.DEFAULT);
                        bitmapImages[i]=BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    }

                    if(jsonArrayGymImages.length()!=0){

                        imageListener = new ImageListener() {
                            @Override
                            public void setImageForPosition(int position, ImageView imageView) {
                                imageView.setImageBitmap(bitmapImages[position]);
                            }
                        };
                        carouselView.setImageListener(imageListener);
                        carouselView.setPageCount(bitmapImages.length);
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

