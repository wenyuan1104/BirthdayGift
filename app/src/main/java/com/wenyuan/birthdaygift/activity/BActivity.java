package com.wenyuan.birthdaygift.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ant.liao.GifView;
import com.wenyuan.birthdaygift.R;

public class BActivity extends AppCompatActivity {


    GifView mGifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_b);
        mGifView = (GifView) findViewById(R.id.gifview);
        mGifView.setGifImage(R.mipmap.love);
    }
}
