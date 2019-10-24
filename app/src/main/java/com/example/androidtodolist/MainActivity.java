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
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    EditText edtTitle;
    EditText edtDate;
    Button btnAdd;
    Button btnDelete;
    LinearLayout main;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();


        getTodoFromDatabase();

        btnAdd = findViewById(R.id.bt_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTodo.class));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteCartItem(final int i) {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        final List <todo> todo =  db.todoDao().getAll();
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                db.todoDao().delete(todo.get(i));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getTodoFromDatabase();
            }
        }.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private void getTodoFromDatabase() {
        new AsyncTask<Void, Void, List<todo>>() {

            @Override
            protected List<todo> doInBackground(Void... voids) {
                return db.todoDao().getAll();
            }

            @Override
            protected void onPostExecute(final List<todo> todos) {
                super.onPostExecute(todos);
                main = findViewById(R.id.main_layout);
                for (int i = 0; i < todos.size(); i++) {
                    LinearLayout ln = new LinearLayout(MainActivity.this);
                    ln.setOrientation(LinearLayout.HORIZONTAL);
                    TextView title = new TextView(MainActivity.this);
                    TextView date = new TextView(MainActivity.this);
                    Button delete = new Button(MainActivity.this);
                    title.setText(todos.get(i).getTitle());
                    date.setText(todos.get(i).getDate());
                    delete.setId(todos.get(i).getId());
                    delete.setText("delete");
                    final int j = i;
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new AsyncTask<Void,Void,Void>(){
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    deleteCartItem(todos.get(j).getId());

                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);

                                }
                            }.execute();
                        }
                    });

                    ln.addView(title);
                    ln.addView(date);
                    ln.addView(delete);
                    main.addView(ln);

                }
            }
        }.execute();
    }


}
