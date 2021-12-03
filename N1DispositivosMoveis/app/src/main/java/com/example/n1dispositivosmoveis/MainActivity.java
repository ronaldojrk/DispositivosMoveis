package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonConfig = findViewById(R.id.buttonConfig);
        Button buttonCredi = findViewById(R.id.buttonCredi);
        Button buttonGNSS = findViewById(R.id.buttonGNSS);
        Button buttonHis = findViewById(R.id.buttonHis);
        Button buttonNav = findViewById(R.id.buttonNav);

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaConfig.class);
                startActivity(intent);
                // finish();
            }
        });

        buttonCredi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaCredito.class);
                startActivity(intent);
                // finish();
            }
        });
        buttonGNSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaGNSS.class);
                startActivity(intent);
                // finish();
            }
        });

        buttonNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                // finish();
            }
        });


        buttonHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaHis.class);
                startActivity(intent);
                // finish();
            }
        });


        buttonHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TelaHis.class);
                startActivity(intent);
                // finish();
            }
        });






    }
}