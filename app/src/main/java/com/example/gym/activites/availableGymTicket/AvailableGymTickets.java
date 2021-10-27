package com.example.gym.activites.availableGymTicket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.R;
import com.example.gym.dataClasses.Ticket;

public class AvailableGymTickets extends AppCompatActivity implements AvailableGymTicketListFragment.AvailableGymTicketsListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_gym_tickets);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setTicketsListFragment();
        }
    }




    @Override
    public void onItemSelected(Ticket ticket) {
        AvailableGymTicketDetailFragment fragment = (AvailableGymTicketDetailFragment) getSupportFragmentManager().findFragmentById(R.id.AvailableTicketDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setData(ticket);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((AvailableGymTicketDetailFragment) this.currentFragment).setData(ticket);

            // w trybie portrait wywołujemy drugą aktywność
        /*    Intent intent = new Intent(getApplicationContext(), TrainerDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            startActivity(intent);*/
        }
    }

    private void setTicketsListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new AvailableGymTicketListFragment();
        ft.replace(R.id.AvailableGymTicketsFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new AvailableGymTicketDetailFragment();

        ft.replace(R.id.AvailableGymTicketsFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
