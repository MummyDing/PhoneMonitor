package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class showALinkDiaAty extends Activity implements View.OnClickListener{

    private TextView title;
    Button ok_btn,cancel_btn;
    private String phoneNum = ReceiveMsg.phoneNum;
    public static boolean isdiaStart;
    public static int screenWidth;
    public static int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_show_alink_dia_aty);

        if(isdiaStart == true) finish();


        //获取屏幕的尺寸
        DisplayMetrics dm= new DisplayMetrics();
        dm=this.getResources().getDisplayMetrics();
        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels;

        set_findView();
    }

    void set_findView(){

        WindowManager.LayoutParams params = getWindow().getAttributes();

        //设置对话框的尺寸
        params.height = screenHeight/4;
        params.width = (screenWidth/3)*2;
        this.getWindow().setAttributes(params);

        title = (TextView) findViewById(R.id.dialog_title);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        ok_btn.setWidth(params.width/3);
        ok_btn.setHeight(params.height/2);

        //按钮尺寸
        cancel_btn.setWidth(params.width/3);
        cancel_btn.setHeight(params.height/2);

        title.setText(phoneNum+"\n请求控制");

    }



    public  void sendControlCommand(String phoneNum,String Message ){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,showALinkDiaAty.class ), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNum, null, Message, pi, null);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_btn:
                sendControlCommand(phoneNum, COMMANDS.CONTROL_SUCCESSFUL_COMMAND);
                MainActivity.isControlled = true;
                Controlled_Mode_Setting.control_Num = phoneNum;
                //保存状态&号码
                MainActivity.editor.putBoolean("isControlled",true);
                MainActivity.editor.putString("control_Num", phoneNum);
                MainActivity.editor.commit();
                isdiaStart = true;
                this.finish();
                break;
            case R.id.cancel_btn:
                //发送拒绝
                sendControlCommand(phoneNum, COMMANDS.CONTROL_FAIL_COMMAND);
                isdiaStart = true;
                this.finish();
                break;
        }
    }
}
