package com.asus.zenbodialogsample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AlarmActivity extends AppCompatActivity {

    private Button btn_work;
    private Button btn_rest;
    //private Button btn_cancel;

    private TextView showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        showTime = (TextView) findViewById(R.id.showTime);

        final int intWorkTime = 20;
        final int intRestTime = 10;

        //工作倒數計時
        final CountDownTimer countDownTimer_work = new CountDownTimer(intWorkTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            @Override
            public void onFinish() {
                showTime.setText("0");
                btn_rest.setEnabled(true);
            }
        };

        //工作鬧鈴
        final AlarmManager workManager = (AlarmManager) getSystemService(ALARM_SERVICE); //鬧鈴註冊
        btn_work = (Button)findViewById(R.id.btn_work);
        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);

                btn_rest.setEnabled(false);
                //執行時間
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.add(Calendar.SECOND, intWorkTime);

                //鬧鈴啟動
                workManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

                countDownTimer_work.start();

                Toast.makeText(AlarmActivity.this, "啟用工作計時", Toast.LENGTH_LONG).show();
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
                btn_work.setEnabled(true);
            }
        };

        //休息鬧鈴
        final AlarmManager restManager = (AlarmManager)getSystemService(ALARM_SERVICE); //鬧鈴註冊
        btn_rest = (Button)findViewById(R.id.btn_rest);
        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);

                btn_work.setEnabled(false);
                //執行時間
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.add(Calendar.SECOND, intRestTime);

                //鬧鈴啟動
                restManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

                countDownTimer_rest.start();

                Toast.makeText(AlarmActivity.this, "啟用休息鬧鈴", Toast.LENGTH_LONG).show();
            }
        });

        //取消按鈕
        //btn_cancel = (Button)findViewById(R.id.btn_cancel);
        //btn_cancel.setOnClickListener(new View.OnClickListener() {

                //Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                //PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);

                //取消鬧鈴
                //AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                //am.cancel(sender);

        //});
    }

    public void goBack(View v){
        finish();
    }
}


