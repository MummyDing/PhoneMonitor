package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Control_Mode_Setting extends Activity implements View.OnClickListener{

    Button control_Command_btn;
    EditText controlled_Num_et;


    public static String controlled_Num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_control__mode__setting);
        if(MainActivity.isControl||MainActivity.isControlled){
            Intent newIntent = new Intent(Control_Mode_Setting.this,MainActivity.class);
            // 在Service中启动Activity，必须设置如下标志
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            return;
        }
        findView();
    }
    void findView(){
        control_Command_btn = (Button) findViewById(R.id.Control_Command_btn);
        controlled_Num_et = (EditText) findViewById(R.id.Controlled_Num_et);
        control_Command_btn.setOnClickListener(this);
    }

    boolean checkNum(String str){
        if(str.length() != Controlled_Mode_Setting.phoneNumLength){
            return false;
        }
        return true;
    }

    void sendControlCommand(){
         PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Control_Mode_Setting.class), 0);

         SmsManager sms = SmsManager.getDefault();

         sms.sendTextMessage(controlled_Num, null,COMMANDS.CONTROL_COMMAND, pi, null);

        Toast.makeText(Control_Mode_Setting.this,"控制指令已发出！",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Control_Command_btn:
                if(checkNum(controlled_Num_et.getText().toString())){
                    controlled_Num = controlled_Num_et.getText().toString();
                    MainActivity.isControl = true;
                    sendControlCommand();
                }
                else {
                     Toast.makeText(Control_Mode_Setting.this,"号码输入不完整！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
