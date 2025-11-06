package com.tuapp.programacion2.model;

public class Client {
    public String ci;
    public String nombreCompleto;
    public String direccion;
    public String telefono;
    public Client(String ci, String nombreCompleto, String direccion, String telefono){
        this.ci=ci; this.nombreCompleto=nombreCompleto; this.direccion=direccion; this.telefono=telefono;
    }
}
