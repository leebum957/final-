package com.androidmobile.courseworkemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddExpenseScreen extends AppCompatActivity {

    EditText amount;
    TextView time;
    Spinner spinnerType;

    private final String[] typeExpenseList = {
            "Food",
            "Travel",
            "Transport",
            "Phone",
            "Medicine"
    };
    private int tripYear, tripMonth, tripDay;

    private String id_intent, name_intent, date_intent, destination_intent, assessment_intent, description_intent;
    private Toolbar addExpenseActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_screen);
        addExpenseActionBar = findViewById(R.id.subActionBar);
        setSupportActionBar(addExpenseActionBar);
        addExpenseActionBar.setTitle("Add Type Expense");

        amount = findViewById(R.id.editTextAmount);
        time = findViewById(R.id.tvTime);

        spinnerType = findViewById(R.id.spinnerTypeExpense);
        ArrayAdapter<String> dataTypeExpenseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeExpenseList);
        dataTypeExpenseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter((dataTypeExpenseAdapter));

        id_intent = getIntent().getStringExtra("trip_id_extra");
        name_intent = getIntent().getStringExtra("trip_name_extra");
        date_intent = getIntent().getStringExtra("trip_date_extra");
        destination_intent = getIntent().getStringExtra("trip_destination_extra");
        assessment_intent = getIntent().getStringExtra("trip_assessment_extra");
        description_intent = getIntent().getStringExtra("trip_description_extra");

        addExpenseActionBar.setNavigationIcon(R.drawable.ic_arrow_back);
        addExpenseActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExpenseScreen.this, ExpenseScreen.class);
                intent.putExtra("extra_id", String.valueOf(id_intent));
                intent.putExtra("extra_name", String.valueOf(name_intent));
                intent.putExtra("extra_destination", String.valueOf(destination_intent));
                intent.putExtra("extra_date", String.valueOf(date_intent));
                intent.putExtra("extra_assessment", String.valueOf(assessment_intent));
                intent.putExtra("extra_description", String.valueOf(description_intent));
                startActivity(intent);
            }
        });
    }

    public void createExpense(View view) {
        String type_expense = spinnerType.getSelectedItem().toString();
        String amount_expense = amount.getText().toString();
        String date_expense = time.getText().toString();

        if (TextUtils.isEmpty(amount_expense)) {
            handleErrorEmptyAlert();
            return;
        }

        if (date_expense.matches("Select time of Expense")) {
            handleErrorEmptyAlert();
            return;
        }

        DatabaseHelper db = new DatabaseHelper(AddExpenseScreen.this);
        db.createExpenseByTripId(type_expense, amount_expense, date_expense, Integer.parseInt(id_intent));


        Intent intent = new Intent(AddExpenseScreen.this, ExpenseScreen.class);
        intent.putExtra("extra_id", String.valueOf(id_intent));
        intent.putExtra("extra_name", String.valueOf(name_intent));
        intent.putExtra("extra_destination", String.valueOf(destination_intent));
        intent.putExtra("extra_date", String.valueOf(date_intent));
        intent.putExtra("extra_assessment", String.valueOf(assessment_intent));
        intent.putExtra("extra_description", String.valueOf(description_intent));
        startActivity(intent);
    }

    public void selectTimeOfExpense(View view){
        final Calendar currentDate = Calendar.getInstance();
        tripYear = currentDate.get(Calendar.YEAR);
        tripMonth = currentDate.get(Calendar.MONTH);
        tripDay = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        time.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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