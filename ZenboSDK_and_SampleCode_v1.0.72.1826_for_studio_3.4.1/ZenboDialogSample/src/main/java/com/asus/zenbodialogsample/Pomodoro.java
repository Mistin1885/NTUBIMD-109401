package com.asus.zenbodialogsample;


import android.content.Context;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Pomodoro extends AppCompatActivity {

    private Button btn_work;
    private Button btn_rest;

    private TextView showTime;

    int cnt_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        showTime = (TextView) findViewById(R.id.showTime); //倒數顯示器

        //倒數計時秒數
        final int intWorkTime = 20;
        final int intRestTime = 10;

        //readReward();
        //String c_reward = data.toString();

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
                cnt_reward += 1; //完成次數+1

                String s = Integer.toString(cnt_reward);
                writeReward(s); //完成次數存入txt檔
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

    //完成次數(獎勵)寫入HabiZenbo_Reward.txt
    FileOutputStream out = null;
    public void writeReward(String text){
        try{
            out = openFileOutput("HabiZenbo_Reward.txt", Context.MODE_PRIVATE);
            out.write(text.getBytes());
            out.flush();
        }catch (Exception e){
            ;
        }finally {
            try{
                out.close();
            }catch (Exception e){
                ;
            }
        }
    }

    public void goBack(View v){
        finish();
    }
}
