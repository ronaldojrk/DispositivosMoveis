package com.example.n1dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class visualizar extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);


        LocalizacaoDAO crud = new LocalizacaoDAO(getBaseContext());
        final Cursor cursor = crud.carregaDados();

        String[] nomeCampos = new String[]
                {BancoDados.CODIGO,BancoDados.LATITUDE,BancoDados.LONGITUDE ,BancoDados.DATA};
        int[] idViews = new int[] {R.id.loca, R.id.lat,R.id.log,R.id.data};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.localayout,cursor,nomeCampos,idViews, 0);
        lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);

    }
}