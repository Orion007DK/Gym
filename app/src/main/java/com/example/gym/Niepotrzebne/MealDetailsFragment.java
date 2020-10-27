package com.example.gym.Niepotrzebne;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

public class MealDetailsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meal_details_fragment, container, false);

        

        //   landscapeConfiguration(view); nie działa, nie trzeba zmniejszać paddingów tylko rozmiar.
        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }



    public void setText(String title){
        TextView textViewTitle = getView().findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewAboutTrainer);
            textView.setPadding(0,0,0,0);
        }
    }
}
