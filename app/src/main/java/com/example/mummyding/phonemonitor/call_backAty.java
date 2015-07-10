package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class call_backAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_back_aty);
        callback();
        onDestroy();
    }
    void callback(){
        Intent intent = new Intent();//创建一个意图对象，用来激发拨号的Activity
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + Controlled_Mode_Setting.control_Num));
        startActivity(intent);
    }

}
