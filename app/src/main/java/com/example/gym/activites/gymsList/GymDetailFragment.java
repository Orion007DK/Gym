package com.example.gym.activites.gymsList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.example.gym.Address;
import com.example.gym.R;

public class GymDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gym_details, container, false);

        landscapeConfiguration(view);
        return view;
    }

    public void setText(String textName, Address address, String aboutGym) {
        TextView textViewName = getView().findViewById(R.id.textViewGymNameValue);
        TextView textViewSurname = getView().findViewById(R.id.textViewGymAddressValue);
        TextView textViewAboutGym = getView().findViewById(R.id.textViewAboutGymValue);
        textViewName.setText(textName);
        textViewSurname.setText(address.getStringAddress());
        textViewAboutGym.setText(aboutGym);
    }

    private void landscapeConfiguration(View view) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView = view.findViewById(R.id.textViewAboutGym);
            textView.setPadding(0, 0, 0, 0);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutLabels);
           // linearLayoutCompat
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    4.7f
            );
            linearLayout.setLayoutParams(param);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5.6f
            );
            TextView textViewAboutGym = view.findViewById(R.id.textViewAboutGym);
            textViewAboutGym.setLayoutParams(param2);
            LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    3.0f
            );
            ScrollView scrollViewAboutGymValue=view.findViewById(R.id.scrollViewAboutGymValue);
            scrollViewAboutGymValue.setLayoutParams(param3);
        }
    }
}

