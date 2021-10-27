package com.example.gym.dataClasses;

import android.text.format.DateFormat;
import android.util.Log;

import com.example.gym.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Ticket {

    int id;
    String name;
    String description;
    String[] possibilitiesNames;
    String[] possibilities;
    String duration;
    Date startDate;
    Date endDate;
    String price;
    int entries;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPossibilitiesNames() {
        return possibilitiesNames;
    }

    public String[] getPossibilities() {
        return possibilities;
    }

    public String getDuration() {
        return duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getPrice() {
        return price;
    }

    public int getEntries() {
        return entries;
    }

    public String getStringStartDate(){
        String stringDate = DateFormat.format(Constants.DATA_FORMAT, startDate).toString();
        return stringDate;
    }

    public Ticket (JSONObject jsonObjectTicket) throws JSONException, ParseException {
        Log.e("jsTICKET:", jsonObjectTicket.toString());
        if(!jsonObjectTicket.isNull("ticketId"))
            this.id=jsonObjectTicket.getInt("ticketId");
        this.name=jsonObjectTicket.getString("ticketName");
        if(!jsonObjectTicket.isNull("dateStart"))
             this.startDate= new SimpleDateFormat("yyyy-MM-dd").parse(jsonObjectTicket.getString("dateStart"));
        if(!jsonObjectTicket.isNull("dateEnd"))
            this.endDate=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObjectTicket.getString("dateEnd"));
        if(!jsonObjectTicket.isNull("entries"))
            this.entries=jsonObjectTicket.getInt("entries");
        else
            entries=-1;
        if(!jsonObjectTicket.isNull("duration"))
            this.duration=jsonObjectTicket.getString("duration");
        if(!jsonObjectTicket.isNull("price"))
            this.price=jsonObjectTicket.getString("price");
        JSONArray jsonPossibilities = jsonObjectTicket.getJSONArray("possibilities");
        possibilitiesNames=new String[jsonPossibilities.length()];
        possibilities=new String[jsonPossibilities.length()];
        for(int j=0;j<jsonPossibilities.length();j++){
            JSONObject jsPossibility = jsonPossibilities.getJSONObject(j);
            possibilitiesNames[j]=jsPossibility.getString("possibilityName");
            Log.e("name:", jsPossibility.getString("possibilityName"));
            possibilities[j]=jsPossibility.getString("possibilityDescription");
            Log.e("description:", jsPossibility.getString("possibilityDescription"));
        }
    }

    public Ticket(int id, String name, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Ticket(int id, String name, String duration, String price) {
        this.id=id;
        this.name = name;
        this.duration = duration;
        this.price=price;
    }

    public String getStringEndDate(){
        String stringDate = DateFormat.format(Constants.DATA_FORMAT, endDate).toString();
        return stringDate;
    }

    public String getStringPossibilities(){
        String text="";
        if(possibilities!=null)
        for(int i=0;i<possibilities.length;i++)
        {
            if(possibilitiesNames[i]!=null)
               text+="-->"+possibilitiesNames[i]+" - ";
            if(possibilities[i]!=null)
               text+=possibilities[i];
            text+="\n";
        }
        return text;
    }


}


