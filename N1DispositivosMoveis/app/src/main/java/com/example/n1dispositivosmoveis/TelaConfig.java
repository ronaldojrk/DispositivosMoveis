package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TelaConfig extends AppCompatActivity {


    String [] coordenadas = {"Grau decimal","Grau-Minuto decimal","Grau-Minuto-Segundo decimal"};
    String [] velocidade = {"Km/h","Mph",};
    String [] OrientacaodoMapa = {"Nenhuma","North Up","Course Up"};
    String [] TipodoMapa = {"Vetorial","Imagem de Satélite",};
    boolean trafego =false;

    final String MY_PREFS_NAME  = "projeto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_config);

       /* SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("coordenadas", "Elena");
        //editor.putInt("idName", 12);
        editor.apply();*/

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String coordenadasSave = prefs.getString("coordenadas", "notDefined");
        String velocidadeSave = prefs.getString("velocidade", "notDefined");
        String OrientacaodoMapaSave = prefs.getString("OrientacaodoMapa", "notDefined");
        String TipodoMapaSave = prefs.getString("TipodoMapa", "notDefined");
        Boolean trafegoSave = prefs.getBoolean("trafego", false);
        //int idName = prefs.getInt("idName", 0);

        ////TextView agora = findViewById(R.id.textView);
        //agora.setText(coordenadasSave);

        Spinner spinnercoordenadas = findViewById(R.id.spinnercoordenadas);

        Spinner spinnervelocidade = findViewById(R.id.spinnervelocidade);

        Spinner spinnerOrientacaodoMapa = findViewById(R.id.spinnerMapa);

        Spinner spinnerTipodoMapa = findViewById(R.id.spinnerTipodoMapa);

        Switch switchTrafego = findViewById(R.id.trafego);

        Button btnVoltar = findViewById(R.id.btnvoltarConfig);

        switchTrafego.setChecked(trafegoSave);

        // spinner coordenadas
        ArrayAdapter<String> adaptercoordenadas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,coordenadas);
        adaptercoordenadas.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnercoordenadas.setAdapter(adaptercoordenadas);
        if (coordenadasSave != "notDefined") {
            int spinnerPosition = adaptercoordenadas.getPosition(coordenadasSave);
            spinnercoordenadas.setSelection(spinnerPosition);
        }


        //spinner velocidade


        ArrayAdapter<String> adaptervelocidade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,velocidade);
        adaptervelocidade.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnervelocidade.setAdapter(adaptervelocidade);

        if (velocidadeSave != "notDefined") {
            int spinnerPosition = adaptervelocidade.getPosition(velocidadeSave);
            spinnervelocidade.setSelection(spinnerPosition);
        }

        // spinner orientacaodoMapa


        ArrayAdapter<String> adapterOrientacaodoMapa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,OrientacaodoMapa);
        adapterOrientacaodoMapa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerOrientacaodoMapa.setAdapter(adapterOrientacaodoMapa);

        if (OrientacaodoMapaSave != "notDefined") {
            int spinnerPosition = adapterOrientacaodoMapa.getPosition(OrientacaodoMapaSave);
            spinnerOrientacaodoMapa.setSelection(spinnerPosition);
        }


        // spinner tipodoMapa

        ArrayAdapter<String> adapterTipodoMapa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,TipodoMapa);
        adapterTipodoMapa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerTipodoMapa.setAdapter(adapterTipodoMapa);

        if (TipodoMapaSave != "notDefined") {
            int spinnerPosition = adapterTipodoMapa.getPosition(TipodoMapaSave);
            spinnerTipodoMapa.setSelection(spinnerPosition);
        }



        // on click

       /* Spinner spinnercoordenadas = findViewById(R.id.spinnercoordenadas);

        Spinner spinnervelocidade = findViewById(R.id.spinnervelocidade);

        Spinner spinnerOrientacaodoMapa = findViewById(R.id.spinnerMapa);

        Spinner spinnerTipodoMapa = findViewById(R.id.spinnerTipodoMapa);

        String coordenadasSave = prefs.getString("coordenadas", "notDefined");
        String velocidadeSave = prefs.getString("velocidade", "notDefined");
        String OrientacaodoMapaSave = prefs.getString("OrientacaodoMapa", "notDefined");
        String TipodoMapaSave = prefs.getString("TipodoMapa", "notDefined");
        Boolean trafegoSave = prefs.getBoolean("trafego", false);
        */
        // on click spinnercoordenadas
        spinnercoordenadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("coordenadas",spinnercoordenadas.getSelectedItem().toString());
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // on click spinnervelocidade
        spinnervelocidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("velocidade",spinnervelocidade.getSelectedItem().toString());
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on click spinnerOrientacaodoMapa
        spinnerOrientacaodoMapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("OrientacaodoMapa",spinnerOrientacaodoMapa.getSelectedItem().toString());
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on click spinnerTipodoMapa
        spinnerTipodoMapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("TipodoMapa",spinnerTipodoMapa.getSelectedItem().toString());
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // trafego

        switchTrafego.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("trafego",switchTrafego.isChecked());
                editor.apply();

            }
        });


        // btn voltar

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}