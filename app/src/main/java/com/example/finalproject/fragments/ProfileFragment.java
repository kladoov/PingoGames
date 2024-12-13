package com.example.finalproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.R;


public class ProfileFragment extends Fragment {

    private TextView usernamescrolling;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ProfileFragment", "Se ha ejecutado la clase: " + getClass().getName());

        View root = inflater.inflate(R.layout.activity_scrolling, container, false);
        usernamescrolling = root.findViewById(R.id.textviewUsuario);

        //se establece el correo electronico con el que se ha registrado en la aplicacion
        SharedPreferences sharedPref = requireContext().getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("Preferencias_email", "");
        usernamescrolling.setText(userEmail);

        return root;
    }
}