package com.example.n1dispositivosmoveis;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final int VERSAO = 1;
    private static final String BANCO_LOCALIZACAO = "bd_localizacao";

    private static final String TABELA_LOCALIZACAO = "tb_localizacao";

    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_LATITUDE = "latitude";
    private static final String COLUNA_LONGITUDE = "longitude";
    private static final String COLUNA_DATA = "data";
    private static final String COLUNA_VELOCIDADE = "velocidade";

    public BancoDados(@Nullable Context context) {
        super(context, BANCO_LOCALIZACAO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABELA_LOCALIZACAO + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY,"
                + COLUNA_LATITUDE + " DOUBLE,"
                + COLUNA_LONGITUDE + " DOUBLE,"
                + COLUNA_DATA + "STRING,"
                + COLUNA_VELOCIDADE + "DOUBLE"
                +")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_LOCALIZACAO);
        onCreate(db);
    }


    // CRUD ABAIXO
    
    /*void addLocalizacao(Localizacao localizacao) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_LATITUDE, localizacao.getLatitude());
        values.put(COLUNA_LONGITUDE, localizacao.getLongitude());
        values.put(COLUNA_DATA, localizacao.getData());
        values.put(COLUNA_VELOCIDADE, localizacao.getVelocidade());

        db.insert(TABELA_LOCALIZACAO, null, values);
        db.close();
    }*/


}
