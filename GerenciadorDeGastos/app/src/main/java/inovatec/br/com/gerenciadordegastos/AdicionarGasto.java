package inovatec.br.com.gerenciadordegastos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AdicionarGasto extends AppCompatActivity {
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton alimentacao, traspostes, lazer, custosD, outros;
    private DatabaseHelper helper;
    private EditText descricao,valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_gasto);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Adicionar Gasto");
        getSupportActionBar().setLogo(R.drawable.add);

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        alimentacao = (RadioButton) findViewById(R.id.alimentacao);
        traspostes = (RadioButton) findViewById(R.id.transportes);
        lazer = (RadioButton) findViewById(R.id.lazer);
        custosD = (RadioButton) findViewById(R.id.custosDomesticos);
        outros = (RadioButton) findViewById(R.id.outros);

        descricao = (EditText)findViewById(R.id.descricao);
        valor = (EditText)findViewById(R.id.valor);

        helper = new DatabaseHelper(this);


    }

    public void adicionaGasto(View view) {

        if((descricao.getText().toString().isEmpty())||(valor.getText().toString().isEmpty())){
            Log.i("LOG","aki");
            Toast.makeText(this,"Valores inválidos!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdicionarGasto.this, AdicionarGasto.class);
            startActivity(intent);
         //   finish();
        }else{
            String tipo = "";

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == alimentacao.getId()) {
                tipo = "Alimentação";

            } else if (selectedId == traspostes.getId()) {
                tipo = "Trasporte";

            } else if (selectedId == lazer.getId()) {
                tipo = "Lazer";

            } else if (selectedId == custosD.getId()) {
                tipo = "Custos domésticos";

            } else if (selectedId == outros.getId()) {
                tipo = "Outros";

            }
            String aux = "";
            if(descricao.getText().toString().length()>=25){
                aux = (descricao.getText().toString()).substring(0, 25) + "...";
            }else{
                aux = descricao.getText().toString();
            }

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("valor", valor.getText().toString());
            values.put("descricao", aux);
            values.put("tipo", tipo);

            long resultado = db.insert("gastos", null, values);
            if (resultado != -1) {
                Log.i("LOG", "SALVO");
            } else {
                Log.i("LOG", "NAAAAAO SALVO");
            }

            Intent intent = new Intent(AdicionarGasto.this, MainActivity.class);
            startActivity(intent);


        }


    }

    @Override
    public boolean onSupportNavigateUp() {
       // finish();
        Intent intent = new Intent(AdicionarGasto.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        //Execute your code here


        Intent intent = new Intent(AdicionarGasto.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adicionar_gasto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
