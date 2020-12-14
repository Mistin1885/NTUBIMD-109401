package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Sleep extends AppCompatActivity {

    List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        TextView tv_goSleep = findViewById(R.id.tv_saodi);

        TextView tv_sleep_look = findViewById(R.id.sleep_look);

        tv_goSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sleep.this, SleepTimer.class);
                intent.putExtra("name", "開始睡覺");
                startActivity(intent);
            }
        });

        tv_sleep_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sleep.this, SleepTimeRecord.class);
                startActivity(intent);
            }
        });

    }
}
