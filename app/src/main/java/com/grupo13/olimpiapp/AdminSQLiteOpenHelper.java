package com.grupo13.olimpiapp;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int version = 1;
    private static final String database = "almacen10.db";

    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, database, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table almacen(codigo int primary key,presupuesto real, tiempo real, trabajadores real,notoriedad real,ciudad text,situacion int,puntuacion int,medicion_0 int,medicion_1 int, medicion_2 int,dificultad text)");
        db.execSQL("create table decisiones(codigo int primary key,decision text, descripcion text )");
        db.execSQL("create table situacion(numero int, titulo text,descripcion text)");
        db.execSQL("create table problema(numero int,situacion int, titulo text,descripcion text,opcionA text,opcionB text,opcionC text)");
        db.execSQL("create table usuario(nombre text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
