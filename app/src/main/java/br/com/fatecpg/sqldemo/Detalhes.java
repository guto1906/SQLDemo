package br.com.fatecpg.sqldemo;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Detalhes extends AppCompatActivity {
    DisciplinasSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;
    Integer cod;
    Float p1;
    Float p2;
    String n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Intent detalhes = getIntent();
        cod = detalhes.getIntExtra("cod", 0);
        n = detalhes.getStringExtra("nome");
        p1 = detalhes.getFloatExtra("p1", 0);
        p2 = detalhes.getFloatExtra("p2", 0);
        EditText nome = (EditText) findViewById(R.id.nmdisciplina);
        nome.setText(n);
        EditText vlrp1 = (EditText) findViewById(R.id.vlp1);
        vlrp1.setText(p1.toString());
        EditText vlrp2 = (EditText) findViewById(R.id.vlp2);
        vlrp2.setText(p2.toString());

        EditText codigo = (EditText) findViewById(R.id.codid);
        codigo.setText(cod.toString());
    }//fecha o oncreat

    public void gravar(View view){
        try{
            Intent main = new Intent(Detalhes.this, MainActivity.class);
            EditText nome = (EditText) findViewById(R.id.nmdisciplina);
            String nmdisciplina = nome.getText().toString();

            EditText p1 = (EditText) findViewById(R.id.vlp1);
            Float vlrp1 = Float.parseFloat(p1.getText().toString());

            EditText p2 = (EditText) findViewById(R.id.vlp2);
            Float vlrp2 = Float.parseFloat(p2.getText().toString());

            dbHelper = new DisciplinasSQLiteHelp(getApplicationContext());
            db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE DISCIPLINAS SET NOME = '"
                                           +nmdisciplina+"', P1 = "
                                           +vlrp1+" , P2 = "
                                           +vlrp2+" WHERE ID = "+cod+";");
            db.close();
            dbHelper.close();
            startActivity(main);

        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
        }//fecha o catch
    }//fecha o gravar

     //função de exclusao de linha
     public void excluir(View view){
         try{
             Intent main = new Intent(Detalhes.this, MainActivity.class);
             dbHelper = new DisciplinasSQLiteHelp(getApplicationContext());
             db = dbHelper.getWritableDatabase();
             db.execSQL("DELETE FROM DISCIPLINAS WHERE ID = "+cod+";");
             db.close();
             dbHelper.close();
             startActivity(main);

         } catch (Exception ex){
             new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
         }//fecha o catch
     }//fecha exclusao


}
