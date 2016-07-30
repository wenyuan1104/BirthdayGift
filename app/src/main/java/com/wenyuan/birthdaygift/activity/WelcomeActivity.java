package com.wenyuan.birthdaygift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tandong.swichlayout.SwitchLayout;
import com.wenyuan.birthdaygift.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 设置进入Activity的Activity特效动画，同理可拓展为布局动画
        SwitchLayout.getSlideFromBottom(this, false,
                null);
        // 三个参数分别为（Activity/View，是否关闭Activity，特效（可为空））

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        },3000);
    }
}
