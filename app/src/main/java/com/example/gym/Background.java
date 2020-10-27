package com.example.gym;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Background extends AsyncTask<String, Void, String> {

    AlertDialog dialog;
    Context context;
    String email;
    String password;
    public Background(Context context, EditText email, EditText password)
    {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.setMessage(s);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... voids) {
        String result ="";
        String email=voids[0];
        String password=voids[1];
        String userDB = "test";
        String passDB = "test";


        //String connstr="http://localhost:8080/login.php";
        String connstr="http://192.168.0.104/login.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, StandardCharsets.UTF_8));
            String data = URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(userDB,"UTF-8")
                    +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(passDB,"UTF-8")
                    +"&&"+URLEncoder.encode("query", "UTF-8")+"="+URLEncoder.encode("select user, password from users where id = 2 ", "UTF-8")
                    +"&&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")
                    +"&&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line = "";
           /* while ((line=reader.readLine())!=null)
            {
                result+=line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            Log.e("tak",result);*/
            String returnemail="";
            String returnpassword="";
           if((line=reader.readLine())!=null)
               returnemail = line;
            if((line=reader.readLine())!=null)
                returnpassword = line;
            reader.close();
            ips.close();
            http.disconnect();
//            editTextEmail.setText(email);
            //editTextPassword.setText(password);
            result=returnemail+" "+returnpassword;
            return result;

        } catch (MalformedURLException e) {
            result=e.getMessage();
        } catch (IOException e) {
            result=e.getMessage();
        }
        return result;
    }
}
