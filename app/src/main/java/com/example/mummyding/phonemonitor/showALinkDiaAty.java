package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class showALinkDiaAty extends Activity {

    public static boolean isIn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alink_dia_aty);
        if(isIn)
        showALinkDia(ReceiveMsg.phoneNum);
    }

    public  void sendControlCommand(String phoneNum,String Message ){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,showALinkDiaAty.class ), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNum, null, Message, pi, null);

    }
    void showALinkDia(final String phoneNum){

        new AlertDialog.Builder(this).setTitle(phoneNum+"请求控制")
                .setMessage("是否接受控制？")
                .setPositiveButton("接受", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //回复同意确认
                        sendControlCommand(phoneNum, COMMANDS.CONTROL_SUCCESSFUL_COMMAND);
                        MainActivity.isControlled = true;
                        Controlled_Mode_Setting.control_Num = phoneNum;
                        isIn = false;

                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发送拒绝
                        sendControlCommand(phoneNum, COMMANDS.CONTROL_FAIL_COMMAND);
                        isIn = false;

                    }
                })
                .setIcon(R.drawable.abc_dialog_material_background_dark)
                .show();

    }
}
