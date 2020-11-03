package com.example.gym.activites.dimensionHistory;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.Dimensions;
import com.example.gym.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DimensionHistoryListFragment extends Fragment {

    protected ListView listView;
    DimensionsHistoryListAdapter dimensionsHistoryListAdapter;
    private ArrayList<Date> dimensionsHistoryDates= new ArrayList<Date>();
    private DimensionsHistoryListFragmentActivityListener listFragmentActivityListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dimensions_history_list, container, false);

        if(dimensionsHistoryDates.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            dimensionHistoryListsInit();

        listView = view.findViewById(R.id.listViewDimensionsHistory);

        ArrayList<Dimensions> dimensionsArrayList = new ArrayList<>();
        dimensionsArrayList.add(new Dimensions(new Date(2003-1900,1,2)));
        dimensionsArrayList.add(new Dimensions(new Date(2007-1900,7,5)));


        dimensionsHistoryListAdapter=new DimensionsHistoryListAdapter((AppCompatActivity)view.getContext(),dimensionsHistoryDates,this);
        listView.setAdapter(dimensionsHistoryListAdapter);


        landscapeConfiguration(view);
        return view;
    }

    public interface DimensionsHistoryListFragmentActivityListener{
        public void onItemSelected(Date date, String height, String weight, String adiposeTissue, String muscleTissue, String bodyWaterPercentage);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DimensionsHistoryListFragmentActivityListener) {
            listFragmentActivityListener = (DimensionsHistoryListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: TrainersListFragment.TrainersListFragmentActivityListener");
        }
    }

    public void updateDetail(Date date) {
        listFragmentActivityListener.onItemSelected(date, null, null, null, null, null);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout layout = view.findViewById(R.id.linearLayoutDimensionsHistoryListTitle);
            layout.setPadding(0,0,0,0);
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    //metoda do usuniecia jak bedzie baza danych
    private void dimensionHistoryListsInit(){
        dimensionsHistoryDates.add(new Date(2003-1900,1,2));
        dimensionsHistoryDates.add(new Date(2007-1900,7,5));
        dimensionsHistoryDates.add(new Date(2012-1900,9,1));
        dimensionsHistoryDates.add(new Date(2020-1900,8,15));
    }
}
