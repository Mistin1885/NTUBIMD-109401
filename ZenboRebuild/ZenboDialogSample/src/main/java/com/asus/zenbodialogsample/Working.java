package com.asus.zenbodialogsample;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Working extends AppCompatActivity {

    Chronometer ch;
    ImageView start;
    ImageView pause;
    UserOpenHelper helper;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time = dateFormat.format(new Date());
        TextView textView = findViewById(R.id.code);
        textView.setText(getIntent().getStringExtra("name"));
        int type = getIntent().getIntExtra("type", 0);
        /*
        if (type == 5) {
            start.setText("開始工作");
        }
        */
        helper = new UserOpenHelper(this);
        //获取计时器组件
        ch = (Chronometer) findViewById(R.id.test);
        //获取开始按钮
        start = (ImageView) findViewById(R.id.start);
        //暂停计时按钮
        pause = (ImageView) findViewById(R.id.pause);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置开始计时时间
                ch.setBase(SystemClock.elapsedRealtime());
                //启动计时器
                ch.start();
                pause.setEnabled(true);
                start.setEnabled(false);
            }
        });
        //暂停按钮监听器
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chronometerSeconds = getChronometerSeconds(ch);
                Toast.makeText(Working.this, chronometerSeconds, Toast.LENGTH_SHORT).show();
                save(chronometerSeconds);
                ch.stop();
                start.setEnabled(true);
                pause.setEnabled(false);
            }
        });
        //为Chronomter绑定事件监听器
        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //如果计时到现在超过了一小时秒
                if (SystemClock.elapsedRealtime() - ch.getBase() > 3600 * 1000) {
                    ch.stop();
                    start.setEnabled(true);
                    pause.setEnabled(false);
                }
            }
        });
    }

    public void save(String chronometerSeconds) {
        String name = "";
        final SQLiteDatabase db = helper.getWritableDatabase();
        switch (getIntent().getIntExtra("type", 0)) {
            case 1:
                name = "開始掃地";

                break;

            case 2:
                name = "開始拖地";

                break;
            case 3:
                name = "開始洗碗";

                break;
            case 4:
                name = "開始洗衣";

                break;
            case 5:
                name = "其他";

                break;
        }
        String sql = String.format("insert into res(_DATE,_HEAD,_MSG,_TYPE) values('%s','%s','%s',%d)", time, chronometerSeconds, name, getIntent().getIntExtra("type", 0));
        try {
            db.execSQL(sql);
//                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    Intent intent = new Intent(AddPage.this, BroadcastAlarm.class);
//                    PendingIntent sender = PendingIntent.getBroadcast(AddPage.this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//                    //if(interval==0)
//                    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, sender);
            Toast.makeText(Working.this, "儲存成功", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(Working.this, "儲存失敗", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param cmt Chronometer控件
     * @return 小时+分钟+秒数  的所有秒数
     */
    public static String getChronometerSeconds(Chronometer cmt) {
        int totalss = 0;
        String string = cmt.getText().toString();
        if (string.length() == 7) {

            String[] split = string.split(":");
            String string2 = split[0];
            int hour = Integer.parseInt(string2);
            int Hours = hour * 3600;
            String string3 = split[1];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[2]);
            totalss = Hours + Mins + SS;
            return String.valueOf(totalss);
        } else if (string.length() == 5) {

            String[] split = string.split(":");
            String string3 = split[0];
            int min = Integer.parseInt(string3);
            int Mins = min * 60;
            int SS = Integer.parseInt(split[1]);

            totalss = Mins + SS;
            return String.valueOf(totalss);
        }
        return String.valueOf(totalss);

    }
}
