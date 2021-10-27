package com.example.gym.activites.gymsList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.dataClasses.Gym;
import com.example.gym.R;

public class GymsListActivity extends AppCompatActivity implements GymsListFragment.GymsListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyms_list);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setGymsListFragment();
        }
    }


    @Override
    public void onItemSelected(Gym gym) {
        GymDetailFragment fragment = (GymDetailFragment) getSupportFragmentManager().findFragmentById(R.id.GymsDetailFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(gym);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((GymDetailFragment) this.currentFragment).setText(gym);
        /*    Intent intent = new Intent(getApplicationContext(), TrainerDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            startActivity(intent);*/
        }
    }


    private void setGymsListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new GymsListFragment();
        ft.replace(R.id.GymListFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new GymDetailFragment();

        ft.replace(R.id.GymListFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }

}