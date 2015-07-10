package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;


public class rejectAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_aty);
        sendControlCommand(Controlled_Mode_Setting.control_Num,"拒绝响应！");
    }
    public  void sendControlCommand(String phoneNum,String Message ){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,showALinkDiaAty.class ), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNum, null, Message, pi, null);

    }

}
