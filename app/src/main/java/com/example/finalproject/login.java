package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    Intent i, c;
    private MediaPlayer mediaPlayer;
    private FirebaseAuth mAuth;
    Button iniciarsesion;
    EditText correo, contra;
    TextView registrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        i = new Intent(this, MainActivity.class);
        c = new Intent(this, login_registrarse.class);

        mAuth = FirebaseAuth.getInstance();
        iniciarsesion = findViewById(R.id.btnIniciar);
        correo = findViewById(R.id.correo);
        contra = findViewById(R.id.contra);
        registrate = findViewById(R.id.txtRegistrarse);

        registrate.setOnClickListener((v) -> {
            startActivity(new Intent(login.this, login_registrarse.class));
        });

        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = correo.getText().toString().trim();
                String passwdUser = contra.getText().toString().trim();

                if (emailUser.isEmpty() && passwdUser.isEmpty()) {
                    Toast.makeText(login.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailUser, passwdUser);
                }

            }
        });
    }

    private void loginUser(String emailUser, String passwdUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwdUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //guardar correo del login
                    SharedPreferences sharedPref = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Preferencias_email", emailUser);
                    editor.apply();

                    finish();
                    startActivity(new Intent(login.this, MainActivity.class));
                    Toast.makeText(login.this, "Welcome!", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarDialogoErrorLogin();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                mostrarDialogoErrorLogin();
            }
        });


    }

    //si hay una sesion abierta esta la deja abierta y no hay que volver a loguearse
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Obtener el correo del usuario
            String emailUser = currentUser.getEmail();
            SharedPreferences sharedPref = getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Preferencias_email", emailUser);
            editor.apply();

            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }
    }

    //en caso de login erroneo puedo volver a intentarlo o realizar un nuevo registro
    private void mostrarDialogoErrorLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setTitle("Error de inicio de sesión");
        builder.setMessage("No se ha podido iniciar sesión.");

        builder.setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(c);
            }
        });

        builder.setNegativeButton("Volver a intentar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    //mi pinguino debe de saltar y hacer boing
    public void pinguinoboing(View view) {
        ImageView img = findViewById(R.id.pinguino);
        Animation animationPinguino = AnimationUtils.loadAnimation(this, R.anim.anim_pinguino);
        img.startAnimation(animationPinguino);

        //musica
        mediaPlayer = MediaPlayer.create(this, R.raw.boing);
        mediaPlayer.start();
    }






}