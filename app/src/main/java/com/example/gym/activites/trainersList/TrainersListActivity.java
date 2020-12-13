package com.example.gym.activites.trainersList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.GymWorker;
import com.example.gym.R;

public class TrainersListActivity extends AppCompatActivity implements TrainersListFragment.TrainersListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_list_test);



        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setTrainersListFragment();
        }
    }

    @Override
    public void onItemSelected(GymWorker trainer) {
        TrainerDetailFragment fragment = (TrainerDetailFragment) getSupportFragmentManager().findFragmentById(R.id.TrainersDetailFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setData(trainer);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((TrainerDetailFragment) this.currentFragment).setData(trainer);

            // w trybie portrait wywołujemy drugą aktywność
        /*    Intent intent = new Intent(getApplicationContext(), TrainerDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            startActivity(intent);*/
        }
    }


    private void setTrainersListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new TrainersListFragment();
        ft.replace(R.id.TrainersListFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new TrainerDetailFragment();

        ft.replace(R.id.TrainersListFragmentContainer, this.currentFragment);
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