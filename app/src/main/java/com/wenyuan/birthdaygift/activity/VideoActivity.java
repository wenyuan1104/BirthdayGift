package com.wenyuan.birthdaygift.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wenyuan.birthdaygift.MyService;
import com.wenyuan.birthdaygift.R;

public class VideoActivity extends Activity {

    SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mSurfaceView = (SurfaceView) findViewById(R.id.activity_video);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(MyCallBack);
        stopService(new Intent(this , MyService.class));

    }


    private SurfaceHolder.Callback MyCallBack = new SurfaceHolder.Callback() {
        MediaPlayer mMediaPlayer;

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            ////mMediaPlayer = MediaPlayer.create(VideoActivity.this, R.raw.video);
            //try {
            //    mMediaPlayer.prepare();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
            //mMediaPlayer.setDisplay(holder);
            //mMediaPlayer.start();
            try {
                AssetManager assetManager = VideoActivity.this.getAssets();
                AssetFileDescriptor afd = assetManager.openFd("video.mp4");
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(afd.getFileDescriptor(),
                        afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setLooping(true);//循环播放
                mMediaPlayer.prepare();
                mMediaPlayer.setDisplay(holder);
                mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mMediaPlayer.release();
        }
    };

}
