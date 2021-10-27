package com.example.gym.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Constants;
import com.example.gym.R;
import com.example.gym.activites.myClassesList.MyClassesListActivity;
import com.example.gym.activites.myDietsList.MyDietsListActivity;
import com.example.gym.activites.trainingPlansList.MyTrainingPlansListActivity;

public class OptionsMenuActivity extends AppCompatActivity {
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.optionLogout);
        String name=getSharedPreferences(Constants.SP_USER_DATA,Context.MODE_PRIVATE).getString(Constants.SP_USER_NAME, null);
        String surname=getSharedPreferences(Constants.SP_USER_DATA,Context.MODE_PRIVATE).getString(Constants.SP_USER_SURNAME, null);
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
                goToMyTrainingPlansListActivity();
                return true;
            }
            case R.id.optionTrainer:{
                goToTrainerHomePageActivity();
                return true;
            }
            case R.id.optionClasses:{
                goToMyClassesListActivity();
                return true;
            }
            case R.id.optionDiets:{
                goToMyDietsListActivity();
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
                clearUserData();
                clearDieticianData();
                clearTrainerData();
                Intent nextActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextActivityIntent);
                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void clearUserData(){
        SharedPreferences data = getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();
    }

    private void clearDieticianData(){
        SharedPreferences data = getSharedPreferences(Constants.SP_DIETICIAN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();
    }

    private void clearTrainerData(){
        SharedPreferences data = getSharedPreferences(Constants.SP_TRAINER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.commit();
    }


    protected void goToHomePageActivity(){
        Intent homePageIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(homePageIntent);
    }

    protected void goToGymHomeActivity(){
        Intent gymIntent = new Intent(getApplicationContext(), GymHomePageActivity.class);
        startActivity(gymIntent);
    }

    protected void goToMyClassesListActivity(){
        Intent classesIntent = new Intent(getApplicationContext(), MyClassesListActivity.class);
        startActivity(classesIntent);
    }

    protected void goToMyTrainingPlansListActivity(){
        Intent classesIntent = new Intent(getApplicationContext(), MyTrainingPlansListActivity.class);
        startActivity(classesIntent);
    }

    protected void goToMyDietsListActivity(){
        Intent dietsIntent = new Intent(getApplicationContext(), MyDietsListActivity.class);
        startActivity(dietsIntent);
    }


    protected void goToTrainerHomePageActivity(){
        Intent trainerIntent = new Intent(getApplicationContext(), TrainerHomePageActivity.class);
        startActivity(trainerIntent);
    }


    protected void goToDieticianHomePageActivity(){
        Intent dieticianIntent = new Intent(getApplicationContext(), DieticianHomePageActivity.class);
        startActivity(dieticianIntent);
    }

    protected void goToMyProfileActivity(){
        Intent myProfileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
        startActivity(myProfileIntent);
    }
}
