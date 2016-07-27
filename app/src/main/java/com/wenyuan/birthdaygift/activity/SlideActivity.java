package com.wenyuan.birthdaygift.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wenyuan.birthdaygift.R;
import com.wenyuan.birthdaygift.view.Template1Fragment;

public class SlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new Template1Fragment()).commit();
        }
    }
}
