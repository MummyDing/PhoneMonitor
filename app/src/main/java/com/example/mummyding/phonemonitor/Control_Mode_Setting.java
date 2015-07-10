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
        setContentView(R.layout.activity_control__mode__setting);
        findView();
    }
    void findView(){
        control_Command_btn = (Button) findViewById(R.id.Control_Command_btn);
        controlled_Num_et = (EditText) findViewById(R.id.Controlled_Num_et);
        control_Command_btn.setOnClickListener(this);
    }

    boolean checkNum(String str){
        if(str.length() != 11){
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
                    sendControlCommand();
                }
                else {
                     Toast.makeText(Control_Mode_Setting.this,"号码输入不完整！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
