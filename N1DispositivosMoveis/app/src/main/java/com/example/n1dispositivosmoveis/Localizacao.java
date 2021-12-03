package com.example.n1dispositivosmoveis;

import android.provider.ContactsContract;

import java.sql.Date;

public class Localizacao {

    int codigo;
    String latitude;
    String longitude;
    String data;
    String velocidade;

    /*public Localizacao() {

    }*/

    public Localizacao(int codigo, String latitude, String longitude, String data, String velocidade) {
        this.codigo = codigo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.data = data;
        this.velocidade = velocidade;
    }

   /* public Localizacao(double _latitude, double _longitude, String _data,  double _velocidade) {
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.data = _data;
        this.velocidade = _velocidade;
    }*/


    //==========================================
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }
}
