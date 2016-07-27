package com.wenyuan.birthdaygift.bean;

/**
 * Created by xiaoyu on 15/12/14.
 */
public class Item {
    private String name;
    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
