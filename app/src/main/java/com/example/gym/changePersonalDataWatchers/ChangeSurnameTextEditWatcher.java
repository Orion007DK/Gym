package com.example.gym.changePersonalDataWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;

public class ChangeSurnameTextEditWatcher implements TextWatcher {

    private EditText surnameEditText;
    private ChangeDataCorrectWatcher changeDataCorrectWatcher;
    private String previousSurname;

    public ChangeSurnameTextEditWatcher(EditText surnameEditText, ChangeDataCorrectWatcher changeDataCorrectWatcher, String previousSurname) {
        this.surnameEditText = surnameEditText;
        this.changeDataCorrectWatcher=changeDataCorrectWatcher;
        this.previousSurname = previousSurname;
    }

    public void setPreviousSurname(String previousSurname) {
        this.previousSurname = previousSurname;
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
        if(previousSurname.equals(textSurname)){
            changeDataCorrectWatcher.setSurnameChanged(false);
        } else {
            changeDataCorrectWatcher.setSurnameChanged(true);
            if (textSurname.length() != 0) {
                //sprawdzenie czy podany tekst pasuje do wzoru
                if (ValidationRules.isSurnameCorrect(textSurname)) {
                    //ustawienie flagi poprawności pola na "true"
                    changeDataCorrectWatcher.setSurnameCorrect(true);
                } else {
                    surnameEditText.setError("Niewłaściwe nazwisko, nazwisko może się składać jedynie z liter a-z, A-Z oraz znaku '-', np. 'Wiktorowski-Wania'");
                    changeDataCorrectWatcher.setSurnameCorrect(false);
                }
            } else {
                changeDataCorrectWatcher.setSurnameCorrect(false);
                surnameEditText.setError("Brak danych");
            }
        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        changeDataCorrectWatcher.validation();
    }
}
