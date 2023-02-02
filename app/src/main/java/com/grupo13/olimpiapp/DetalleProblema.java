package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.DAO.DecisionesDAO;
import com.grupo13.olimpiapp.DAO.ProblemaDAO;
import com.grupo13.olimpiapp.Modelo.Almacen;
import com.grupo13.olimpiapp.Modelo.Decision;
import com.grupo13.olimpiapp.Modelo.Problema;

public class DetalleProblema extends AppCompatActivity {
    TextView etiqueta_problema, descripcion_problema;
    int problema, situacion;
    String titulo;
    Button boton_guardar;
    RadioButton opcion1, opcion2, opcion3;
    DecisionesDAO decisionesDAO;
    AlmacenDAO almacenDAO;
    ProblemaDAO problemaDAO;
    String Sit_y_Prob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_problema);

        //enlazar componentes----------------------
        etiqueta_problema = findViewById(R.id.etiqueta_problema);
        descripcion_problema = findViewById(R.id.descripcion_problema);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup2);
        opcion1 = findViewById(R.id.opcion1);
        opcion2 = findViewById(R.id.opcion2);
        opcion3 = findViewById(R.id.opcion3);
        boton_guardar = findViewById(R.id.boton_guardar);


        //inicializar daos--------------------

        decisionesDAO = new DecisionesDAO(DetalleProblema.this);
        decisionesDAO.abrirConexion();
        almacenDAO = new AlmacenDAO(DetalleProblema.this);
        almacenDAO.abrirConexion();
        problemaDAO = new ProblemaDAO(DetalleProblema.this);
        problemaDAO.abrirConexion();


        //cogemos la situacion donde estemos y el problema que llegamos---------------------
        problema = getIntent().getExtras().getInt("problema");
        situacion = getIntent().getExtras().getInt("situacion");
        titulo = getIntent().getExtras().getString("titulo");
        Sit_y_Prob = situacion + "/" + problema;


        //ponemos si ya ha marcado esta decision--------------------------------------

        Decision decision = decisionesDAO.consultaSituacionProblema(situacion, problema);

        switch (decision.getDecision()) {
            case "a":
                opcion1.setChecked(true);
                break;
            case "b":
                opcion2.setChecked(true);
                break;
            case "c":
                opcion3.setChecked(true);
                break;
        }


        //pongo texto que correponda-------------------------------------------------------


        Almacen almacen = almacenDAO.consulta(1);
        String ciudad = almacen.getCiudad();
        etiqueta_problema.setText(titulo);

        Problema problemaClase = problemaDAO.consulta(situacion, problema);
        descripcion_problema.setText(problemaClase.getDescripcion().replace("[]", ciudad));
        opcion1.setText(problemaClase.getOpcionA());
        opcion2.setText(problemaClase.getOpcionB());
        opcion3.setText(problemaClase.getOpcionC());


        //guardamos la deciision------------------------------------------------------
        boton_guardar.setOnClickListener(view -> {
            decision.setDecision("a");
            if (opcion1.isChecked()) {
                decision.setDecision("a");
                decision.setDescripcion(opcion1.getText().toString());
                decisionesDAO.actualizarDecision(decision);

            } else if (opcion2.isChecked()) {

                decision.setDecision("b");
                decision.setDescripcion(opcion2.getText().toString());
                decisionesDAO.actualizarDecision(decision);
            } else if (opcion3.isChecked()) {

                decision.setDecision("c");
                decision.setDescripcion(opcion3.getText().toString());
                decisionesDAO.actualizarDecision(decision);
            }


            //validacion ha marcado alguna opcion-----------------------
            if (radioGroup.getCheckedRadioButtonId() != -1) {
                Intent intent = new Intent(DetalleProblema.this, DescripcionSituacion.class);
                startActivity(intent);
            } else {

                Toast.makeText(getApplicationContext(), "Seleccione una opción entre las posibles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}