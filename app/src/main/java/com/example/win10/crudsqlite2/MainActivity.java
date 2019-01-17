package com.example.win10.crudsqlite2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win10.crudsqlite2.Function.F_Students;
import com.example.win10.crudsqlite2.TableDB.Students;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreateStudent =  findViewById(R.id.buttonCreateStudent);
        buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent());

        readRecords();

        countRecords();
    }

    @SuppressLint("SetTextI18n")
    public void countRecords() {
        /**
         * Value yang ada di dalam table akan di jadikan integer(angka) / banyaknya row(baris) dalam table
         * */
        int recordCount = new F_Students(this).count();
        TextView textViewRecordCount =  findViewById(R.id.textViewRecordCount);
        /**
         * Angka atau value tadi di tampilkan dengan tambahan String (" ")
         * */
        textViewRecordCount.setText(recordCount + " records found.");


    }


    /**
     *  Membaca field dan menampilkan isinya , bisa juga kita modifikasi
     * */
    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        /**
         *  Akan menghapus semua Tampilan kecuali tampilan record
         * */
        /*linearLayoutRecords.removeAllViews();*/

        List<Students> students = new F_Students(this).read();

        if (students.size() > 0) {

            for (Students obj : students) {

                /**
                 *  Satu Record akan Membawa atau berisi field
                 * */
                int id = obj.id;
                String studentFirstname = obj.firstname;
                String studentEmail = obj.email;

                /**
                 * Modifikasi output yang ada per Row(baris)
                 * */
                String textViewContents = "Nama =" + studentFirstname;
                String  textViewContents2   = "Email =" + studentEmail;

                /**
                 *  Menampilan Value dalam table menjadi text
                 * */
                TextView textViewStudentItem= new TextView(this);
                TextView textViewStudentItem2   =   new TextView(this);
                /**
                 *  Mengatur tampilan padding
                 * */
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);

                textViewStudentItem2.setPadding(0, 10, 0, 10);
                textViewStudentItem2.setText(textViewContents2);
                /**
                 *  Tampilan yang di tampilkan akan berdasarkan ID dalam table
                 * */
                textViewStudentItem.setTag(Integer.toString(id));
                /**
                 *  Pilihan apa bila ingin di edit dan juga di hapus Recordnya ada disini
                 * */
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());


                linearLayoutRecords.addView(textViewStudentItem);
                linearLayoutRecords.addView(textViewStudentItem2);
            }

        }

        else {

            /** jika tidak ada maka akan menampilkan " No Record yet " dalam tampilan */
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }
}
