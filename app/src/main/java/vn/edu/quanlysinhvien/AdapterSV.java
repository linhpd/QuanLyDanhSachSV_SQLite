package vn.edu.quanlysinhvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSV extends BaseAdapter {
    final String DATABASE_NAME = "my_database.sqlite";
    Activity context;
    ArrayList<Sinhvien>sinhviens;

    public AdapterSV(Activity context, ArrayList<Sinhvien> sinhviens) {
        this.context = context;
        this.sinhviens = sinhviens;
    }

    @Override
    public int getCount() {
        return sinhviens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview, null);
        TextView name = row.findViewById(R.id.name);
        TextView mssv = row.findViewById(R.id.mssv);
        TextView birthday = row.findViewById(R.id.birthday);
        TextView email = row.findViewById(R.id.email);
        Button xoa = row.findViewById(R.id.xoa);
        Button sua = row.findViewById(R.id.sua);

        final Sinhvien sinhvien = sinhviens.get(position);
        name.setText(sinhvien.name);
        mssv.setText(sinhvien.MSSV + "");
        birthday.setText(sinhvien.birthday);
        email.setText(sinhvien.email);

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("mssv", sinhvien.MSSV);
                context.startActivity(intent);
            }
        });

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xac nhan xoa");
                builder.setMessage("Ban co chac chan ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(sinhvien.MSSV);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return  row;
    }

    private void delete(int mssv) {
        SQLiteDatabase database = Database.initDatabase(context, DATABASE_NAME);
        database.delete("sinh_vien", "MSSV = ?", new String[]{mssv + ""});
        sinhviens.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM sinh_vien", null);
        while (cursor.moveToNext()){
            int mssv1 = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String email = cursor.getString(3);

            sinhviens.add(new Sinhvien(mssv1,name,birthday,email));
        }
        notifyDataSetChanged();
    }
}
