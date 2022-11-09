package com.androidmobile.courseworkemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateTripItemScreen extends AppCompatActivity {

    String id_holder, name_holder, date_holder, destination_holder, assessment_holder, description_holder;
    private String idTrip, nameUpdate, destinationUpdate, dateUpdate, descriptionUpdate;

    EditText nameTrip, destinationTrip, description;
    TextView dateOfTrip;
    RadioButton valueYes;
    RadioButton valueNo;

    private int tripYear, tripMonth, tripDay;
    private Toolbar updateTripBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip_screen);
        updateTripBar = findViewById(R.id.subActionBar);
        setSupportActionBar(updateTripBar);
        updateTripBar.setTitle("Edit Trip");

        nameTrip = findViewById(R.id.editTextName);
        destinationTrip = findViewById(R.id.editTextDestination);
        description = findViewById(R.id.editTextDescription);
        dateOfTrip = findViewById(R.id.tvDate);
        valueYes = findViewById(R.id.checkYes);
        valueNo = findViewById(R.id.checkNo);

        getAndSetIntentData();

        nameTrip.setText(name_holder);
        destinationTrip.setText(destination_holder);
        dateOfTrip.setText(date_holder);
        description.setText(description_holder);

        if ("Yes".equals(assessment_holder) ) {
            valueYes.setChecked(true);
        } else if ("No".equals(assessment_holder)) {
            valueNo.setChecked(true);
        }
    }

    private void getAndSetIntentData(){
        if(getIntent().hasExtra("id_holder") && getIntent().hasExtra("name_holder") &&
                getIntent().hasExtra("date_holder") && getIntent().hasExtra("assessment_holder")
                && getIntent().hasExtra("destination_holder") && getIntent().hasExtra("description_holder")){
            id_holder = getIntent().getStringExtra("id_holder");
            name_holder = getIntent().getStringExtra("name_holder");
            date_holder = getIntent().getStringExtra("date_holder");
            assessment_holder = getIntent().getStringExtra("assessment_holder");
            destination_holder = getIntent().getStringExtra("destination_holder");
            description_holder = getIntent().getStringExtra("description_holder");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTripItemById(View view) {
        idTrip = id_holder;
        nameUpdate = nameTrip.getText().toString().trim();
        dateUpdate = dateOfTrip.getText().toString().trim();
        destinationUpdate = destinationTrip.getText().toString().trim();
        descriptionUpdate = description.getText().toString().trim();

        String assessmentUpdate = "";
        if (valueYes.isChecked()) {
            assessmentUpdate = valueYes.getText().toString().trim();
        }

        if (valueNo.isChecked()) {
            assessmentUpdate = valueNo.getText().toString().trim();
        }

        DatabaseHelper db = new DatabaseHelper(UpdateTripItemScreen.this);
        db.updateTripItem(idTrip, nameUpdate, destinationUpdate, dateUpdate, assessmentUpdate ,descriptionUpdate);

        new AlertDialog.Builder(this).setTitle("Update detail trip").setMessage(
                        "\nName: " + nameUpdate +
                        "\nDestination: " + destinationUpdate +
                        "\nDate of trip: " + dateUpdate +
                        "\nAssessment: " + assessmentUpdate +
                        "\nDescription: " + descriptionUpdate
        ).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UpdateTripItemScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }

    public void selectDateOfTripToUpdate(View view){
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

    public void expensesOfTrip(View view) {
        Intent intent = new Intent(UpdateTripItemScreen.this, ExpenseScreen.class);
        intent.putExtra("extra_id", id_holder);
        intent.putExtra("extra_name", name_holder);
        intent.putExtra("extra_destination", destination_holder);
        intent.putExtra("extra_date", date_holder);
        intent.putExtra("extra_assessment", assessment_holder);
        intent.putExtra("extra_description", description_holder);
        startActivity(intent);
    }

    public void onclickDeleteTripItem(View view) {
        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(UpdateTripItemScreen.this);
        confirmAlert.setTitle("Delete Warning");
        confirmAlert.setMessage("Are you sure you want to delete all trip forever !!!!!!!");
        confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(UpdateTripItemScreen.this);
                db.deleteTripItemById(id_holder);
                Intent intent = new Intent(UpdateTripItemScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
        confirmAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        confirmAlert.create().show();
    }
}