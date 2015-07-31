package com.example.mummyding.phonemonitor;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;


public class ringAty extends Service {

   void playSound(){
       Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
       MediaPlayer mMediaPlayer = new MediaPlayer();

       try {
           mMediaPlayer.setDataSource(this, alert);  //后面的是try 和catch ，自动添加的
       } catch (IllegalArgumentException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       } catch (SecurityException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       } catch (IllegalStateException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       } catch (IOException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       }
       mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
       mMediaPlayer.setLooping(true);    //循环播放开
       try {
           mMediaPlayer.prepare();     //后面的是try 和catch ，自动添加的
       } catch (IllegalStateException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       mMediaPlayer.start();//开始播放
   }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playSound();
    }
}


