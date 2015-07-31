package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{


    Button control_btn,controlled_btn;
    public static boolean isControl = false;
    public static boolean isControlled = false;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);
        init_findView();
    }

    void init_findView(){
        control_btn = (Button) findViewById(R.id.Control_Mode_btn);
        controlled_btn = (Button) findViewById(R.id.Controlled_Mode_btn);
        control_btn.setOnClickListener(this);
        controlled_btn.setOnClickListener(this);
        sp =getSharedPreferences("userInfo", MODE_PRIVATE);
        isControl = sp.getBoolean("isControl",false);
        isControlled = sp.getBoolean("isControlled", false);
        Control_Mode_Setting.controlled_Num = sp.getString("controlled_Num", "");
        Controlled_Mode_Setting.control_Num = sp.getString("controll_Num","");
        editor =sp.edit();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case R.id.Control_Mode_btn:

                if(isControl){
                    Intent intent_view_intent = new Intent(MainActivity.this,Control_View.class);
                    startActivity(intent_view_intent);
                    return ;
                }
                if(isControlled){
                    Toast.makeText(MainActivity.this,"你是被控端，无法进入主控模式",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent control_setting_intent = new Intent(MainActivity.this,Control_Mode_Setting.class);
                startActivity(control_setting_intent);
                break;
            case R.id.Controlled_Mode_btn:
                if(isControlled){
                    Intent controlled_view_intent= new Intent(MainActivity.this,Controlled_View.class);
                    startActivity(controlled_view_intent);
                    return ;
                }
                if(isControl){
                    Toast.makeText(MainActivity.this,"你是主控端，无法进入被控模式",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Intent controlled_setting_intent = new Intent(MainActivity.this,Controlled_Mode_Setting.class);
                startActivity(controlled_setting_intent);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }
        return super.onKeyDown(keyCode, event);
    }
}
