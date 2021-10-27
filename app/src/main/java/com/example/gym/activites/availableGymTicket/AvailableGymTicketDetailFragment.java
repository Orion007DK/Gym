package com.example.gym.activites.availableGymTicket;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.dataClasses.Ticket;

import net.glxn.qrgen.android.QRCode;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AvailableGymTicketDetailFragment extends Fragment {



    // Ticket ticket=null;
   // IntentFilter filter;
    // Button buttonSign;
    // private final static String SUBSCRIBE_TRAINER="subscribeTrainer";
    // private final static String UNSUBSCRIBE_TRAINER="unsubscribeTrainer";
    Context context;
    private Button buttonQr;
    Ticket ticket;
    DatePickerDialog picker;
    TextView textViewStartDate;
    TextView textViewEndDate;
    Button buttonDateChange;
    final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    Date today;
    int dayOfMonth;
    int monthOfYear;
    int year;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.available_gym_ticket_detail_fragment, container, false);

      /*  filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(SUBSCRIBE_TRAINER); //dodanie akcji od zapisania do trenera
        filter.addAction(UNSUBSCRIBE_TRAINER);
*/
        buttonQr=view.findViewById(R.id.buttonQR);
        landscapeConfiguration(view); //nie działa, nie trzeba zmniejszać paddingów tylko rozmiar.
        buttonDateChange=view.findViewById(R.id.buttonChangeDate);
        textViewStartDate=view.findViewById(R.id.textViewTicketStartDateValue);
        textViewEndDate =view.findViewById(R.id.textViewTicketEndDateValue);
        final Calendar calToday = Calendar.getInstance();
        today = new Date();
        Log.e("day", String.valueOf(calToday.get(Calendar.DAY_OF_MONTH)));
       // Log.e("dat",today.toString());
        setSelectedDate(calToday.get(Calendar.DAY_OF_MONTH), calToday.get(Calendar.MONTH), calToday.get(Calendar.YEAR));
        buttonDateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
               // final int day = calendar.get(Calendar.DAY_OF_MONTH);
               // int month = calendar.get(Calendar.MONTH);
              //  int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(context, R.style.ColorPickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date selectedDate = new Date(year-1900, monthOfYear, dayOfMonth);
                                //format.format(date)
                                textViewStartDate.setText(format.format(selectedDate));
                                Log.e("duration:",ticket.getDuration());
                                textViewEndDate.setText(format.format(new Date(selectedDate.getTime()+(Long.valueOf(ticket.getDuration())*86400000))));
                                setSelectedDate(dayOfMonth, monthOfYear, year);
                        //        editTextEndDate.setText();
                        }
                        }, year, monthOfYear, dayOfMonth);
                picker.getDatePicker().setMinDate(calToday.getTimeInMillis());
               // picker.getDatePicker().updateDate();
                picker.show();

            }
        });
        // buttonSign = view.findViewById(R.id.buttonSign);
        //if(getActivity().getClass().getSimpleName().equals(TrainerDetailActivity.class.getSimpleName())) {
        qrButtonInit();
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void setSelectedDate(int dayOfMonth, int monthOfYear, int year){
        this.year=year;
        this.monthOfYear=monthOfYear;
        this.dayOfMonth=dayOfMonth;
        Log.e("Date", String.valueOf(dayOfMonth)+"."+monthOfYear+"."+year);
    }

    private void qrButtonInit(){
        buttonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("userId", String.valueOf(SharedPreferencesOperations.getUserId(context)));
                params.put("ticketId",String.valueOf(ticket.getId()));
                params.put("startDay",String.valueOf(dayOfMonth));
                params.put("startMonth", String.valueOf(monthOfYear));
                params.put("startYear", String.valueOf(year));
                params.put("duration",ticket.getDuration());
                params.put("entries",String.valueOf(ticket.getEntries()));
                params.put("user", SharedPreferencesOperations.getUserName(context));
                params.put("ticketName", ticket.getName());

                JSONObject jsonObjectParams = new JSONObject(params);
                Bitmap myBitmap = QRCode.from(jsonObjectParams.toString()).withSize(350, 350).withCharset("UTF-8").bitmap();
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

    public void setData(Ticket ticket){
        TextView textViewTicketName = getView().findViewById(R.id.textViewTicketName);
        TextView textViewTicketDurationValue = getView().findViewById(R.id.textViewTicketDurationValue);
        TextView textViewTicketPriceValue = getView().findViewById(R.id.textViewTicketPriceValue);
        TextView textViewTicketPossibilities = getView().findViewById(R.id.textViewTicketPossibilitiesValue);
        TextView textViewTicketEntries = getView().findViewById(R.id.textViewTicketRemainingEntrieValue);

        if(textViewTicketName!=null && ticket.getName()!=null)
            textViewTicketName.setText(ticket.getName());
        if(textViewTicketDurationValue!=null  && ticket.getDuration()!=null)
            textViewTicketDurationValue.setText(ticket.getDuration()+" dni");
        if(textViewTicketPriceValue!=null && ticket.getPrice()!=null)
            textViewTicketPriceValue.setText(ticket.getPrice());
        if(textViewTicketPossibilities!=null && ticket.getStringPossibilities()!=null)
            textViewTicketPossibilities.setText(ticket.getStringPossibilities());//trainer.getDescription()aboutTrainer
        if(textViewTicketEntries!=null && ticket.getEntries()!=-1)
            textViewTicketEntries.setText(String.valueOf(ticket.getEntries()));
        else
            textViewTicketEntries.setText(R.string.AvailableTicketUnlimitedEntries);
        Log.e("id",String.valueOf(ticket.getId()));
        this.ticket=ticket;
        textViewStartDate.setText(format.format(today));
        textViewEndDate.setText(format.format(new Date(today.getTime()+(Long.valueOf(ticket.getDuration())*86400000))));
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
