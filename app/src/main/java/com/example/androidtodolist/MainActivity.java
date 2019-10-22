package com.example.androidtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    EditText edtTitle;
    EditText edtDate;
    Button btnAdd;
    LinearLayout main;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                todo todo = new todo("aaa", "vvv");
                db.todoDao().insert(todo);
                return null;
            }
        }.execute();
        getTodoFromDatabase();

        //main = findViewById(R.id.main_layout);
        //final List<todo> listTodo = db.todoDao().getAll();
        btnAdd = findViewById(R.id.bt_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTodo.class));
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void getTodoFromDatabase() {
        new AsyncTask<Void, Void, List<todo>>() {

            @Override
            protected List<todo> doInBackground(Void... voids) {
                return db.todoDao().getAll();
            }

            @Override
            protected void onPostExecute(List<todo> todos) {
                super.onPostExecute(todos);
                main = findViewById(R.id.main_layout);
                for (int i = 0; i < todos.size(); i++) {
                    //Log.i("tag", "value"+todos.get(i).getTitle());

                }
            }
        }.execute();
    }

//    private void setInfor(String title, String date) {
//        main = findViewById(R.id.main_layout);
//        TextView tv_title = new TextView(MainActivity.this);
//        tv_title.setText(title);
//        TextView tv_date = new TextView(MainActivity.this);
//        tv_date.setText(date);
//        Button bt_
//    }
}
