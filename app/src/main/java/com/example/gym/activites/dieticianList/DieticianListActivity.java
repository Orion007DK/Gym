package com.example.gym.activites.dieticianList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.gym.R;
import com.example.gym.activites.dimensionHistory.DimensionsHistoryDetailFragment;

public class DieticianListActivity extends AppCompatActivity implements DieticiansListFragment.DieticiansListFragmentActivityListener {

    private boolean isLand=false;
    private Fragment currentFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietician_list);
        // pobieramy informację o pozycji urządzenia
        this.isLand = getResources().getBoolean(R.bool.isLand);
        // w trybie portrait dodajemy do kontenera ListFragment
        if(!isLand)
            setDieticianListFragment();
    }

    @Override
    public void onItemSelected(String name, String surname) {
        DieticiansDetailFragment fragment = (DieticiansDetailFragment) getSupportFragmentManager().findFragmentById(R.id.DieticianDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(name, surname);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDieticianDetailFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((DieticiansDetailFragment)currentFragment).setText(name,surname);
        }
    }

    private void setDieticianListFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        currentFragment = new DieticiansListFragment();
        fragmentTransaction.replace(R.id.DieticianListFragmentContainer, currentFragment);
        fragmentTransaction.commit();
    }

    private void setDieticianDetailFragment(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        currentFragment=new DieticiansDetailFragment();
        //zamiana fragmentu z listą na fragment przedstawiający szczegółowe informacje
        fragmentTransaction.replace(R.id.DieticianListFragmentContainer, currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        fragmentTransaction.addToBackStack(null);
        // zatwierdzamy transakcję
        fragmentTransaction.commit();
    }
}
