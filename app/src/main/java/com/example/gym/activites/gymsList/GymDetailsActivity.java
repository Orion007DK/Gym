package com.example.gym.activites.gymsList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gym.Address;
import com.example.gym.R;
import com.example.gym.activites.gymsList.GymDetailFragment;

public class GymDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_details);
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            String name=bundle.getString("name");
            Address address=bundle.getParcelable("address");
            //String address=bundle.getString("address");
            String aboutGym=bundle.getString("aboutGym");

            GymDetailFragment gymDetailFragment = (GymDetailFragment)getSupportFragmentManager().findFragmentById(R.id.gymDetailsFragment);
            gymDetailFragment.setText(name, address, aboutGym);
        }
    }
}
