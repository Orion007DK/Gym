package com.example.gym.activites.MyGymTickets;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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
import com.example.gym.GymWorker;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.Ticket;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Objects;

public class GymTicketDetailFragment extends Fragment{

   // Ticket ticket=null;
    //IntentFilter filter;
   // Button buttonSign;
   // private final static String SUBSCRIBE_TRAINER="subscribeTrainer";
   // private final static String UNSUBSCRIBE_TRAINER="unsubscribeTrainer";
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.gym_ticket_detail_fragment, container, false);

      /*  filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_TRAINER); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_TRAINER);
*/

           landscapeConfiguration(view); //nie działa, nie trzeba zmniejszać paddingów tylko rozmiar.

       // buttonSign = view.findViewById(R.id.buttonSign);
        //if(getActivity().getClass().getSimpleName().equals(TrainerDetailActivity.class.getSimpleName())) {


        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void setData(Ticket ticket){
        TextView textViewTicketName = getView().findViewById(R.id.textViewTicketName);
        TextView textViewTicketStartDate = getView().findViewById(R.id.textViewTicketStartDateValue);
        TextView textViewTicketEndDate = getView().findViewById(R.id.textViewTicketEndDateValue);
        TextView textViewTicketPossibilities = getView().findViewById(R.id.textViewTicketPossibilitiesValue);
        TextView textViewLeftEntries = getView().findViewById(R.id.textViewLeftEntriesValue);
        if(textViewTicketName!=null && ticket.getName()!=null)
            textViewTicketName.setText(ticket.getName());
        if(textViewTicketStartDate!=null  && ticket.getStringStartDate()!=null)
            textViewTicketStartDate.setText(ticket.getStringStartDate());
        if(textViewTicketEndDate!=null && ticket.getStringEndDate()!=null)
            textViewTicketEndDate.setText(ticket.getStringEndDate());
        if(textViewTicketPossibilities!=null && ticket.getStringPossibilities()!=null)
            textViewTicketPossibilities.setText(ticket.getStringPossibilities());//trainer.getDescription()aboutTrainer
        if(textViewLeftEntries!=null)
            textViewLeftEntries.setText(String.valueOf(ticket.getEntries()));
    }


    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTicketName);
            textView.setPadding(10,20,10,0);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
