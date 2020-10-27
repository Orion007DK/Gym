package com.example.gym.activites.dieticianList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.gym.R;

public class DieticianDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(!(bundle!=null && bundle.getBoolean("landscapeOn", false))) { //normalnie okno zamyka się w trybie landscape
            //i wyświetla razem z listą trenerów, ale jeśli przeglądamy bezpośrednio profil bez listy, to nie powinien się
            //zamknąć
            // jeżeli użytkownik będzie w orientacji landscape, należy zamknąć
            // aktywność
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_dietician_details);

        if (bundle != null) {
            String name = bundle.getString("name");
            String surname = bundle.getString("surname");
            DieticiansDetailFragment detailFragment = (DieticiansDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);

            // ustawiamy tekst fragmentu w tej aktywności
            detailFragment.setText(name,surname);
        }
}
}
