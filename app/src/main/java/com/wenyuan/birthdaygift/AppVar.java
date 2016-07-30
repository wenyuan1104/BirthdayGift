package com.wenyuan.birthdaygift;

/**
 * Created by www22_000 on 2016/7/30 23:23.
 */

public class AppVar {

    private static AppVar sAppVar;

    String activity;

    private AppVar() {
    }

    /**
     * 双层单例模式
     *
     * @return
     */
    public static AppVar getInstance() {
        if (sAppVar == null) {
            synchronized (AppVar.class) {
                if (sAppVar == null)
                    sAppVar = new AppVar();
            }
        }
        return sAppVar;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
