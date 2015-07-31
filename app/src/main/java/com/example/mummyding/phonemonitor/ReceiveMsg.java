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
    public static String phoneNum,message;
    void dealData(Context context,String phoneNum,String message){
        //处理短信

        //控制指令
        if(message.equals(COMMANDS.CONTROL_COMMAND)){
            Intent newIntent = new Intent(context,showALinkDiaAty.class);
            // 在Service中启动Activity，必须设置如下标志 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showALinkDiaAty.isdiaStart = false;
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
            return ;
        }
        if(message.equals(COMMANDS.CONTROL_SUCCESSFUL_COMMAND)){
            Toast.makeText(context,"成功建立控制连接！",Toast.LENGTH_SHORT).show();
            MainActivity.isControl = true;
            MainActivity.isControlled = false;
            Intent newIntent = new Intent(context,Control_View.class);
            // 在Service中启动Activity，必须设置如下标志
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
            return ;
        }
        if(message.equals(COMMANDS.CONTROL_FAIL_COMMAND)){
            Toast.makeText(context,"控制请求被拒绝！",Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(context,MainActivity.class);
            // 在Service中启动Activity，必须设置如下标志
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
            return ;
        }

        //请求被控指令
        if(message.equals(COMMANDS.CONTROLLED_COMMAND)){
            MainActivity.isControl = true;
            MainActivity.isControlled = false;
            Control_Mode_Setting.controlled_Num = phoneNum;
        }

        //被控端请求断开控制
        if(phoneNum.equals(Control_Mode_Setting.controlled_Num)){
            if(message.equals(COMMANDS.CONTROLLED_DISCONNECT)){
                MainActivity.isControl = false;
                MainActivity.isControlled = false;
                Control_Mode_Setting.controlled_Num ="";
                Controlled_Mode_Setting.control_Num = "";

                //保存状态&号码
                MainActivity.editor.putBoolean("isControl",false);
                MainActivity.editor.putBoolean("isControlled",false);
                MainActivity.editor.putString("control_Num", "");
                MainActivity.editor.putString("controlled_Num", "");
                Intent newIntent = new Intent(context,MainActivity.class);
                // 在Service中启动Activity，必须设置如下标志
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            }
        }

        if(phoneNum.equals(Controlled_Mode_Setting.control_Num)){
            //主控端请求断开控制
            if(message.equals(COMMANDS.CONTROL_DISCONNECT)){
                MainActivity.isControl = false;
                MainActivity.isControlled = false;
                Control_Mode_Setting.controlled_Num ="";
                Controlled_Mode_Setting.control_Num = "";

                //保存状态&号码
                MainActivity.editor.putBoolean("isControl",false);
                MainActivity.editor.putBoolean("isControlled",false);
                MainActivity.editor.putString("control_Num", "");
                MainActivity.editor.putString("controlled_Num", "");
                Intent newIntent = new Intent(context,MainActivity.class);
                // 在Service中启动Activity，必须设置如下标志
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);

            }

            //接收处理指令
            //定位
            if(message.equals(COMMANDS.CONTROL_LOCATION)){
                if(Controlled_View.isLocation == false){
                    //拒绝请求
                    Intent newIntent = new Intent(context,rejectService.class);
                    context.startService(newIntent);
                    context.stopService(newIntent);
                    return ;
                }
                Intent newIntent = new Intent(context,sendPosService.class);
                context.startService(newIntent);
                context.stopService(newIntent);
            }
            //回拨
            if(message.equals(COMMANDS.CONTROL_CALLBACK)){


                if(Controlled_View.isCall_back == false){
                //拒绝请求
                    Intent newIntent = new Intent(context,rejectService.class);
                    context.startService(newIntent);
                    //执行完后一定要停止 Service,不然下次无法启动
                    context.stopService(newIntent);
                    return  ;
                }
                Intent newIntent = new Intent(context,call_backService.class);
                context.startService(newIntent);
                context.stopService(newIntent);
            }
            //振动
            if(message.equals(COMMANDS.CONTROL_VIBRATION)){

                if(Controlled_View.isVibrator == false){
                    //拒绝请求
                    Intent newIntent = new Intent(context,rejectService.class);
                    context.startService(newIntent);
                    //执行完后一定要停止 Service,不然下次无法启动
                    context.stopService(newIntent);
                    return ;
                }
                Intent newIntent = new Intent(context,vibratorService.class);
                context.startService(newIntent);
                context.stopService(newIntent);
            }
            //响铃
            if(message.equals(COMMANDS.CONTROL_RING)){

                if(Controlled_View.isRing == false){
                    //拒绝请求
                    Intent newIntent = new Intent(context,rejectService.class);
                    context.startService(newIntent);
                    //执行完后一定要停止 Service,不然下次无法启动
                    context.stopService(newIntent);
                    return;
                }

                Intent newIntent = new Intent(context,ringService.class);
                context.startService(newIntent);
                //执行完后一定要停止 Service,不然下次无法启动
                context.stopService(newIntent);
            }
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(receiveBroadcast)){
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];
            for (int n = 0; n < messages.length; n++)
            {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            }

            //提取信息

            for(SmsMessage msg :smsMessage){

                phoneNum = msg.getDisplayOriginatingAddress();
                message = msg.getDisplayMessageBody();
                Toast.makeText(context,"收到指令，开始处理",Toast.LENGTH_SHORT).show();
                dealData(context, phoneNum, message);
            }
        }
    }
}
