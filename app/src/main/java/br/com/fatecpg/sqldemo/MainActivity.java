package br.com.fatecpg.sqldemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DisciplinasSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;
    ArrayAdapter<String> disciplinasAdapter = null;

    protected void atualizarLista(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DISCIPLINAS", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Integer id = cursor.getInt(0);
            String nome = cursor.getString(1);
            disciplinasAdapter.add(nome);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        dbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cria um array list de strings para armazenar os nomes das disciplinas
        final ArrayList<String> disciplinas = new ArrayList<String>();

        //cria um ArrayAdapter como os nomes das disciplinas
        disciplinasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, disciplinas);

        //pega a listView da activitymain pelo id
        ListView listViewDisciplinas = (ListView) findViewById(R.id.listViewDisciplinas);

        //insere na listView os ArrayAdapter das disciplinas
        listViewDisciplinas.setAdapter(disciplinasAdapter);

        try{
            //Cria uma base de dados como os dados de contexto da aplicacao usando
            //o metodo criado na classe DisciplinasSQLiteHelp
            dbHelper = new DisciplinasSQLiteHelp(getApplicationContext());

            //chama o método atualizar
            atualizarLista();

            //trata os erros
        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
        }

        //método para executar ao clicar em um item da lista
        listViewDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //cria a intent com os dados da Detalhe.class
                Intent detalhes = new Intent(MainActivity.this,Detalhes.class);

                //pegar o valor do id do item da lista de disciplinas
                String m = disciplinas.get((int) id);
                db = dbHelper.getReadableDatabase();
                Cursor dados = db.rawQuery("SELECT ID, NOME, P1, P2  FROM DISCIPLINAS WHERE NOME = '" + m + "';", null);
                dados.moveToFirst();
                Integer cod = dados.getInt(0);
                String nome = dados.getString(1);
                Float p1 = dados.getFloat(2);
                Float p2 = dados.getFloat(3);
                dados.close();
                db.close();
                dbHelper.close();
                detalhes.putExtra("cod", cod);
                detalhes.putExtra("nome", nome);
                detalhes.putExtra("p1", p1);
                detalhes.putExtra("p2", p2);
                startActivity(detalhes);

            }
        });
    }

    public void addClick(View view){
        try{
            EditText editTextNovo = (EditText) findViewById(R.id.editTextNovo);
            String disciplina = editTextNovo.getText().toString();
            db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO DISCIPLINAS(NOME) VALUES('"+disciplina+"')");
            db.close();
            dbHelper.close();
            editTextNovo.setText("");
            disciplinasAdapter.add(disciplina);
        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
        }
    }


}
