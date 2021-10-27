package com.example.gym.activites.workoutsHistory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gym.dataClasses.FinishedTrainingPlan;
import com.example.gym.R;
import com.example.gym.activites.OptionsMenuActivity;

public class WorkoutsHistoryActivity extends OptionsMenuActivity implements WorkoutsHistoryListFragment.WorkoutHistoryListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_history);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setWorkoutsHistoryListFragment();
        }

    }

    @Override
    public void onItemSelected(FinishedTrainingPlan finishedTrainingPlan) {
        WorkoutsHistoryDetailFragment workoutsHistoryDetailFragment = (WorkoutsHistoryDetailFragment)getSupportFragmentManager().findFragmentById(R.id.workoutsHistoryDetailFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        //String stringDate= DateFormat.format("dd.MM.yyyy",date).toString();
        if(workoutsHistoryDetailFragment!=null && workoutsHistoryDetailFragment.isInLayout()){
            workoutsHistoryDetailFragment.setText(finishedTrainingPlan);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setWorkoutsHistoryDetailFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((WorkoutsHistoryDetailFragment) this.currentFragment).setText(finishedTrainingPlan);
        }
    }

     private void setWorkoutsHistoryListFragment(){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.currentFragment=new WorkoutsHistoryListFragment();
            fragmentTransaction.replace(R.id.workoutsHistoryListFragmentContainer,this.currentFragment);
            fragmentTransaction.commit();
        }

        private void setWorkoutsHistoryDetailFragment(){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.currentFragment=new WorkoutsHistoryDetailFragment();
            fragmentTransaction.replace(R.id.workoutsHistoryListFragmentContainer, this.currentFragment);
            // dodajemy transakcję na stos
            // dzięki temu możemy wrócić przyciskiem BACK
            fragmentTransaction.addToBackStack(null);
            // zatwierdzamy transakcję
            fragmentTransaction.commit();
        }

}
