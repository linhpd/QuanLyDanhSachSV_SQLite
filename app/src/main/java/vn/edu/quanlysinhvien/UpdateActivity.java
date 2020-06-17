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

public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "my_database.sqlite";
    Button luu, huy;
    EditText edtname, edtemail, edtbirthday;
    int mssv = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        addControls();
        addEvents();
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        mssv = intent.getIntExtra("mssv",-1);
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sinh_vien WHERE MSSV = ?",new String[]{mssv + ""});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String birthday = cursor.getString(2);
        String email = cursor.getString(3);

        edtname.setText(name);
        edtemail.setText(email);
        edtbirthday.setText(birthday);

    }

    private void addEvents(){
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void update(){
        String name = edtname.getText().toString();
        String birthday = edtbirthday.getText().toString();
        String email = edtemail.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("birthday", birthday);
        contentValues.put("email", email);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.update("sinh_vien",contentValues,"MSSV = ?",new String[]{mssv + ""});
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
    }
}
