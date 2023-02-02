package com.grupo13.olimpiapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.grupo13.olimpiapp.AdminSQLiteOpenHelper;
import com.grupo13.olimpiapp.Modelo.Decision;

import java.util.ArrayList;

public class DecisionesDAO {
    private AdminSQLiteOpenHelper admin;
    private Context context;
    private SQLiteDatabase bd;

    public DecisionesDAO(Context context){
        this.context=context;
    }


    public DecisionesDAO abrirConexion(){
        admin=new AdminSQLiteOpenHelper(context);
        bd = admin.getWritableDatabase();
        return this;
    }


    public void insert(int codigo){
        ContentValues registro = new ContentValues();


        registro.put("codigo", codigo);

        registro.put("decision", "pendiente");

        registro.put("descripcion", "pendiente");

        bd.insert("decisiones", null, registro);
    }

    public void delete(int codigo){


        bd.delete("decisiones","codigo="+codigo, null);
    }

    public ArrayList<Decision> consultaTodas(){
        Decision decision;
        ArrayList<Decision> arrayDecisiones=new ArrayList<Decision>();

        Cursor fila1 = bd.rawQuery(
                "select codigo,decision,descripcion from decisiones", null);
        while(fila1.moveToNext()){
            decision=new Decision(fila1.getInt(0),fila1.getString(1),fila1.getString(2));
            arrayDecisiones.add(decision);

        }

        return arrayDecisiones;
    }

    public ArrayList<Decision> consultaPorSituacion(int situacion){
        Decision decision;
        ArrayList<Decision> arrayDecisiones=new ArrayList<Decision>();

        Cursor fila1=null;

        if(situacion==0){
           fila1= bd.rawQuery(
                    "select codigo,decision,descripcion from decisiones where codigo<10", null);
    Log.d("busco en la cero:","busco en la cero");

        }else if(situacion==1){
            fila1=   bd.rawQuery(
                    "select codigo,decision,descripcion from decisiones where codigo<20 and codigo>9", null);

        }else {
            fila1=   bd.rawQuery(
                    "select codigo,decision,descripcion from decisiones where codigo>19", null);

        }




        while(fila1.moveToNext()){
            decision=new Decision(fila1.getInt(0),fila1.getString(1),fila1.getString(2));
            arrayDecisiones.add(decision);

        }

        return arrayDecisiones;
    }

    public Decision consultaSituacionProblema(int situacion, int problema) {
        Decision decision=null;
        Cursor filaPregunta0 = bd.rawQuery(
                "select codigo,decision,descripcion from decisiones where codigo="+situacion+""+problema, null);
        if(filaPregunta0.moveToFirst()){
            decision=new Decision(filaPregunta0.getInt(0),filaPregunta0.getString(1),filaPregunta0.getString(2));
        }

        return decision;
    }


    public void actualizarDecision(Decision decision){
        ContentValues registro = new ContentValues();
        registro.put("codigo",decision.getCodigo());

        registro.put("decision", decision.getDecision());
        registro.put("descripcion", decision.getDescripcion());
        bd.update("decisiones", registro, "codigo="+decision.getCodigo(), null);

    }
}
