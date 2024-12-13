package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_registrarse extends AppCompatActivity {
    Button registrar;
    EditText correo, contra;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_registrarse);

        registrar = findViewById(R.id.btnRegistrar);
        correo = findViewById(R.id.correo);
        contra = findViewById(R.id.contra);
        mAuth = FirebaseAuth.getInstance();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = correo.getText().toString().trim();
                String passwdUser = contra.getText().toString().trim();

                if (emailUser.isEmpty() && passwdUser.isEmpty()) {
                    Toast.makeText(login_registrarse.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(emailUser, passwdUser);
                }
            }
        });
    }

    private void registerUser(String emailUser, String passwdUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passwdUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(login_registrarse.this, login.class));
                    Toast.makeText(login_registrarse.this, "Datos registrados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login_registrarse.this, "Escribe un correo y una contraseña segura", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
