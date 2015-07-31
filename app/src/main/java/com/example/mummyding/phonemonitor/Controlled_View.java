package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class Controlled_View extends Activity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    Switch call_back_sw,vibrator_sw,location_sw,ring_sw;
    public static boolean isCall_back = true,isVibrator = true,isLocation = true,isRing = true;
    Button disControlled_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_controlled__view);

        // 防止 Activity 重启～
        if(MainActivity.isControlled == false) finish();
        findView();
    }
    void findView(){
        call_back_sw = (Switch) findViewById(R.id.call_back_sw);
        vibrator_sw = (Switch) findViewById(R.id.vibration_sw);
        location_sw = (Switch) findViewById(R.id.location_sw);
        ring_sw = (Switch) findViewById(R.id.ring_sw);
        disControlled_btn = (Button) findViewById(R.id.disControlled_btn);

        call_back_sw.setOnCheckedChangeListener(this);
        vibrator_sw.setOnCheckedChangeListener(this);
        location_sw.setOnCheckedChangeListener(this);
        ring_sw.setOnCheckedChangeListener(this);
        disControlled_btn.setOnClickListener(this);

        call_back_sw.setChecked(isCall_back);
        vibrator_sw.setChecked(isVibrator);
        location_sw.setChecked(isLocation);
        ring_sw.setChecked(isRing);
    }

    void sendControlCommand(String phoneNum,String message){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Controlled_View.class), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNum, null, message, pi, null);

        Toast.makeText(Controlled_View.this, "断开请求已发出！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.location_sw:
                isLocation = location_sw.isChecked();
                break;
            case R.id.call_back_sw:
                Toast.makeText(this,"主控号码:"+Controlled_Mode_Setting.control_Num,Toast.LENGTH_SHORT).show();

                isCall_back = call_back_sw.isChecked();
                break;
            case R.id.vibration_sw:
                isVibrator = vibrator_sw.isChecked();
                break;
            case R.id.ring_sw:
                isRing = ring_sw.isChecked();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.disControlled_btn:
                sendControlCommand(Controlled_Mode_Setting.control_Num,COMMANDS.CONTROLLED_DISCONNECT);
                MainActivity.isControl = false;
                MainActivity.isControlled = false;
                Control_Mode_Setting.controlled_Num ="";
                Controlled_Mode_Setting.control_Num = "";


                //返回主界面
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }
}
