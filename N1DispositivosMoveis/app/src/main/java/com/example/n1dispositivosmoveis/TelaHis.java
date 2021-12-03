package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaHis extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_his);

       // SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy:");
        ///Date data = new Date();

       // String dataFormatada = formataData.format(data);

        Button btnlog = findViewById(R.id.btnlog);

        Button btndeletar = findViewById(R.id.btndeletar);
        Button btnpausar = findViewById(R.id.btnpausar);

        Button btnmapa = findViewById(R.id.btnmapa);
        Button btnstart = findViewById(R.id.btnstart);







        btnlog.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),visualizar.class);
                startActivity(intent);
            }
        });

        btnmapa.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),visualizar.class);
                startActivity(intent);
            }
        });
        btndeletar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                LocalizacaoDAO crud = new LocalizacaoDAO(getBaseContext());

                crud.deletaRegistro();
                Toast.makeText(getApplicationContext(),"historico deletado", Toast.LENGTH_LONG).show();

            }
        });

        btnpausar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putBoolean("historico",false);
                editor.apply();

                Toast.makeText(getApplicationContext(),"Status do historico: desativado", Toast.LENGTH_LONG).show();




            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putBoolean("historico",true);
                editor.apply();

                Toast.makeText(getApplicationContext(),"Status do historico: ativado", Toast.LENGTH_LONG).show();


            }
        });
    }
}