package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainerDetailFragment;

public class TrainerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_trainer_detail);

        if (bundle != null) {
            String name = bundle.getString("name");
            String surname = bundle.getString("surname");
            TrainerDetailFragment detailFragment = (TrainerDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);

            // ustawiamy tekst fragmentu w tej aktywności
            detailFragment.setText(name,surname);
        }

    }
}
