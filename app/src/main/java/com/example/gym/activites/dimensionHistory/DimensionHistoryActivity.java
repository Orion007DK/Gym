package com.example.gym.activites.dimensionHistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gym.dataClasses.Dimensions;
import com.example.gym.R;
import com.example.gym.activites.OptionsMenuActivity;

public class DimensionHistoryActivity extends OptionsMenuActivity implements DimensionHistoryListFragment.DimensionsHistoryListFragmentActivityListener {

    private boolean isLand=false;
    private Fragment currentFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_history);
   //     DimensionsHistoryDetailFragment dimensionsHistoryDetailFragment = (DimensionsHistoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.dimensionHistoryDetailActivityFragment);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setDimensionHistoryListFragment();
        }

    }


    @Override
    public void onItemSelected(Dimensions dimensions) {
        DimensionsHistoryDetailFragment dimensionsHistoryDetailFragment = (DimensionsHistoryDetailFragment)getSupportFragmentManager().findFragmentById(R.id.dimensionsHistoryDetailFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        //String stringDate= DateFormat.format("dd.MM.yyyy",dimensions.getDate()).toString();
        if(dimensionsHistoryDetailFragment!=null && dimensionsHistoryDetailFragment.isInLayout()){
            dimensionsHistoryDetailFragment.setText(dimensions);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDimensionHistoryDetailFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((DimensionsHistoryDetailFragment)this.currentFragment).setText(dimensions);
        }
    }

    private void setDimensionHistoryListFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.currentFragment=new DimensionHistoryListFragment();
        fragmentTransaction.replace(R.id.dimensionHistoryListFragmentContainer, this.currentFragment);
        // zatwierdzamy transakcję
        fragmentTransaction.commit();
    }

    private void setDimensionHistoryDetailFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.currentFragment=new DimensionsHistoryDetailFragment();
        fragmentTransaction.replace(R.id.dimensionHistoryListFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        fragmentTransaction.addToBackStack(null);
        // zatwierdzamy transakcję
        fragmentTransaction.commit();
    }
}
