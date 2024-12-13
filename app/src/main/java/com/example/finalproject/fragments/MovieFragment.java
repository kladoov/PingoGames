package com.example.finalproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.design.CustomRecyclerAdapter;
import com.example.finalproject.login;
import com.example.finalproject.model.ListArrayItem;
import com.example.finalproject.model.ModelOfItems;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MovieFragment extends Fragment {

    private String userEmail; //correo electorinico codigo del Login
    private int puntos;
    private int puntosRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_view, container, false);

        //recojo el correo
        SharedPreferences sharedPref = requireContext().getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        userEmail = sharedPref.getString("Preferencias_email", "");

        //take points
        SharedPreferences sharedpuntos = requireContext().getSharedPreferences("PuntosJuegoHundirFlota", Context.MODE_PRIVATE);
        puntos = sharedpuntos.getInt("PuntosObtenidos", 0);

        //take record points
        SharedPreferences sharedRecord = requireContext().getSharedPreferences("NuevoRecordPuntos", Context.MODE_PRIVATE);
        puntosRecord = sharedRecord.getInt("PuntosObtenidosRecord", 0);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerviewmio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(getActivity(), new ModelOfItems().setListOfArrayItems());

        ListArrayItem jugador = adapter.getItemAtIndex(2); //el nuevo jugador siempre sera el 2
        jugador = new ListArrayItem(userEmail, puntosRecord);
        adapter.addItem(jugador);

        if (puntos > puntosRecord) {
            jugador = new ListArrayItem(jugador.getUsuario(), puntos);
            adapter.listArrayItems.set(2, jugador);

            //guardo el nuevo record
            SharedPreferences sharedPrefRecord = requireContext().getSharedPreferences("NuevoRecordPuntos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefRecord.edit();
            editor.putInt("PuntosObtenidosRecord", puntos);
            editor.apply();
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return root;
    }

}