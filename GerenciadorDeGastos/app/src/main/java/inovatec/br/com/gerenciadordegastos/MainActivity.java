package inovatec.br.com.gerenciadordegastos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private List<Despesa> listDespesa = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapaterDespesa mAdapter;

    private DatabaseHelper helper;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(" Meus Gastos");
        getSupportActionBar().setLogo(R.drawable.logo);


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              //  Toast.makeText(MainActivity.this, "adicionar", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AdicionarGasto.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              //  Toast.makeText(MainActivity.this, "ver", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, VerGastos.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AdapaterDespesa(listDespesa);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                          Despesa d = listDespesa.get(position);
                        //     Toast.makeText(MainActivity.this, ">>" + d.getValor(), Toast.LENGTH_LONG).show();
                        //    mAdapter.removeItem(position);
                        final String des = d.getDescricao();
                        final int i = position;
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Deletar Gasto?")
                                .setMessage("Deseja remover esse gasto da sua lista?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Toast.makeText(MainActivity.this, "Removido", Toast.LENGTH_SHORT).show();
                                        SQLiteDatabase db = helper.getReadableDatabase();
                                        db.execSQL("DELETE FROM gastos WHERE descricao = '"+des+"'");
                                        db.close();
                                        mAdapter.removeItem(i);
                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                     //   Toast.makeText(MainActivity.this," ",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setIcon(R.drawable.remover)
                                .show();
                    }
                })
        );


        mAdapter.notifyDataSetChanged();
        carregaMsg();
    }


    public void carregaMsg() {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valor,descricao,tipo FROM gastos;", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String valor = cursor.getString(0);
            String descricao = cursor.getString(1);
            String tipo = cursor.getString(2);
            Log.i("LOG",""+ valor +" "+descricao+" tipo "+tipo);
            mAdapter.addItem(new Despesa( valor,descricao,tipo));
            cursor.moveToNext();
        }
        cursor.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
