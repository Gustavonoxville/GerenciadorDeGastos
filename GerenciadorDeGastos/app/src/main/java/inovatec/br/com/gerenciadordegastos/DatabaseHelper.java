package inovatec.br.com.gerenciadordegastos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavo on 29/10/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DE_DADOS = "Gerenciador";
    private static int VERSAO = 1;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DE_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gastos (valor TEXT ,descricao TEXT,tipo TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}