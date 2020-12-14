package com.asus.zenbodialogsample;


import android.content.Context;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pomodoro extends AppCompatActivity {

    private Button btn_work;
    private Button btn_rest;

    private TextView showTime;

    UserOpenHelper helper;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time = dateFormat.format(new Date());
        helper = new UserOpenHelper(this);

        showTime = (TextView) findViewById(R.id.showTime); //倒數顯示器

        //倒數計時秒數
        final int intWorkTime = 25;
        final int intRestTime = 5;

        //工作倒數計時
        final CountDownTimer countDownTimer_work = new CountDownTimer(intWorkTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            @Override
            public void onFinish() {
                showTime.setText("0");
                btn_rest.setEnabled(true); //啟用休息按鈕
                save(); //完成次數存入database
            }
        };

        //工作按鈕
        btn_work = (Button)findViewById(R.id.btn_work);
        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_rest.setEnabled(false); //停用休息按鈕

                //執行時間
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.add(Calendar.SECOND, intWorkTime);

                countDownTimer_work.start(); //開始工作倒數計時

                Toast.makeText(Pomodoro.this, "開始計時", Toast.LENGTH_LONG).show();
            }
        });

        //休息倒數計時
        final CountDownTimer countDownTimer_rest = new CountDownTimer(intRestTime*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            @Override
            public void onFinish() {
                showTime.setText("0");
                btn_work.setEnabled(true); //啟用工作按鈕
                Toast.makeText(Pomodoro.this, "休息時間結束", Toast.LENGTH_SHORT).show();
            }
        };

        //休息按鈕
        btn_rest = (Button)findViewById(R.id.btn_rest);
        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_work.setEnabled(false); //停用工作按鈕
                //執行時間
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.add(Calendar.SECOND, intRestTime);

                countDownTimer_rest.start(); //開始休息倒數計時

                Toast.makeText(Pomodoro.this, "開始計時", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void save() {
        final SQLiteDatabase db = helper.getWritableDatabase();
        String sql = String.format("insert into reward(_DATE,_HEAD,_TYPE) values('%s','%s',%d)", time, "蕃茄鐘", getIntent().getIntExtra("type", 0));
        try {
            db.execSQL(sql);

            Toast.makeText(Pomodoro.this, "獲得一顆蕃茄", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(Pomodoro.this, "執行錯誤", Toast.LENGTH_SHORT).show();
        }
    }



    public void goBack(View v){
        finish();
    }
}
