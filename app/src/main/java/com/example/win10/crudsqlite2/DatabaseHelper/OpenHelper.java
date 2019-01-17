package com.example.win10.crudsqlite2.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentDatabase";

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *  Membuat Table
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE students " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstname TEXT," +
                "email TEXT)";

        db.execSQL(sql);

    }

    /**
     *  Apabila table sudah tersedia maka akan dihilangkan atau di hapus dan di gantu dengan yang baru
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS students";
        db.execSQL(sql);

        onCreate(db);
    }

}

