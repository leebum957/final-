package com.androidmobile.courseworkemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpenseScreen extends AppCompatActivity {

    private  String idTrip, nameTrip, dateTrip, destinationTrip, assessmentTrip, descriptionTrip;
    ArrayList<String> id, type, amount, time;

    RecyclerView expenseListView;
    ExpenseDetailAdapter expenseDetailAdapter;
    FloatingActionButton addTypeExpense;

    private Toolbar actionSubBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_screen);
        actionSubBar = findViewById(R.id.subActionBar);
        setSupportActionBar(actionSubBar);
        actionSubBar.setTitle("Expenses Detail");

        idTrip = getIntent().getStringExtra("extra_id");
        nameTrip = getIntent().getStringExtra("extra_name");
        dateTrip = getIntent().getStringExtra("extra_date");
        destinationTrip = getIntent().getStringExtra("extra_destination");
        assessmentTrip = getIntent().getStringExtra("extra_assessment");
        descriptionTrip = getIntent().getStringExtra("extra_description");

        actionSubBar.setNavigationIcon(R.drawable.ic_arrow_back);
        actionSubBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseScreen.this, UpdateTripItemScreen.class);
                intent.putExtra("id_holder", String.valueOf(idTrip));
                intent.putExtra("name_holder", String.valueOf(nameTrip));
                intent.putExtra("destination_holder", String.valueOf(destinationTrip));
                intent.putExtra("date_holder", String.valueOf(dateTrip));
                intent.putExtra("assessment_holder", String.valueOf(assessmentTrip));
                intent.putExtra("description_holder", String.valueOf(descriptionTrip));
                startActivity(intent);
            }
        });

        id = new ArrayList<>();
        type = new ArrayList<>();
        amount = new ArrayList<>();
        time = new ArrayList<>();

        addTypeExpense = findViewById(R.id.fabAddExpense);
        addTypeExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseScreen.this, AddExpenseScreen.class);
                intent.putExtra("trip_id_extra", idTrip);
                intent.putExtra("trip_name_extra", nameTrip);
                intent.putExtra("trip_destination_extra", destinationTrip);
                intent.putExtra("trip_date_extra", dateTrip);
                intent.putExtra("trip_assessment_extra", assessmentTrip);
                intent.putExtra("trip_description_extra", descriptionTrip);
                startActivity(intent);
            }
        });

        getAllDataExpenseOfTrip();
        expenseListView = findViewById(R.id.rcvExpense);
        expenseDetailAdapter = new ExpenseDetailAdapter(ExpenseScreen.this, id, type, amount, time);
        expenseListView.setAdapter(expenseDetailAdapter);
        expenseListView.setLayoutManager(new LinearLayoutManager(ExpenseScreen.this));
    }

    private void getAllDataExpenseOfTrip() {
        DatabaseHelper db = new DatabaseHelper(ExpenseScreen.this);
        Cursor cursor = db.displayExpenseOfTripById(Integer.parseInt(idTrip));
        while (cursor.moveToNext()){
            id.add(cursor.getString(0));
            type.add(cursor.getString(1));
            amount.add(cursor.getString(2));
            time.add(cursor.getString(3));
        }
    }
}