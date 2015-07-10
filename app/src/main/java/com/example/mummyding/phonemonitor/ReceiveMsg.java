package com.example.mummyding.phonemonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by mummyding on 15-7-10.
 */
public class ReceiveMsg extends BroadcastReceiver {

    public static final String receiveBroadcast = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(receiveBroadcast)){
            //主控端
            if(MainActivity.isControl){
                Bundle bundle = intent.getExtras();
                Object messages[] = (Object[]) bundle.get("pdus");
                SmsMessage smsMessage[] = new SmsMessage[messages.length];
                for (int n = 0; n < messages.length; n++)
                {
                    smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
                }

                for(SmsMessage msg :smsMessage){
                    if(msg.getDisplayOriginatingAddress().equals(Control_Mode_Setting.controlled_Num)){
                        //处理指令
                      if(msg.getMessageBody().equals(COMMANDS.CONTROL_SUCCESSFUL_COMMAND)){
                          MainActivity.isControl = true;
                          Toast.makeText(context,"控制成功！",Toast.LENGTH_SHORT).show();
                          //进入主控界面
                         /* Intent intent=new Intent(context,);
                          intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          context.startActivity(intent1);*/
                      }
                      if(msg.getMessageBody().equals(COMMANDS.CONTROL_FAIL_COMMAND)){
                          Toast.makeText(context,"控制失败,对方拒绝！",Toast.LENGTH_SHORT).show();
                      }


                    }
                }
            }
            //被控端
            if(MainActivity.isControlled){

            }
        }
    }
}
