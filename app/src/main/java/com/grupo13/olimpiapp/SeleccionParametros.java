package com.grupo13.olimpiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo13.olimpiapp.DAO.AlmacenDAO;
import com.grupo13.olimpiapp.DAO.DecisionesDAO;
import com.grupo13.olimpiapp.DAO.ProblemaDAO;
import com.grupo13.olimpiapp.DAO.SituacionDAO;
import com.grupo13.olimpiapp.DAO.UsuarioDAO;
import com.grupo13.olimpiapp.Modelo.Problema;
import com.grupo13.olimpiapp.Modelo.Situacion;
import com.grupo13.olimpiapp.Modelo.Usuario;

public class SeleccionParametros extends AppCompatActivity {
    Button boton_juego;
    EditText entrada_ciudad;
    RadioGroup radioGroup;
    RadioButton radioDificil,radioMedio,radioFacil;
    CardView cartaFacil,cartaMedia,cartaDificil;
    TextView textViewFacil, textViewMedio, textViewDificil;
    boolean acceso=false;
    AlmacenDAO almacenDAO;
    DecisionesDAO decisionesDAO;
    SituacionDAO situacionDAO;
    ProblemaDAO problemaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_parametros);

        //enlazar componentes------------------------------------
        boton_juego=findViewById(R.id.boton_juego);
        entrada_ciudad=findViewById(R.id.entrada_ciudad);
        radioGroup=findViewById(R.id.radioGroup);

        radioFacil = findViewById(R.id.radioFacil);
        radioMedio = findViewById(R.id.radioMedio);
        radioDificil = findViewById(R.id.radioDificil);

        cartaFacil = findViewById(R.id.cartaFacil);
        cartaMedia = findViewById(R.id.cartaMedia);
        cartaDificil = findViewById(R.id.cartaDificil);

        textViewFacil=findViewById(R.id.textView71);
        textViewMedio=findViewById(R.id.textView7);
        textViewDificil=findViewById(R.id.textView8);

        //incializar dao----------------------------------
        almacenDAO=new AlmacenDAO(SeleccionParametros.this);
        almacenDAO.abrirConexion();
        decisionesDAO=new DecisionesDAO(SeleccionParametros.this);
        decisionesDAO.abrirConexion();
        situacionDAO=new SituacionDAO(SeleccionParametros.this);
        situacionDAO.abrirConexion();
        problemaDAO=new ProblemaDAO(SeleccionParametros.this);
        problemaDAO.abrirConexion();

        //pongo de color la opcion marcada (opci??n y texto cambian de color)------------------------
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if(radioFacil.isChecked()){
                cartaFacil.setCardBackgroundColor(Color.parseColor("#6C9B31"));
                cartaMedia.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                cartaDificil.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                textViewFacil.setTextColor(Color.parseColor("#000000"));
                textViewMedio.setTextColor(Color.parseColor("#000000"));
                textViewDificil.setTextColor(Color.parseColor("#FFFFFF"));
            }

            if(radioMedio.isChecked()){
                cartaFacil.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                cartaMedia.setCardBackgroundColor(Color.parseColor("#6C9B31"));
                cartaDificil.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                textViewMedio.setTextColor(Color.parseColor("#FFFFFF"));
                textViewFacil.setTextColor(Color.parseColor("#000000"));
                textViewDificil.setTextColor(Color.parseColor("#000000"));
            }

            if(radioDificil.isChecked()){
                cartaFacil.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                cartaMedia.setCardBackgroundColor(Color.parseColor("#E7E7E7"));
                cartaDificil.setCardBackgroundColor(Color.parseColor("#6C9B31"));
                textViewDificil.setTextColor(Color.parseColor("#000000"));
                textViewMedio.setTextColor(Color.parseColor("#000000"));
                textViewFacil.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        // Al pulsar sobre una cardview tambien activa el radio button de dificultad
        cartaFacil.setOnClickListener(view -> radioFacil.performClick());
        cartaMedia.setOnClickListener(view -> radioMedio.performClick());
        cartaDificil.setOnClickListener(view -> radioDificil.performClick());

        //boton jugar----------------------
        boton_juego.setOnClickListener(view -> {

            //control fallo ciudad o dificultad no introducida----------------------------
            if(entrada_ciudad.getText().toString().length()==0){
                entrada_ciudad.setError("Introduzca una ciudad");
                Toast.makeText(SeleccionParametros.this, "Introduzca nombre de la ciudad donde se producira el evento",
                        Toast.LENGTH_SHORT).show();

            } else if (!radioFacil.isChecked() && !radioMedio.isChecked() && !radioDificil.isChecked()) {
                Toast.makeText(SeleccionParametros.this, "Escoja una dificultad",
                        Toast.LENGTH_SHORT).show();

            } else {
                acceso = true;
            }




            //inicializar recursos segun dificultad----------------------------------
            if(radioFacil.isChecked()){
                almacenDAO.delete(1);
                almacenDAO.insert(1,33,80,90,2000,0,0,entrada_ciudad.getText().toString(),0,0,0,"Facil");
            }
            else if(radioMedio.isChecked()){
                almacenDAO.delete(1);
                almacenDAO.insert(1,25,50,30,900,0,0,entrada_ciudad.getText().toString(),0,0,0,"Medio");
            }
            else if(radioDificil.isChecked()){
                almacenDAO.delete(1);
                almacenDAO.insert(1,0,43,20,700,0,0,entrada_ciudad.getText().toString(),0,0,0,"Dificil");
            }


            //si los parametros estan bien-----------------------------
            if(acceso){

                decisionesDAO.delete(0);
                decisionesDAO.delete(1);
                decisionesDAO.delete(2);

                decisionesDAO.delete(10);
                decisionesDAO.delete(11);
                decisionesDAO.delete(12);

                decisionesDAO.delete(20);
                decisionesDAO.delete(21);
                decisionesDAO.delete(22);



                //creo las decisoones en pendiente------------------------

                decisionesDAO.insert(0);
                decisionesDAO.insert(1);
                decisionesDAO.insert(2);
                decisionesDAO.insert(10);
                decisionesDAO.insert(11);
                decisionesDAO.insert(12);
                decisionesDAO.insert(20);
                decisionesDAO.insert(21);
                decisionesDAO.insert(22);



                //creo las situaciones------------------

                Situacion situacion = new Situacion();

                situacion.setNumero(0);
                situacion.setTitulo("Construccion de infraestructuras");
                situacion.setDescripcion("A tu empresa le ha sido asignado el trabajo de construir un complejo deportivo con motivo del desarrollo de los juegos olimpicos en []" +
                        ".  Como gestor, tu trabajo consiste en la resoluci??n de los incidentes que puedan surgir durante la construcci??n de dichas infraestructuras.");

                situacionDAO.insert(situacion);


                situacion.setNumero(1);
                situacion.setTitulo("Promocion del evento");
                situacion.setDescripcion("La Junta Directiva de la empresa ha decidido que para mejorar las previsiones de " +
                        "audiencia de los juegos olimpicos en [], debemos desarrollar diferentes campa??as de publicidad, " +
                        "acciones de marketing, tomar diferentes decisiones estrat??gicas y " +
                        "establecer merchandising relacionado con este gran evento. \n\n " +
                        "Tu trabajo, en este momento, es dar soluci??n a los incidentes que han ido surgiendo durante el establecimiento de estas campa??as promocionales.");

                situacionDAO.insert(situacion);

                situacion.setNumero(2);
                situacion.setTitulo("Desarrollo del evento");
                situacion.setDescripcion("Actualmente, nos encontramos en plena ejecuci??n de los juegos olimpicos de []. Sin embargo, tu trabajo a??n no ha acabado. " +
                        "Durante esta etapa han surgido diferentes situaciones que requieren de tu intervenci??n. ");

                situacionDAO.insert(situacion);








                //creo los problemas----------------------------


                Problema problema = new Problema();

                problema.setDescripcion("Debido al contexto de la situaci??n laboral actual los distintos trabajadores encargados de la construcci??n de uno de los estadios mas innovadores de [] han presentado a la organizaci??n sus quejas, amenazando con ponerse en huelga si no son atendidas sus pretensiones, en concreto solicitan una mejora de sus condiciones laborales, en concreto solicitan un incremento de un 20% de su salario, disminuci??n de 4 horas de su jornada de trabajo y que la empresa les proporcione mejores instrumentos para poder trabajar (est?? inversi??n supondr??a la compra de diferentes herramientas valoradas en 150.000???).\n" +
                        "Condiciones actuales de los trabajadores:\n" +
                        "Sueldo 1.300???\n" +
                        "N??mero de horas/d??a: 9h (de Lunes a S??bado).\n");
                problema.setTitulo("Amenazas de huelga");
                problema.setSituacion(0);
                problema.setNumero(0);
                problema.setOpcionA("Negociar con los trabajadores las condiciones y tratar de alcanzar alg??n acuerdo entre las partes. \n(-Tiempo, -Presupuesto)");
                problema.setOpcionB("Subcontratar a otra empresa que se ocupe de ello. \n(-Trabajadores, -Presupuesto)");
                problema.setOpcionC("Aceptar las condiciones de los trabajadores. \n(--Presupuesto, +Tiempo).");

                problemaDAO.insert(problema);

                problema.setDescripcion("Para la construcci??n del estadio principal de los juegos olimpicos de [], desde la organizaci??n del evento se ha decidido que va a ser construido con hormig??n armado, para la compra de dicho material a la empresa se presentan las propuestas de dos proveedores:\n\n" +
                        "- Proveedor 1\n La empresa ???HORMIG??N WORLD??? situada en China ofrece sus servicios de distintos tipos de hormig??n a pa??ses de todo el mundo. \nCaracter??sticas de sus productos:\n" +
                        "  Calidad: Muy elevada.\n" +
                        "  Tiempo de entrega: 14-16 d??as.\n" +
                        "  Duraci??n de los materiales: 15-20 a??os.\n" +
                        "  Costes del producto: 75???/kg + Tasas de Aduanas y aranceles.\n\n" +
                        "- Proveedor 2\n La empresa ???HORMIGON SPAIN??? situada en Espa??a ofrece sus productos a nivel internacional pero su calidad es menor. \nCaracter??sticas de sus productos: \n" +
                        "  Calidad: Media\n" +
                        "  Tiempo de entrega: 5-7 d??as.\n" +
                        "  Duraci??n de los materiales: 8-10 a??os.\n" +
                        "  Costes del producto: 60???/kg.\n\n" +
                        "??Qu?? opci??n seleccionamos para conseguir los materiales?\n"
                );
                problema.setTitulo("Elecci??n de proveedor");
                problema.setSituacion(0);
                problema.setNumero(1);
                problema.setOpcionA("Escoger al Proveedor 1 \n(--Tiempo, ++Notoriedad).");
                problema.setOpcionB("Escoger al Proveedor 2 \n(-Tiempo, +Notoriedad).");
                problema.setOpcionC("No escoger ninguno");

                problemaDAO.insert(problema);

                problema.setDescripcion("Durante la realizaci??n de las obras del recinto donde se realizar??n gran parte de las pruebas de interior de los juegos olimpicos de [] ha " +
                        "ocurrido un accidente con una de las maquinarias del equipo de obra en el que han " +
                        "resultado heridos algunos miembros de la plantilla de trabajadores de la empresa.\n\n " +
                        "La opini??n p??blica te se??ala como el principal responsable de lo ocurrido.\n\n ??Qu?? acciones tomamos?\n"
                );
                problema.setTitulo("Control de da??os de imagen");
                problema.setSituacion(0);
                problema.setNumero(2);
                problema.setOpcionA("Dar una rueda de prensa asumiendo la responsabilidad de lo ocurrido. \n(--Notoriedad)");
                problema.setOpcionB("Pagar a una cadena de televisi??n para que hagan una campa??a desmintiendo lo ocurrido. \n(-Presupuesto, +Notoriedad)");
                problema.setOpcionC("Culpabilizar al jefe de obra del hecho y despedirlo. \n(-Trabajadores) ");


                problemaDAO.insert(problema);

                problema.setDescripcion("Nuestra empresa hasta el momento tiene un acuerdo de patrocinio con ???POWERGREEN???, una empresa que promueve valores ??ticos con sus actuaciones, ecologismo y sostenibilidad. \n\n En los ??ltimos d??as la empresa ha recibido la propuesta de la empresa ???PETROPOL???, la cual aportar??a una gran cantidad de dinero por ser patrocinadora del evento, pero se tiene conocimiento de que tiene valores totalmente contrarios a los que se quiere representar tanto la organizaci??n del evento como la otra empresa patrocinadora, debido a que emite una gran cantidad de gases contaminantes y hay investigaciones sobre las condiciones laborales en las que desarrollan su actividad\n\n Dada la situaci??n, ??Qu?? decisi??n tomamos?\n"
                );
                problema.setTitulo("Nuevo patrocinio");
                problema.setSituacion(1);
                problema.setNumero(0);
                problema.setOpcionA("Aceptar la nueva propuesta y rescindir el contrato con la primera empresa. \n(+++Presupuesto, --Notoriedad)");
                problema.setOpcionB("Rechazar la nueva propuesta y mantenerme con la empresa ya acordada. \n(+++Notoriedad)");
                problema.setOpcionC("Negociar con ambas empresas para que ambas fueran patrocinadores del evento. \n(+Presupuesto,-Tiempo, ???Notoriedad)");

                problemaDAO.insert(problema);

                problema.setDescripcion("Tras un sondeo realizado para determinar la asistencia que se prev?? que habr?? a los juegos olimpicos de [], se observa que el n??mero de espectadores al evento podr??a ser ser superior al esperado inicialmente.\n\n Para poder hacer frente a este incremento de espectadores se solicita tu decisi??n:\n"
                );
                problema.setTitulo("Medidas para asistencia");
                problema.setSituacion(1);
                problema.setNumero(1);
                problema.setOpcionA("Incrementar el n?? de trabajadores de seguridad para evitar conflictos y aglomeraciones. \n(-Presupuesto, +Trabajadores)");
                problema.setOpcionB("Habilitar una fan zone en la que los espectadores puedan esperar hasta que puedan acceder al evento. \n(+Notoriedad, -Presupuesto)");
                problema.setOpcionC("Que la organizaci??n establezca unos horarios de buses para que puedan pasar a recoger a los espectadores en un punto concreto y llevarlos en distintos intervalos de tiempo al recinto. \n(???Notoriedad, ???Tiempo, -Presupuesto)");

                problemaDAO.insert(problema);

                problema.setDescripcion("La empresa est?? planteando llevar a cabo una campa??a para publicitar el evento. \n\n la empresa est?? plante??ndose diferentes opciones:\n\n" +
                        "- Opci??n A\n  Realizar una campa??a de publicidad digital, consistente en crear perfiles oficiales del evento en redes sociales, aparecer en forma de anuncio en webs de internet, crear un anuncio de televisi??n. \nEsta opci??n  implicar??a:\n" +
                        "  Coste: 9/10.\n" +
                        "  Riesgo: 8/10.\n" +
                        "  Rentabilidad y resultados futuros: 10/10.\n" +
                        "  Adicionalmente se recompensar?? a los trabajadores por la consecuci??n de los objetivos.\n\n" +
                        "- Opci??n B\n Poner una lona gigante anunciando los juegos olimpicos de [] en un edificio famoso, en el centro de [], lugar emblem??tico o zona muy concurrida por los ciudadanos de la ciudad. Esta opci??n implicar??a:\n" +
                        "  Coste: 6/10.\n" +
                        "  Riesgo: 7/10.\n" +
                        "  Rentabilidad y resultados futuros: 8/10.\n" +
                        "  No se recompensa a los trabajadores.\n\n" +
                        "- Opci??n C\n Repartir folletos y carteles por los barrios y casas de [], esta opci??n implicar??a:\n" +
                        "  Coste: 4/10:\n" +
                        "  Riesgo: 3/10.\n" +
                        "  Rentabilidad y resultados futuros: 6/10.\n" +
                        "  Se recompensar?? a los trabajadores por la consecuci??n de los objetivos.\n\n" +
                        "Conoci??ndose lo que supondr??a cada opci??n, ??Cu??l escoger??as?\n"
                );
                problema.setTitulo("Opciones de publicidad");
                problema.setSituacion(1);
                problema.setNumero(2);
                problema.setOpcionA("Seleccionar opcion A. \n(???Notoriedad, ???Tiempo, ---Presupuesto, --Trabajadores)");
                problema.setOpcionB("Seleccionar opcion B. \n(???Notoriedad, ???Tiempo, --Presupuesto, -Trabajadores,)");
                problema.setOpcionC("Seleccionar opcion C. \n(???Notoriedad, ???Tiempo, -Presupuesto, -Trabajadores )");

                problemaDAO.insert(problema);

                problema.setDescripcion("Durante el desarrollo de una de las pruebas mas televisadas de los juegos olimpicos de [], algunos de los miembros del Comit?? Directivo de representantes de Alemania han realizado comentarios inapropiados cuestionando la organizaci??n del evento.\n\n Posteriormente, te encuentras con ellos e inician una conversaci??n contigo\n\n ??C??mo reaccionas? ");
                problema.setTitulo("Tensiones internas");
                problema.setSituacion(2);
                problema.setNumero(0);
                problema.setOpcionA("Act??as con naturalidad sin dejarte influir por sus comentarios. \n(???Presupuesto, +Notoriedad)");
                problema.setOpcionB("Pones en conocimiento de ellos los comentarios que han realizado. \n(???Tiempo)");
                problema.setOpcionC("Intentar evitar acercamiento con ellos. \n(???Presupuesto)");

                problemaDAO.insert(problema);

                problema.setDescripcion("El d??a de la inauguracion de los juegos olimpicos de [] suceden problemas en cuanto al acceso al recinto por parte de los espectadores.\n\n Un grupo de personas ha conseguido entrar al recinto sin poseer entrada, lo cual ha dejado fuera a aquellos espectadores que s?? ten??an entrada, provocando manifestaciones para intentar acceder al recinto\n\n ??C??mo actuar??as ante esta situaci??n?");
                problema.setTitulo("Acceso al recinto");
                problema.setSituacion(2);
                problema.setNumero(1);
                problema.setOpcionA("Decido retrasar el comienzo del evento e intento localizar a trav??s del personal contratado a la gente que ha entrado sin poseer entrada, y proceder a su expulsi??n. \n(???Notoriedad)");
                problema.setOpcionB("Reembolso a los asistentes que no han podido acceder a??n teniendo entrada. \n(-Presupuesto, ???Tiempo, +Notoriedad)");
                problema.setOpcionC("Incremento la seguridad en los puntos de acceso para evitar que m??s personas entren sin entrada. \n(-Trabajadores)");

                problemaDAO.insert(problema);

                problema.setDescripcion("Los juegos olimpicos de [] estan a punto de llegar a su fin\n\n la Junta de Administraci??n de la organizaci??n se re??ne para decidir la forma de gestionar los resultados generados con la celebraci??n del evento\n\n ??Por qu?? propuesta te decantar??as?\n");
                problema.setTitulo("Propuestas finales");
                problema.setSituacion(2);
                problema.setNumero(2);
                problema.setOpcionA("Reinvertirlo para el desarrollo de futuros eventos (mejorarlos, corregir errores???). \n(+Presupuesto)");
                problema.setOpcionB("Repartir dividendos entre los accionistas. \n(-Notoriedad)");
                problema.setOpcionC("Ante la buena gesti??n y trabajo de los trabajadores, decides recompensarlos desembolsando primas. \n(+Notoriedad)");

                problemaDAO.insert(problema);


                Intent intent = new Intent(SeleccionParametros.this, DescripcionSituacion.class);
                startActivity(intent);
            }
        });
    }
}