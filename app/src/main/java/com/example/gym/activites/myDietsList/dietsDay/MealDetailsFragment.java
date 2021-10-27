package com.example.gym.activites.myDietsList.dietsDay;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.dataClasses.Meal;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class MealDetailsFragment extends Fragment {

    private ListView listViewComponents;
    private ListView listViewPreparation;
    private TextView textViewTitle;
    private TextView textViewPreparation;
    private TextView textViewPreparationLabel;
    private TextView textViewComponentsLabel;
    private TextView textViewCalories;
    private TextView textViewCaloriesLabel;
    private TextView textViewProtein;
    private TextView textViewProteinLabel;
    private TextView textViewCarbohydrates;
    private TextView textViewCarbohydratesLabel;

    private ArrayList<String> arrayListComponents = new ArrayList<>();
    private ArrayList<String> arrayListComponentsQuantity = new ArrayList<>();
    private ArrayList<String> arrayListPreparation = new ArrayList<>();
    private ArrayList<String> arrayListAmount = new ArrayList<>();


    private String preparation;

    IntentFilter filter;
    private SpotsDialog progressDialog;

    final private static String GET_MEAL_DATA = "getMealData";
    private TextView emptyListViewText;

    Meal meal;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.meal_details_fragment, container, false);

            filter = new IntentFilter(); //utworzenie filtru zamiaru
            filter.addAction(GET_MEAL_DATA); //dodanie akcji od pobierania informacji o użytkownikach
            idInit(view);
        //

            //setListViewAdapters(arrayListComponents, arrayListComponentsQuantity, arrayListPreparation);
            landscapeConfiguration(view);
           // UIUtils.setListViewHeightBasedOnItems(listViewPreparation, R.id.textViewPreparationStep);
           // UIUtils.setListViewHeightBasedOnItems(listViewComponents, R.id.textViewComponentName);
            return  view;

            //return super.onCreateView(inflater, container, savedInstanceState);

        }



        public void setData(String title){
            textViewTitle.setText(title);
        }

   // String title, List<String> components, List<String> componentsQuantity, List<String> preparation
        public void setData(Meal meal){
        if(meal!=null) {
            Log.e("prot", meal.getProtein());
//            Log.e("carbo", meal.getCarbohydrates());
            Log.e("cal", meal.getCalories());

            textViewTitle.setText(meal.getName());
            textViewProtein.setText(meal.getProtein());
            textViewCarbohydrates.setText(meal.getCarbohydrates());
            textViewCalories.setText(meal.getCalories());
            textViewCaloriesLabel.setVisibility(View.VISIBLE);
            textViewProteinLabel.setVisibility(View.VISIBLE);
            textViewCarbohydratesLabel.setVisibility(View.VISIBLE);
            //
            getMealData(meal);
        }

     }

        private void landscapeConfiguration(View view){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                TextView textView  = view.findViewById(R.id.textViewTitle);
                textView.setPadding(0,0,0,0);
            }
        }

        private void idInit(View view){
            textViewCarbohydratesLabel=view.findViewById(R.id.textViewMealCarbohydrates);
            textViewCarbohydrates=view.findViewById(R.id.textViewMealCarbohydratesValue);
            textViewCaloriesLabel=view.findViewById(R.id.textViewMealCalories);
            textViewCalories=view.findViewById(R.id.textViewMealCaloriesValue);
            textViewProteinLabel=view.findViewById(R.id.textViewMealProtein);
            textViewProtein=view.findViewById(R.id.textViewMealProteinValue);
            emptyListViewText=view.findViewById(R.id.emptyListViewText);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            listViewComponents=view.findViewById(R.id.listViewComponents);
            textViewPreparation=view.findViewById(R.id.textViewPreparationValue);
            //textViewComponentsLabel=view.findViewById(R.id.textViewComponents);
            textViewPreparationLabel=view.findViewById(R.id.textViewPreparation);
            //listViewPreparation=view.findViewById(R.id.listViewPreparation);
        }

   // private void setListViewAdapters(List<String> components, List<String> componentsQuantity, List<String> preparationList){
        private void setListViewAdapters(Meal meal){
            ComponentsListAdapter componentsListAdapter = new ComponentsListAdapter((AppCompatActivity)getActivity(), meal.getComponents(), this);
         //   PreparationListAdapter preparationListAdapter = new PreparationListAdapter((AppCompatActivity)getActivity(), preparation, this);
            textViewPreparationLabel.setVisibility(View.VISIBLE);
            //textViewComponentsLabel.setVisibility(View.VISIBLE);
            listViewComponents.setAdapter(componentsListAdapter);
            textViewPreparation.setText(meal.getPreparation());
            //setListViewHeightBasedOnChildren(listViewComponents);
            emptyListViewText.setVisibility(View.VISIBLE);
            listViewComponents.setEmptyView(emptyListViewText);
            View header_view =getLayoutInflater().inflate(R.layout.component_list_header, null);
            if(listViewComponents.getHeaderViewsCount()==0)
                listViewComponents.addHeaderView(header_view);

            setListViewHeightBasedOnChildren(listViewComponents);
          //  justifyListViewHeightBasedOnChildren(listViewComponents);
          //  listViewPreparation.setAdapter(preparationListAdapter);
        }

    private void getMealData(Meal meal){
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);
        progressDialog.show();
        getContext().registerReceiver(broadcastReceiver, filter);
        HashMap<String, String> params = new HashMap<>();
        params.put("mealId", String.valueOf(meal.getId()));
        PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_MEAL_DATA, params, Constants.CODE_POST_REQUEST, getContext(), GET_MEAL_DATA);
        request.execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("rec", "Start");
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_MEAL_DATA)) {
                try {
                    Log.e("Reciever", "tre");
                    String jsonstr = bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    //JSONArray jsonArray = json.getJSONArray("mealData");
                    //arrayListComponents.clear();
                    //arrayListComponentsQuantity.clear();
                    //jsonArray.getJSONObject(1);
                    /*
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject js = jsonArray.getJSONObject(i);
                        Log.e("js!", js.toString());
                        arrayListComponentsQuantity.add(js.getString("quantity"));
                        arrayListComponents.add(js.getString("mealName"));
                        preparation=js.getString("description");
                        arrayListAmount.add(js.getString("amount"));
                        //Meal meal = new Meal(js);
                    }*/
                    meal = new Meal(json.getJSONObject("mealData"));
                    setListViewAdapters(meal);
                    //listsViewInit();
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
                if (getContext() != null)
                    getContext().unregisterReceiver(broadcastReceiver);
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                // progressDialog.dismiss();
                // justifyListViewHeight();
                // UIUtils.setListViewHeightBasedOnItems(listViewBreakfast, R.id.textViewMealName);
                //UIUtils.setListViewHeightBasedOnItems(listViewBrunch, R.id.textViewMealName);

            }
        }
    };



        //do usunięcia jak będzie baza danych
        private void arrayListsInit(){
            arrayListComponents.add("Chleb");
            arrayListComponents.add("Szynka");
            arrayListComponents.add("Masło");
            arrayListComponents.add("Sól");
            arrayListComponents.add("Pomidor");
            arrayListComponents.add("Ogórek");

            arrayListComponentsQuantity.add("2 kromki");
            arrayListComponentsQuantity.add("200g");
            arrayListComponentsQuantity.add("100g");
            arrayListComponentsQuantity.add("szczypta");
            arrayListComponentsQuantity.add("1 sztuka");
            arrayListComponentsQuantity.add("1 sztuka");

            arrayListPreparation.add("Ukrój dwie kromki chleba");
            arrayListPreparation.add("Posmaruj kromki masłem");
            arrayListPreparation.add("Pokrój szynkę i połóż na chlebie");
            arrayListPreparation.add("Pokrój ogórek i pomidor, ułóż je w plasterkach na kanapce");
            arrayListPreparation.add("Posól kanapkę szczyptą soli");
            arrayListPreparation.add("Gotowe! Smacznego!");
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

    public static void setListViewHeightBasedOnChildren
            (ListView listView) {ListAdapter listAdapter = listView.getAdapter();if (listAdapter == null) return;int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
            View.MeasureSpec.UNSPECIFIED);int totalHeight = 0;
        View view = null;for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    RecyclerView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
