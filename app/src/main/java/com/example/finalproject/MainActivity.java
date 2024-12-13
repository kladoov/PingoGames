package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.fragments.HomeFragment;
import com.example.finalproject.fragments.MovieFragment;
import com.example.finalproject.fragments.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    // para las notificaciones es importante habilitar las notificaciones de la propia app
    public static final String CHANNEL_ID = "mi_canal";
    private final static int NOTIFICACION_ID = 0;
    private MediaPlayer mediaPlayer;
    private Fragment fragment = null;
    private String userEmail; //correo electorinico codigo del Login

    FloatingActionButton cerrarsesion;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

        TabLayout tabLayout = findViewById(R.id.tab);
        fragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch ( (tab.getPosition())) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MovieFragment();
                break;
            case 2:
                fragment = new ProfileFragment();
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    // crea una nuevo canal de notificacion
    private void showNotification() {
        NotificationChannel c = new NotificationChannel(CHANNEL_ID, "NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.createNotificationChannel(c);
        showNewNoficication();
    }

    // personalizacion de la notificacion
    @SuppressLint("MissingPermission")
    private void showNewNoficication() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.pinguino)
                .setContentTitle("¡Hello Pinguin!")
                .setContentText("BOING BOING BOING BOING")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    // mi pinguino debe de saltar y hacer boing
    public void pinguinoboing(View view) {
        ImageView img = findViewById(R.id.pinguino);
        Animation animationPinguino = AnimationUtils.loadAnimation(this, R.anim.anim_pinguino);
        img.startAnimation(animationPinguino);

        //musica
        mediaPlayer = MediaPlayer.create(this, R.raw.boing);
        mediaPlayer.start();

        showNewNoficication();
        showNotification();
    }

    //cerrar sesion
    public void cerrarsesion(View view) {
        cerrarsesion = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, login.class));
        Toast.makeText(MainActivity.this, "Has cerrado sesion", Toast.LENGTH_SHORT).show();
    }

    //guarda el usuario y el record de puntos
    public void guardardatos(View view) {
        //take record points
        int puntosRecord;
        SharedPreferences sharedRecord = getSharedPreferences("NuevoRecordPuntos", Context.MODE_PRIVATE);
        puntosRecord = sharedRecord.getInt("PuntosObtenidosRecord", 0);

        //recogo el correo
        SharedPreferences sharedPref = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("Preferencias_email", "");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("correo", userEmail);
        user.put("puntos", puntosRecord);

        db.collection("datos")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Datos añadidos correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error en el guardado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //metodo que te dice con que usuario has sido registrado
    public void añadirnombre(View v) {
        SharedPreferences sharedPref = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("Preferencias_email", "");
        Toast.makeText(this, "Correo registrado: " + userEmail, Toast.LENGTH_SHORT).show();
    }

    //redirige al juego de hundir la flota
    public void hundirflota(View view) {
        Intent e = new Intent(this, hundir_flota.class);
        startActivity(e);
    }

    //redirige al juego de los clicks
    public void clickpingo(View view) {
        Intent e = new Intent(this, click_pingo.class);
        startActivity(e);
    }
}