package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class call_backAty extends Service {
    void callback(){
        Log.d("回拨", "callback执行");
        Intent intent = new Intent();//创建一个意图对象，用来激发拨号的Activity
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + Controlled_Mode_Setting.control_Num));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        callback();
    }
}
