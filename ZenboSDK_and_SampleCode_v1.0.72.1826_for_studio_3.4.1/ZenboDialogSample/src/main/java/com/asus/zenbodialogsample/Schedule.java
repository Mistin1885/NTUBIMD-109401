package com.asus.zenbodialogsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1 ;
        if(w<0){
            w = 0;
        }

        ImageView image_week = findViewById(R.id.schedule_mon);

        if(weekDays[w] == "星期五"){
            image_week = (ImageView) findViewById(R.id.schedule_fri);
        }

        image_week.setVisibility(View.VISIBLE);
    }



    public void goBack(View v){
        finish();
    }
}
