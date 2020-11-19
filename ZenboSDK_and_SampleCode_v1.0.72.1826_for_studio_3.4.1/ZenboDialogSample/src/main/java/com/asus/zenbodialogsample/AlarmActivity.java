package com.asus.zenbodialogsample;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AlarmActivity extends AppCompatActivity {

    private Button btn_work;
    private Button btn_rest;
    //private Button btn_cancel;

    private TextView showTime;

    int cnt_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        showTime = (TextView) findViewById(R.id.showTime);

        final int intWorkTime = 20;
        final int intRestTime = 10;


        readReward();

        String c_reward = data.toString();

        /*
        if(c_reward==""){
            writeReward("0");
            readReward();
        }

        c_reward = data.toString();



        if(isNumeric(c_reward)==true) {
            cnt_reward = Integer.valueOf(c_reward);
        }
        */




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
                cnt_reward += 1;
                /*
                updateReward();
                cnt_rewardUpdate();
                */
                String s = Integer.toString(cnt_reward);
                writeReward(s);
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

        /*
        取消按鈕
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

                Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);

                取消鬧鈴
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);

        }); */

        /* 資料庫
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);

        String createTable="CREATE TABLE IF NOT EXISTS " + tb_name + "(mainID INT, " + "reward INT, " + "exchange INT)";
        db.execSQL(createTable);

        Cursor c = db.rawQuery("SELECT * FROM " + tb_name, null);

        if(c.getCount() == 0){
            addData(0, 0, 0);
            c = db.rawQuery("SELECT * FROM " + tb_name, null);
        }

        cnt_reward = c.getInt(1); //rewardValue

        db.close();
        */


    }

    /*
    private void updateReward(){
        ContentValues v = new ContentValues();
        v.put("reward", cnt_reward);

        db.update(tb_name, v, "mainID=?", new String[]{ "0" }); //將mainID = 0紀錄的reward值更新
    }

    private void cnt_rewardUpdate(){
        Cursor c = db.rawQuery("SELECT * FROM " + tb_name, null);
        cnt_reward = c.getInt(1);
    }

    private void addData(int mainID, int reward, int exchange){
        ContentValues cv = new ContentValues(3);
        cv.put("mainID", mainID);
        cv.put("reward", reward);
        cv.put("exchange", exchange);

        db.insert(tb_name, null, cv);
    }

    static final String db_name="HabiZenboDB";
    static final String tb_name="reward";
    SQLiteDatabase db;
    */

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

    FileInputStream in = null;
    StringBuffer data = new StringBuffer();
    public void readReward(){
        try{
            in = openFileInput("HabiZenbo_Reward.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line;
            while((line = reader.readLine()) != null){
                data.append(line);
            }
        }catch (Exception e){
            ;
        }finally {
            try{
                in.close();
                File dir = getFilesDir();
                File file = new File(dir, "HabiZenbo_Reward.txt");
                file.delete();
            }catch (Exception e){
                ;
            }
        }
    }

    protected void onStop(){
        super.onStop();
        String s = Integer.toString(cnt_reward);
        writeReward(s);
    }

    protected void onPause(){
        super.onPause();
        String s = Integer.toString(cnt_reward);
        writeReward(s);
    }


    public void goBack(View v){
        finish();
    }

}


