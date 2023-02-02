package com.grupo13.olimpiapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.grupo13.olimpiapp.Modelo.ClasificacionRanking;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    private ArrayList<ClasificacionRanking> listaRanking;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView lista;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        back_button=findViewById(R.id.back_button);

        listaRanking = new ArrayList<>();
        db.collection("ranking").orderBy("puntuacion", Query.Direction.DESCENDING).limit(15)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int i = 1;
                        Long puntuacion;
                        String nombre;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            puntuacion = ((Long) document.getData().get("puntuacion"));
                            nombre = (String) document.getData().get("nombre");
                            if (puntuacion != null) {
                                ClasificacionRanking clasificacion = new ClasificacionRanking(i, puntuacion.intValue(), nombre);
                                listaRanking.add(clasificacion);
                                i++;
                            }
                        }
                        //mostrar elementos lista-------------------
                        AdaptadorPersonas adaptador = new AdaptadorPersonas(this);
                        lista = findViewById(R.id.listaRanking);
                        lista.setAdapter(adaptador);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        back_button.setOnClickListener(view -> onBackPressed());

    }

    class AdaptadorPersonas extends ArrayAdapter<ClasificacionRanking> {

        AppCompatActivity appCompatActivity;

        AdaptadorPersonas(AppCompatActivity context) {
            super(context, R.layout.clasificacion_ranking, listaRanking);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item;
            if (convertView == null) {
                LayoutInflater inflater = appCompatActivity.getLayoutInflater();
                item = inflater.inflate(R.layout.clasificacion_ranking, parent, false);
            } else{
                item = convertView;
            }

            TextView posicion = item.findViewById(R.id.posicion);
            TextView nombre = item.findViewById(R.id.nombre);
            TextView puntuacion = item.findViewById(R.id.puntuacionRanking);
            CardView cartaRanking = item.findViewById(R.id.cartaRanking);


            //oro,plata,bronce-------------------------------
            String texto = listaRanking.get(position).getPosicion() + ".";
            posicion.setText(texto);
            if (listaRanking.get(position).getPosicion() == 1) {
                cartaRanking.setCardBackgroundColor(Color.parseColor("#EFB810"));
                nombre.setTypeface(nombre.getTypeface(),Typeface.BOLD);
                nombre.setTextColor(Color.parseColor("#FF1D1D1C"));
                puntuacion.setTypeface(puntuacion.getTypeface(),Typeface.BOLD);
                puntuacion.setTextColor(Color.parseColor("#FF1D1D1C"));
            }

            else if (listaRanking.get(position).getPosicion() == 2) {
                cartaRanking.setCardBackgroundColor(Color.parseColor("#E3E4E5"));
                nombre.setTypeface(nombre.getTypeface(),Typeface.BOLD);
                nombre.setTextColor(Color.parseColor("#FF1D1D1C"));
                puntuacion.setTypeface(puntuacion.getTypeface(),Typeface.BOLD);
                puntuacion.setTextColor(Color.parseColor("#FF1D1D1C"));
            }

            else if (listaRanking.get(position).getPosicion() == 3) {
                cartaRanking.setCardBackgroundColor(Color.parseColor("#BF8970"));
                nombre.setTypeface(nombre.getTypeface(),Typeface.BOLD);
                nombre.setTextColor(Color.parseColor("#FF1D1D1C"));
                puntuacion.setTypeface(puntuacion.getTypeface(),Typeface.BOLD);
                puntuacion.setTextColor(Color.parseColor("#FF1D1D1C"));
            }
            else {
                cartaRanking.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                nombre.setTypeface(nombre.getTypeface(),Typeface.ITALIC);
                nombre.setTextColor(Color.parseColor("#6A6A6A"));
                puntuacion.setTypeface(puntuacion.getTypeface(),Typeface.ITALIC);
                puntuacion.setTextColor(Color.parseColor("#6A6A6A"));
            }


            //pongo valores----------------
            nombre.setText(listaRanking.get(position).getNombre());

            texto = listaRanking.get(position).getPuntuacion() + "";
            puntuacion.setText(texto);


            return item;
        }
    }
}