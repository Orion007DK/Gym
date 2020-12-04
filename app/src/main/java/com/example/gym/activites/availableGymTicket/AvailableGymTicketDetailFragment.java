package com.example.gym.activites.availableGymTicket;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.SharedPreferencesOperations;
import com.example.gym.Ticket;

import net.glxn.qrgen.android.QRCode;

public class AvailableGymTicketDetailFragment extends Fragment {



    // Ticket ticket=null;
   // IntentFilter filter;
    // Button buttonSign;
    // private final static String SUBSCRIBE_TRAINER="subscribeTrainer";
    // private final static String UNSUBSCRIBE_TRAINER="unsubscribeTrainer";
    Context context;
    private Button buttonQr;
    Ticket ticket;

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

        // buttonSign = view.findViewById(R.id.buttonSign);
        //if(getActivity().getClass().getSimpleName().equals(TrainerDetailActivity.class.getSimpleName())) {
        qrButtonInit();
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void qrButtonInit(){
        buttonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap myBitmap = QRCode.from("{\"ticketId\":" + ticket.getId()+","+"\"userId:\""+ SharedPreferencesOperations.getUserId(context)+"}").withSize(350, 350).bitmap();
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
        if(textViewTicketName!=null && ticket.getName()!=null)
            textViewTicketName.setText(ticket.getName());
        if(textViewTicketDurationValue!=null  && ticket.getDuration()!=null)
            textViewTicketDurationValue.setText(ticket.getDuration()+" dni");
        if(textViewTicketPriceValue!=null && ticket.getPrice()!=null)
            textViewTicketPriceValue.setText(ticket.getPrice());
        if(textViewTicketPossibilities!=null && ticket.getStringPossibilities()!=null)
            textViewTicketPossibilities.setText(ticket.getStringPossibilities());//trainer.getDescription()aboutTrainer
        Log.e("id",String.valueOf(ticket.getId()));
        this.ticket=ticket;
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
