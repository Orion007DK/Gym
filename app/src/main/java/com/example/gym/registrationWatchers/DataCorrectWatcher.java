package com.example.gym.registrationWatchers;

import android.graphics.Color;
import android.widget.Button;

//do sprawdzania czy wszystkie dane są poprawnie wprowadzone
public class DataCorrectWatcher {

    private boolean nameCorrect=false;
    private boolean surnameCorrect=false;
    private boolean emailCorrect=false;
    private boolean phoneNumberCorrect=false;
    private boolean passwordCorrect=false;
    private boolean regulationsAccepted=false;
    private boolean dataCorrect=false;
    private Button buttonRegister;

    public DataCorrectWatcher(Button button){
        buttonRegister=button;
    }

    public boolean isNameCorrect() {
        return nameCorrect;
    }

    public boolean isSurnameCorrect() {
        return surnameCorrect;
    }

    public boolean isEmailCorrect() { return emailCorrect; }

    public boolean isPhoneNumberCorrect() { return phoneNumberCorrect; }

    public boolean isPasswordCorrect() { return passwordCorrect; }

    public boolean isRegulationsAccepted() { return regulationsAccepted; }

    public void setNameCorrect(boolean nameCorrect) {
        this.nameCorrect = nameCorrect;
    }

    public void setSurnameCorrect(boolean surnameCorrect) {
        this.surnameCorrect = surnameCorrect;
    }

    public void setEmailCorrect(boolean emailCorrect) {
        this.emailCorrect = emailCorrect;
    }

    public void setPhoneNumberCorrect(boolean phoneNumberCorrect) { this.phoneNumberCorrect = phoneNumberCorrect; }

    public void setPasswordCorrect(boolean passwordCorrect) { this.passwordCorrect = passwordCorrect; }

    public void setRegulationsAccepted(boolean regulationsAccepted) { this.regulationsAccepted = regulationsAccepted; }

    public boolean isDataCorrect() {
        return dataCorrect;
    }

    public boolean isValidated(){
        if(this.nameCorrect && this.surnameCorrect && this.emailCorrect && this.phoneNumberCorrect && this.passwordCorrect && this.regulationsAccepted)
            return true;
        return false;
    }

    public void validation(){
        if(isValidated()){
            dataCorrect=true;
            //buttonRegister.setBackgroundColor(Color.GREEN);
            buttonRegister.setTextColor(Color.GREEN);
            buttonRegister.setEnabled(true);
        }
        else{
            dataCorrect=false;
            buttonRegister.setTextColor(Color.GRAY);
            //buttonRegister.setBackgroundColor(Color.GRAY);
            buttonRegister.setEnabled(false);
        }

    }
}
