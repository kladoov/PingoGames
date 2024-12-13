package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class click_pingo extends AppCompatActivity {
    private TextView clicks;
    private TextView tiempo;
    private ImageView pinguino;
    private Button nivel1;
    private Button nivel2;
    private Button nivel3;

    private int clickCount = 0;
    private int tiempoTranscurrido = 0;
    private int duracionTemporizador = 15; // Duración predeterminada del temporizador
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_pingo);

        clicks = findViewById(R.id.clicks);
        tiempo = findViewById(R.id.tiempo);
        pinguino = findViewById(R.id.pinguino);
        pinguino.setEnabled(false);
        nivel1 = findViewById(R.id.level1);
        nivel2 = findViewById(R.id.level2);
        nivel3 = findViewById(R.id.level3);

        nivel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego(15);
                nivel1.setBackgroundResource(R.drawable.disenio_boton_clicks_pulsado);
                nivel1.setEnabled(false);
                nivel2.setEnabled(false);
                nivel3.setEnabled(false);
                pinguino.setEnabled(true);
            }
        });

        nivel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego(10);
                nivel2.setBackgroundResource(R.drawable.disenio_boton_clicks_pulsado);
                nivel1.setEnabled(false);
                nivel2.setEnabled(false);
                nivel3.setEnabled(false);
                pinguino.setEnabled(true);
            }
        });

        nivel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego(6);
                nivel3.setBackgroundResource(R.drawable.disenio_boton_clicks_pulsado);
                nivel1.setEnabled(false);
                nivel2.setEnabled(false);
                nivel3.setEnabled(false);
                pinguino.setEnabled(true);
            }
        });
    }

    //iniciar juego
    private void iniciarJuego(final int duracion) {
        clickCount = 0;
        tiempoTranscurrido = 0;
        duracionTemporizador = duracion;
        clicks.setText("Clicks: " + clickCount);
        tiempo.setText("Tiempo: " + duracionTemporizador + " segundos");

        //temporizador
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tiempoTranscurrido++;
                tiempo.setText("Tiempo: " + (duracionTemporizador - tiempoTranscurrido) + " segundos");
                if (tiempoTranscurrido == duracionTemporizador) {
                    handler.removeCallbacks(this);
                    pinguino.setOnClickListener(null);
                    nivel1.setEnabled(true);
                    nivel2.setEnabled(true);
                    nivel3.setEnabled(true);

                    nivel1.setBackgroundResource(R.drawable.disenio_boton_clicks);
                    nivel2.setBackgroundResource(R.drawable.disenio_boton_clicks);
                    nivel3.setBackgroundResource(R.drawable.disenio_boton_clicks);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

        //click
        pinguino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                clicks.setText("Clicks: " + clickCount);
            }
        });
    }

    public void volver(View view) {
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
    }

    //metodo para que no se pueda volver a la actividad
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
