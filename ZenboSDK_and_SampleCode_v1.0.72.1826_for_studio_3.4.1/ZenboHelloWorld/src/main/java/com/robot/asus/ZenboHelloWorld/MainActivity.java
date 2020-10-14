package com.robot.asus.ZenboHelloWorld;

import android.os.Bundle;
import  android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

public class MainActivity extends RobotActivity {

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

        }

        @Override
        public void onResult(JSONObject jsonObject) {

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void openNote(View v){
        Button btn_note;
        Button btn_scheduel;
        ImageView Mon;
        btn_note = findViewById(R.id.btn_note);
        btn_scheduel = findViewById(R.id.btn_schedule);
        Mon = findViewById(R.id.Mon);

        btn_note.setVisibility(View.INVISIBLE);
        btn_scheduel.setVisibility(View.INVISIBLE);
        Mon.setVisibility(View.VISIBLE);
    }



    public MainActivity() {
        super(robotCallback, robotListenCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //robotAPI.robot.speak("Hello world. I am Zenbo. Nice to meet you.");
        //robotAPI.robot.setExpression(RobotFace.HAPPY);
        //robotAPI.vision.cancelDetectFace();
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);
    }


}
