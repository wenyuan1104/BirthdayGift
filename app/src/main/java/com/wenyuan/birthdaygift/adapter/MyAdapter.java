package com.wenyuan.birthdaygift.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenyuan.birthdaygift.R;

import java.util.List;

/**
 * Created by www22_000 on 2016/7/30 16:27.
 */

public class MyAdapter extends BaseQuickAdapter<String> {


    public MyAdapter(List<String> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.text_item, s);
        int item = baseViewHolder.getLayoutPosition();
        int flag = 0;
        switch (item) {
            case 1:
                flag = R.mipmap.meinv1;
                break;
            case 3:
                flag = R.mipmap.welcome;
                break;
            case 6:
                flag = R.mipmap.meinv3;
                break;
            case 7:
                flag = R.mipmap.logo;
                break;
            case 9:
                flag = R.mipmap.meinv5;
                break;
        }
        if (flag != 0) {
            baseViewHolder.setVisible(R.id.image_item, true);
            baseViewHolder.setImageResource(R.id.image_item, flag);
        }
    }

}
