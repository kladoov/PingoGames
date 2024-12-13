package com.example.finalproject.model;

public class ListArrayItem {
    //private final int imagen;
    private final String usuario;
    private final int puntos;
    public ListArrayItem(/*int imagen,*/ String usuario, int p) {
        //this.imagen = imagen;
        this.usuario = usuario;
        this.puntos = p;
    }
    /*public int getImagen() {
        return imagen;
    }*/

    public String getUsuario() {
        return usuario;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int i) {i = puntos;}



    public void setUsuario(String u) {u = usuario;}
}
