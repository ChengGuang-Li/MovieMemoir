package com.example.moviememoir.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class},version = 1, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDao MovieDao();

    public static volatile MovieDB INSTANCE;

    public static synchronized MovieDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDB.class, "MovieDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;

    }
}
