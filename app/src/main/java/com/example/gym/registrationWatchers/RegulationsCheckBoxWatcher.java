package com.example.gym.registrationWatchers;

import android.widget.CompoundButton;

public class RegulationsCheckBoxWatcher implements CompoundButton.OnCheckedChangeListener {

    DataCorrectWatcher dataCorrectWatcher;

    public RegulationsCheckBoxWatcher(DataCorrectWatcher dataCorrectWatcher){
        this.dataCorrectWatcher=dataCorrectWatcher;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        dataCorrectWatcher.setRegulationsAccepted(isChecked);
        dataCorrectWatcher.validation();
    }
}
