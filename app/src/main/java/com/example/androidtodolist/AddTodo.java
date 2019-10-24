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
import android.widget.Toast;

public class AddTodo extends AppCompatActivity {
    Button bt_add;
    EditText ed_title;
    EditText ed_date;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        bt_add = findViewById(R.id.bt_add);
        ed_date = findViewById(R.id.ed_date);
        ed_title = findViewById(R.id.ed_title);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Log.i("tag", "ok");
                //finish();
                changeScreen();
            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    private void saveData(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        final String title = ed_title.getText().toString();
        final String date = ed_date.getText().toString();
//        if (title.isEmpty() || date.isEmpty()){
//            Toast.makeText(this, "Title or date must not null", Toast.LENGTH_SHORT).show();
//        }
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                todo newtodo = new todo(title, date);
                db.todoDao().insert(newtodo);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddTodo.this, "New todo added", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
    private  void changeScreen(){
        Intent intent = new Intent(AddTodo.this, MainActivity.class);
        startActivity(intent);
    }
}
