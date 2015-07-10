package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class Control_View extends Activity implements View.OnClickListener{

    Button location_btn,callback_btn,vibration_btn,ring_btn,disControl_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control__view);

        findView();
    }
    void findView(){
        location_btn = (Button) findViewById(R.id.location_btn);
        callback_btn = (Button) findViewById(R.id.call_back_btn);
        vibration_btn = (Button) findViewById(R.id.vibration_btn);
        ring_btn = (Button) findViewById(R.id.ring_btn);
        disControl_btn = (Button) findViewById(R.id.disControl_btn);

        location_btn.setOnClickListener(this);
        callback_btn.setOnClickListener(this);
        vibration_btn.setOnClickListener(this);
        ring_btn.setOnClickListener(this);
        disControl_btn.setOnClickListener(this);
    }

    void sendControlCommand(String phoneNum,String message){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Control_View.class), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNum, null, message, pi, null);

        Toast.makeText(Control_View.this, "控制请求已发出！", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_btn:
                sendControlCommand(Control_Mode_Setting.controlled_Num,COMMANDS.CONTROL_LOCATION);
                break;
            case R.id.call_back_btn:
                sendControlCommand(Control_Mode_Setting.controlled_Num,COMMANDS.CONTROL_CALLBACK);
                break;
            case R.id.vibration_btn:
                sendControlCommand(Control_Mode_Setting.controlled_Num,COMMANDS.CONTROL_VIBRATION);
                break;
            case R.id.ring_btn:
                sendControlCommand(Control_Mode_Setting.controlled_Num,COMMANDS.CONTROL_RING);
                break;
            case R.id.disControl_btn:
                sendControlCommand(Control_Mode_Setting.controlled_Num,COMMANDS.CONTROL_DISCONNECT);
                MainActivity.isControl = false;
                MainActivity.isControl = false;
                Control_Mode_Setting.controlled_Num ="";
                Controlled_Mode_Setting.control_Num = "";
                break;
        }
    }
}
