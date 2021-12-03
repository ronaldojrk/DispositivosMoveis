package com.example.n1dispositivosmoveis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationManagerActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager; // O Gerente de localização
    private LocationProvider locationProvider; // O provedor de localizações
    private GnssStatusCallback gnssStatusCallback; //O escutador do sistema GNSS
    private static final int REQUEST_LOCATION=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider=locationManager.getProvider(LocationManager.GPS_PROVIDER);
        ativaGNSS();
        Button btnSateliteBack = findViewById(R.id.btnVoltarSatelite);

        btnSateliteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
            gnssStatusCallback=new GnssStatusCallback();
            locationManager.registerGnssStatusCallback(gnssStatusCallback);
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }
    private void desativaGNSS() {
        locationManager.removeUpdates(this);
        locationManager.unregisterGnssStatusCallback(gnssStatusCallback);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                ativaGNSS();
            }
            else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this,"Sem permissão para acessar o sistema de posicionamento",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        TextView tv_location=(TextView)findViewById(R.id.tv_locationInfo);
        String mens="Dados da Última posição\n";
        if (location!=null) {
            mens+="Latitude(graus)="+Location.convert(location.getLatitude(),Location.FORMAT_SECONDS)+"\n"
                    +"Longitude(graus)="+Location.convert(location.getLongitude(),Location.FORMAT_SECONDS)+"\n"
                    +"Velocidade(m/s)="+location.getSpeed()+"\n"
                    +"Rumo(graus)="+location.getBearing();
        }
        else {
            mens+="Localização Não disponível";
        }
        tv_location.setText(mens);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private class GnssStatusCallback extends GnssStatus.Callback {
        public GnssStatusCallback() {
            super();
        }
        @Override
        public void onStarted() { }

        @Override
        public void onStopped() { }

        @Override
        public void onFirstFix(int ttffMillis) { }

        @Override
        public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
            TextView tv_gnss=(TextView)findViewById(R.id.tv_gnssInfo);
            String mens="Dados do Sitema de Posicionamento\n";
            if (status!=null) {
                mens+="Número de Satélites:"+status.getSatelliteCount()+"\n";
                for(int i=0;i<status.getSatelliteCount();i++) {
                    mens+="SVID="+status.getSvid(i)+"-"+status.getConstellationType(i)+
                            "Azi="+status.getAzimuthDegrees(i)+
                            "Elev="+status.getElevationDegrees(i)+
                            "Used in Fix"+status.usedInFix(i)+
                            "SNR="+status.getCn0DbHz(i)+"|X|\n";
                }
            }
            else {
                mens+="GNSS Não disponível";
            }
            tv_gnss.setText(mens);
        }
    }
}

