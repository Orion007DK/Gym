package com.example.gym.registrationWatchers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.gym.R;


public class EmailTextEditWatcher implements TextWatcher {
    private EditText emailEditText;
    private DataCorrectWatcher dataCorrectWatcher;
    private Context context;

    public EmailTextEditWatcher(EditText emailEditText, DataCorrectWatcher dataCorrectWatcher, Context context){
        this.emailEditText=emailEditText;
        this.dataCorrectWatcher=dataCorrectWatcher;
        this.context=context;
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
                emailEditText.setError(context.getString(R.string.registration_incorrect_email_error));
                dataCorrectWatcher.setEmailCorrect(false);
            }
        } else {
            dataCorrectWatcher.setEmailCorrect(false);
            emailEditText.setError(context.getString(R.string.registration_no_data_error));

        }
        //sprawdzanie czy wszystkie pola zawierają poprawne dane, jeśli tak
        //wyświetlany jest przycisk umożliwiający przejście dalej
        dataCorrectWatcher.validation();
    }
}
