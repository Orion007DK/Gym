package com.example.gym.activites.dieticianList;

import android.content.Context;
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

public class DieticiansDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dietician_details, container, false);
        return view;
    }

    public void setText(String textName, String textSurname){
        TextView textViewName = getView().findViewById(R.id.textViewName);
        TextView textViewSurname = getView().findViewById(R.id.textViewSurname);
        textViewName.setText(textName);
        textViewSurname.setText(textSurname);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewAboutDietician);
            textView.setPadding(0,0,0,0);
        }
    }
}
