package com.example.gym.registrationWatchers;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.R;
import com.example.gym.ValidationRules;

//klasa pozwalająca na sprawdzanie poprawności danych wprowadzonych do pola tekstowego do którego
//wprowadzane powinno być imie
//klasa implementuje interfejs TextWatcher
public class NameTextEditWatcher implements TextWatcher {

    private EditText nameEditText;
    private DataCorrectWatcher dataCorrectWatcher;
    private Context context;

    public NameTextEditWatcher(EditText nameEditText, DataCorrectWatcher dataCorrectWatcher, Context context) {
        this.nameEditText = nameEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
        this.context=context;
        
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //metoda uruchamiana po zmianie w polu tekstowym
    //metoda sprawdza czy wprowadzone dane są prawidłowe, czyli czy imie zaczyna się od
    // dużej litery i czy po niej występują tylko małe litery(co najmniej jedna)
    @Override
    public void afterTextChanged(Editable s) {
        String textName;
        textName = s.toString();
        if(textName.length()!=0) {
            if(ValidationRules.isNameCorrect(textName)){
                dataCorrectWatcher.setNameCorrect(true);
            } else {
                nameEditText.setError(context.getString(R.string.registration_incorrect_name_error));
                dataCorrectWatcher.setNameCorrect(false);
            }
        } else {
            dataCorrectWatcher.setNameCorrect(false);
            nameEditText.setError(context.getString(R.string.registration_no_data_error));
        }
        //check if other data is correct and enable or disable registration button
        dataCorrectWatcher.validation();
    }
    }
