package com.wenyuan.birthdaygift.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenyuan.birthdaygift.R;
import com.wenyuan.birthdaygift.adapter.MyAdapter;

import java.util.ArrayList;

public class PullActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<String> mList;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        mRecyclerView = (RecyclerView) findViewById(R.id.show_text);
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Toast.makeText(PullActivity.this, "进去干什么呢！！！\n" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("很多我们以为一辈子都不会忘记的事情，就在我们念念不忘的日子里，被我们遗忘了");
        mList.add("我知道我不是一个很好的记录者，但我比任何人都喜欢回首自己来时的路，我不但的回首，伫足，然手 时光仍下我轰轰烈烈的向前奔去");
        mList.add("你给我一滴眼泪，我就看到了你心中全部的海洋");
        mList.add("如果上帝要毁灭一个人必先令其疯狂.可我疯狂了这么久为何上帝还不把我毁掉.");
        mList.add("那些刻在椅子背后的爱情，会不会像水泥上的花朵，开出没有风的，寂寞的森林");
        mList.add("在这个忧伤而明媚的三月，我从我单薄的青春里打马而过，穿过紫堇，穿过木棉，穿过时隐时现的悲喜 和无常。");
        mList.add("你笑一次，我就可以高兴好几天；可看你哭一次，我就难过了好几年。");
        mList.add("那些曾经以为念念不忘的事情就在我们念念不忘的过程里，被我们遗忘了。");
        mList.add("我每天都在数着你的笑，可是你连笑的时候，都好寂寞。他们说你的笑容，又漂亮又落拓。");
        mList.add("风吹起如花般破碎的流年，而你的笑容摇晃摇晃，成为我命途中最美的点缀，看天，看雪，看季节深 深的暗影。");
        mList.add("牵着我的手，闭着眼睛走你也不会迷路。");
        mList.add("你永远也看不到我最寂寞时候的样子，因为只有你不在我身边的时候，我才最寂寞");
    }
}
