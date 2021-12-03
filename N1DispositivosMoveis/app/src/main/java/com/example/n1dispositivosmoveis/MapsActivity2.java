package com.example.n1dispositivosmoveis;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.n1dispositivosmoveis.databinding.ActivityMaps2Binding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    ArrayList<Localizacao> locali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions unifacs_ctn_markerOptions=new MarkerOptions();
        unifacs_ctn_markerOptions.position(new LatLng(-12.98155, -38.45121));
        unifacs_ctn_markerOptions.title("CTN");
        unifacs_ctn_markerOptions.snippet("Campus Tancredo Neves");

        LocalizacaoDAO crud = new LocalizacaoDAO(getBaseContext());
        final Cursor cursor = crud.carregaDados();

        locali =new ArrayList<>();
        while (cursor.moveToNext())
        {


            String id =cursor.getString(cursor.getColumnIndex(BancoDados.CODIGO));
            String latitude =cursor.getString(cursor.getColumnIndex(BancoDados.LATITUDE));
            String longitude =cursor.getString(cursor.getColumnIndex(BancoDados.LONGITUDE));
            String data =cursor.getString(cursor.getColumnIndex(BancoDados.DATA));
            Localizacao ron =new Localizacao (1,latitude,longitude,data,"1000");

           locali.add(ron);
        }

        if(locali.size()>0){
            LatLng mark = new LatLng(Double.parseDouble(locali.get(0).getLatitude()), Double.parseDouble(locali.get(0).getLongitude()));
            MarkerOptions inicio=new MarkerOptions();
            inicio.position(mark);
            inicio.title("Inicio da rota");
            inicio.snippet("Começou");
            mMap.addMarker(inicio);

            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            for (int z = 0; z < locali.size(); z++) {
                LatLng point = new LatLng(Double.parseDouble(locali.get(z).getLatitude()), Double.parseDouble(locali.get(z).getLongitude()));
                options.add(point);
            }
            mMap.addPolyline(options);






            LatLng pointt = new LatLng(Double.parseDouble(locali.get(locali.size()-1).getLatitude()), Double.parseDouble(locali.get(locali.size()-1).getLongitude()));

            MarkerOptions fim=new MarkerOptions();
            fim.position(pointt);
            fim.title("Fim da rota");
            fim.snippet("Acabou");
            mMap.addMarker(fim);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointt, 15.0f));
        }else{

             mMap.addMarker(unifacs_ctn_markerOptions);

            // cria um ponto com as coordenadas do CTN
            LatLng unifacs_ctn = new LatLng(-12.98155, -38.45121);
            // posiciona o ponto de vista (centraliza em um ponto)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(unifacs_ctn, 15.0f));

        }


       // mMap.addPolyline();
        // adiciona marcador ao mapa


        // Define tipo do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // habilita mapas indoor e 3D
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);

        // habilita camada de tráfego
        //mMap.setTrafficEnabled(true);

        // Configura elementos da interface gráfica
        UiSettings mapUI = mMap.getUiSettings();
        // habilita: pan, zoom, tilt, rotate
        mapUI.setAllGesturesEnabled(true);
        // habilita norte
        mapUI.setCompassEnabled(true);
        // habilta contole do zoom
        mapUI.setZoomControlsEnabled(true);


    }
}