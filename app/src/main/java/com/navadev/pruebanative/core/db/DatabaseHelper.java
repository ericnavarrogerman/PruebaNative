package com.navadev.pruebanative.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "incidentes.db";

    private static final String CREATE_TABLE_INCIDENTE = "CREATE TABLE incidente (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fecha TEXT," +
            "ubicacion TEXT," +
            "numero_incidente TEXT," +
            "descripcion TEXT," +
            "foto_path TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INCIDENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS incidente");
        onCreate(db);
    }

}
