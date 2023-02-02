package com.grupo13.olimpiapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.grupo13.olimpiapp.AdminSQLiteOpenHelper;
import com.grupo13.olimpiapp.Modelo.Almacen;
import com.grupo13.olimpiapp.SeleccionParametros;

public class AlmacenDAO {

    private AdminSQLiteOpenHelper admin;
    private Context context;
    private SQLiteDatabase bd;

    public AlmacenDAO(Context context){
        this.context=context;
    }


    public AlmacenDAO abrirConexion(){
        admin=new AdminSQLiteOpenHelper(context);
        bd = admin.getWritableDatabase();
        return this;
    }


    public void insert(int codigo, int notoriedad, int tiempo, int trabajadores, int presupuesto, int situacion, int puntuacion,String ciudad, int medicion_0, int medicion_1, int medicion_2,String dificultad){
        ContentValues registro = new ContentValues();

        registro.put("codigo", codigo);
        registro.put("notoriedad", notoriedad);
        registro.put("tiempo", tiempo);
        registro.put("trabajadores", trabajadores);
        registro.put("presupuesto", presupuesto);
        registro.put("situacion", situacion);
        registro.put("puntuacion", puntuacion);


        registro.put("ciudad", ciudad);

        registro.put("medicion_0", medicion_0);
        registro.put("medicion_1", medicion_1);
        registro.put("medicion_2", medicion_2);
        registro.put("dificultad", dificultad);
        bd.insert("almacen", null, registro);
    }

    public void delete(int codigo){
        bd.delete("almacen","codigo="+codigo, null);
    }


    public Almacen consulta(int codigo) {
        Almacen almacen=null;
        Cursor fila = bd.rawQuery(
                "select presupuesto, tiempo,trabajadores,notoriedad,ciudad,situacion,puntuacion,medicion_0,medicion_1,medicion_2,dificultad from almacen where codigo=" + codigo, null);
        if (fila.moveToFirst()) {
            almacen=new Almacen(codigo,fila.getInt(0),fila.getInt(1),fila.getInt(2),fila.getInt(3),fila.getString(4),fila.getInt(5),fila.getInt(6),fila.getInt(7),fila.getInt(8),fila.getInt(9),fila.getString(10));
        }

        return almacen;

    }

    public void actualizar(Almacen almacen){
        ContentValues registro = new ContentValues();
        registro.put("codigo", almacen.getCodigo());
        registro.put("notoriedad", almacen.getNotoriedad());
        registro.put("presupuesto", almacen.getPresupuesto());
        registro.put("trabajadores", almacen.getTrabajadores());
        registro.put("tiempo", almacen.getTiempo());
        registro.put("puntuacion", almacen.getPuntuacion());
        registro.put("situacion", almacen.getSituacion());

        registro.put("medicion_0", almacen.getMedicion_0());
        registro.put("medicion_1", almacen.getMedicion_1());
        registro.put("medicion_2", almacen.getMedicion_2());

        bd.update("almacen", registro, "codigo=" + almacen.getCodigo(), null);

    }



}
