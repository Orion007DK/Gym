package com.example.gym.Niepotrzebne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.R;
import com.example.gym.activites.dieticianList.DieticiansDetailFragment;
/*
public class Diet_activity extends AppCompatActivity implements OneDayOfDietFragment.OneDayOfDietFragmentActivityListener, DietDayListFragment.DietDayListFragmentActivityListener {

    private boolean isLand=false;
    private Fragment firstFragment=null;
    private Fragment secondFragment=null;
    private Fragment currentFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_activity);
        // pobieramy informację o pozycji urządzenia
        this.isLand = getResources().getBoolean(R.bool.isLand);
        // w trybie portrait dodajemy do kontenera ListFragment
       // if(!isLand)
            setDietDayListFragment();

    }

    public void onItemOfOneDayOfDietFragmentSelected(String title, String surname) {
        if(isLand)
        {
            setMealDetailsFragment();
            ((MealDetailsFragment)firstFragment).setText(title);
        }


     /*   DieticiansDetailFragment fragment = (DieticiansDetailFragment) getSupportFragmentManager().findFragmentById(R.id.DieticianDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(title, surname);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
           // setDieticianDetailFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((DieticiansDetailFragment)firstFragment).setText(title,surname);
        }
    }

    @Override
    public void onDietDayItemSelected(String dietName, String dayName) {
        DieticiansDetailFragment fragment = (DieticiansDetailFragment) getSupportFragmentManager().findFragmentById(R.id.DieticianDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(dietName, dayName);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            // setDieticianDetailFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((DieticiansDetailFragment)firstFragment).setText(dietName,dayName);
        }
    }



    private void setDietDayListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(isLand) {
            this.firstFragment = new DietDayListFragment();
            ft.replace(R.id.DietFragmentFirstContainer, this.firstFragment);
            ft.commit();
        }
        else
        {
            this.currentFragment=new DietDayListFragment();
            ft.replace(R.id.DietFragmentContainer,this.currentFragment);
            ft.commit();
        }
    }
    private void setOneDayOfDietFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(isLand){
            this.secondFragment=new OneDayOfDietFragment();
            ft.replace(R.id.DietFragmentSecondContainer, this.secondFragment);
            ft.commit();
        }
        else
        {
            this.currentFragment = new OneDayOfDietFragment();
            ft.replace(R.id.DietFragmentContainer, this.currentFragment);
            // dodajemy transakcję na stos
            // dzięki temu możemy wrócić przyciskiem BACK
            ft.addToBackStack(null);
            // zatwierdzamy transakcję
            ft.commit(); //commitAllowingStateLoss
        }
    }

    private void setMealDetailsFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(isLand){
            this.firstFragment=new MealDetailsFragment();
            ft.replace(R.id.DietFragmentFirstContainer, this.firstFragment);
            ft.commit();
        }
        else
        {
            this.currentFragment = new MealDetailsFragment();
            ft.replace(R.id.DietFragmentContainer, this.currentFragment);
            // dodajemy transakcję na stos
            // dzięki temu możemy wrócić przyciskiem BACK
            ft.addToBackStack(null);
            // zatwierdzamy transakcję
            ft.commit(); //commitAllowingStateLoss
        }

    }

}
*/