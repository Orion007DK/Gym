package com.example.gym.registrationWatchers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.R;
import com.example.gym.ValidationRules;

public class PhoneNumberTextEditWatcher implements TextWatcher {

    private EditText phoneNumberEditText;
    private DataCorrectWatcher dataCorrectWatcher;
    private Context context;

    public PhoneNumberTextEditWatcher(EditText phoneNumberEditText, DataCorrectWatcher dataCorrectWatcher, Context context){
        this.dataCorrectWatcher=dataCorrectWatcher;
        this.phoneNumberEditText=phoneNumberEditText;
        this.context=context;
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
            if(ValidationRules.isPhoneNumberCorrect(textNumber)){
                //ustawienie flagi poprawności pola na "tru
                //ustawienie flagi poprawności pola na "true"
                dataCorrectWatcher.setPhoneNumberCorrect(true);

            } else {
                phoneNumberEditText.setError(context.getString(R.string.registration_incorrect_phone_number_error));
                dataCorrectWatcher.setPhoneNumberCorrect(false);
            }
        } else {
            dataCorrectWatcher.setPhoneNumberCorrect(false);
            phoneNumberEditText.setError(context.getString(R.string.registration_no_data_error));

        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
}
