package vn.edu.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "my_database.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<Sinhvien>sinhviens;
    AdapterSV adapterSV;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sinh_vien",null);
        cursor.moveToFirst();
        
        addControls();
        readData();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnAdd = findViewById(R.id.add);
        listView = findViewById(R.id.list_view);
        sinhviens = new ArrayList<>();
        adapterSV = new AdapterSV(this, sinhviens);
        listView.setAdapter(adapterSV);
    }

    private void readData(){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sinh_vien",null);
        sinhviens.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int mssv =cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String email = cursor.getString(3);
            sinhviens.add(new Sinhvien(mssv,name,birthday,email));
        }
        adapterSV.notifyDataSetChanged();
    }
}
