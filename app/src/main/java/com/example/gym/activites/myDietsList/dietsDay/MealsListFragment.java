package com.example.gym.activites.myDietsList.dietsDay;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.UIUtils;
import com.example.gym.R;

import java.util.ArrayList;

public class MealsListFragment extends Fragment {

    protected ListView listViewBreakfast;
    protected ListView listViewBrunch;
    protected ListView listViewLunch;
    protected ListView listViewTea;
    protected ListView listViewDinner;

    DietMealsListAdapter breakfastDietMealsListAdapter;
    DietMealsListAdapter brunchDietMealsListAdapter;
    DietMealsListAdapter lunchDietMealsListAdapter;
    DietMealsListAdapter teaDietMealsListAdapter;
    DietMealsListAdapter dinnerDietMealsListAdapter;

    private ArrayList<String> breakfastMealsListNames = new ArrayList<String>();
    private ArrayList<String> breakfastMealsListCalories = new ArrayList<String>();
    private ArrayList<String> brunchMealsListNames = new ArrayList<String>();
    private ArrayList<String> brunchMealsListCalories = new ArrayList<String>();
    private ArrayList<String> lunchMealsListNames = new ArrayList<String>();
    private ArrayList<String> lunchMealsListCalories = new ArrayList<String>();
    private ArrayList<String> teaMealsListNames = new ArrayList<String>();
    private ArrayList<String> teaMealsListCalories = new ArrayList<String>();
    private ArrayList<String> dinnerMealsListNames = new ArrayList<String>();
    private ArrayList<String> dinnerMealsListCalories = new ArrayList<String>();

    private MealsListFragmentActivityListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_day_of_diet,container,false);

        listMealsInit();
        listsViewInit(view);
        landscapeConfiguration(view);
        //listViewBreakfast.setScrollContainer(false);
        UIUtils.setListViewHeightBasedOnItems(listViewBreakfast, R.id.textViewMealName);
        UIUtils.setListViewHeightBasedOnItems(listViewBrunch, R.id.textViewMealName);



        return view;
    }

    public interface MealsListFragmentActivityListener{
        public void onItemFromMealsListFragmentSelected(String mealName, String calories);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MealsListFragmentActivityListener) {
            listener = (MealsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: MealListFragment.MealsListFragmentActivityListener");
        }
    }

    public void updateDetail(String mealName, String mealCalories) {
        listener.onItemFromMealsListFragmentSelected( mealName, mealCalories);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void listMealsInit(){
        if(breakfastMealsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            breakfastMealsListsInit();
        if(brunchMealsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            brunchMealsListsInit();
        if(lunchMealsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            lunchMealsListsInit();
        if(teaMealsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            teaMealsListsInit();
        if(dinnerMealsListNames.size()==0)//żeby przy powrocie z fragmentu do fragmentu lista się nie powielała
            dinnerMealsListsInit();
    }

    private void breakfastMealsListsInit(){
        breakfastMealsListNames.add("Grzanki");
        breakfastMealsListCalories.add("102");
        breakfastMealsListNames.add("Herbata bez cukru, ale za to z miodem i to jakim!");
        breakfastMealsListCalories.add("10");
    }
    private void brunchMealsListsInit(){
        brunchMealsListNames.add("Banan");
        brunchMealsListCalories.add("30");
        brunchMealsListNames.add("Bułka");
        brunchMealsListCalories.add("15");
    }
    private void lunchMealsListsInit(){
        lunchMealsListNames.add("Kotlety schabowe");
        lunchMealsListCalories.add("475");
    }
    private void teaMealsListsInit(){
    }
    private void dinnerMealsListsInit(){
        dinnerMealsListNames.add("Płatki z mlekiem");
        dinnerMealsListCalories.add("150");
    }

    private void listsViewInit(View view){
        if(breakfastMealsListNames.size()!=0){
            listViewBreakfast=view.findViewById(R.id.listViewBreakfast);
            breakfastDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),breakfastMealsListNames, breakfastMealsListCalories, this);
            listViewBreakfast.setAdapter(breakfastDietMealsListAdapter);}
        if(brunchMealsListNames.size()!=0){
            listViewBrunch=view.findViewById(R.id.listViewBrunch);
            brunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),brunchMealsListNames, brunchMealsListCalories, this);
            listViewBrunch.setAdapter(brunchDietMealsListAdapter);}
        if(lunchMealsListNames.size()!=0){
            listViewLunch=view.findViewById(R.id.listViewLunch);
            lunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),lunchMealsListNames, lunchMealsListCalories, this);
            listViewLunch.setAdapter(lunchDietMealsListAdapter);}
        if(teaMealsListNames.size()!=0){
            listViewTea=view.findViewById(R.id.listViewTea);
            teaDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),teaMealsListNames, teaMealsListCalories, this);
            listViewTea.setAdapter(teaDietMealsListAdapter);}
        if(dinnerMealsListNames.size()!=0){
            listViewDinner=view.findViewById(R.id.listViewDinner);
            dinnerDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),dinnerMealsListNames, dinnerMealsListCalories, this);
            listViewDinner.setAdapter(dinnerDietMealsListAdapter);}
        emptyLabelsHide(view);
    }

    public void setText(String title){
        TextView textViewTitle=getView().findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
    }

    private void emptyLabelsHide(View view){
        LinearLayout linearLayoutBreakfast = view.findViewById(R.id.linearLayoutBreakfast);
        LinearLayout linearLayoutBrunch = view.findViewById(R.id.linearLayoutBrunch);
        LinearLayout linearLayoutLunch = view.findViewById(R.id.linearLayoutLunch);
        LinearLayout linearLayoutTea = view.findViewById(R.id.linearLayoutTea);
        LinearLayout linearLayoutDinner = view.findViewById(R.id.linearLayoutDinner);

        if(breakfastMealsListNames.size()==0)
            linearLayoutBreakfast.setVisibility(View.GONE);
        if(brunchMealsListNames.size()==0)
            linearLayoutBrunch.setVisibility(View.GONE);
        if(lunchMealsListNames.size()==0)
            linearLayoutLunch.setVisibility(View.GONE);
        if(teaMealsListNames.size()==0)
            linearLayoutTea.setVisibility(View.GONE);
        if(dinnerMealsListNames.size()==0)
            linearLayoutDinner.setVisibility(View.GONE);

    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            totalHeight+=listItem.getPaddingTop()+listItem.getPaddingBottom();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
