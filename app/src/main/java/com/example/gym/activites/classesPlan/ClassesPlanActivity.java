package com.example.gym.activites.classesPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gym.R;

import java.util.Calendar;
import java.util.Locale;

public class ClassesPlanActivity extends AppCompatActivity implements ChooseClassesDayFragment.ChooseClassesDayFragmentActivityListener {

    private boolean isLand = false;
    private Fragment currentFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        this.isLand = getResources().getBoolean(R.bool.isLand);
        if (!this.isLand) {
            setChooseClassesDayFragment();
        } else {
            //domyślnie wyświetla plan dzisiejszy
            Calendar cal = Calendar.getInstance();
            onItemSelected(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        }
    }
    @Override
    public void onItemSelected(int day, int month, int year) {
        ClassesDayDetailsFragment fragment = (ClassesDayDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.ClassesDayDetailsFragment);
        // jeżeli fragment istnieje w tej aktywności,
        // znaczy, że jesteśmy w trybie landscape

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String dayOfWeekName =calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(day, month, year);
        } else {
            // w trybie portrait podmieniamy fragmenty w kontenerze
            setDetailsFragment();
            // upewniamy się, że transakcja jest gotowa
            // i możemy korzystać z fragmentu
            getSupportFragmentManager().executePendingTransactions();
            // ustawiamy tekst fragmentu
            ((ClassesDayDetailsFragment) this.currentFragment).setText(day, month, year);

            // w trybie portrait wywołujemy drugą aktywność
        /*    Intent intent = new Intent(getApplicationContext(), TrainerDetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("surname", surname);
            startActivity(intent);*/
        }

    }

    private void setChooseClassesDayFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new ChooseClassesDayFragment();
        ft.replace(R.id.classesFragmentContainer, this.currentFragment);
        ft.commit();
    }

    private void setDetailsFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.currentFragment = new ClassesDayDetailsFragment();

        ft.replace(R.id.classesFragmentContainer, this.currentFragment);
        // dodajemy transakcję na stos
        // dzięki temu możemy wrócić przyciskiem BACK
        ft.addToBackStack(null);

        // zatwierdzamy transakcję
        ft.commit(); //commitAllowingStateLoss
    }


}
