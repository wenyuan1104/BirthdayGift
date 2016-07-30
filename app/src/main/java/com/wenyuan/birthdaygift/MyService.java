package com.wenyuan.birthdaygift;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.FileDescriptor;
import java.io.IOException;

public class MyService extends Service {

    MediaPlayer mMediaPlayer;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    doPlay();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            doPlay();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void doPlay() throws IOException {
        AssetFileDescriptor assetFileDescriptor = getAssets().openFd("mp3.mp3");
        FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        mMediaPlayer.reset();//重置
        mMediaPlayer.setDataSource(fileDescriptor);//设置数据
        mMediaPlayer.prepare();//预处理
        mMediaPlayer.start();//开始
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /**
     *
     */
    class MyBinder extends Binder {

        public MyService getService() {
            return MyService.this;
        }

    }

}
