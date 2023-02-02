package com.grupo13.olimpiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Tutorial extends AppCompatActivity {
    Button boton_empezar;
    VideoView video_tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //enlace componentes-------------------------------
        boton_empezar = findViewById(R.id.boton_empezar);
        video_tutorial = findViewById(R.id.video_tutorial);


        //ponemos el video---------------------------
        video_tutorial.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.olympia_tutorial);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video_tutorial);
        video_tutorial.setMediaController(mediaController);
        video_tutorial.start();


        //ventana siguiente--------------------------
        boton_empezar.setOnClickListener(view -> {
            Intent intent = new Intent(Tutorial.this, SeleccionParametros.class);
            startActivity(intent);
        });

    }
}