package com.example.win10.crudsqlite2.Function;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.win10.crudsqlite2.DatabaseHelper.OpenHelper;
import com.example.win10.crudsqlite2.TableDB.Students;

import java.util.ArrayList;
import java.util.List;

public class F_Students extends OpenHelper {

    public F_Students(Context context) {
        super(context);
    }

    /**
     * Insert nilai atau value ke dalam table
     * */
    public boolean create(Students students) {

        /**
         *  Menyimpan Value yang akan di proses atau bisa di proses
         * */
        ContentValues values = new ContentValues();

        /**
         *  Menyimput 2 Value yaitu firstname atau email
         * */
        values.put("firstname", students.firstname);
        values.put("email", students.email);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("students", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    /**
     * Value atau data yang berada di dalam table students akan di tampilkan atau di panggil
     * */
    public int count() {
          SQLiteDatabase db = this.getWritableDatabase();

          /**
           *  Menampilkan isi dalam table atau Semua Record yang ada
           * */
        String sql = "SELECT * FROM students";
        @SuppressLint("Recycle") int recordCount = db.rawQuery(sql,null).getCount();
        db.close();

        return recordCount;

    }

    public List<Students> read() {

        /**
         *  Record yang ada akan dijadikan Arraylist
         * */
        List<Students> recordsList = new ArrayList<Students>();

        /**
         *  Urutan yang ada akan berdasarkan huruf Z-A
         * */

        String sql = "SELECT * FROM students ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String studentFirstname = cursor.getString(cursor.getColumnIndex("firstname"));
                String studentEmail = cursor.getString(cursor.getColumnIndex("email"));

                Students objectStudent = new Students();
                objectStudent.id = id;
                objectStudent.firstname = studentFirstname;
                objectStudent.email = studentEmail;

                recordsList.add(objectStudent);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    /**
     *  Akan membaca satu record atau melihat isinya
     * */
    public Students readSingleRecord(int studentId) {

        Students objectStudent = null;

        String sql = "SELECT * FROM students WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            objectStudent = new Students();
            objectStudent.id = id;
            objectStudent.firstname = firstname;
            objectStudent.email = email;

        }

        cursor.close();
        db.close();

        return objectStudent;

    }

    /**
     *  Function yang akan mengupdate record yang kita pilih dan mengembalikannya ke data base
     * */
    public boolean update(Students objectStudent) {

        ContentValues values = new ContentValues();

        values.put("firstname", objectStudent.firstname);
        values.put("email", objectStudent.email);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectStudent.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("students", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }
    /**
     *  Akan Menghapus Record berdasarkan ID yang ada di dalam tampilkan
     * */
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("students", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
