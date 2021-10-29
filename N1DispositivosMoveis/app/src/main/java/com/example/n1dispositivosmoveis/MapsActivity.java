package com.example.n1dispositivosmoveis;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.n1dispositivosmoveis.databinding.ActivityMapsBinding;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_LAST_LOCATION = 1;
    private static final int REQUEST_LOCATION_UPDATES = 2;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lastLocation();
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
        String OrientacaodoMapaSave = prefs.getString("OrientacaodoMapa", "notDefined");

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


        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(15.0f);

        // Configura elementos da interface gráfica
        UiSettings mapUI = mMap.getUiSettings();
        // habilita: pan, zoom, tilt, rotate

        if (OrientacaodoMapaSave != "notDefined") {
            mapUI.setAllGesturesEnabled(true);
            mapUI.setRotateGesturesEnabled(true);
            mapUI.setCompassEnabled(true);
            mapUI.setTiltGesturesEnabled(true);
        }else{
           if(OrientacaodoMapaSave.equals("North Up")){
               Log.d("OrientacaodoMapaSave: ", OrientacaodoMapaSave);
               mapUI.setAllGesturesEnabled(false);
               mapUI.setRotateGesturesEnabled(false);
               mapUI.setCompassEnabled(false);
               mapUI.setTiltGesturesEnabled(false);
           }else{
               Log.d("OrientacaodoMapaSave: ", OrientacaodoMapaSave);
               if(OrientacaodoMapaSave.equals("Course Up")){
                   mapUI.setAllGesturesEnabled(false);
                   mapUI.setRotateGesturesEnabled(false);
                   mapUI.setCompassEnabled(true);
                   mapUI.setTiltGesturesEnabled(true);
               }else{
                   Log.d("OrientacaodoMapaSave: ", OrientacaodoMapaSave);
                   if(OrientacaodoMapaSave.equals("Nenhuma")){
                       mapUI.setAllGesturesEnabled(true);
                       mapUI.setRotateGesturesEnabled(true);
                       mapUI.setCompassEnabled(true);
                       mapUI.setTiltGesturesEnabled(true);
                   }
               }
           }
        }

        startLocationUpdates();



    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada


            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            // Configura solicitações de localização

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5*1000);
            mLocationRequest.setFastestInterval(1*1000);


            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    mMap.clear();
                    Location location=locationResult.getLastLocation();
                    LatLng brasil = new LatLng(location.getLatitude(), location.getLongitude());


                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(brasil)
                            .title("atual")
                            .flat(true)
                            );
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.carfinal));

                    marker.setRotation(location.getBearing());

                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(brasil)
                            .radius(700)
                            .strokeColor(Color.rgb(36, 67, 97))
                            .fillColor(Color.TRANSPARENT));



                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(brasil)
                            .zoom(17)
                            .bearing(location.getBearing())
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(brasil));



                    TextView text = findViewById(R.id.Legenda);
                    text.setText("Latitude ");



                    String status="";

                    status+=getLatLong(location)+getVelocidade(location);


                    text.setText(status);


                }
            };
            //
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,mLocationCallback,null);
        } else {
            // Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_UPDATES);
        }
    }



    private void lastLocation() {
        // Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {


                    getlastLocalization(location);
                }
            });
        } else {
            // pede a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LAST_LOCATION);
        }
    }

    private void getlastLocalization(Location location){
        TextView text = findViewById(R.id.Legenda);
        String status="";
        status+=getLatLong(location)+getVelocidade(location);
        text.setText(status);
    }


    private String getVelocidade(Location location){

        SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
        String velocidadeSave = prefs.getString("velocidade", "notDefined");
        Log.d("velocidade", velocidadeSave+location.getSpeed()*3.6);
        if(velocidadeSave.equals("notDefined")){
            return String.valueOf("Velocidade(km/h)="+location.getSpeed()*3.6);
        }else{
            if(velocidadeSave.equals("Km/h")){
                return String.valueOf("Velocidade(Km/h)="+location.getSpeed()*3.6);
            }else{
                if(velocidadeSave.equals("Mph")){
                    return String.valueOf("Velocidade(Mph)="+location.getSpeed());
                }else{

                    return String.valueOf("Velocidade(Mph)="+location.getSpeed());
                }
            }

        }





    }
    private String getLatLong(Location location){

        SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
        String coordenadasSave = prefs.getString("coordenadas", "notDefined");

        Log.d("coordenadas", coordenadasSave+String.valueOf("Latitude ="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                +String.valueOf("Longitude ="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n");

        if(coordenadasSave=="notDefined"){

            return String.valueOf("Latitude ="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                    +String.valueOf("Longitude ="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n";
        }else{
            if(coordenadasSave=="Grau decimal"){
                 /*return String.valueOf("Latitude(Graus)="+location.getLatitude())+"°"+"\n"
                        +String.valueOf("Longitude(Graus)="+location.getLongitude())+"°"+"\n";*/
               return String.valueOf("Latitude(Grau)="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                        +String.valueOf("Longitude(Grau)="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n";
            }else{
                if(coordenadasSave=="Grau-Minuto decimal"){


                    return String.valueOf("Latitude="+Location.convert(location.getLatitude(),Location.FORMAT_MINUTES).replace(":","° ").replace(",","\'"))+"\n"
                            +String.valueOf("Longitude="+Location.convert(location.getLongitude(),Location.FORMAT_MINUTES).replace(":","° ").replace(",","\'"))+"\n";

                }else{
                        if(coordenadasSave=="Grau-Minuto-Segundo decimal"){

                                String latnormal = Location.convert(location.getLatitude(),Location.FORMAT_SECONDS).replace(":","\' ").replace(",","\'")+"\"";
                                String lat = latnormal.replaceFirst("\'","° ");

                            String longnormal = Location.convert(location.getLatitude(),Location.FORMAT_SECONDS).replace(":","\' ").replace(",","\'")+"\"";
                            String longa = longnormal.replaceFirst("\'","° ");
                            return String.valueOf("Latitude ="+lat)+"\n"
                                    +String.valueOf("Longitude ="+longa)+"\n";
                        }else{
                            return String.valueOf("Latitude ="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                                    +String.valueOf("Longitude ="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n";
                        }
                }
            }
        }





    }






}