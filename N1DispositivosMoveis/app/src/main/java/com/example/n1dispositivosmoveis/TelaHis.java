package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaHis extends AppCompatActivity {

    private BancoDados banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_his);

        Button btnVoltar = findViewById(R.id.btnVoltarHis);

        //btnVoltar
        banco.addLocalizacao();
        //dasdas
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}