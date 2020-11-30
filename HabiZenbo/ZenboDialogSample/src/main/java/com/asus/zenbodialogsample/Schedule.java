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

        ImageView iv_schedule = findViewById(R.id.show_schedule);

        if(weekDays[w] == "星期二"){
            iv_schedule.setImageResource(R.drawable.tue);
        }else if(weekDays[w] == "星期三"){
            iv_schedule.setImageResource(R.drawable.wen);
        }else if(weekDays[w] == "星期四"){
            iv_schedule.setImageResource(R.drawable.thu);
        }else if(weekDays[w] == "星期五"){
            iv_schedule.setImageResource(R.drawable.fri);
        }

    }

    //返回按鈕
    public void goBack(View v){
        finish();
    }
}
