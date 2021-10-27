package com.example.gym.activites.dimensionHistory;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gym.dataClasses.Dimensions;
import com.example.gym.R;

public class DimensionsHistoryDetailFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dimensions_history_details, container, false);
        landscapeConfiguration(view);
        return  view;
    }

    public void setText(Dimensions dimensions){
        TextView textViewHeighValue = getView().findViewById(R.id.textViewHeightValue);
        TextView textViewWeightValue= getView().findViewById(R.id.textViewWeightValue);
        TextView textViewAdiposeTissueValue = getView().findViewById(R.id.textViewAdiposeTissueValue);
        TextView textViewMuscleTissueValue= getView().findViewById(R.id.textViewMuscleTissueValue);
        TextView textViewBodyWaterPercentageValue = getView().findViewById(R.id.textViewBodyWaterPercentageValue);
        TextView textViewDate = getView().findViewById(R.id.textViewDateValue);
        textViewHeighValue.setText(dimensions.getHeightWithoutNull());
        textViewWeightValue.setText(dimensions.getWeightWithoutNull());
        textViewAdiposeTissueValue.setText(dimensions.getAdiposeTissueWithoutNull());
        textViewMuscleTissueValue.setText(dimensions.getMuscleTissueWithoutNull());
        textViewBodyWaterPercentageValue.setText(dimensions.getBodyWaterPercentageWithoutNull());
        textViewDate.setText(dimensions.getStringDate());
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewTitle.setPadding(10, 20, 10, 20);
        }
    }
}
