package com.asus.zenbodialogsample;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;


import org.json.JSONObject;

public class ZenboDialogSample extends RobotActivity {
    public final static String TAG = "ZenboDialogSample";
    public final static String DOMAIN = "7C610C5D6ABD4BE59674F93A0B16BE11"; //9EF85697FF064D54B32FF06D21222BA2

    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenbo_dialog_sample);

        //mTextView = (TextView) findViewById(R.id.textview_info);
        robotAPI.robot.speak("Nice to meet you");
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


    public void gotoSchedule(View v){
        Intent it = new Intent();
        it.setClass(ZenboDialogSample.this, Schedule.class);
        startActivity(it);
    }

    public void gotoHouseWork(View v){
        Intent it = new Intent();
        it.setClass(ZenboDialogSample.this, HouseWork.class);
        startActivity(it);
    }

    public void gotoSleep(View v){
        Intent it = new Intent();
        it.setClass(ZenboDialogSample.this, Sleep.class);
        startActivity(it);
    }

    public void gotoPomodoro(View v){
        Intent it = new Intent();
        it.setClass(ZenboDialogSample.this, Pomodoro.class);
        startActivity(it);
    }

    public void gotoCheck_reward(View v){
        Intent it = new Intent();
        it.setClass(ZenboDialogSample.this, CheckReward.class);
        startActivity(it);
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


    public ZenboDialogSample() {
        super(robotCallback, robotListenCallback);
    }


}

