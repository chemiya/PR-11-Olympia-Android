package com.grupo13.olimpiapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grupo13.olimpiapp.AdminSQLiteOpenHelper;
import com.grupo13.olimpiapp.Modelo.Situacion;
import com.grupo13.olimpiapp.Modelo.Usuario;

public class UsuarioDAO {
    private AdminSQLiteOpenHelper admin;
    private Context context;
    private SQLiteDatabase bd;

    public UsuarioDAO(Context context){
        this.context=context;
    }


    public UsuarioDAO abrirConexion(){
        admin=new AdminSQLiteOpenHelper(context);
        bd = admin.getWritableDatabase();
        return this;
    }



    public void insert(Usuario usuario){

        ContentValues registro = new ContentValues();

        registro.put("nombre", usuario.getNombre());

        bd.insert("usuario", null, registro);

    }

    public Usuario consulta(){
        Cursor fila1 = bd.rawQuery(
                "select nombre from usuario order by rowid DESC limit 1", null);
        Usuario usuario = new Usuario();
        if(fila1.moveToFirst()){
            usuario.setNombre(fila1.getString(0));
        }

        return usuario;

    }
}
