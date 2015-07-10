package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;


public class sendPosAty extends Activity {

    // 定义LocationManager对象
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pos_aty);
        getPosInfo();
        finish();
    }
    public  void sendControlCommand(String phoneNum,String Message ){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,showALinkDiaAty.class ), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNum, null, Message, pi, null);

    }
    void getPosInfo(){
        // 获取系统LocationManager服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 从GPS获取最近的定位信息
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("实时的位置信息：\n经度：");
            sb.append(location.getLongitude());
            sb.append("\n纬度：");
            sb.append(location.getLatitude());
            sb.append("\n高度：");
            sb.append(location.getAltitude());
            sb.append("\n速度：");
            sb.append(location.getSpeed());
            sb.append("\n方向：");
            sb.append(location.getBearing());
            sb.append("\n精度：");
            sb.append(location.getAccuracy());
            sendControlCommand(Controlled_Mode_Setting.control_Num,sb.toString());
        } else {
            // 如果传入的Location对象为空则清空EditText
            sendControlCommand(Controlled_Mode_Setting.control_Num,"位置获取失败");
        }
    }

}
