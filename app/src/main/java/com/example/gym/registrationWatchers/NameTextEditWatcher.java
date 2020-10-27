package com.example.gym.registrationWatchers;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;

//klasa pozwalająca na sprawdzanie poprawności danych wprowadzonych do pola tekstowego do którego
//wprowadzane powinno być imie
//klasa implementuje interfejs TextWatcher
public class NameTextEditWatcher implements TextWatcher {

    private EditText nameEditText;
    private DataCorrectWatcher dataCorrectWatcher;

    public NameTextEditWatcher(EditText nameEditText, DataCorrectWatcher dataCorrectWatcher) {
        this.nameEditText = nameEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
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
            if(ValidationRules.isNameRight(textName)){
                //ustawienie flagi poprawności pola na "true"
                dataCorrectWatcher.setNameCorrect(true);

            } else {
                nameEditText.setError("Błąd, imię powinno zaczynać się od jednej dużej litery, a po niej, powinny następować co najmniej dwie małe litery, dozwolone są tylko znaki A-Z i a-z");
                dataCorrectWatcher.setNameCorrect(false);
            }
        } else {
            dataCorrectWatcher.setNameCorrect(false);
            nameEditText.setError("Brak wprowadzonych danych!");

        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
    }
