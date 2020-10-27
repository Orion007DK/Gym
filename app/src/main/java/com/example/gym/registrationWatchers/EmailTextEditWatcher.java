package com.example.gym.registrationWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class EmailTextEditWatcher implements TextWatcher {
    private EditText emailEditText;
    private DataCorrectWatcher dataCorrectWatcher;

    public EmailTextEditWatcher(EditText emailEditText, DataCorrectWatcher dataCorrectWatcher){
        this.emailEditText=emailEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        String textEmail;
        textEmail = s.toString();
        if(textEmail.length()!=0) {
            //sprawdzenie czy podany tekst pasuje do wzoru adresu email
            //https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                //ustawienie flagi poprawności pola na "true"
                dataCorrectWatcher.setEmailCorrect(true);

            } else {
                emailEditText.setError("Błąd, niepoprawny adres email");
                dataCorrectWatcher.setEmailCorrect(false);
            }
        } else {
            dataCorrectWatcher.setEmailCorrect(false);
            emailEditText.setError("Brak wprowadzonych danych!");

        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
}
