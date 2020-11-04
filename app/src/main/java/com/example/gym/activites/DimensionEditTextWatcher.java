package com.example.gym.activites;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.ValidationRules;
import com.example.gym.registrationWatchers.DataCorrectWatcher;

public class DimensionEditTextWatcher implements TextWatcher {

        private EditText phoneNumberEditText;
        private DataCorrectWatcher dataCorrectWatcher;

        public DimensionEditTextWatcher(EditText checkedEditText, DataCorrectWatcher dataCorrectWatcher){
            this.dataCorrectWatcher=dataCorrectWatcher;
            this.phoneNumberEditText=phoneNumberEditText;
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
            if(textNumber.length()!=0) {
                //sprawdzenie czy podany tekst pasuje do wzoru
                if(ValidationRules.isPhoneNumberRight(textNumber)){
                    //ustawienie flagi poprawności pola na "tru
                    //ustawienie flagi poprawności pola na "true"
                    dataCorrectWatcher.setPhoneNumberCorrect(true);

                } else {
                    phoneNumberEditText.setError("Błąd, niepoprawny numer telefonu");
                    dataCorrectWatcher.setPhoneNumberCorrect(false);
                }
            } else {
                dataCorrectWatcher.setPhoneNumberCorrect(false);
                phoneNumberEditText.setError("Brak wprowadzonych danych!");

            }
            //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
            //wyświetlany jest przycisk umożliwiający przejście dalej
            dataCorrectWatcher.validation();
        }
    }
