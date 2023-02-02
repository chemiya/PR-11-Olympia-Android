package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.Modelo.Almacen;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProblemaTimed extends AppCompatActivity {

    private TextView numero;
    private boolean timer_terminado = false;
    private int seleccion = 0;
    private int situacion;
    private int[] ideal;
    AlmacenDAO almacenDAO;
    String[] descripciones_bonus = new String[3];
    TextView texto, timer;
    Button boton_avanzar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //enlazar e inicializar componentes------------------------
        ideal = new int[3];
        ideal[0] = 73;
        ideal[1] = 47;
        ideal[2] = 1;

        descripciones_bonus[0] = "Uno de nuestros mejores gestores al cargo de los trabajos de infraestructura demanda un incremento a su sueldo mensual. Su sueldo es elevado, pero disponemos de un fondo para este tipo de situaciones. ¿Que porcentaje de subida de sueldo propone?";
        descripciones_bonus[1] = "Se ha detectado un riesgo estructural en el sistema de drenaje de las piscinas. El análisis de riesgos inicial en el proyecto indica que disponemos de 100€ para la contingencia. El sistema afecta solo a una piscina y a falta de analizar el desperfecto en profundidad los empleados estiman un coste medio de 60€. Basado en esto, ¿Cuanto dinero asignamos inicialmente?";
        descripciones_bonus[2] = "Una serie de arbitros en un evento futbolístico han decidido manifestarse vistiendo prendas con simbología que favorece a colectivos en riesgo de discriminación. Este acto ha trascendido a los medios y ha llegado a oídos de la FIFA. La normativa contempla la disminución disciplinar del sueldo. ¿En que porcentaje disminuímos su sueldo?";
        setContentView(R.layout.activity_problema_timed);
        timer = findViewById(R.id.timer1);
        texto = findViewById(R.id.texto);
        SeekBar selector = findViewById(R.id.selector);
        numero = findViewById(R.id.numero);
        boton_avanzar = findViewById(R.id.boton_avanzar);


        //inicializar dao---------------------
        almacenDAO = new AlmacenDAO(ProblemaTimed.this);
        almacenDAO.abrirConexion();


        //poner texto-----------------------------------------
        Almacen almacen = almacenDAO.consulta(1);
        situacion = almacen.getSituacion();
        texto.setText(descripciones_bonus[situacion]);
        String format = getResources().getString(R.string.formato_seekbar);
        numero.setText(String.format(format, 0, 0, 100));


        //avanzar a siguiente----------------------------
        boton_avanzar.setOnClickListener(view -> {
            if (!timer_terminado) {
                timer_terminado = true;


                //anadimos puntuacion----------------------------
                int resultado = almacen.getPuntuacion();
                resultado += selector.getMax() - Math.abs(ideal[situacion] - seleccion);
                almacen.setPuntuacion(resultado);
                almacenDAO.actualizar(almacen);

                Intent intent = new Intent(ProblemaTimed.this, Confirmacion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        //manejo de eventos del selector-------------------------
        selector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            final int min = 0;
            final int max = 100;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
                numero.setText(String.format(format, min, pval, max));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numero.setText(String.format(format, min, pval, max));
                seleccion = pval;
            }
        });


        //manejo del timer----------------------
        new CountDownTimer(30000, 1000) {
            final String format_timer = getResources().getString(R.string.formato_timer);

            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                timer.setText(String.format(format_timer, f.format(hour), f.format(min), f.format(sec)));
            }

            public void onFinish() {
                if (!timer_terminado) {
                    timer_terminado = true;

                    int resultado = almacen.getPuntuacion();
                    resultado += selector.getMax() - Math.abs(ideal[situacion] - seleccion);
                    almacen.setPuntuacion(resultado);
                    almacenDAO.actualizar(almacen);

                    Intent intent = new Intent(ProblemaTimed.this, Confirmacion.class);
                    startActivity(intent);
                }
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }


}