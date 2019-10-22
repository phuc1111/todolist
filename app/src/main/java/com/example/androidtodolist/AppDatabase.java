package com.example.androidtodolist;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {todo.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract todoDao todoDao();
}
