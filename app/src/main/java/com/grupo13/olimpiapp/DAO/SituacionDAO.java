package com.grupo13.olimpiapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grupo13.olimpiapp.AdminSQLiteOpenHelper;
import com.grupo13.olimpiapp.Modelo.Situacion;

public class SituacionDAO {
    private AdminSQLiteOpenHelper admin;
    private Context context;
    private SQLiteDatabase bd;

    public SituacionDAO(Context context){
        this.context=context;
    }


    public SituacionDAO abrirConexion(){
        admin=new AdminSQLiteOpenHelper(context);
        bd = admin.getWritableDatabase();
        return this;
    }



    public void insert(Situacion situacion){

        ContentValues registro = new ContentValues();


        registro.put("numero", situacion.getNumero());

        registro.put("titulo", situacion.getTitulo());

        registro.put("descripcion", situacion.getDescripcion());

        bd.insert("situacion", null, registro);

    }

    public Situacion consulta(int numero){
        Cursor fila1 = bd.rawQuery(
                "select titulo,descripcion from situacion where numero="+numero, null);
        Situacion situacion=new Situacion();
        if(fila1.moveToFirst()){
            situacion.setNumero(numero);
            situacion.setTitulo(fila1.getString(0));
            situacion.setDescripcion(fila1.getString(1));
        }

        return situacion;

    }
}
