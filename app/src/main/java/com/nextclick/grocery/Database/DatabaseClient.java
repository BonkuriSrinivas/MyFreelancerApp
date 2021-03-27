package com.nextclick.grocery.Database;

import androidx.room.Room;
import android.content.Context;

public class DatabaseClient {

    Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //UsersData is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "Ecommerce").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {

        return appDatabase;
    }
}