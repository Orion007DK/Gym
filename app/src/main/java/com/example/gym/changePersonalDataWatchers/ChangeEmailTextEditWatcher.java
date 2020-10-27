package com.example.gym.changePersonalDataWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class ChangeEmailTextEditWatcher implements TextWatcher {

    private EditText emailEditText;
    private ChangeDataCorrectWatcher changeDataCorrectWatcher;
    private String previousEmail;

    public ChangeEmailTextEditWatcher(EditText emailEditText, ChangeDataCorrectWatcher changeDataCorrectWatcher, String previousEmail){
        this.emailEditText=emailEditText;
        this.changeDataCorrectWatcher=changeDataCorrectWatcher;
        this.previousEmail =previousEmail;
    }

    public void setPreviousEmail(String previousEmail) {
        this.previousEmail = previousEmail;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        String textEmail;
        textEmail = s.toString();
        if(previousEmail.equals(textEmail)){
            changeDataCorrectWatcher.setSurnameChanged(false);
        } else {
            changeDataCorrectWatcher.setSurnameChanged(true);
            if (textEmail.length() != 0) {
                //sprawdzenie czy podany tekst pasuje do wzoru adresu email
                //https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    //ustawienie flagi poprawności pola na "true"
                    changeDataCorrectWatcher.setEmailCorrect(true);

                } else {
                    emailEditText.setError("Błąd, niepoprawny adres email");
                    changeDataCorrectWatcher.setEmailCorrect(false);
                }
            } else {
                changeDataCorrectWatcher.setEmailCorrect(false);
                emailEditText.setError("Brak wprowadzonych danych!");

            }
        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        changeDataCorrectWatcher.validation();
    }
}
