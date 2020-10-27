package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;

import com.example.gym.activites.classesPlan.ClassesListAdapter;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;

public class ClassesDetailsActivity extends AppCompatActivity {

    private TextView textViewClassesTitle;
    private TextView textViewTrainerNameSurname;
    private TextView textViewClassesDateValue;
    private TextView textViewClassesTimeValue;
    private TextView textViewClassesFreeSlotsValue;
    private TextView textViewClassesDescriptionValue;
    private TextView textViewRequiredGymTicketTypeValue;
    private Button buttonCheckTicket;
    private Button buttonAddClassses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_details);
        idInit();

        textViewClassesTitle.setText(getIntent().getStringExtra(ClassesListAdapter.CLASSES_NAME));
        Calendar calendarSelectedDate = (Calendar)getIntent().getSerializableExtra(ClassesListAdapter.CALENDAR_SELECTED_DATE);
        textViewClassesDateValue.setText(DateFormat.format("dd.MM.yyyy",calendarSelectedDate));
        Time classesStartTime = (Time)getIntent().getSerializableExtra(ClassesListAdapter.CLASSES_START_TIME);
        Time classesEndTime = (Time)getIntent().getSerializableExtra(ClassesListAdapter.CLASSES_END_TIME);
        String time =DateFormat.format("HH:mm",classesStartTime)+" - "+DateFormat.format("HH:mm",classesEndTime);
        textViewClassesTimeValue.setText(time);

    }


    private void idInit(){
        textViewClassesTitle=findViewById(R.id.textViewClassesTitle);
        textViewTrainerNameSurname=findViewById(R.id.textViewTrainerNameSurname);
        textViewClassesDateValue=findViewById(R.id.textViewClassesDateValue);
        textViewClassesTimeValue=findViewById(R.id.textViewClassesTimeValue);
        textViewClassesFreeSlotsValue=findViewById(R.id.textViewClassesFreeSlotsValue);
        textViewClassesDescriptionValue=findViewById(R.id.textViewClassesDescriptionValue);
        textViewRequiredGymTicketTypeValue=findViewById(R.id.textViewRequiredGymTicketTypeValue);
        buttonCheckTicket=findViewById(R.id.buttonCheckTicket);
        buttonAddClassses=findViewById(R.id.buttonAddClassses);
    }
}
