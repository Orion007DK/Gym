package com.example.gym.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Constants;
import com.example.gym.R;
import com.example.gym.activites.trainersList.TrainersListActivity;

public class OptionsMenuActivity extends AppCompatActivity {
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.optionLogout);
        String name=getSharedPreferences(Constants.USER_DATA,Context.MODE_PRIVATE).getString(Constants.USER_NAME, null);
        String surname=getSharedPreferences(Constants.USER_DATA,Context.MODE_PRIVATE).getString(Constants.USER_SURNAME, null);
        if(name!=null)
            menuItem.setTitle(menuItem.getTitle()+"("+name+" "+surname+")");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionHomePage:{
              //  Intent homePageIntent = new Intent(getApplicationContext(), HomePageActivity.class);
              //  startActivity(homePageIntent);
                goToHomePageActivity();
                return true;
            }
            case R.id.optionGym:{
                goToGymHomeActivity();
                return true;
            }
            case R.id.optionTrainingPlans:{
                //Toast.makeText(this, "próba nr 2",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.optionTrainer:{
                goToTrainerHomePageActivity();
                return true;
            }
            case R.id.optionClasses:{
               // Intent trainerIntent = new Intent(getApplicationContext(), activityTrainersListTest.class);
               // startActivity(trainerIntent);
                return true;
            }
            case R.id.optionDiets:{
               // Intent trainerIntent = new Intent(getApplicationContext(), activityTrainersListTest.class);
               // startActivity(trainerIntent);
                return true;
            }
            case R.id.optionDietician:{
                goToDieticianHomePageActivity();
                return true;
            }
            case R.id.optionMyProfile:{
                goToMyProfileActivity();
                return true;
            }

            case R.id.optionLogout:{
                SharedPreferences data = getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = data.edit();
                editor.clear();
                editor.commit();
                Intent nextActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(nextActivityIntent);
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    protected void goToHomePageActivity(){
        Intent homePageIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(homePageIntent);
    }

    protected void goToGymHomeActivity(){
        Intent gymIntent = new Intent(getApplicationContext(), GymHomePageActivity.class);
        startActivity(gymIntent);
    }



    protected void goToTrainerHomePageActivity(){
        Intent trainerIntent = new Intent(getApplicationContext(), TrainerHomePageActivity.class);
        startActivity(trainerIntent);
    }


    protected void goToDieticianHomePageActivity(){
        Intent dieticianIntent = new Intent(getApplicationContext(), DieticianHomePage.class);
        startActivity(dieticianIntent);
    }

    protected void goToMyProfileActivity(){
        Intent myProfileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
        startActivity(myProfileIntent);
    }
}
