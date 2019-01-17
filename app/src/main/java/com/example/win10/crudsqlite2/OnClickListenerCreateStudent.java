package com.example.win10.crudsqlite2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.win10.crudsqlite2.Function.F_Students;
import com.example.win10.crudsqlite2.TableDB.Students;

class OnClickListenerCreateStudent implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        final Context context = v.getRootView().getContext();
        /**
         *  Akan membawa layout dari activity_input
         * */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View formElementsView = inflater.inflate(R.layout.activity_input, null, false);

        /**
         *  Membuat variable dari form activity_input
         * */
        final EditText editTextStudentFirstname =  formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail =  formElementsView.findViewById(R.id.editTextStudentEmail);

        /**
         *  Akan menampilkan Form Create Student dari form Activity_main tidak pindah halaman
         * */
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create Student")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                /**
                                 *  Menjadikan form yang kita isi menjadi string
                                 * */
                                String studentFirstname = editTextStudentFirstname.getText().toString();
                                String studentEmail = editTextStudentEmail.getText().toString();

                                /**
                                 *  Memasukan value yang kita isi ke dalam field dalam table
                                 **/
                                Students students = new Students();
                                students.firstname= studentFirstname;
                                students.email= studentEmail;

                                /**
                                 *  Nilai yang di masukan dari form akan langsung ke dalam table students
                                 * */
                                boolean createSuccessful = new F_Students(context).create(students);

                                if(createSuccessful){
                                    Toast.makeText(context, "Student information was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save student information.", Toast.LENGTH_SHORT).show();
                                }

                                /**
                                 * Memanggil method di dalam Main Activity yang isinya  =
                                 * Record dalam table akan di jadikan integer ( 1 Record =   1 )
                                 * */
                                MainActivity    mainActivity    =   new MainActivity();
                                mainActivity.countRecords();

                                /**
                                 *  Menjadikan Banyak Record menampilkan valuenya ( Menampilkan per Record )
                                 * */
                                ((MainActivity) context).readRecords();

                                dialog.cancel();



                            }

                        }).show();



    }
}
