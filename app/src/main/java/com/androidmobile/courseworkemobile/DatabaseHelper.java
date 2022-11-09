package com.androidmobile.courseworkemobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context contextDB;
    private static final String DATABASE = "Course_Work.db";
    private static final int VERSION_DB = 1;

    private static final String TRIP_TABLE = "Trips";
    private static final String TRIP_ID = "id";
    private static final String NAME = "name";
    private static final String DESTINATION = "destination";
    private static final String DATE = "date_of_trip";
    private static final String RISK_ASSESSMENT = "assessment";
    private static final String DESCRIPTION = "description";

    private static final String EXPENSES_TABLE = "Expenses";
    private static final String EXPENSE_ID = "id";
    private static final String TYPE = "type";
    private static final String AMOUNT = "amount";
    private static final String TIME = "time_of_trip";
    private static final String TRIP_ID_FK = "trip_id";


    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE, null, VERSION_DB);
        this.contextDB = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTripTable = "CREATE TABLE " + TRIP_TABLE + " (" + TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                             NAME + " TEXT, " +
                                                             DESTINATION + " TEXT, " +
                                                             DATE + " TEXT, " +
                                                             RISK_ASSESSMENT  + " TEXT, " +
                                                             DESCRIPTION + " TEXT);";

        String queryCreateExpenseTable = "CREATE TABLE " + EXPENSES_TABLE + " (" + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                             TYPE + " TEXT, " +
                                                             AMOUNT + " TEXT, " +
                                                             TIME + " TEXT, " +
                                                             TRIP_ID_FK  + " INTERGER NOT NULL, " +
                                                             " FOREIGN KEY (" + TRIP_ID_FK + ") REFERENCES " + TRIP_TABLE+" (" + EXPENSE_ID +"))";
        db.execSQL(queryCreateTripTable);
        db.execSQL(queryCreateExpenseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS '" + EXPENSES_TABLE + "'");
        onCreate(db);
    }

    void createTripItem(String name, String destination, String date, String assessment, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valueItem = new ContentValues();

        valueItem.put(NAME, name);
        valueItem.put(DESTINATION, destination);
        valueItem.put(DATE, date);
        valueItem.put(RISK_ASSESSMENT, assessment);
        valueItem.put(DESCRIPTION, description);

         db.insert(TRIP_TABLE,null, valueItem);

    }

    void updateTripItem(String id,String name, String destination, String date, String assessment, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valueItem = new ContentValues();

        valueItem.put(NAME, name);
        valueItem.put(DESTINATION, destination);
        valueItem.put(DATE, date);
        valueItem.put(RISK_ASSESSMENT, assessment);
        valueItem.put(DESCRIPTION, description);

        db.update(TRIP_TABLE, valueItem, "id=?", new String[]{id});

    }

    Cursor displayAllTrip(String param) {
        if (param.length() == 0) {
            String query = "SELECT * FROM " + TRIP_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if(db != null){
                cursor = db.rawQuery(query, null);
            }
            return cursor;
        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = null;
            String query = "SELECT * FROM "+TRIP_TABLE+" WHERE "+ NAME +" LIKE '%"+param+"%'" + " OR " + DATE + " LIKE '%"+param+"%'";
            cursor = db.rawQuery(query,null);
            return cursor;
        }
    }

    public void delete() {
        SQLiteDatabase exeDelete = this.getWritableDatabase();
        exeDelete.execSQL("DELETE FROM " + TRIP_TABLE);
    }

    public void deleteTripItemById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TRIP_TABLE, "id=?", new String[]{id});
        db.delete(EXPENSES_TABLE, "trip_id=?", new String[]{id});
    }

    // Expense Table
    public void createExpenseByTripId(String type, String amount, String time, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataExpense = new ContentValues();
        dataExpense.put(TYPE, type);
        dataExpense.put(AMOUNT, amount);
        dataExpense.put(TIME, time);
        dataExpense.put(TRIP_ID_FK, tripId);

        db.insert(EXPENSES_TABLE, null, dataExpense);

        db.close();
    }

    public Cursor displayExpenseOfTripById(int tripId) {
        String query = "SELECT * FROM " + EXPENSES_TABLE + " WHERE " + TRIP_ID_FK + " = " + tripId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
