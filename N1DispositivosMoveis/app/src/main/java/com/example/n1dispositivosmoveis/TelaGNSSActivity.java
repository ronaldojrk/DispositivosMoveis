package com.example.n1dispositivosmoveis;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class TelaGNSSActivity extends AppCompatActivity implements LocationListener {
        private LocationManager locationManager; // O Gerente de localização
        private LocationProvider locationProvider; // O provedor de localizações
        private MyGnssStatusCallback gnssStatusCallback; //O escutador do sistema GNSS
        private static final int REQUEST_LOCATION = 2;
        private TelaGNSSView gnssView;

    Button btnTodos,btn1,btn5,btn6 ,btn3,btn7,btn4,btn2, btn0 ;
    TextView cont;



                 // Beidou 5

                            //  Galileo 6:

                            //  Glonass 3:

                            // Ba GPS .1:

                            //  IRNSS. 7:

                            //QZSS. 4:

                            // SBAS. 2:

                            // desconhecido.  0




    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela_draw);
            gnssView=findViewById(R.id.gnssDraw1);


        btnTodos  = findViewById(R.id.btnTodos);
         btn1  = findViewById(R.id.btncontela1);

         btn5  = findViewById(R.id.btn5);

         btn6  = findViewById(R.id.btn6);

         btn3  = findViewById(R.id.btn3);

         btn7  = findViewById(R.id.btn7);

        btn4  = findViewById(R.id.btn4);

       btn2  = findViewById(R.id.btn2);

         btn0  = findViewById(R.id.btn0);

        clean();


       /* SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
        int opc = prefs.getInt("SatelliteCount", 0);*/

       cont = findViewById(R.id.numeroSat);

       // cont.setText(opc);





        // Beidou 5

        //  Galileo 6:

        //  Glonass 3:

        // Ba GPS .1:

        //  IRNSS. 7:

        //QZSS. 4:

        // SBAS. 2:

        // desconhecido.  0
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            ativaGNSS();




        btnTodos.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","todos");
                editor.apply();
                clean();
                btnTodos.setBackgroundColor(Color.YELLOW);

                //Toast.makeText(getApplicationContext(),"Status do historico: desativado", Toast.LENGTH_LONG).show();




            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","1");
                editor.apply();
                clean();
                btn1.setBackgroundColor(Color.YELLOW);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","5");
                editor.apply();
                clean();
                btn5.setBackgroundColor(Color.YELLOW);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","6");
                editor.apply();
                clean();
                btn6.setBackgroundColor(Color.YELLOW);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","3");
                editor.apply();
                clean();
                btn3.setBackgroundColor(Color.YELLOW);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","7");
                editor.apply();
                clean();
                btn7.setBackgroundColor(Color.YELLOW);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","4");
                editor.apply();
                clean();
                btn4.setBackgroundColor(Color.YELLOW);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","2");
                editor.apply();
                clean();
                btn2.setBackgroundColor(Color.YELLOW);
            }
        });


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("projeto", MODE_PRIVATE).edit();
                editor.putString("Sat","0");
                editor.apply();
                clean();
                btn0.setBackgroundColor(Color.YELLOW);
            }
        });

    }


   private void clean (){

       btn1.setBackgroundColor(Color.BLUE);
       btnTodos.setBackgroundColor(Color.BLUE);
       btn5.setBackgroundColor(Color.BLUE);
       btn6.setBackgroundColor(Color.BLUE);
       btn3.setBackgroundColor(Color.BLUE);
       btn7.setBackgroundColor(Color.BLUE);
       btn4.setBackgroundColor(Color.BLUE);
       btn2.setBackgroundColor(Color.BLUE);
       btn0.setBackgroundColor(Color.BLUE);



   }



        @Override
        protected void onStop() {
            super.onStop();
            desativaGNSS();
        }

        private void ativaGNSS() {
            // Se a app já possui a permissão, ativa a camada de localização
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(locationProvider.getName(),
                        5*1000,
                        0.1f,
                        this);
                gnssStatusCallback = new MyGnssStatusCallback();
                locationManager.registerGnssStatusCallback(gnssStatusCallback);
            } else {
                // Solicite a permissão
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            }
        }

        private void desativaGNSS() {
                locationManager.unregisterGnssStatusCallback(gnssStatusCallback);
        }

        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_LOCATION) {
                if (grantResults.length == 1 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // O usuário acabou de dar a permissão
                    ativaGNSS();
                } else {
                    // O usuário não deu a permissão solicitada
                    Toast.makeText(this, "Sem permissão para acessar o sistema de posicionamento",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {

        }

        private class MyGnssStatusCallback extends GnssStatus.Callback {
            public MyGnssStatusCallback() {
                super();
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onStopped() {
            }

            @Override
            public void onFirstFix(int ttffMillis) {
            }

            @Override
            public void onSatelliteStatusChanged(@NonNull GnssStatus status) {

                SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
                String opc = prefs.getString("Sat", "todos");





                cont.setText(""+status.getSatelliteCount());
                gnssView.onSatelliteStatusChanged(status,opc);
                gnssView.invalidate();

            }
        }
}
