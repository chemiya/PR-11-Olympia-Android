package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.grupo13.olimpiapp.DAO.UsuarioDAO;
import com.grupo13.olimpiapp.Modelo.Usuario;

public class DescripcionAplicacion extends AppCompatActivity {

    private final String TAG = "Perfil";
    String usernameEtiqueta;
    Button boton_empezar, boton_como_jugar, boton_ranking;
    TextView etiqueta_bienvenida;
    Usuario user;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_aplicacion);

        //enlace componentes-----------------------------------
        boton_empezar = findViewById(R.id.boton_empezar);
        boton_como_jugar = findViewById(R.id.boton_como_jugar);
        boton_ranking = findViewById(R.id.boton_ranking);
        etiqueta_bienvenida = findViewById(R.id.etiqueta_bienvenida);

        usuarioDAO = new UsuarioDAO(DescripcionAplicacion.this);
        usuarioDAO.abrirConexion();
        user = usuarioDAO.consulta();

        // Se coge el nombre de usuario

        if (user.getNombre() != null) {
            usernameEtiqueta = user.getNombre();
        } else {
            usernameEtiqueta = "NoName";
        }

        String format = getResources().getString(R.string.bienvenida);
        etiqueta_bienvenida.setText(String.format(format, usernameEtiqueta));


        //pasar ventana comienzo-------------------------------------
        boton_empezar.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionAplicacion.this, SeleccionParametros.class);
            startActivity(intent);
        });


        //ventana como jugar-----------------------------
        boton_como_jugar.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionAplicacion.this, Tutorial.class);
            startActivity(intent);
        });

        //ventana ranking-----------------------------
        boton_ranking.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionAplicacion.this, Ranking.class);
            startActivity(intent);
        });

    }

    // Permite volver a la pantalla inicial
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DescripcionAplicacion.this, MainActivity.class);
        startActivity(intent);
    }
}