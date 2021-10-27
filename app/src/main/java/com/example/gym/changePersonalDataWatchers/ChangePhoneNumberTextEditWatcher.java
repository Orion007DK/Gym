package com.example.gym.changePersonalDataWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;

public class ChangePhoneNumberTextEditWatcher implements TextWatcher {
    private EditText phoneNumberEditText;
    private ChangeDataCorrectWatcher changeDataCorrectWatcher;
    private String previousPhoneNumber;

    public ChangePhoneNumberTextEditWatcher(EditText phoneNumberEditText, ChangeDataCorrectWatcher changeDataCorrectWatcher, String previousPhoneNumber){
        this.changeDataCorrectWatcher=changeDataCorrectWatcher;
        this.phoneNumberEditText=phoneNumberEditText;
        this.previousPhoneNumber=previousPhoneNumber;
    }

    public void setPreviousPhoneNumber(String previousPhoneNumber) {
        this.previousPhoneNumber = previousPhoneNumber;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String textNumber;
        textNumber = s.toString();
        if(previousPhoneNumber.equals(textNumber))
        {
            changeDataCorrectWatcher.setEmailChanged(false);
        } else {
            changeDataCorrectWatcher.setEmailChanged(true);
            if (textNumber.length() != 0) {
                //sprawdzenie czy podany tekst pasuje do wzoru
                if (ValidationRules.isPhoneNumberCorrect(textNumber)) {
                    //ustawienie flagi poprawności pola na "tru
                    //ustawienie flagi poprawności pola na "true"
                    changeDataCorrectWatcher.setPhoneNumberCorrect(true);

                } else {
                    phoneNumberEditText.setError("Błąd, niepoprawny numer telefonu");
                    changeDataCorrectWatcher.setPhoneNumberCorrect(false);
                }
            } else {
                changeDataCorrectWatcher.setPhoneNumberCorrect(false);
                phoneNumberEditText.setError("Brak wprowadzonych danych!");

            }
        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        changeDataCorrectWatcher.validation();
    }
}
