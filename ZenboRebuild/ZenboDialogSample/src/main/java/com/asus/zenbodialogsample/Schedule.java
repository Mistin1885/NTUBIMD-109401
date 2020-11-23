package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
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

        ImageView image_week = (ImageView) findViewById(R.id.schedule_mon);
        //TextView text_week = (TextView) findViewById(R.id.schedule_sat);

        if (weekDays[w]=="星期一"){
            image_week = (ImageView) findViewById(R.id.schedule_mon);
        }else if (weekDays[w]=="星期二"){
            image_week = (ImageView) findViewById(R.id.schedule_tue);
        }else if (weekDays[w]=="星期三"){
            image_week = (ImageView) findViewById(R.id.schedule_wen);
        }else if (weekDays[w]=="星期四"){
            image_week = (ImageView) findViewById(R.id.schedule_thu);
        }else if (weekDays[w]=="星期五"){
            image_week = (ImageView) findViewById(R.id.schedule_fri);
        }

        image_week.setVisibility(View.VISIBLE);

        /*
        switch (weekDays[w]){
            case "星期一":
                image_week = (ImageView) findViewById(R.id.schedule_mon);
                break;
            case "星期二":
                image_week = (ImageView) findViewById(R.id.schedule_tue);
                break;
            case "星期三":
                image_week = (ImageView) findViewById(R.id.schedule_wen);
                break;
            case "星期四":
                image_week = (ImageView) findViewById(R.id.schedule_thu);
                break;
            case "星期五":
                image_week = (ImageView) findViewById(R.id.schedule_fri);
                break;
            case "星期六":
                text_week = (TextView) findViewById(R.id.schedule_sat);
                break;
            case "星期日":
                text_week = (TextView) findViewById(R.id.schedule_sun);
                break;
        }
        */
    }

    //返回按鈕
    public void goBack(View v){
        finish();
    }
}
