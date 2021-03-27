package com.nextclick.grocery.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.nextclick.grocery.modal.Login;
import com.nextclick.grocery.modal.LoginDao;
import com.nextclick.grocery.modal.Productdetails;

@Database(entities = {Login.class, Productdetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoginDao loginDao();
}