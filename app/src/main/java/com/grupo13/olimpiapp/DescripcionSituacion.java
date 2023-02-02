package com.grupo13.olimpiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.DAO.DecisionesDAO;
import com.grupo13.olimpiapp.DAO.ProblemaDAO;
import com.grupo13.olimpiapp.DAO.SituacionDAO;
import com.grupo13.olimpiapp.Modelo.Almacen;
import com.grupo13.olimpiapp.Modelo.Decision;
import com.grupo13.olimpiapp.Modelo.Situacion;

public class DescripcionSituacion extends AppCompatActivity {

    TextView etiqueta_situacion, titulo_situacion, numero_presupuesto, numero_trabajadores, numero_notoriedad, numero_tiempo;
    Button boton_avanzar, problema_1, problema_2, problema_3;
    int situacion_actual;
    String ciudad;
    int avanzar = 0;
    String[] decisiones = new String[3];
    AlmacenDAO almacenDAO;
    SituacionDAO situacionDAO;
    ProblemaDAO problemaDAO;
    DecisionesDAO decisionesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_situacion);

        //enlazar componentes-----------------------------------
        etiqueta_situacion = findViewById(R.id.etiqueta_situacion);
        boton_avanzar = findViewById(R.id.boton_avanzar);
        titulo_situacion = findViewById(R.id.titulo_situacion);
        numero_presupuesto = findViewById(R.id.numero_presupuesto);
        numero_trabajadores = findViewById(R.id.numero_trabajadores);
        numero_tiempo = findViewById(R.id.numero_tiempo);
        numero_notoriedad = findViewById(R.id.numero_notoriedad);

        problema_1 = findViewById(R.id.problema_1);
        problema_2 = findViewById(R.id.problema_2);
        problema_3 = findViewById(R.id.problema_3);


        //inicializar dao----------------------------
        almacenDAO = new AlmacenDAO(DescripcionSituacion.this);
        almacenDAO.abrirConexion();
        situacionDAO = new SituacionDAO(DescripcionSituacion.this);
        situacionDAO.abrirConexion();
        problemaDAO = new ProblemaDAO(DescripcionSituacion.this);
        problemaDAO.abrirConexion();
        decisionesDAO = new DecisionesDAO(DescripcionSituacion.this);
        decisionesDAO.abrirConexion();


        //guardo los recursos que tengo------------------------------------------
        Almacen almacen = almacenDAO.consulta(1);

        int numeroNotoriedad = almacen.getNotoriedad();
        int numeroTiempo = almacen.getTiempo();
        int numeroTrabajadores = almacen.getTrabajadores();
        int numeroPresupuesto = almacen.getPresupuesto();

        String format = getResources().getString(R.string.card_notoriedad);
        numero_notoriedad.setText(String.format(format, numeroNotoriedad));
        format = getResources().getString(R.string.card_tiempo);
        numero_tiempo.setText(String.format(format, numeroTiempo));
        format = getResources().getString(R.string.card_trabajadores);
        numero_trabajadores.setText(String.format(format, numeroTrabajadores));
        format = getResources().getString(R.string.card_presupuesto);
        numero_presupuesto.setText(String.format(format, numeroPresupuesto));

        // Colores segun recursos positivos/negativos
        if (numeroNotoriedad < 0){
            numero_notoriedad.setBackgroundColor(Color.parseColor("#FFFF4400"));
            numero_notoriedad.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (numeroNotoriedad > 0){
            numero_notoriedad.setBackgroundColor(Color.parseColor("#6C9B31"));
            numero_notoriedad.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            numero_notoriedad.setBackgroundColor(Color.parseColor("#F6B90C"));
            numero_notoriedad.setTextColor(Color.parseColor("#6A6A6A"));
        }

        if (numeroTiempo < 0){
            numero_tiempo.setBackgroundColor(Color.parseColor("#FFFF4400"));
            numero_tiempo.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (numeroTiempo > 0){
            numero_tiempo.setBackgroundColor(Color.parseColor("#6C9B31"));
            numero_tiempo.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            numero_tiempo.setBackgroundColor(Color.parseColor("#F6B90C"));
            numero_tiempo.setTextColor(Color.parseColor("#6A6A6A"));
        }

        if (numeroTrabajadores < 0){
            numero_trabajadores.setBackgroundColor(Color.parseColor("#FFFF4400"));
            numero_trabajadores.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (numeroTrabajadores > 0){
            numero_trabajadores.setBackgroundColor(Color.parseColor("#6C9B31"));
            numero_trabajadores.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            numero_trabajadores.setBackgroundColor(Color.parseColor("#F6B90C"));
            numero_trabajadores.setTextColor(Color.parseColor("#6A6A6A"));
        }

        if (numeroPresupuesto < 0){
            numero_presupuesto.setBackgroundColor(Color.parseColor("#FFFF4400"));
            numero_presupuesto.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (numeroPresupuesto > 0){
            numero_presupuesto.setBackgroundColor(Color.parseColor("#6C9B31"));
            numero_presupuesto.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            numero_presupuesto.setBackgroundColor(Color.parseColor("#F6B90C"));
            numero_presupuesto.setTextColor(Color.parseColor("#6A6A6A"));
        }

        situacion_actual = almacen.getSituacion();
        ciudad = almacen.getCiudad();

        //pongo los textos que correspdan a la situacion----------------------------------------------------

        Situacion situacion = situacionDAO.consulta(situacion_actual);
        String[] titulos = problemaDAO.consultaTitulos(situacion_actual);
        titulo_situacion.setText(situacion.getTitulo());
        etiqueta_situacion.setText(situacion.getDescripcion().replace("[]", ciudad));
        problema_1.setText(titulos[0]);
        problema_2.setText(titulos[1]);
        problema_3.setText(titulos[2]);

        //cambio colores botones decisiones tomadas-------------------------------------------------------------
        Decision decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 0);

        if (!decision.getDecision().equals("pendiente")) {
            problema_1.setBackgroundColor(Color.parseColor("#6C9B31"));
            problema_1.setTextColor(Color.parseColor("#FFFFFFFF"));
            avanzar++;
            decisiones[0] = decision.getDecision();
        }


        decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 1);

        if (!decision.getDecision().equals("pendiente")) {
            problema_2.setBackgroundColor(Color.parseColor("#6C9B31"));
            problema_2.setTextColor(Color.parseColor("#FFFFFFFF"));
            avanzar++;
            decisiones[1] = decision.getDecision();
        }

        decision = decisionesDAO.consultaSituacionProblema(situacion_actual, 2);

        if (!decision.getDecision().equals("pendiente")) {
            problema_3.setBackgroundColor(Color.parseColor("#6C9B31"));
            problema_3.setTextColor(Color.parseColor("#FFFFFFFF"));
            avanzar++;
            decisiones[2] = decision.getDecision();
        }

        //avanzar siguiente ventana---------------------------------
        boton_avanzar.setOnClickListener(view -> {

            //comprobacion todas las decisiones-------------------------
            if (avanzar == 3) {

                //dialogo de confirmacion-------------------------
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DescripcionSituacion.this);
                dialogo1.setTitle("Confirmacion");
                dialogo1.setMessage("¿ seguro que quiere tomar estas decisiones ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", (dialogo11, id) -> {
                    Intent intent = new Intent(DescripcionSituacion.this, ProblemaTimed.class);
                    startActivity(intent);
                });
                dialogo1.setNegativeButton("Cancelar", (dialogo112, id) -> dialogo112.cancel());
                dialogo1.show();


            } else {
                Toast.makeText(DescripcionSituacion.this, "debe responder a todos los problemas",
                        Toast.LENGTH_SHORT).show();
            }

        });


        //ir al problema que se quiera--------------------------------
        problema_1.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionSituacion.this, DetalleProblema.class);
            intent.putExtra("problema", 0);
            intent.putExtra("situacion", situacion_actual);
            intent.putExtra("titulo", problema_1.getText().toString());
            startActivity(intent);
        });

        problema_2.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionSituacion.this, DetalleProblema.class);
            intent.putExtra("problema", 1);
            intent.putExtra("situacion", situacion_actual);
            intent.putExtra("titulo", problema_2.getText().toString());
            startActivity(intent);
        });

        problema_3.setOnClickListener(view -> {
            Intent intent = new Intent(DescripcionSituacion.this, DetalleProblema.class);
            intent.putExtra("problema", 2);
            intent.putExtra("situacion", situacion_actual);
            intent.putExtra("titulo", problema_3.getText().toString());
            startActivity(intent);
        });


    }

    // Impide volver a la pantalla anterior
    @Override
    public void onBackPressed() {
        // No hace nada
    }
}