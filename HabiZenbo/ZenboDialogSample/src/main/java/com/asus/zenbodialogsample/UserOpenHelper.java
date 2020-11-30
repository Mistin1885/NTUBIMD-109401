package com.asus.zenbodialogsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserOpenHelper extends SQLiteOpenHelper {
    public UserOpenHelper(@Nullable Context context) {
        super(context, "HabiZenbo.db", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints 开启外键约束
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table res ( _DATE varchar(20),_HEAD varchar(20),_MSG varchar(20),_TYPE int);"); //工作記錄資料表
        db.execSQL("create table sleep ( _DATE varchar(20),_HEAD varchar(20),_MSG varchar(20),_TYPE int);"); //睡眠記錄資料表
        db.execSQL("create table reward (_DATE varchar(20),_HEAD varchar(20),_TYPE int);"); //蕃茄鐘次數記錄

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists res");
        db.execSQL("drop table if exists sleep");
        db.execSQL("drop table if exists reward");
        onCreate(db);
    }
}
