package com.example.captemperature;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "temperatureDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TEMPERATURE = "temperature";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEMP_VALUE = "temp_value";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TEMPERATURE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEMP_VALUE + " REAL, " +
                    COLUMN_TIMESTAMP + " TEXT);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE);
        onCreate(db);
    }

    // Insérer la température dans la base de données
    public void insertTemperature(float temperatureValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMP_VALUE, temperatureValue);
        values.put(COLUMN_TIMESTAMP, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        db.insert(TABLE_TEMPERATURE, null, values);
        db.close();
    }

    // Récupérer la dernière température de la base de données
    public float getLatestTemperature() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEMPERATURE, new String[]{COLUMN_TEMP_VALUE}, null, null, null, null, COLUMN_TIMESTAMP + " DESC", "1");

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") float temperature = cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMP_VALUE));
            cursor.close();
            return temperature;
        } else {
            return -1;  // Retourner -1 si aucune température n'est trouvée
        }
    }
}
