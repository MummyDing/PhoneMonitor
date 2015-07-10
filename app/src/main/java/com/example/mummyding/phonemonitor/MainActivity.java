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


public class MainActivity extends Activity implements View.OnClickListener{

    Button control_btn,controlled_btn;
    public static boolean isControl = false;
    public static boolean isControlled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    void findView(){
        control_btn = (Button) findViewById(R.id.Control_Mode_btn);
        controlled_btn = (Button) findViewById(R.id.Controlled_Mode_btn);
        control_btn.setOnClickListener(this);
        controlled_btn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Control_Mode_btn:


                Intent intent = new Intent(MainActivity.this,Control_View.class);
                startActivity(intent);
               if(true) return ;



                if(isControl){
                    //跳转到主控界面
                }
                Intent control_setting_intent = new Intent(MainActivity.this,Control_Mode_Setting.class);
                startActivity(control_setting_intent);
                break;
            case R.id.Controlled_Mode_btn:




                if(isControlled){
                    //跳转到被控界面
                }
                Intent controlled_setting_intent = new Intent(MainActivity.this,Controlled_Mode_Setting.class);
                startActivity(controlled_setting_intent);
                break;
        }
    }
}
