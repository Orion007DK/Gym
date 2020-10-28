package com.example.gym.activites.gymsList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.example.gym.Address;
import com.example.gym.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class GymDetailFragment extends Fragment {


    CarouselView carouselView;

    int[] sampleImages = {
            R.drawable.gym1,
            R.drawable.gym2,
            R.drawable.gym3,
            R.drawable.gym4,
            R.drawable.gym5
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gym_details, container, false);

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        landscapeConfiguration(view);
        return view;
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private static String loremIpsum="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fringilla rutrum neque sit amet viverra. Integer neque purus, tristique eget ante ac, pharetra rhoncus felis. Vestibulum lacinia tellus condimentum lobortis congue. In hac habitasse platea dictumst. Nullam est nulla, cursus eget pretium sed, congue non ante. Vivamus ac ligula et nisl varius vestibulum. Vestibulum vitae tellus vitae ligula ultricies blandit vitae at ligula. Integer faucibus fringilla eleifend. Morbi vehicula aliquet consectetur. ";


    public void setText(String textName, Address address, String aboutGym) {
        TextView textViewName = getView().findViewById(R.id.textViewGymNameValue);
        TextView textViewSurname = getView().findViewById(R.id.textViewGymAddressValue);
        TextView textViewAboutGym = getView().findViewById(R.id.textViewAboutGymValue);
        textViewName.setText(textName);
        textViewSurname.setText(address.getStringAddress());
        textViewAboutGym.setText(loremIpsum);
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
           // ScrollView scrollViewAboutGymValue=view.findViewById(R.id.scrollViewAboutGymValue);
            //scrollViewAboutGymValue.setLayoutParams(param3);
        }
    }
}

