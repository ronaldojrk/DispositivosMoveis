package com.example.n1dispositivosmoveis;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.n1dispositivosmoveis.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback , GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //@SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final String MY_PREFS_NAME  = "projeto";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String TipodoMapaSave = prefs.getString("TipodoMapa", "notDefined");
        Boolean trafegoSave = prefs.getBoolean("trafego", false);

        onMyLocationButtonClick();

        // Define tipo do mapa


        String [] TipodoMapa = {"Vetorial","Imagem de Satélite",};

        if (TipodoMapaSave == "notDefined") {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }else{

            if(TipodoMapaSave=="Vetorial"){
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            }else{
                if(TipodoMapaSave=="Imagem de Satélite"){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                }
            }
        }

        //

        // habilita mapas indoor e 3D
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);

        // habilita camada de tráfego

        mMap.setTrafficEnabled(trafegoSave);





        // Configura elementos da interface gráfica
        UiSettings mapUI = mMap.getUiSettings();
        // habilita: pan, zoom, tilt, rotate
        mapUI.setAllGesturesEnabled(true);
        // habilita norte
        //mapUI.setCompassEnabled(true);
        // habilta contole do zoom
        mapUI.setZoomControlsEnabled(true);

        // habilita camada de localização (MyLocation)
        habilitaMyLocation();



    }

    private void habilitaMyLocation() {
        // Registra esta atividade como escutadora do click no botão de localização
        mMap.setOnMyLocationButtonClickListener(this);
        // Registra esta atividade como escutadora do click do ícone de  localização
        mMap.setOnMyLocationClickListener(this);
        // Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            mMap.setMyLocationEnabled(true);

        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // O usuário acabou de dar a permissão
                habilitaMyLocation();
            }
            else {
                // O usuário não deu a permissão solicitada
                Toast.makeText(this,"Sem permissão para mostrar sua localização",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "Clicou no botão de localização",Toast.LENGTH_SHORT).show();
        // retorne falso para indicar que o comportamento default desta ação deve ser
        // mantido (animar a câmera para a posição atual do usuário)


        return false;
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Localização Atual (Lat,Lon,Alt):\n"+
                "("+location.getLatitude()+","+location.getLongitude()+","
                +location.getAltitude()+")", Toast.LENGTH_LONG).show();
        LatLng mylocali = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocali, 15.0f));


    }




}