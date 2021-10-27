package com.example.gym.activites.myDietsList.dietsDay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.dataClasses.Meal;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

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

    private ArrayList<Meal> breakfastMealsList = new ArrayList<Meal>();
    private ArrayList<Meal> brunchMealsList = new ArrayList<Meal>();
    private ArrayList<Meal> lunchMealsList = new ArrayList<Meal>();
    private ArrayList<Meal> teaMealsList = new ArrayList<Meal>();
    private ArrayList<Meal> dinnerMealsList = new ArrayList<Meal>();

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

    IntentFilter filter;
    private SpotsDialog progressDialog;

    final private static String GET_DIET_DAY_MEALS = "getDietDayMeals";

    int dietId=2;
    String dayOfWeekName ="przypadek";
    int dayOfWeekId=1;
    TextView textViewTitle;
    View view;
  /*  MealsListFragment(int dietId, String dayofWeekName, int dayOfWeekId){
        this.dietId=dietId;
        this.dayofWeekName=dayofWeekName;
        this.dayOfWeekId=dayOfWeekId;

    }*

   */
    @Nullable
   // @Override
    // public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_day_of_diet,container,false);
        this.view=view;

        dayOfWeekName =getArguments().getString(Constants.BUNDLE_DIET_DAY_OF_WEEK_NAME);
        dayOfWeekId=getArguments().getInt(Constants.BUNDLE_DIET_DAY_OF_WEEK_ID);
        dietId=getArguments().getInt(Constants.BUNDLE_DIET_ID);

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_DIET_DAY_MEALS); //dodanie akcji od pobierania informacji o użytkownikach
        textViewTitle=view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(dayOfWeekName);
        listViewIdInit();
        //listMealsInit();
        //listsViewInit(view);
        landscapeConfiguration(view);
        //listViewBreakfast.setScrollContainer(false);
     //   UIUtils.setListViewHeightBasedOnItems(listViewBreakfast, R.id.textViewMealName);
      //  UIUtils.setListViewHeightBasedOnItems(listViewBrunch, R.id.textViewMealName);

        if(breakfastMealsList.size()==0 && brunchMealsList.size()==0 && lunchMealsList.size()==0 && teaMealsList.size()==0 && dinnerMealsList.size()==0)
        getDietDayMeals();
        else {
            listsViewInit();
            //justifyListViewHeight();
            //setwHeight();
        }

        return view;
    }

    public interface MealsListFragmentActivityListener{
        public void onItemFromMealsListFragmentSelected(Meal meal);
    }

    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MealsListFragmentActivityListener) {
            listener = (MealsListFragmentActivityListener) activity;
        } else {
            throw new ClassCastException( activity.toString() + "musi implementować interfejs: MealListFragment.MealsListFragmentActivityListener");
        }
    }

    public void updateDetail(Meal meal) {
        listener.onItemFromMealsListFragmentSelected(meal);
    }

    private void landscapeConfiguration(View view){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView textView  = view.findViewById(R.id.textViewTitle);
            textView.setPadding(10,20,10,0);
        }
    }

    private void getDietDayMeals(){
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);
        progressDialog.show();
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
     //   Log.e("dietId",String.valueOf(dietId));
     //   Log.e("dayId", String.valueOf(dayOfWeekId));
        params.put("dietId", String.valueOf(dietId));
        params.put("dayId", String.valueOf(dayOfWeekId));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_DIET_DAY_MEALS, params, Constants.CODE_POST_REQUEST, getContext(), GET_DIET_DAY_MEALS);
        request.execute();
        Log.e("getDiets","diets");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
       //     Log.e("rec","Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_DIET_DAY_MEALS)) {
                try {
               //     Log.e("Reciever","tre");
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    JSONArray jsonArray = json.getJSONArray("mealsList");
                    //jsonArray.getJSONObject(1);
                 //   Log.e("arrayLength ", String.valueOf(jsonArray.length()));
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                 //       Log.e("js!", js.toString());
                        Meal meal = new Meal(js);
                        switch (js.getInt("mealTypeId")) {
                            case 1:
                     //           Log.e("meal", "nr1");
                                breakfastMealsList.add(meal);
                                break;
                            case 2:
                                brunchMealsList.add(meal);
                                break;
                            case 3:
                                lunchMealsList.add(meal);
                                break;
                            case 4:
                                teaMealsList.add(meal);
                                break;
                            case 5:
                                dinnerMealsList.add(meal);
                                break;
                        }

                    }

                    listsViewInit();
                    //Log.e("gymsArrayList: ",trainersArrayList.toString());

                    // trainersListAdapter=new TrainersListAdapter(appContext, trainersArrayList, fragment);
                    //listView.setAdapter(trainersListAdapter);

                   // dietsListAdapter=new DietsListAdapter(MyDietsListActivity.this, dietNamesList, dietIdList);
                    //dietListView.setAdapter(dietsListAdapter);
                    } else {
                        Dialogs.noNetworkFinishDialog(context, getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(getContext()!=null)
                getContext().unregisterReceiver(broadcastReceiver);
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
               // progressDialog.dismiss();
                //justifyListViewHeight();
              //  setwHeight();
               // UIUtils.setListViewHeightBasedOnItems(listViewBreakfast, R.id.textViewMealName);
                //UIUtils.setListViewHeightBasedOnItems(listViewBrunch, R.id.textViewMealName);
            }
        }
    };

private void justifyListViewHeight() {
    if(breakfastMealsListNames.size()!=0)
    justifyListViewHeightBasedOnChildren(listViewBreakfast);
    if(brunchMealsListNames.size()!=0)
    justifyListViewHeightBasedOnChildren(listViewBrunch);
    if(lunchMealsListNames.size()!=0)
    justifyListViewHeightBasedOnChildren(listViewLunch);
    if(teaMealsListNames.size()!=0)
    justifyListViewHeightBasedOnChildren(listViewTea);
    if(dinnerMealsListNames.size()!=0)
    justifyListViewHeightBasedOnChildren(listViewDinner);
}

private void setwHeight() {
        if(breakfastMealsListNames.size()!=0)
            setListViewHeightBasedOnChildren(listViewBreakfast);
        if(brunchMealsListNames.size()!=0)
            setListViewHeightBasedOnChildren(listViewBrunch);
        if(lunchMealsListNames.size()!=0)
            setListViewHeightBasedOnChildren(listViewLunch);
        if(teaMealsListNames.size()!=0)
            setListViewHeightBasedOnChildren(listViewTea);
        if(dinnerMealsListNames.size()!=0)
            setListViewHeightBasedOnChildren(listViewDinner);
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

    private void listViewIdInit(){
        listViewBreakfast=view.findViewById(R.id.listViewBreakfast);
        listViewBrunch=view.findViewById(R.id.listViewBrunch);
        listViewLunch=view.findViewById(R.id.listViewLunch);
        listViewTea=view.findViewById(R.id.listViewTea);
        listViewDinner=view.findViewById(R.id.listViewDinner);
    }

    private void listsViewInit(){
        if(breakfastMealsList.size()!=0){//było listNames
       //     Log.e("breakfastListinit","tak");
            //listViewBreakfast=view.findViewById(R.id.listViewBreakfast);
            breakfastDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(), breakfastMealsList, this);
            listViewBreakfast.setAdapter(breakfastDietMealsListAdapter);
        //    Log.e("cokolwiek","nie");
        }
        if(brunchMealsList.size()!=0){
            //listViewBrunch=view.findViewById(R.id.listViewBrunch);
            //brunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),brunchMealsListNames, brunchMealsListCalories, this);
            brunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),brunchMealsList, this);
            listViewBrunch.setAdapter(brunchDietMealsListAdapter);}
        if(lunchMealsList.size()!=0){
            //listViewLunch=view.findViewById(R.id.listViewLunch);
            //lunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),lunchMealsListNames, lunchMealsListCalories, this);
            lunchDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),lunchMealsList, this);
            listViewLunch.setAdapter(lunchDietMealsListAdapter);}
        if(teaMealsList.size()!=0){
           //listViewTea=view.findViewById(R.id.listViewTea);
            //teaDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),teaMealsListNames, teaMealsListCalories, this);
            teaDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),teaMealsList, this);
            listViewTea.setAdapter(teaDietMealsListAdapter);}
        if(dinnerMealsList.size()!=0){
            //listViewDinner=view.findViewById(R.id.listViewDinner);
            //dinnerDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),dinnerMealsListNames, dinnerMealsListCalories, this);
            dinnerDietMealsListAdapter = new DietMealsListAdapter((AppCompatActivity)view.getContext(),dinnerMealsList, this);
            listViewDinner.setAdapter(dinnerDietMealsListAdapter);
               // setListViewHeightBasedOnChildren(listViewBreakfast);
               // setListViewHeightBasedOnChildren(listViewBrunch);
               // setListViewHeightBasedOnChildren(listViewLunch);
               // setListViewHeightBasedOnChildren(listViewTea);
              //  setListViewHeightBasedOnChildren(listViewDinner);
                UIUtils.setListViewHeightBasedOnItems(listViewBreakfast, R.id.textViewMealName, getContext());
                UIUtils.setListViewHeightBasedOnItems(listViewBrunch, R.id.textViewMealName, getContext());
                UIUtils.setListViewHeightBasedOnItems(listViewLunch, R.id.textViewMealName, getContext());
                UIUtils.setListViewHeightBasedOnItems(listViewTea, R.id.textViewMealName, getContext());
                UIUtils.setListViewHeightBasedOnItems(listViewDinner, R.id.textViewMealName, getContext());
        }
       emptyLabelsHide();
        //setwHeight();
    }

    public void setText(String title){
        TextView textViewTitle=getView().findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
    }

    private void emptyLabelsHide(){
        LinearLayout linearLayoutBreakfast = view.findViewById(R.id.linearLayoutBreakfast);
        LinearLayout linearLayoutBrunch = view.findViewById(R.id.linearLayoutBrunch);
        LinearLayout linearLayoutLunch = view.findViewById(R.id.linearLayoutLunch);
        LinearLayout linearLayoutTea = view.findViewById(R.id.linearLayoutTea);
        LinearLayout linearLayoutDinner = view.findViewById(R.id.linearLayoutDinner);

        if(breakfastMealsList.size()==0)//było Names
            linearLayoutBreakfast.setVisibility(View.GONE);
      //  else
       //     linearLayoutBreakfast.setVisibility(View.VISIBLE);


       // Log.e("BR:", String.valueOf(brunchMealsList.size()));
        if(brunchMealsList.size()==0) {
            linearLayoutBrunch.setVisibility(View.GONE);
          //  Log.e("za malo", "btunch");
        }
       // else
       //     linearLayoutBreakfast.setVisibility(View.VISIBLE);


        if(lunchMealsList.size()==0)
            linearLayoutLunch.setVisibility(View.GONE);
     //   else
       //     linearLayoutBreakfast.setVisibility(View.VISIBLE);


        if(teaMealsList.size()==0)
            linearLayoutTea.setVisibility(View.GONE);
      //  else
        //    linearLayoutBreakfast.setVisibility(View.VISIBLE);


        if(dinnerMealsList.size()==0)
            linearLayoutDinner.setVisibility(View.GONE);
       // else
         //   linearLayoutBreakfast.setVisibility(View.VISIBLE);

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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) return;
    int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
            View.MeasureSpec.AT_MOST); int totalHeight = 0;
        View view = null;for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
