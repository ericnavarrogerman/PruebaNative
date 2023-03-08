package com.navadev.pruebanative.core.repository.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.navadev.pruebanative.core.db.DatabaseHelper;
import com.navadev.pruebanative.feature.add.model.Incidente;

import java.util.ArrayList;
import java.util.List;

public class IncidenteDaoImpl implements IncidenteDao {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public IncidenteDaoImpl(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean insert(Incidente entidad) {
        ContentValues values = new ContentValues();
        values.put("fecha", entidad.getFecha().toString());
        values.put("ubicacion", entidad.getUbicacion());
        values.put("descripcion", entidad.getDescripcion());
        values.put("foto_path", entidad.getFoto());

        long result = db.insert("incidente", null, values);
        return (result != -1);
    }

    @Override
    public boolean update(Incidente entidad) {
        ContentValues values = new ContentValues();
        values.put("fecha", entidad.getFecha());
        values.put("ubicacion", entidad.getUbicacion());
        values.put("descripcion", entidad.getDescripcion());
        values.put("foto_path", entidad.getFoto());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(entidad.getId())};

        int result = db.update("incidente", values, whereClause, whereArgs);
        return (result > 0);
    }

    @Override
    public boolean delete(Incidente entidad) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(entidad.getId())};

        int result = db.delete("incidente", whereClause, whereArgs);
        return (result > 0);
    }

    @Override
    public List<Incidente> getAll() {
        List<Incidente> lista = new ArrayList<>();

        String[] projection = {
                "id",
                "fecha",
                "ubicacion",
                "descripcion",
                "foto_path"
        };

        Cursor cursor = db.query(
                "incidente",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Incidente incidente = new Incidente();
            incidente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            incidente.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
            incidente.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")));
            incidente.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
            incidente.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto_path")));

            lista.add(incidente);
        }

        cursor.close();
        return lista;
    }
    @Override
    public int getNextAvailableId() {
        int siguienteId = 1;

        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM incidente", null);
        if (cursor.moveToFirst()) {
            siguienteId = cursor.getInt(0) + 1;
        }

        cursor.close();
        return siguienteId;
    }

    @Override
    public boolean deleteAll() {
        int result = db.delete("incidente", null, null);
        return (result > 0);
    }


}