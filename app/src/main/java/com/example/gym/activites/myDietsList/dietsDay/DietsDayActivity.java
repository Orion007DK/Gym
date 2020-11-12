package com.example.gym.activites.myDietsList.dietsDay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.Constants;
import com.example.gym.Meal;
import com.example.gym.R;

public class DietsDayActivity extends AppCompatActivity implements MealsListFragment.MealsListFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;

    int dietId;
    int dayOfWeekId;
    String dayOfWeekName;
    Meal currentMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diets_day);

        Bundle bundle = getIntent().getExtras();

        dietId=bundle.getInt(Constants.BUNDLE_DIET_ID);
        dayOfWeekId=bundle.getInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID);
        dayOfWeekName=bundle.getString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME);

        //Log.e("dietId: ", String.valueOf(bundle.getInt(Constants.BUNDLE_DIET_ID)));
        //Log.e("dayOfWeekId: ", String.valueOf(bundle.getInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID)));
        //Log.e("dayOfWeekName: ", bundle.getString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME));

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setMealsListFragment();
        } else {
           // setMealsListFragmentLandscape();
            //setDetailsFragmentLandscape();
            setFragmentLandscape();
        }
    }

    @Override
    public void onItemFromMealsListFragmentSelected(Meal meal) {
        MealDetailsFragment fragment = (MealDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.mealDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (isLand) {
            fragment.setData(meal);
            currentMeal=meal;
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((MealDetailsFragment) this.currentFragment).setData(meal);
            currentMeal=meal;

            // w trybie portrait wywołujemy drugą aktywność
        /*    Intent intent = new Intent(getApplicationContext(), TrainerDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            startActivity(intent);*/
        }
    }
    private void setMealsListFragmentLandscape() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MealsListFragment();//(dietId, dayOfWeekName, dayOfWeekId);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_DIET_ID, dietId);
        bundle.putString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME, dayOfWeekName);
        bundle.putInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID, dayOfWeekId);
        currentFragment.setArguments(bundle);
        ft.replace(R.id.mealsListFragment, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragmentLandscape() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MealDetailsFragment();

        ft.replace(R.id.mealDetailsFragment, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }


    private void setFragmentLandscape() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MealsListFragment();//(dietId, dayOfWeekName, dayOfWeekId);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_DIET_ID, dietId);
        bundle.putString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME, dayOfWeekName);
        bundle.putInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID, dayOfWeekId);
        currentFragment.setArguments(bundle);
        ft.replace(R.id.mealsListFragment, this.currentFragment);
        this.currentFragment = new MealDetailsFragment();
        ft.replace(R.id.mealDetailsFragment, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        //ft.addToBackStack(null);
        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }


    private void setMealsListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MealsListFragment();//(dietId, dayOfWeekName, dayOfWeekId);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_DIET_ID, dietId);
        bundle.putString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME, dayOfWeekName);
        bundle.putInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID, dayOfWeekId);
        currentFragment.setArguments(bundle);
        ft.replace(R.id.dietsDayFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new MealDetailsFragment();

        ft.replace(R.id.dietsDayFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }

}
