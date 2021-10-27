package com.example.gym;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

    //the url where we need to send the request
    private String url;

    //the parameters
    private HashMap<String, String> params;

    //the request code to define whether it is a GET or POST
    private int requestCode;

    //context for sendBroadcast;\

    private Context context;

    private String action;

    //constructor to initialize values
    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode, Context context, String action) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.context=context;
        this.action=action;
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
        if(s!=null) {
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean(context.getString(R.string.requestError))) {
                    sendBroadcastJSON(object, action);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String error = Constants.NETWORK_ERROR;
            JSONObject object;
            try {
                object = new JSONObject(error);
                sendBroadcastJSON(object, action);
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

    private void sendBroadcastJSON(JSONObject object, String action){
        //utworzenie intencji i umieszczenie w niej danych
        Intent intent = new Intent(action);
        intent.putExtra("JSON",object.toString());
        //wysłanie komunikatu
        context.sendBroadcast(intent);

    }
}
