package br.com.fatecpg.sqldemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guto on 29/05/16.
 */

//cria uma extensão da classe SQLiteOpenHelper e cria o banco de dados
public class DisciplinasSQLiteHelp extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "disciplinas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table DISCIPLINAS("
            +"ID INTEGER primary key autoincrement"
            +", NOME VARCHAR NOT NULL"
            +", P1 NUMERIC"
            +", P2 NUMERIC"
            +");";
    public DisciplinasSQLiteHelp(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    //metodo que cria a tabela DISCIPLINAS na base de dados disciplinas.db
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }

    //metodo que cria a tabela DISCIPLINAS na base de dados disciplinas.db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS DISCIPLINAS");
        onCreate(db);
    }

    //metodo que atualiza a tabela DISCIPLINAS na base de dados disciplinas.db na linha desejada


}
