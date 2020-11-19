package com.asus.zenbodialogsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.io.File;

public class CheckReward extends AppCompatActivity {

    TextView textView_cnt_reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reward);

        refreshReward();
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
    public void refreshReward(){
        textView_cnt_reward = (TextView)findViewById(R.id.TextView_cnt_reward);
        readReward();
        String str_cnt_reward = data.toString();

        if(str_cnt_reward != "") {
            textView_cnt_reward.setText(str_cnt_reward);
        }else{
            textView_cnt_reward.setText("0");
        }
    }

    public void btn_refresh(View v){
        refreshReward();
    }
    public void goBack(View v){
        finish();
    }
}
