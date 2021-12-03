package com.example.n1dispositivosmoveis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class LocalizacaoDAO {

    private SQLiteDatabase db;
    private BancoDados conexao;

    public LocalizacaoDAO(Context context){
        conexao = new BancoDados(context);
    }

    public String insereDado(Localizacao localizacao){




        ContentValues valores;
        long resultado;

        db = conexao.getWritableDatabase();
        valores = new ContentValues();

        valores.put(BancoDados.LATITUDE, localizacao.getLatitude());
        valores.put(BancoDados.LONGITUDE, localizacao.getLongitude());
        valores.put(BancoDados.DATA, localizacao.getData());
        valores.put(BancoDados.VELOCIDADE, localizacao.getVelocidade());

        // nullcolumnhack identificar coluna que aceite nulo
        resultado = db.insert(BancoDados.TABELA, null, valores);
        db.close();
        //return localizacao.getData();
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {conexao.CODIGO,conexao.LATITUDE,conexao.LONGITUDE,conexao.DATA};
        db = conexao.getReadableDatabase();
        cursor = db.query(conexao.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void deletaRegistro(){
        //String where = BancoDados.CODIGO + "!=" + livro.getId();
        db = conexao.getReadableDatabase();

        db.delete(BancoDados.TABELA,null,null);
        db.close();
    }
}
