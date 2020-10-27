package com.example.gym.registrationWatchers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PasswordTextEditWatcher implements TextWatcher {

    private EditText passwordEditText;
    private EditText repeatedPasswordEditText;
    private DataCorrectWatcher dataCorrectWatcher;

    public PasswordTextEditWatcher(EditText passwordEditText, EditText repeatedPasswordEditText, DataCorrectWatcher dataCorrectWatcher){
        this.passwordEditText=passwordEditText;
        this.repeatedPasswordEditText=repeatedPasswordEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String textPassword,repeatedPassword;
        textPassword = s.toString();
        repeatedPassword=repeatedPasswordEditText.getText().toString();
        if(textPassword.length()!=0) {
            //Pattern pattern = Pattern.compile("^[0-9]{9}$");
            //stworzenie matchera i przypisanie wzorowi tekstu wpisanego do pola tekstowego
           // Matcher matchName = pattern.matcher(textName);
            //sprawdzenie czy podany tekst pasuje do wzoru
            if(textPassword.equals(repeatedPassword)){
                //ustawienie flagi poprawności pola na "tru
                //ustawienie flagi poprawności pola na "true"
                dataCorrectWatcher.setPasswordCorrect(true);
                repeatedPasswordEditText.setError(null);

            } else {
                passwordEditText.setError("Błąd, podane hasła różnią się");
                dataCorrectWatcher.setPasswordCorrect(false);
            }
        } else {
            dataCorrectWatcher.setPasswordCorrect(false);
            passwordEditText.setError("Brak wprowadzonego hasła!");

        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
}
