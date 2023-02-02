package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grupo13.olimpiapp.DAO.UsuarioDAO;
import com.grupo13.olimpiapp.Modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    Button boton_entrar;
    EditText entrada_usuario;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton_entrar=findViewById(R.id.boton_entrar);
        entrada_usuario=findViewById(R.id.entrada_user);

        boton_entrar.setOnClickListener(view -> {
            //Comprueba que el nombre no este vacio

            if(entrada_usuario.getText().toString().length()<=3){
                Toast.makeText(MainActivity.this, "introduzca usuario de minimo 4 caracteres",
                        Toast.LENGTH_SHORT).show();
            } else{
                usuarioDAO = new UsuarioDAO(MainActivity.this);
                usuarioDAO.abrirConexion();

                // Insert del usuario

                Usuario usuario = new Usuario(entrada_usuario.getText().toString());
                usuarioDAO.insert(usuario);

                Intent intent = new Intent(MainActivity.this, DescripcionAplicacion.class);
                startActivity(intent);
            }




        });

    }

}
