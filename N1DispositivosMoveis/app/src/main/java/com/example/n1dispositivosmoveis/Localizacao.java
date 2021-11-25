package com.example.n1dispositivosmoveis;

import android.provider.ContactsContract;

import java.sql.Date;

public class Localizacao {

    int codigo;
    double latitude;
    double longitude;
    String data;
    double velocidade;

    /*public Localizacao() {

    }*/

    public Localizacao(int _codigo, double _latitude, double _longitude, String _data, double _velocidade) {
        this.codigo = _codigo;
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.data = _data;
        this.velocidade = _velocidade;
    }

    public Localizacao(double _latitude, double _longitude, String _data,  double _velocidade) {
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.data = _data;
        this.velocidade = _velocidade;
    }


    //==========================================
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
}
