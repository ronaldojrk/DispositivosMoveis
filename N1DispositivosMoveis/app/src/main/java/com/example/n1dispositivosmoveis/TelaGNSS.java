package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaGNSS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gnss);
        Button btnVoltar = findViewById(R.id.btnVoltar);
        Button btnSatelite = findViewById(R.id.btnSatelite);
        Button btnDraw = findViewById(R.id.btnDraw);

        //btnDraw
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaDraw.class);
                startActivity(intent);
                // finish();
            }
        });

        //btnVoltar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //btnSatelite
        btnSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LocationManagerActivity.class);
                startActivity(intent);
                // finish();
            }
        });
    }



}