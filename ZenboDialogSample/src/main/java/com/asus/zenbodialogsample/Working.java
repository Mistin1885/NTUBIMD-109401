package com.asus.zenbodialogsample;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.asus.robotframework.API.WheelLights;
import com.robot.asus.robotactivity.RobotActivity;


import org.json.JSONObject;

public class Working extends RobotActivity {
    public final static String TAG = "ZenboDialogSample";
    public final static String DOMAIN = "7C610C5D6ABD4BE59674F93A0B16BE11"; //9EF85697FF064D54B32FF06D21222BA2

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
                //輪燈
                robotAPI.wheelLights.setColor(WheelLights.Lights.SYNC_BOTH, 0xff, 0x0000ff00);
                robotAPI.wheelLights.setBrightness(WheelLights.Lights.SYNC_BOTH, 0xff, 10);
                robotAPI.wheelLights.startBreathing(WheelLights.Lights.SYNC_BOTH, 0xff, 20, 10, 0);
                //跟隨
                robotAPI.utility.followUser();
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
                robotAPI.wheelLights.turnOff(WheelLights.Lights.SYNC_BOTH, 0xff);
                robotAPI.motion.stopMoving();
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


    @Override
    protected void onResume() {
        super.onResume();

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "lanuchHelloWolrd_Plan");

        // listen user utterance
        //robotAPI.robot.speakAndListen("\"Which city do you like?", new SpeakConfig().timeout(20));


        robotAPI.robot.setExpression(RobotFace.HAPPY);

        // close faical
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // show hint
        //mTextView.setText(getResources().getString(R.string.dialog_example));

    }


    @Override
    protected void onPause() {
        super.onPause();

        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static RobotCallback robotCallback = new RobotCallback() {
        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);
        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
        }

        @Override
        public void initComplete() {
            super.initComplete();

        }


    };


    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {
            String text;
            text = "onEventUserUtterance: " + jsonObject.toString();
            Log.d(TAG, text);
        }

        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);

            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            /*
            if(sIntentionID.equals("helloWorld")) {
                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "myCity1", null);
                Log.d(TAG, "Result City = " + sSluResultCity);

                if(sSluResultCity!= null) {
                    //mTextView.setText("You are now at " + sSluResultCity);
                }
            }
            */

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };

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

    public Working() {
        super(robotCallback, robotListenCallback);
    }


}

