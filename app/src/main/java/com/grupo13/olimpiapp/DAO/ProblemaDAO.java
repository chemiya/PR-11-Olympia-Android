package com.grupo13.olimpiapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grupo13.olimpiapp.AdminSQLiteOpenHelper;
import com.grupo13.olimpiapp.Modelo.Problema;
import com.grupo13.olimpiapp.Modelo.Situacion;

public class ProblemaDAO {
    private AdminSQLiteOpenHelper admin;
    private Context context;
    private SQLiteDatabase bd;

    public ProblemaDAO(Context context){
        this.context=context;
    }


    public ProblemaDAO abrirConexion(){
        admin=new AdminSQLiteOpenHelper(context);
        bd = admin.getWritableDatabase();
        return this;
    }

    public void insert(Problema problema){

        ContentValues registro = new ContentValues();


        registro.put("numero", problema.getNumero());
        registro.put("situacion", problema.getSituacion());
        registro.put("titulo",problema.getTitulo());

        registro.put("descripcion", problema.getDescripcion());

        registro.put("opcionA", problema.getOpcionA());
        registro.put("opcionB", problema.getOpcionB());
        registro.put("opcionC", problema.getOpcionC());

        bd.insert("problema", null, registro);

    }

    public String[] consultaTitulos(int situacion){
        String titulos[]=new String[3];

        Cursor fila1 = bd.rawQuery(
                "select numero,titulo from problema where situacion="+situacion, null);

        while(fila1.moveToNext()){
            titulos[fila1.getInt(0)]=fila1.getString(1);
        }
        return titulos;
    }

    public Problema consulta(int situacion,int numero){
        Problema problemaClase=null;
        Cursor fila1 = bd.rawQuery(
                "select titulo,descripcion,opcionA,opcionB,opcionC from problema where situacion="+situacion+" and numero="+numero, null);

       if(fila1.moveToFirst()){
            problemaClase=new Problema(situacion,numero,fila1.getString(0),fila1.getString(1),fila1.getString(2),fila1.getString(3),fila1.getString(4));
        }
        return problemaClase;
    }
}
