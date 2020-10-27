package com.example.gym.Niepotrzebne;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.activites.dieticianList.DieticiansListFragment;

public class DietDayListFragment extends Fragment {

    Button buttonDaysOfWeek[] = new Button[7];

   private DietDayListFragmentActivityListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diet_days_list,container,false);

      /*  if(dieticiansListNames.size()==0)
            dieticianListInit();

        listView=view.findViewById(R.id.listViewDietician);
        //       listView=container.findViewById(R.id.listViewTrainers);
        dieticiansListAdapter = new DieticiansListAdapter((AppCompatActivity)view.getContext(), dieticiansListNames, dieticiansListSurnames, this);
        listView.setAdapter(dieticiansListAdapter);
        landscapeConfiguration(view);*/

        return view;
    }

    public interface DietDayListFragmentActivityListener{
        public void onDietDayItemSelected(String dietName, String dayName);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DietDayListFragmentActivityListener) {
            listener = (DietDayListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: DietDayListFragment.DietDayListFragmentActivityListener");
        }
    }

    public void updateDietDayDetails(String dietName, String dayName) {
        listener.onDietDayItemSelected(dietName, dayName);
    }

    private void idInit(View view){
        buttonDaysOfWeek[0]=view.findViewById(R.id.buttonMonday);
        buttonDaysOfWeek[1]=view.findViewById(R.id.buttonTuesday);
        buttonDaysOfWeek[2]=view.findViewById(R.id.buttonWednsday);
        buttonDaysOfWeek[3]=view.findViewById(R.id.buttonThursday);
        buttonDaysOfWeek[4]=view.findViewById(R.id.buttonFriday);
        buttonDaysOfWeek[5]=view.findViewById(R.id.buttonSaturday);
        buttonDaysOfWeek[6]=view.findViewById(R.id.buttonSunday);
    }

    private void buttonsListenersInit(final View view){
        for(final Button button:buttonDaysOfWeek){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String weekDay = button.getText().toString();
                    String dietName = view.findViewById(R.id.textViewDietName).toString();
                    updateDietDayDetails(dietName, weekDay);
                }
            });

        }
    }
}
