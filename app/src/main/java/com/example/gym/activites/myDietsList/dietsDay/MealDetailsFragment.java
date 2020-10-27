package com.example.gym.activites.myDietsList.dietsDay;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gym.R;
import com.example.gym.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment {

    private ListView listViewComponents;
    private ListView listViewPreparation;
    private TextView textViewTitle;

    private ArrayList<String> arrayListComponents = new ArrayList<>();
    private ArrayList<String> arrayListComponentsQuantity = new ArrayList<>();
    private ArrayList<String> arrayListPreparation = new ArrayList<>();

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.meal_details_fragment, container, false);


            idInit(view);
            arrayListsInit();
            setListViewAdapters(arrayListComponents, arrayListComponentsQuantity, arrayListPreparation);
            landscapeConfiguration(view);
            UIUtils.setListViewHeightBasedOnItems(listViewPreparation, R.id.textViewPreparationStep);
            UIUtils.setListViewHeightBasedOnItems(listViewComponents, R.id.textViewComponentName);
            return  view;

            //return super.onCreateView(inflater, container, savedInstanceState);

        }



        public void setText(String title){
            textViewTitle.setText(title);
        }
        public void setText(String title, List<String> components, List<String> componentsQuantity, List<String> preparation){
        textViewTitle.setText(title);
     }

        private void landscapeConfiguration(View view){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                TextView textView  = view.findViewById(R.id.textViewTitle);
                textView.setPadding(0,0,0,0);
            }
        }

        private void idInit(View view){
            textViewTitle = view.findViewById(R.id.textViewTitle);
            listViewComponents=view.findViewById(R.id.listViewComponents);
            listViewPreparation=view.findViewById(R.id.listViewPreparation);
        }

        private void setListViewAdapters(List<String> components, List<String> componentsQuantity, List<String> preparation){
            ComponentsListAdapter componentsListAdapter = new ComponentsListAdapter((AppCompatActivity)getActivity(),components, componentsQuantity, this);
            PreparationListAdapter preparationListAdapter = new PreparationListAdapter((AppCompatActivity)getActivity(), preparation, this);
            listViewComponents.setAdapter(componentsListAdapter);
            listViewPreparation.setAdapter(preparationListAdapter);
        }

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
}
