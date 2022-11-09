package com.androidmobile.courseworkemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

public class AddTripScreen extends AppCompatActivity {

    EditText nameTrip, destinationTrip, description;
    TextView dateOfTrip;
    RadioButton valueYes;
    RadioButton valueNo;

    private int tripYear, tripMonth, tripDay;
    private String nameInput, destinationInput, dateInput, descriptionInput;
    private Toolbar addTripBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_screen);
        addTripBar = findViewById(R.id.subActionBar);
        setSupportActionBar(addTripBar);
        addTripBar.setTitle("New Trip");

        nameTrip = findViewById(R.id.editTextName);
        destinationTrip = findViewById(R.id.editTextDestination);
        description = findViewById(R.id.editTextDescription);
        dateOfTrip = findViewById(R.id.tvDate);
        valueYes = findViewById(R.id.checkYes);
        valueNo = findViewById(R.id.checkNo);
    }

    public void insertValueOfTrip(View view) {
        nameInput = nameTrip.getText().toString().trim();
        destinationInput = destinationTrip.getText().toString().trim();
        dateInput = dateOfTrip.getText().toString().trim();
        descriptionInput = description.getText().toString().trim();

        String assessmentInput = "";
        if (valueYes.isChecked()) {
            assessmentInput = valueYes.getText().toString().trim();
        }

        if (valueNo.isChecked()) {
            assessmentInput = valueNo.getText().toString().trim();
        }

        if (TextUtils.isEmpty(nameInput)) {
            handleErrorEmptyAlert();
            return;
        }

        if (TextUtils.isEmpty(destinationInput)) {
            handleErrorEmptyAlert();
            return;
        }

        if (dateInput.matches("Select date of Trip")) {
            handleErrorEmptyAlert();
            return;
        }

        if (TextUtils.isEmpty(descriptionInput)) {
            handleErrorEmptyAlert();
            return;
        }

        DatabaseHelper db = new DatabaseHelper(AddTripScreen.this);
        db.createTripItem(nameInput, destinationInput, dateInput, assessmentInput, descriptionInput);

        new AlertDialog.Builder(this).setTitle("Details trip").setMessage(
                        "\nName: " + nameInput +
                        "\nDestination: " + destinationInput +
                        "\nDate of trip: " + dateInput +
                        "\nAssessment: " + assessmentInput +
                        "\nDescription: " + descriptionInput
        ).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AddTripScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }

    public void selectDateOfTrip(View view){

        final Calendar currentDate = Calendar.getInstance();
        tripYear = currentDate.get(Calendar.YEAR);
        tripMonth = currentDate.get(Calendar.MONTH);
        tripDay = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateOfTrip.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, tripYear, tripMonth, tripDay);
        datePickerDialog.show();
    }

    private void handleErrorEmptyAlert() {
        new AlertDialog.Builder(this).setTitle("WARNING").setMessage(
                "Required fields is empty"
        ).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}