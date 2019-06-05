package com.example.myruleta;

public class Usuario {

    public String email;
    public int dinero;


    public Usuario(int dinero) {
        this.dinero = dinero;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
}
