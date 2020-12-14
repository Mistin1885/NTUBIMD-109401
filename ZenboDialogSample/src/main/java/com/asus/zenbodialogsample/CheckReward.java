package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import android.database.sqlite.SQLiteDatabase;


public class CheckReward extends AppCompatActivity {

    UserOpenHelper helper;
    SQLiteDatabase db;

    TextView tv_cnt_sleep;
    TextView tv_cnt_work;
    TextView tv_cnt_pomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reward);

        helper = new UserOpenHelper(this);
        db = helper.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tv_cnt_sleep = (TextView)findViewById(R.id.cnt_sleep);
        tv_cnt_work = (TextView)findViewById(R.id.cnt_work);
        tv_cnt_pomodoro = (TextView)findViewById(R.id.cnt_pomodoro);

        String sql_sleep = String.format("select count(*) from sleep ;");
        String sql_work = String.format("select count(*) from res ;");
        String sql_reward = String.format("select count(*) from reward ;");

        String s_cnt = "null";
        //睡眠次數
        Cursor cursor = db.rawQuery(sql_sleep, null);
        while (cursor.moveToNext()) {
             s_cnt = String.valueOf(cursor.getInt(0));
        }
        String show = "睡眠次數: " + s_cnt;
        tv_cnt_sleep.setText(show);

        //做家事次數
        cursor = db.rawQuery(sql_work, null);
        while (cursor.moveToNext()) {
            s_cnt = String.valueOf(cursor.getInt(0));
        }
        show = "做家事次數: " + s_cnt;
        tv_cnt_work.setText(show);

        //蕃茄鐘次數
        cursor = db.rawQuery(sql_reward, null);
        while (cursor.moveToNext()) {
            s_cnt = String.valueOf(cursor.getInt(0));
        }
        show = "蕃茄數量: " + s_cnt;
        tv_cnt_pomodoro.setText(show);
    }
}
