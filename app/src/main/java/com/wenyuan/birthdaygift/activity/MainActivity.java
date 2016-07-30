package com.wenyuan.birthdaygift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cooltechworks.views.ScratchTextView;
import com.tandong.swichlayout.SwitchLayout;
import com.wenyuan.birthdaygift.AppVar;
import com.wenyuan.birthdaygift.MyService;
import com.wenyuan.birthdaygift.R;
import com.wenyuan.birthdaygift.dalong.FancyCoverFlow;
import com.wenyuan.birthdaygift.dalong.Item;
import com.wenyuan.birthdaygift.dalong.MyFancyCoverFlowAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ScratchTextView mScratchTextView;
    private FancyCoverFlow mfancyCoverFlow;
    private MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppVar.getInstance().setActivity("0");
        SwitchLayout.ScaleBig(this, false, null);
        initView();
        initLisenter();
        initImageView();
    }

    /**
     * 初始化监听器
     */
    private void initLisenter() {
        mScratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView scratchTextView) {
                //刮刮乐完成刮完之后的自动操作
                Toast.makeText(MainActivity.this, "刮完了哦！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        mScratchTextView = (ScratchTextView) findViewById(R.id.scratchtextview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }

    /**
     * 图片滑动视图
     */
    private void initImageView() {
        List<Item> mFancyCoverFlows = new ArrayList<>();
        for (int i = 0; i < 365; i++) {
            Item item = new Item();
            item.setName((i + 1) + "天");
            item.setSelected(false);
            mFancyCoverFlows.add(item);
        }
        mfancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(this, mFancyCoverFlows);
        mfancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
        mfancyCoverFlow.setUnselectedAlpha(0.5f);//通明度
        mfancyCoverFlow.setUnselectedSaturation(0.5f);//设置选中的饱和度
        mfancyCoverFlow.setUnselectedScale(0.3f);//设置选中的规模
        mfancyCoverFlow.setSpacing(0);//设置间距
        mfancyCoverFlow.setMaxRotation(0);//设置最大旋转
        mfancyCoverFlow.setScaleDownGravity(0.5f);
        mfancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        int num = Integer.MAX_VALUE / 2 % mFancyCoverFlows.size();
        int selectPosition = Integer.MAX_VALUE / 2 - num;
        mfancyCoverFlow.setSelection(selectPosition);
        mfancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Item homeFancyCoverFlow = (Item) mfancyCoverFlow.getSelectedItem();
                if (homeFancyCoverFlow != null) {
                    Toast.makeText(MainActivity.this, homeFancyCoverFlow.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * @param view
     */
    public void jumpNext(View view) {
        startActivity(new Intent(this, SlideActivity.class));
    }
}
