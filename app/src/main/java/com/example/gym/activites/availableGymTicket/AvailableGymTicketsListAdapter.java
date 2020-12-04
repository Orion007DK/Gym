package com.example.gym.activites.availableGymTicket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.Ticket;

import java.util.List;

public class AvailableGymTicketsListAdapter extends ArrayAdapter<Ticket> {

        private AppCompatActivity context;
        private Fragment fragmentParrent; //fragment nadrzędny, do którego należy adapter
        private List<Ticket> ticketsList;


        public AvailableGymTicketsListAdapter(@NonNull AppCompatActivity context, @NonNull List<Ticket> ticketsList, Fragment fragment) {
                super(context, R.layout.available_gym_tickets_one_line_layout, ticketsList);
                this.context = context;
                this.ticketsList = ticketsList;
                this.fragmentParrent = fragment;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View line;
                TextView textViewTicketName;
                TextView textViewTicketDuration;
                TextView textViewTicketPrice;
                // TextView textViewSurname;
                //jeśli nie ma linii do przetworzenia(ponownego uzycia)
                if (convertView == null) {
                        //tworzenie tzw. "pompki" do tworzenia listy ocen
                        LayoutInflater inflater = context.getLayoutInflater();
                        //tworzenie pojedyńczej linii na podstawie XML-a
                        line = inflater.inflate(R.layout.available_gym_tickets_one_line_layout, null);
                } else {
                        //jeśli jest linia, to użycie jej
                        line = convertView;
                }


                textViewTicketName = line.findViewById(R.id.textViewTicketName);
                textViewTicketDuration = line.findViewById(R.id.textViewTicketDuration);
                textViewTicketPrice = line.findViewById(R.id.textViewTicketPrice);

                if(textViewTicketName!=null)
                        textViewTicketName.setText(ticketsList.get(position).getName());
                else{
                        TextView textViewTicketNameHeader=line.findViewById(R.id.textViewTicketNameHeader);
                        textViewTicketNameHeader.setText("Nazwa");
                }
                if(textViewTicketDuration!=null)
                        textViewTicketDuration.setText(ticketsList.get(position).getDuration()+" dni");
                else {
                        TextView textViewTicketDatesHeader=line.findViewById(R.id.textViewTicketDatesHeader);
                        textViewTicketDatesHeader.setText("Ważny");
                }
                if(textViewTicketPrice!=null)
                        textViewTicketPrice.setText(ticketsList.get(position).getPrice()+" zł");
                else {
                        TextView textViewTicketPriceHeader=line.findViewById(R.id.textViewTicketPriceHeader);
                        textViewTicketPriceHeader.setText("Cena");
                }




                line.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                Toast.makeText(context, trainersListNames.get(position)+" "+trainersListSurnames.get(position),Toast.LENGTH_SHORT).show();
                                ((AvailableGymTicketListFragment) fragmentParrent).updateDetail(ticketsList.get(position));
                        }
                });
                return line;
        }
}