package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SleepTimeRecord extends AppCompatActivity {

    Button add;
    private ArrayList<Item> data = new ArrayList<>();
    UserOpenHelper helper;
    ListView listView;
    SQLiteDatabase db;
    SleepItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time_record);
        data.clear();
        listView = (ListView) findViewById(R.id.sleepTime_list);
        helper = new UserOpenHelper(this);
        db = helper.getWritableDatabase();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = (String) ((TextView) view.findViewById(R.id.sleep_list_item_time)).getText();
                String del = String.format("delete from sleep where _DATE='%s';", data.get(i).getDate());
                try {
                    db.execSQL(del);
                    Toast.makeText(SleepTimeRecord.this, "刪除成功", Toast.LENGTH_SHORT).show();
                    data.remove(i);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(SleepTimeRecord.this, "刪除失敗", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        //List加载
        String sql = String.format("select _HEAD,_MSG,_DATE,_TYPE from sleep ;");
        Cursor cursor = db.rawQuery(sql, null);
        adapter = new SleepItemAdapter(this, R.layout.sleep_item_list, data);
        while (cursor.moveToNext()) {
            Item a = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            data.add(a);
        }
        listView.setAdapter(adapter);
    }
}
