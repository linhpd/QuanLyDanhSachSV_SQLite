package vn.edu.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {
    final String DATABASE_NAME = "my_database.sqlite";
    Button luu, huy;
    EditText edtname, edtemail, edtbirthday, edtmssv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        addControls();
        addEvents();
    }
    private void addEvents(){
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void insert(){

        String name = edtname.getText().toString();
        String birthday = edtbirthday.getText().toString();
        String email = edtemail.getText().toString();
        String mssv = edtmssv.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("birthday", birthday);
        contentValues.put("email", email);
        contentValues.put("MSSV", mssv);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.insert("sinh_vien",null, contentValues);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void addControls() {
        luu = findViewById(R.id.luu);
        huy = findViewById(R.id.quay_lai);
        edtname = findViewById(R.id.name);
        edtbirthday = findViewById(R.id.birthday);
        edtemail  = findViewById(R.id.email);
        edtmssv = findViewById(R.id.mssv);
    }
}
