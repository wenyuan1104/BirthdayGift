package com.wenyuan.birthdaygift.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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
    private int flag;
    List<Item> mFancyCoverFlows;

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
        mScratchTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (flag == 0)
                    Toast.makeText(MainActivity.this, "要把内容全部显示出来哦,有惊喜!", Toast.LENGTH_SHORT).show();
                flag = 2;
                return false;
            }
        });
        mScratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView scratchTextView) {
                //刮刮乐完成刮完之后的自动操作
                //Toast.makeText(MainActivity.this, "刮完了哦！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, BActivity.class));
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
        mFancyCoverFlows = new ArrayList<>();
        int ids[] = {R.drawable.t1, R.drawable.t2, R.drawable.t3,
                R.drawable.t2, R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t1};
        for (int i = 0; i < ids.length; i++) {
            Item item = new Item();
            item.setName((i + 1) + "天");
            item.setSelected(false);
            item.setBitmap(BitmapFactory.decodeResource(getResources(), ids[i]));
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
        mfancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, String.valueOf(position % mFancyCoverFlows.size()), Toast.LENGTH_SHORT).show();
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
