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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.n1dispositivosmoveis.databinding.ActivityMapsBinding;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback , GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{

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


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView text = findViewById(R.id.Legenda);
        text.setText("ronaldo esta aqui testando");
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


        //onMyLocationButtonClick();
         /*  Location myloca= mMap.getMyLocation();
        // Define tipo do mapa
        //myloca.getLatitude()
        LatLng brasil = new LatLng(myloca.getLatitude(), myloca.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(brasil)
                .position(brasil)
                .title("Marker in Igreja do Bomfim"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(brasil));*/


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


        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(15.0f);

        // Configura elementos da interface gráfica
        UiSettings mapUI = mMap.getUiSettings();
        // habilita: pan, zoom, tilt, rotate
        mapUI.setAllGesturesEnabled(true);

        // habilita norte
        mapUI.setCompassEnabled(true);
        // habilta contole do zoom
        //mapUI.setZoomControlsEnabled(true);


        startLocationUpdates();



    }

    private void startLocationUpdates() {
        // Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // A permissão foi dada

            // Cria o cliente FusedLocation
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            // Configura solicitações de localização
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5*1000);
            mLocationRequest.setFastestInterval(1*1000);
            // Programa o evento a ser chamado em intervalo regulares de tempo
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    mMap.clear();
                    Location location=locationResult.getLastLocation();
                    LatLng brasil = new LatLng(location.getLatitude(), location.getLongitude());

                   /* int height = 100;
                    int width = 100;
                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.blankaar2);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);*/
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(brasil)
                            .title("atual")
                            .flat(true)
                            );
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.carfinal));

                    marker.setRotation(location.getBearing());
                   // Marker marker = new MarkerOptions().position(brasil).flat(true).title("asd");

                    /* mMap.addMarker(new MarkerOptions().position(brasil).flat(true)
                            .title("atual")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.blankfigmaleft));
*/
                     //.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.blankaar2));
                    //.setIcon();

                    /*imageView.setImageBitmap(
                            decodeSampledBitmapFromResource(getResources(),R.drawable.blankaar2, 100, 100));*/


                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(brasil )      // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
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

    private String getVelocidade(Location location){

        SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
        String coordenadasSave = prefs.getString("coordenadas", "notDefined");
        String velocidadeSave = prefs.getString("velocidade", "notDefined");

        String [] velocidade = {"Km/h","Mph",};
        Log.d("velocidade", velocidadeSave+location.getSpeed()*3.6);
        if(velocidadeSave=="notDefined"){
            return String.valueOf("Velocidade(km/h)="+location.getSpeed()*3.6);
        }else{
            if(velocidadeSave=="Km/h"){
                return String.valueOf("Velocidade(Km/h)="+location.getSpeed()*3.6);
            }else{
                if(velocidadeSave=="Mph"){
                    return String.valueOf("Velocidade(Mph)="+location.getSpeed());
                }else{
                    return "Velocidade()=";
                }
            }

        }





    }
    private String getLatLong(Location location){

        SharedPreferences prefs = getSharedPreferences("projeto", MODE_PRIVATE);
        String coordenadasSave = prefs.getString("coordenadas", "notDefined");

        Log.d("coordenadas", coordenadasSave);

        String [] coordenadas = {"Grau decimal","Grau-Minuto decimal","Grau-Minuto-Segundo decimal"};
        if(coordenadasSave=="notDefined"){
            /*return String.valueOf("Latitude(graus)="+location.getLatitude())+"\n"
                    +String.valueOf("Longitude(graus)="+location.getLongitude())+"\n";*/
            return String.valueOf("Latitude(Grau)="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                    +String.valueOf("Longitude(Grau)="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n";
        }else{
            if(coordenadasSave=="Grau decimal"){
                 return String.valueOf("Latitude(Graus)="+location.getLatitude())+"°"+"\n"
                        +String.valueOf("Longitude(Graus)="+location.getLongitude())+"°"+"\n";
               /* return String.valueOf("Latitude(Grau)="+Location.convert(location.getLatitude(),Location.FORMAT_DEGREES))+"\n"
                        +String.valueOf("Longitude(Grau)="+Location.convert(location.getLongitude(),Location.FORMAT_DEGREES))+"\n";*/
            }else{
                if(coordenadasSave=="Grau-Minuto decimal"){
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    df.format(1234.36);
                    double numLat =location.getLatitude();
                    double intLat = Math.floor(numLat);
                    double decimalLat =numLat % 1;
                    decimalLat= Math.abs(decimalLat*60);
                    String latitude =intLat+"°"+decimalLat+"’";

                    double numLong =location.getLongitude();
                    double intLong = Math.floor(numLong);
                    double decimalLong =numLong % 1;
                    decimalLong= Math.abs(decimalLong*60);
                    String longitude =intLong+"°"+decimalLong+"’";

                    //+Location.convert(location.getLatitude(),Location.FORMAT_SECONDS))+"\n"

                   /* return String.valueOf("Latitude(Grau-Minuto)="+latitude)+"\n"
                            +String.valueOf("Longitude(Grau-Minuto)="+longitude)+"\n";*/

                    return String.valueOf("Latitude(Grau-Minuto)="+Location.convert(location.getLatitude(),Location.FORMAT_MINUTES))+"\n"
                            +String.valueOf("Longitude(Grau-Minuto)="+Location.convert(location.getLongitude(),Location.FORMAT_MINUTES))+"\n";

                }else{
                        if(coordenadasSave=="Grau-Minuto-Segundo decimal"){
                           /* return String.valueOf("Latitude(graus)4="+location.getLatitude())+"\n"
                                    +String.valueOf("Longitude(graus)="+location.getLongitude())+"\n";*/

                            return String.valueOf("Latitude(Grau-Minuto-Segundo)="+Location.convert(location.getLatitude(),Location.FORMAT_SECONDS))+"\n"
                                    +String.valueOf("Longitude(Grau-Minuto-Segundo)="+Location.convert(location.getLongitude(),Location.FORMAT_SECONDS))+"\n";
                        }else{
                            return "Latitude()="+""+"\n"
                                    +"Longitude()="+""+"\n";
                        }
                }
            }
        }





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

        mMap.addMarker(new MarkerOptions().position(mylocali)

                .position(mylocali)
                .title("Minha localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocali, 15.0f));


        // Define tipo do mapa
        //myloca.getLatitude()






    }




}