package com.example.gym.registrationWatchers;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;

//klasa pozwalająca na sprawdzanie poprawności danych wprowadzonych do pola tekstowego do którego
//wprowadzane powinno być nazwisko
//klasa implementuje interfejs TextWatcher
public class SournameTextEditWatcher implements TextWatcher {

    private EditText surnameEditText;
    private DataCorrectWatcher dataCorrectWatcher;

    public SournameTextEditWatcher(EditText surnameEditText, DataCorrectWatcher dataCorrectWatcher) {
        this.surnameEditText = surnameEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    //metoda uruchamiana po zmianie w polu tekstowym
    //metoda sprawdza czy wprowadzone dane są prawidłowe, czyli czy nazwisko jest
    //prawidłowe, nazwisko może składać się z jednej dużej litery na poczatku i co
    // najmniej jednej małej lub jednej dużej, co najmniej jednej małej, myślnika i znowu
    //jednej dużej litery i co najmniej jednej małej czyli nazwisko może wyglądać np, tak:
    //Lisiecki albo Lisiecki-Nowak
    @Override
    public void afterTextChanged(Editable s) {
        String textSurname;
        textSurname = s.toString();
        if(textSurname.length()!=0) {
            //sprawdzenie czy podany tekst pasuje do wzoru
            if(ValidationRules.isSurnameRight(textSurname)){
                //ustawienie flagi poprawności pola na "true"
                dataCorrectWatcher.setSurnameCorrect(true);
            } else {
                surnameEditText.setError( "Niewłaściwe nazwisko, nazwisko może się składać jedynie z liter a-z, A-Z oraz znaku '-', np. 'Wiktorowski-Wania'");
                dataCorrectWatcher.setSurnameCorrect(false);
            }
        } else {
            dataCorrectWatcher.setSurnameCorrect(false);
            surnameEditText.setError("Brak danych");
        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
    }

