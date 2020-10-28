package com.example.gym.activites.trainersList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.rishabhharit.roundedimageview.RoundedImageView;

public class TrainerDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainer_details, container, false);

     //   landscapeConfiguration(view); nie działa, nie trzeba zmniejszać paddingów tylko rozmiar.


        return  view;

        //return super.onCreateView(inflater, container, savedInstanceState);

    }
/*
    @Override
    public void onResume() {
        RoundedImageView roundedImageView = getView().findViewById(R.id.roundedViewTrainer);
       // roundedImageView.measure(null, null);
        ViewGroup.LayoutParams layoutParams = roundedImageView.getLayoutParams();
        Log.e("tal","sda");
        int height=layoutParams.height;
        // layoutParams.width=height;
        roundedImageView.getViewTreeObserver().removeOnPreDrawListener(null);
        Log.e("aa",String.valueOf(roundedImageView.getMeasuredHeight()));
        Log.e("sv",String.valueOf(roundedImageView.getMeasuredWidth()));
       // roundedImageView.setLayoutParams(new LinearLayout.LayoutParams(height, height));
        roundedImageView.setCornerRadius(height);
        Log.e("koi","koniec");
        super.onResume();
    }

*/
    public void setText(String textName, String textSurname){
        TextView textViewName = getView().findViewById(R.id.textViewNameValue);
        TextView textViewSurname = getView().findViewById(R.id.textViewSurnameValue);
        TextView textViewAboutTrainer = getView().findViewById(R.id.textViewAboutTrainerValue);
        textViewName.setText(textName);
        textViewSurname.setText(textSurname);
        textViewAboutTrainer.setText(aboutTrainer);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewAboutTrainer);
            textView.setPadding(0,0,0,0);
        }
    }
    private static String aboutTrainer="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fringilla rutrum neque sit amet viverra. Integer neque purus, tristique eget ante ac, pharetra rhoncus felis. Vestibulum lacinia tellus condimentum lobortis congue. In hac habitasse platea dictumst. Nullam est nulla, cursus eget pretium sed, congue non ante. Vivamus ac ligula et nisl varius vestibulum. Vestibulum vitae tellus vitae ligula ultricies blandit vitae at ligula. Integer faucibus fringilla eleifend. Morbi vehicula aliquet consectetur. ";

}
