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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class Controlled_Mode_Setting extends Activity implements View.OnClickListener{

    Button controlled_Command_btn;
    EditText control_Num_et;


    public static String control_Num;
    public static int phoneNumLength = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_controlled__mode__setting);
        if(MainActivity.isControl||MainActivity.isControlled){
            Intent newIntent = new Intent(Controlled_Mode_Setting.this,MainActivity.class);
            // 在Service中启动Activity，必须设置如下标志
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            return;
        }
        findView();
    }

    void findView(){
        controlled_Command_btn = (Button) findViewById(R.id.Controlled_Command_btn);
        control_Num_et = (EditText) findViewById(R.id.Control_Num_et);
        controlled_Command_btn.setOnClickListener(this);
    }

    boolean checkNum(String str){
        if(str.length() != phoneNumLength){
            return false;
        }
        return true;
    }

    void sendControlCommand(){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Control_Mode_Setting.class), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(control_Num, null,COMMANDS.CONTROLLED_COMMAND, pi, null);

        Toast.makeText(Controlled_Mode_Setting.this, "被控请求已发出！", Toast.LENGTH_SHORT).show();

        MainActivity.isControlled = true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Controlled_Command_btn:
                if(checkNum(control_Num_et.getText().toString())){
                    control_Num = control_Num_et.getText().toString();
                    MainActivity.isControlled = true;
                    sendControlCommand();
                }else{
                    Toast.makeText(Controlled_Mode_Setting.this,"号码输入不完整！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
