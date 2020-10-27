package com.example.gym.activites.trainingPlansList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.R;

public class MyTrainingPlansListActivity extends AppCompatActivity implements MyTrainingPlansListFragment.MyTrainingPlansListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_training_plans_list);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setTrainingPlansListFragment();
        }

    }

    @Override
    public void onItemSelected(String name, String estimatedDuration, String burnedCalories, String completedTrainings, String trainingPlanDescription) {
        TrainingPlanDetailsFragment fragment = (TrainingPlanDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.MyTrainingPlanDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(name,estimatedDuration, burnedCalories, completedTrainings, trainingPlanDescription);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((TrainingPlanDetailsFragment) this.currentFragment).setText(name,estimatedDuration, burnedCalories, completedTrainings, trainingPlanDescription);

        }
    }


    private void setTrainingPlansListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MyTrainingPlansListFragment();
        ft.replace(R.id.MyTrainingPlansFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new TrainingPlanDetailsFragment();

        ft.replace(R.id.MyTrainingPlansFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }
}
