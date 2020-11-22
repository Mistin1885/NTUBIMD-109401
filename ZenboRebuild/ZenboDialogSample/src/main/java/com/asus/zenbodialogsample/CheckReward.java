package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.io.File;

public class CheckReward extends AppCompatActivity {

    TextView textView_cnt_reward; //顯示獎勵數量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reward);

        refreshReward();  //刷新數值
    }

    //讀取完成次數(獎勵)
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

    //刷新獎勵次數
    public void refreshReward(){
        textView_cnt_reward = (TextView)findViewById(R.id.TextView_cnt_reward);
        readReward();
        String str_cnt_reward = data.toString();
        String cnt_reward = String.valueOf(str_cnt_reward.length());

        if(str_cnt_reward != "") {
            textView_cnt_reward.setText(cnt_reward);
        }else{
            textView_cnt_reward.setText("0");
        }
    }

    //刷新按鈕
    public void btn_refresh(View v){
        refreshReward();
    }

}
