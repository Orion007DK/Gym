package com.example.gym.activites.trainingPlansList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.R;
import com.example.gym.TrainingPlan;
import com.example.gym.activites.OptionsMenuActivity;

public class MyTrainingPlansListActivity extends OptionsMenuActivity implements MyTrainingPlansListFragment.MyTrainingPlansListFragmentActivityListener {

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
    public void onItemSelected(TrainingPlan trainingPlan) {
        TrainingPlanDetailsFragment fragment = (TrainingPlanDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.MyTrainingPlanDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(trainingPlan);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((TrainingPlanDetailsFragment) this.currentFragment).setText(trainingPlan);

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

    @Override
    protected void goToMyTrainingPlansListActivity() {
    }
}
