package com.example.win10.crudsqlite2;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.win10.crudsqlite2.Function.F_Students;
import com.example.win10.crudsqlite2.TableDB.Students;

class OnLongClickListenerStudentRecord implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(View v) {
        context = v.getContext();
        id = v.getTag().toString();

        /**
         *  Pilihan / Tampilan yang akan keluar saat diclick
         * */
        final CharSequence[] items = { "Edit", "Delete" };

        /**
         *  Judul tampilan saat di click
         * */
        new AlertDialog.Builder(context).setTitle("Student Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        /**
                         *  Akan membaca Record atau baris yang kita pilih berdasarkan id
                         * */
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }

                        else if (item == 1) {

                            boolean deleteSuccessful = new F_Students(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }

                        dialog.dismiss();

                    }
                }).show();


        return false;
    }

    public void editRecord(final int studentId) {


        /**
         *  Di Function ReadSingleRecord , setelah kita pilih record berdasarkan id maka akan mengambil
         *  Semua value dalam 1 Record(ID , FIRSTNAME , DAN EMAIL )
         * */
        final F_Students tableControllerStudent = new F_Students(context);
        Students objectStudent = tableControllerStudent.readSingleRecord(studentId);

        /**
         * Layout yang akan di tampilkan saat mengklik pilihan edit
         * */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.activity_input, null, false);

        /**
         *  Mengambil tampilan insert yang akan kita isi dengan value yang sudah ada atau record yang mau kita ubah
         * */
        final EditText editTextStudentFirstname = (EditText) formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail = (EditText) formElementsView.findViewById(R.id.editTextStudentEmail);

        /**
         *  Tampilan akan memiliki value yang kita mau edit
         * */
        editTextStudentFirstname.setText(objectStudent.firstname);
        editTextStudentEmail.setText(objectStudent.email);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                /**
                                 *  Menjadikan value sebelumnya atau yang mau kita ubah menjadi sama seperti insert data
                                 *  Kedalam table database
                                 * */
                                Students objectStudent = new Students();
                                objectStudent.id = studentId;
                                objectStudent.firstname = editTextStudentFirstname.getText().toString();
                                objectStudent.email = editTextStudentEmail.getText().toString();

                                boolean updateSuccessful = tableControllerStudent.update(objectStudent);

                                /**
                                 *  Jika berhasil atau gagal akan menampilkan Text seperti dibawah
                                 * */
                                if(updateSuccessful){
                                    Toast.makeText(context, "Student record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update student record.", Toast.LENGTH_SHORT).show();
                                }

                                /**
                                 * Akan merefresh isi atau tampilan yang kita harus atau kita edit
                                 * */
                                ((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();


                                dialog.cancel();
                            }

                        }).show();



    }
}
