package com.nextclick.grocery.modal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tbl_Login")
public class Login implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "UserId")
    private String userid;

    @ColumnInfo(name = "Password")
    private String password;

    public Login(int id, String userid, String password) {
        this.id = id;
        this.userid = userid;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Login[] InsertData() {
        return new Login[]{
                new Login(1, "a", "a"),
                new Login(2, "seenu", "test@123"),
                new Login(3, "nextclick", "test@123"),
        };
    }
}


