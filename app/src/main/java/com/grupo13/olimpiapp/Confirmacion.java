package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.DAO.DecisionesDAO;
import com.grupo13.olimpiapp.Modelo.Almacen;
import com.grupo13.olimpiapp.Modelo.Decision;

import java.util.ArrayList;

public class Confirmacion extends AppCompatActivity {

    Button boton_confirmar;
    TextView mostrarDecision0, mostrarDecision1, mostrarDecision2, mostrarDecision3, pie_titulo;
    int situacion_actual = 0, puntuacion = 0;
    int[][][] puntos = new int[3][3][3];
    AlmacenDAO almacenDAO;
    DecisionesDAO decisionesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);


        //enlace componentes------------------
        boton_confirmar = findViewById(R.id.boton_confirmar);
        mostrarDecision0 = findViewById(R.id.confirmacion_0);
        mostrarDecision1 = findViewById(R.id.confirmacion_1);
        mostrarDecision2 = findViewById(R.id.confirmacion_2);
        mostrarDecision3 = findViewById(R.id.confirmacion_3);
        pie_titulo = findViewById(R.id.pie_titulo);


        //inicializar dao--------------
        almacenDAO = new AlmacenDAO(Confirmacion.this);
        almacenDAO.abrirConexion();
        decisionesDAO = new DecisionesDAO(Confirmacion.this);
        decisionesDAO.abrirConexion();


        //inicializar puntuaciones--------------------

        puntos[0][0][0] = 200;
        puntos[0][0][1] = 84;
        puntos[0][0][2] = 142; //2x
        puntos[0][1][0] = 111;
        puntos[0][1][1] = 150;
        puntos[0][1][2] = 0; //1.5x
        puntos[0][2][0] = 100;
        puntos[0][2][1] = 8;
        puntos[0][2][2] = 19; //1x


        puntos[1][0][0] = 34;
        puntos[1][0][1] = 100;
        puntos[1][0][2] = 56; //1x
        puntos[1][1][0] = 100;
        puntos[1][1][1] = 200;
        puntos[1][1][2] = 156; //2x
        puntos[1][2][0] = 150;
        puntos[1][2][1] = 84;
        puntos[1][2][2] = 112; //1.5x


        puntos[2][0][0] = 135;
        puntos[2][0][1] = 7;
        puntos[2][0][2] = 150; //1.5x
        puntos[2][1][0] = 100;
        puntos[2][1][1] = 24;
        puntos[2][1][2] = 61; //1x
        puntos[2][2][0] = 44;
        puntos[2][2][1] = 22;
        puntos[2][2][2] = 200; //2x


        //cogemos los datos actuales----------------------------------------------

        Almacen almacen = almacenDAO.consulta(1);
        situacion_actual = almacen.getSituacion();
        int notoriedad = almacen.getNotoriedad();
        int notoriedadInicial = notoriedad;
        int tiempo = almacen.getTiempo();
        int tiempoInicial = tiempo;
        int presupuesto = almacen.getPresupuesto();
        int presupuestoInicial = presupuesto;
        int trabajadores = almacen.getTrabajadores();
        int trabajadoresInicial = trabajadores;
        puntuacion = almacen.getPuntuacion();
        int medicion_0 = almacen.getMedicion_0();
        int medicion_1 = almacen.getMedicion_1();
        int medicion_2 = almacen.getMedicion_2();


        //mostramos las decisiones----------------------------------
        String[] decisiones = new String[3];

        ArrayList<Decision> arrayDecisiones = decisionesDAO.consultaPorSituacion(situacion_actual);

        Decision decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 0);
        decisiones[0] = decision.getDecision();

        decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 1);
        decisiones[1] = decision.getDecision();

        decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 2);
        decisiones[2] = decision.getDecision();


        //Calculamos cambios en recursos y de puntuacion----------------------------

        if (situacion_actual == 0) {
            switch (decisiones[0]) {
                case "a":
                    tiempo = tiempo - 7;
                    presupuesto = presupuesto - 300;
                    puntuacion += puntos[situacion_actual][0][0];
                    medicion_0 += puntos[situacion_actual][0][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 15;
                    tiempo = tiempo - 6;
                    presupuesto = presupuesto - 250;
                    puntuacion += puntos[situacion_actual][0][1];
                    medicion_0 += puntos[situacion_actual][0][1];
                    break;
                case "c":
                    presupuesto = presupuesto - 100;
                    puntuacion += puntos[situacion_actual][0][2];
                    medicion_0 += puntos[situacion_actual][0][2];
                    break;
            }

            switch (decisiones[1]) {
                case "a":
                    notoriedad = notoriedad + 25;
                    tiempo = tiempo - 14;
                    presupuesto = presupuesto - 500;
                    puntuacion += puntos[situacion_actual][1][0];
                    medicion_1 += puntos[situacion_actual][1][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 15;
                    tiempo = tiempo - 6;
                    presupuesto = presupuesto + 300;
                    puntuacion += puntos[situacion_actual][1][1];
                    medicion_1 += puntos[situacion_actual][1][1];
                    break;
                case "c":
                    presupuesto = presupuesto - 100;
                    puntuacion += puntos[situacion_actual][1][2];
                    medicion_1 += puntos[situacion_actual][1][2];
                    break;
            }


            switch (decisiones[2]) {
                case "a":
                    notoriedad = notoriedad - 25;
                    puntuacion += puntos[situacion_actual][2][0];
                    medicion_2 += puntos[situacion_actual][2][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 10;
                    presupuesto = presupuesto - 100;
                    puntuacion += puntos[situacion_actual][2][1];
                    medicion_2 += puntos[situacion_actual][2][1];
                    break;
                case "c":
                    tiempo = tiempo - 10;
                    trabajadores = trabajadores - 15;
                    puntuacion += puntos[situacion_actual][2][2];
                    medicion_2 += puntos[situacion_actual][2][2];
                    break;
            }
        } else if (situacion_actual == 1) {
            switch (decisiones[0]) {
                case "a":
                    notoriedad = notoriedad - 20;
                    presupuesto = presupuesto + 450;
                    puntuacion += puntos[situacion_actual][0][0];
                    medicion_0 += puntos[situacion_actual][0][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 25;
                    puntuacion += puntos[situacion_actual][0][1];
                    medicion_0 += puntos[situacion_actual][0][1];
                    break;
                case "c":
                    presupuesto = presupuesto + 250;
                    tiempo = tiempo - 10;
                    notoriedad = notoriedad - 15;
                    puntuacion += puntos[situacion_actual][0][2];
                    medicion_0 += puntos[situacion_actual][0][2];
                    break;
            }

            switch (decisiones[1]) {
                case "a":
                    trabajadores = trabajadores + 25;
                    presupuesto = presupuesto - 250;
                    puntuacion += puntos[situacion_actual][1][0];
                    medicion_1 += puntos[situacion_actual][1][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 25;
                    presupuesto = presupuesto - 250;
                    puntuacion += puntos[situacion_actual][1][1];
                    medicion_1 += puntos[situacion_actual][1][1];
                    break;
                case "c":
                    notoriedad = notoriedad - 15;
                    tiempo = tiempo - 20;
                    presupuesto = presupuesto - 200;
                    puntuacion += puntos[situacion_actual][1][2];
                    medicion_1 += puntos[situacion_actual][1][2];
                    break;
            }


            switch (decisiones[2]) {
                case "a":
                    notoriedad = notoriedad + 30;
                    presupuesto = presupuesto - 400;
                    tiempo = tiempo - 30;
                    trabajadores = trabajadores - 20;
                    puntuacion += puntos[situacion_actual][2][0];
                    medicion_2 += puntos[situacion_actual][2][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 20;
                    presupuesto = presupuesto - 250;
                    tiempo = tiempo - 20;
                    trabajadores = trabajadores - 15;
                    puntuacion += puntos[situacion_actual][2][1];
                    medicion_2 += puntos[situacion_actual][2][1];
                    break;
                case "c":
                    notoriedad = notoriedad + 10;
                    presupuesto = presupuesto - 125;
                    tiempo = tiempo - 25;
                    trabajadores = trabajadores - 15;
                    puntuacion += puntos[situacion_actual][2][2];
                    medicion_2 += puntos[situacion_actual][2][2];
                    break;
            }
        }
        if (situacion_actual == 2) {
            switch (decisiones[0]) {
                case "a":
                    notoriedad = notoriedad + 5;
                    presupuesto = presupuesto - 10;
                    puntuacion += puntos[situacion_actual][0][0];
                    medicion_0 += puntos[situacion_actual][0][0];
                    break;
                case "b":
                    tiempo = tiempo + 5;
                    puntuacion += puntos[situacion_actual][0][1];
                    medicion_0 += puntos[situacion_actual][0][1];
                    break;
                case "c":
                    presupuesto = presupuesto - 20;
                    puntuacion += puntos[situacion_actual][0][2];
                    medicion_0 += puntos[situacion_actual][0][2];
                    break;
            }

            switch (decisiones[1]) {
                case "a":
                    notoriedad = notoriedad - 10
                    ;
                    puntuacion += puntos[situacion_actual][1][0];
                    medicion_1 += puntos[situacion_actual][1][0];
                    break;
                case "b":
                    notoriedad = notoriedad + 7;
                    tiempo = tiempo - 3;
                    presupuesto = presupuesto - 50;
                    puntuacion += puntos[situacion_actual][1][1];
                    medicion_1 += puntos[situacion_actual][1][1];
                    break;
                case "c":
                    trabajadores = trabajadores - 4;
                    puntuacion += puntos[situacion_actual][1][2];
                    medicion_1 += puntos[situacion_actual][1][2];
                    break;
            }


            switch (decisiones[2]) {
                case "a":
                    presupuesto = presupuesto + 100;
                    puntuacion += puntos[situacion_actual][2][0];
                    medicion_2 += puntos[situacion_actual][2][0];
                    break;
                case "b":
                    notoriedad = notoriedad - 3;
                    puntuacion += puntos[situacion_actual][2][1];
                    medicion_2 += puntos[situacion_actual][2][1];
                    break;
                case "c":
                    notoriedad = notoriedad + 7;
                    puntuacion += puntos[situacion_actual][2][2];
                    medicion_2 += puntos[situacion_actual][2][2];
                    break;
            }
        }
        almacen.setNotoriedad(notoriedad);
        almacen.setTiempo(tiempo);
        almacen.setPresupuesto(presupuesto);
        almacen.setTrabajadores(trabajadores);

        almacen.setMedicion_0(medicion_0);
        almacen.setMedicion_1(medicion_1);
        almacen.setMedicion_2(medicion_2);

        String format = getResources().getString(R.string.confirmacion_presupuesto);
        mostrarDecision0.setText(String.format(format, presupuesto - presupuestoInicial));
        format = getResources().getString(R.string.confirmacion_notoriedad);
        mostrarDecision1.setText(String.format(format, notoriedad - notoriedadInicial));
        format = getResources().getString(R.string.confirmacion_tiempo);
        mostrarDecision2.setText(String.format(format, tiempo - tiempoInicial));
        format = getResources().getString(R.string.confirmacion_trabajadores);
        mostrarDecision3.setText(String.format(format, trabajadores - trabajadoresInicial));


        //Informacion segun intervalos---------------------------------------

        if (puntuacion < 150) {
            pie_titulo.setText(R.string.intervalo_bajo);
            puntuacion = almacen.getPuntuacion() - 100;
        } else if (puntuacion > 300) {
            pie_titulo.setText(R.string.intervalo_alto);
            puntuacion = almacen.getPuntuacion() + 100;
        } else {
            pie_titulo.setText(R.string.intervalo_medio);
            puntuacion = almacen.getPuntuacion() + 25;
        }
        almacen.setPuntuacion(puntuacion);
        almacenDAO.actualizar(almacen);


        //pasar siguiente situacion--------------------
        boton_confirmar.setOnClickListener(view -> {
            if (situacion_actual == 2) {
                Intent intent = new Intent(Confirmacion.this, ResumenJuego.class);
                startActivity(intent);
            } else {


                //aumentar el valor de la decision en la que llegamos-------------------------

                Almacen almacenIncremento = almacenDAO.consulta(1);
                almacenIncremento.setSituacion(situacion_actual + 1);
                almacenDAO.actualizar(almacenIncremento);

                Intent intent = new Intent(Confirmacion.this, DescripcionSituacion.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}