package com.example.gym.changePersonalDataWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;

public class ChangeNameTextEditWatcher implements TextWatcher {

    private EditText nameEditText;
    private ChangeDataCorrectWatcher changeDataCorrectWatcher;
    private String previousName;

    public  ChangeNameTextEditWatcher(EditText nameEditText, ChangeDataCorrectWatcher changeDataCorrectWatcher, String previousName) {
        this.nameEditText = nameEditText;
        this.changeDataCorrectWatcher=changeDataCorrectWatcher;
        this.previousName = previousName;
    }

    public void setPreviousName(String previousName) {
        this.previousName = previousName;
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
        if(previousName.equals(textName)){
            changeDataCorrectWatcher.setNameChanged(false);
        } else
        {
            changeDataCorrectWatcher.setNameChanged(true);
            if(textName.length()!=0) {
                if(ValidationRules.isNameRight(textName)){
                    //ustawienie flagi poprawności pola na "true"
                    changeDataCorrectWatcher.setNameCorrect(true);

                } else {
                    nameEditText.setError("Błąd, imię powinno zaczynać się od jednej dużej litery, a po niej, powinny następować co najmniej dwie małe litery, dozwolone są tylko znaki A-Z i a-z");
                    changeDataCorrectWatcher.setNameCorrect(false);
                }
            } else {
                changeDataCorrectWatcher.setNameCorrect(false);
                nameEditText.setError("Brak wprowadzonych danych!");

            }
        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        changeDataCorrectWatcher.validation();
    }
}
