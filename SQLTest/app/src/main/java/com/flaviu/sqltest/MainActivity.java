package com.flaviu.sqltest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqLite-test-1.db", MODE_PRIVATE, null);
        String sql = "DROP TABLE IF EXISTS contacts;";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE contacts(name TEXT, phone INTEGER, email TEXT);";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO contacts VALUES('tim',582181,'tim@email.com');";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO contacts VALUES('fred',123131,'fred@email.com');";
        sqLiteDatabase.execSQL(sql);

        Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM contacts;", null);
        if (query.moveToFirst()) {
            do {
                String name = query.getString(0);
                int phone = query.getInt(1);
                String email = query.getString(2);
                Toast.makeText(this, "Name =" + name + "Phone:" + phone + "Email" + email, Toast.LENGTH_LONG).show();

            } while (query.moveToNext());
        }
        query.close();
        sqLiteDatabase.close();
    }
}