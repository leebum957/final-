package com.androidmobile.courseworkemobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView tripView;
    FloatingActionButton tripScreen;
    EditText searchTripInput;
    Button searchButton;
    String param;

    DatabaseHelper db;
    ArrayList<String> id, name, destination, date, assessment, description;
    TripListAdapter tripListAdapter;

    private Toolbar homeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeBar = findViewById(R.id.subActionBar);
        setSupportActionBar(homeBar);
        homeBar.setNavigationIcon(null);

        tripView = findViewById(R.id.rcvTrip);
        searchTripInput = findViewById(R.id.searchInput);

        tripScreen = findViewById(R.id.floatingActionButton);
        tripScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTripScreen.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        destination = new ArrayList<>();
        date = new ArrayList<>();
        assessment = new ArrayList<>();
        description = new ArrayList<>();

        searchButton = findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                param = searchTripInput.getText().toString();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("param", param);
                startActivity(intent);
            }
        });

        searchTripInput.setText(getIntent().getStringExtra("param"));
        if (searchTripInput.length() == 0) {
            param = searchTripInput.getText().toString();
            storeDataInArrays(param);
        } else if (searchTripInput.length() > 0) {
            String paramItem = searchTripInput.getText().toString();
            storeDataInArrays(paramItem);
        }

        tripListAdapter = new TripListAdapter(MainActivity.this, this, id, name, destination, date, assessment, description);
        tripView.setAdapter(tripListAdapter);
        tripView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArrays(String param){
        Cursor cursor = db.displayAllTrip(param);
        while (cursor.moveToNext()){
            id.add(cursor.getString(0));
            name.add(cursor.getString(1));
            destination.add(cursor.getString(2));
            date.add(cursor.getString(3));
            assessment.add(cursor.getString(4));
            description.add(cursor.getString(5));
        }
    }

    public void deleteListTrip(View view) {
        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(MainActivity.this);
        confirmAlert.setTitle("Delete Warning");
        confirmAlert.setMessage("Are you sure you want to delete all trip forever !!!!!!!");
        confirmAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.delete();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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