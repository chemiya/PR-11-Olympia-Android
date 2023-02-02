package com.grupo13.olimpiapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.DAO.UsuarioDAO;
import com.grupo13.olimpiapp.Modelo.Almacen;
import com.grupo13.olimpiapp.Modelo.Usuario;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;
import java.util.Map;


public class ResumenJuego extends AppCompatActivity {
    TextView medicion_1;
    TextView medicion_2;
    TextView medicion_3;
    TextView recurso_presupuesto;
    TextView recurso_notoriedad;
    TextView recurso_trabajadores;
    TextView recurso_tiempo;
    TextView puntuacion;
    AlmacenDAO almacenDAO;
    UsuarioDAO usuarioDAO;
    Usuario user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button boton_avanzar, boton_clasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_juego);

        //enlazar componentes------------------------

        puntuacion = findViewById(R.id.puntuacion);
        medicion_1 = findViewById(R.id.medicion_1);
        medicion_2 = findViewById(R.id.medicion_2);
        medicion_3 = findViewById(R.id.medicion_3);
        recurso_presupuesto = findViewById(R.id.recurso_presupuesto);
        recurso_trabajadores = findViewById(R.id.recurso_trabajadores);
        recurso_notoriedad = findViewById(R.id.recurso_notoriedad);
        recurso_tiempo = findViewById(R.id.recurso_tiempo);
        boton_clasificacion = findViewById(R.id.boton_clasificacion);
        boton_avanzar = findViewById(R.id.boton_avanzar);


        //inicializar dao--------------------------
        almacenDAO = new AlmacenDAO(ResumenJuego.this);
        almacenDAO.abrirConexion();


        //Calculo de puntuación general-----------------------------

        Almacen almacen = almacenDAO.consulta(1);

        int presupuesto = almacen.getPresupuesto();
        int tiempo = almacen.getTiempo();
        int notoriedad = almacen.getNotoriedad();
        int trabajadores = almacen.getTrabajadores();

        int puntos = almacen.getPuntuacion();
        int medida_0 = almacen.getMedicion_0();
        int medida_1 = almacen.getMedicion_1();
        int medida_2 = almacen.getMedicion_2();
        puntos += medida_0 + medida_1 + medida_2;


        String dificultad = almacen.getDificultad();
        if (dificultad.equals("Facil")) puntos *= 0.25;
        else if (dificultad.equals("Dificil")) puntos *= 2;

        Log.d("seleccion dificultad: ", dificultad);


        //Dependencia de recursos en negativo--------------------------------
        int negativos = 0;
        if (presupuesto < 0) {
            negativos++;
            recurso_presupuesto.setTextColor(Color.RED);
        }
        if (tiempo < 0) {
            negativos++;
            recurso_tiempo.setTextColor(Color.RED);
        }
        if (notoriedad < 0) {
            negativos++;
            recurso_notoriedad.setTextColor(Color.RED);
        }
        if (trabajadores < 0) {
            negativos++;
            recurso_trabajadores.setTextColor(Color.RED);
        }

        if (negativos == 4) puntos *= 0.01;
        else if (negativos == 3) puntos *= 0.2;
        else if (negativos == 2) puntos *= 0.5;
        else if (negativos == 1) puntos *= 0.75;
        else puntos *= 1.5;

        // Insercion de puntuacion en base de datos
        usuarioDAO = new UsuarioDAO(ResumenJuego.this);
        usuarioDAO.abrirConexion();
        user = usuarioDAO.consulta();
        Map<String, Object> data = new HashMap<>();
        data.put("puntuacion", puntos);
        if (user!= null) data.put("nombre", user.getNombre());

        db.collection("ranking")
                .add(data)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));


        //Display de puntuaciones finales---------------------------
        String format = getResources().getString(R.string.puntuacion);
        puntuacion.setText(String.format(format, puntos));
        format = getResources().getString(R.string.medicion_1);
        medicion_1.setText(String.format(format, medida_0 * 100 / 450));
        format = getResources().getString(R.string.medicion_2);
        medicion_2.setText(String.format(format, medida_1 * 100 / 450));
        format = getResources().getString(R.string.medicion_3);
        medicion_3.setText(String.format(format, medida_2 * 100 / 450));


        //Display de recursos finales--------------------------
        format = getResources().getString(R.string.confirmacion_presupuesto);
        recurso_presupuesto.setText(String.format(format, presupuesto));
        format = getResources().getString(R.string.confirmacion_notoriedad);
        recurso_notoriedad.setText(String.format(format, notoriedad));
        format = getResources().getString(R.string.confirmacion_tiempo);
        recurso_tiempo.setText(String.format(format, tiempo));
        format = getResources().getString(R.string.confirmacion_trabajadores);
        recurso_trabajadores.setText(String.format(format, trabajadores));


        // Grafico sobre Conflicto y negociacion
        GraphView bar_Graph = findViewById(R.id.bar_graph);

        BarGraphSeries<DataPoint> barGraph_Data = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, medida_0)
        });
        LineGraphSeries<DataPoint> ref = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 220),
                new DataPoint(1, 220)
        });
        bar_Graph.addSeries(barGraph_Data);
        bar_Graph.addSeries(ref);

        barGraph_Data.setSpacing(20);
        barGraph_Data.setDrawValuesOnTop(true);
        barGraph_Data.setValuesOnTopColor(Color.BLACK);
        barGraph_Data.setAnimated(true);
        if (medida_0 < 220) barGraph_Data.setColor(Color.parseColor("#000080"));
        else barGraph_Data.setColor(Color.parseColor("#00ff7f"));
        ref.setColor(Color.RED);

        bar_Graph.getViewport().setYAxisBoundsManual(true);
        bar_Graph.getViewport().setMinY(0.0);
        bar_Graph.getViewport().setMaxY(600);
        barGraph_Data.setTitle("Conflicto y negociación");
        ref.setTitle("Linea de referencia");
        bar_Graph.getLegendRenderer().setVisible(true);
        bar_Graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


        // Grafico sobre Liderazgo
        GraphView bar_Graph_1 = findViewById(R.id.bar_graph_1);

        BarGraphSeries<DataPoint> barGraph_Data_1 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, medida_1)
        });
        bar_Graph_1.addSeries(barGraph_Data_1);
        bar_Graph_1.addSeries(ref);
        bar_Graph_1.getViewport().setYAxisBoundsManual(true);
        bar_Graph_1.getViewport().setMinY(0.0);
        bar_Graph_1.getViewport().setMaxY(600);
        barGraph_Data_1.setTitle("Liderazgo");
        bar_Graph_1.getLegendRenderer().setVisible(true);
        bar_Graph_1.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        barGraph_Data_1.setSpacing(20);
        barGraph_Data_1.setDrawValuesOnTop(true);
        barGraph_Data_1.setValuesOnTopColor(Color.BLACK);
        barGraph_Data_1.setAnimated(true);
        if (medida_1 < 220) barGraph_Data_1.setColor(Color.parseColor("#000080"));
        else barGraph_Data_1.setColor(Color.parseColor("#00ff7f"));


        // Grafico sobre Etica y valores
        GraphView bar_Graph_2 = findViewById(R.id.bar_graph_2);

        BarGraphSeries<DataPoint> barGraph_Data_2 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, medida_2)
        });
        bar_Graph_2.addSeries(barGraph_Data_2);
        bar_Graph_2.addSeries(ref);

        bar_Graph_2.getViewport().setYAxisBoundsManual(true);
        bar_Graph_2.getViewport().setMinY(0.0);
        bar_Graph_2.getViewport().setMaxY(600);
        barGraph_Data_2.setTitle("Etica y valores");
        bar_Graph_2.getLegendRenderer().setVisible(true);
        bar_Graph_2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        barGraph_Data_2.setSpacing(20);
        barGraph_Data_2.setDrawValuesOnTop(true);
        barGraph_Data_2.setValuesOnTopColor(Color.BLACK);
        barGraph_Data_2.setAnimated(true);
        if (medida_2 < 220) barGraph_Data_2.setColor(Color.parseColor("#000080"));
        else barGraph_Data_2.setColor(Color.parseColor("#00ff7f"));


        // Listener del boton de volver a menú--------------------------
        boton_avanzar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DescripcionAplicacion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


        //ver clasificacion-------------------
        boton_clasificacion.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Ranking.class);

            startActivity(intent);
        });
    }

    // Impide volver a la pantalla anterior
    @Override
    public void onBackPressed() {
        // No hace nada
    }
}