package com.example.gym.changePersonalDataWatchers;

import android.graphics.Color;
import android.widget.Button;

import com.bartbergmans.gradientbutton.views.GradientButton;

public class ChangeDataCorrectWatcher {

    private boolean nameCorrect=true;
    private boolean nameChanged=false;
    private boolean surnameCorrect=true;
    private boolean surnameChanged=false;
    private boolean emailCorrect=true;
    private boolean emailChanged=false;
    private boolean phoneNumberCorrect=true;
    private boolean phoneNumberChanged=false;
    private boolean dataCorrect=false;
    private boolean dataChanged=false;

    Button buttonSaveChanges;

    public ChangeDataCorrectWatcher(Button button){
        buttonSaveChanges=button;
    }

    public boolean isNameCorrect() {
        return nameCorrect;
    }
    public boolean isSurnameCorrect() {
        return surnameCorrect;
    }
    public boolean isEmailCorrect() { return emailCorrect; }
    public boolean isPhoneNumberCorrect() { return phoneNumberCorrect; }
    public boolean isDataCorrect() { return dataCorrect; }

    public boolean isNameChanged() { return nameChanged; }
    public boolean isSurnameChanged() { return surnameChanged; }
    public boolean isEmailChanged() { return emailChanged; }
    public boolean isPhoneNumberChanged() { return phoneNumberChanged; }

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

    public void setNameChanged(boolean nameChanged) { this.nameChanged = nameChanged; }
    public void setSurnameChanged(boolean surnameChanged) { this.surnameChanged = surnameChanged; }
    public void setEmailChanged(boolean emailChanged) { this.emailChanged = emailChanged; }
    public void setPhoneNumberChanged(boolean phoneNumberChanged) { this.phoneNumberChanged = phoneNumberChanged; }
    public void setDataChanged(boolean dataChanged) { this.dataChanged = dataChanged; }

    public boolean isValidated(){
        if(this.nameCorrect && this.surnameCorrect && this.emailCorrect && this.phoneNumberCorrect)
            return true;
        return false;
    }

    public boolean isChanged(){
        if(this.nameChanged || this.surnameChanged || this.emailChanged || this.phoneNumberChanged)
            return true;
        return false;
    }

    public void validation(){
        if(isChanged()) {
            dataChanged=true;
            buttonSaveChanges.setEnabled(true);
            if (isValidated()) {
                dataCorrect = true;
                //buttonSaveChanges.setBackgroundColor(Color.GREEN);
                buttonSaveChanges.setTextColor(Color.GREEN);
            } else {
                dataCorrect = false;
                buttonSaveChanges.setTextColor(Color.RED);
                //buttonSaveChanges.setBackgroundColor(Color.GRAY);
            }
        } else {
            dataChanged=false;
            buttonSaveChanges.setTextColor(Color.WHITE);
          //  buttonSaveChanges.setBackgroundColor(Color.BLUE);//zmienić na początkowy kolor
            buttonSaveChanges.setEnabled(false);
        }

    }


}
