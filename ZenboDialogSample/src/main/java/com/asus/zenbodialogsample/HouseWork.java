package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HouseWork extends AppCompatActivity {

    List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_work);
        TextView tv_saodi = findViewById(R.id.tv_saodi);
        TextView tv_qita = findViewById(R.id.tv_qita);
        TextView tv_look = findViewById(R.id.tv_look);
        TextView tv_tuodi = findViewById(R.id.tv_tuodi);
        TextView tv_xiwan = findViewById(R.id.tv_xiwan);
        TextView tv_xiyi = findViewById(R.id.tv_xiyi);


        tv_saodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, Working.class);
                intent.putExtra("name","掃地");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        tv_qita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, Working.class);
                intent.putExtra("name","其他");
                intent.putExtra("type",5);

                startActivity(intent);
            }
        });
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, WorkRecord.class);
                startActivity(intent);
            }
        });
        tv_tuodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, Working.class);
                intent.putExtra("name","拖地");
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        tv_xiwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, Working.class);
                intent.putExtra("name","洗碗");
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });
        tv_xiyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseWork.this, Working.class);
                intent.putExtra("name","洗衣");
                intent.putExtra("type",4);
                startActivity(intent);
            }
        });
    }
}
