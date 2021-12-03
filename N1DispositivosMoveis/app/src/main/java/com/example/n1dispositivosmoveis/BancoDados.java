package com.example.n1dispositivosmoveis;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {


    public static final int VERSAO = 1;
    public static final String BANCO_LOCALIZACAO = "banco.db";

    public static final String TABELA = "loca";

    public static final String CODIGO = "_id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DATA = "dataagora";
    public static final String VELOCIDADE = "velocidade";

    public BancoDados( Context context) {
        super(context, BANCO_LOCALIZACAO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       /* String sql = "CREATE TABLE " + TABELA_LOCALIZACAO + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY,"
                + COLUNA_LATITUDE + " DOUBLE,"
                + COLUNA_LONGITUDE + " DOUBLE,"
                + COLUNA_DATA + "STRING,"
                + COLUNA_VELOCIDADE + "DOUBLE"
                +")";*/

        String sql = "CREATE TABLE "+TABELA+"("
                + CODIGO+" integer primary key autoincrement,"
                + LATITUDE+" text,"
                + LONGITUDE+" text,"
                + DATA+" text,"
                + VELOCIDADE +" text"
                +")";

        db.execSQL(sql);

        //String sql = "CREATE TABLE "+TABELA+"(" + ID + " integer primary key autoincrement," + NOME + " text,"+ ID_FILME + " text," + ID_USER + " text)";
        //db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }


    // CRUD ABAIXO
    
    /*public void addLocalizacao(Localizacao localizacao) {

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
