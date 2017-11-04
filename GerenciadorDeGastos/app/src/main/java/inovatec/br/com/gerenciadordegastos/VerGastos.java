package inovatec.br.com.gerenciadordegastos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class VerGastos extends AppCompatActivity {
    private Toolbar toolbar;
    private DatabaseHelper helper;
    double alimentacao = 0, transporte = 0, lazer = 0, custos = 0, outros = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_gastos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Meus Gráficos");
        getSupportActionBar().setLogo(R.drawable.grafico);

        helper = new DatabaseHelper(this);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(valoresAlimentos(), 0));
        entries.add(new Entry(valoresTrasportes(), 1));
        entries.add(new Entry(valoresLazer(), 2));
        entries.add(new Entry(valoresCustos(), 3));
        entries.add(new Entry(valoresOutros(), 4));


        PieDataSet dataset = new PieDataSet(entries, "#Legenda");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Alimentação");
        labels.add("Trasnporte");
        labels.add("Lazer");
        labels.add("Custos");
        labels.add("Outros");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("");
        pieChart.setData(data);

        pieChart.animateY(1000);
    }

    public float valoresAlimentos() {
        float value = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor FROM gastos WHERE tipo ='Alimentação';", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            value = value +  Float.parseFloat(valor);
            Log.i("LOG", "valor float  alimentos" + value);
            cursor.moveToNext();
        }
        cursor.close();

        return value;
    }
    public float valoresTrasportes() {
        float value = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor FROM gastos WHERE tipo ='Trasporte';", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            value = value +  Float.parseFloat(valor);
            Log.i("LOG", "valor float transpostes " + value);
            cursor.moveToNext();
        }
        cursor.close();

        return value;
    }
    public float valoresLazer() {
        float value = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor FROM gastos WHERE tipo ='Lazer';", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            value = value +  Float.parseFloat(valor);
            Log.i("LOG", "valor float lazer " + value);
            cursor.moveToNext();
        }
        cursor.close();

        return value;
    }
    public float valoresCustos() {
        float value = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor FROM gastos WHERE tipo ='Custos domésticos';", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            value = value +  Float.parseFloat(valor);
            Log.i("LOG", "valor float custos  " + value);
            cursor.moveToNext();
        }
        cursor.close();

        return value;
    }

    public float valoresOutros() {
        float value = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor FROM gastos WHERE tipo ='Outros';", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            value = value +  Float.parseFloat(valor);
            Log.i("LOG", "valor float outros " + value);
            cursor.moveToNext();
        }
        cursor.close();

        return value;
    }
    @Override
    public boolean onSupportNavigateUp() {
        // finish();
        Intent intent = new Intent(VerGastos.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        //Execute your code here

        Intent intent = new Intent(VerGastos.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_gastos, menu);
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
