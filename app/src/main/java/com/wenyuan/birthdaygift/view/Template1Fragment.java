package com.wenyuan.birthdaygift.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wenyuan.birthdaygift.R;
import com.wenyuan.birthdaygift.utils.ScreenUtils;

/**
 * ���ճ�Ҷ��ʹ��ϰ�ߣ�ÿһҳӦ����һ��ģ�壬���Դ˴�����ʱʹ����Template�ؼ���
 *
 * @author Sistone.zhang
 */
@SuppressLint({"HandlerLeak", "NewApi"})
public class Template1Fragment extends Fragment {

    private Handler handler;
    private ImageSlidePanel slidePanel;
    AntiAliasTextView t4;

    private View leftShake, rightShake, bottomShake;
    private View leftClickArea, rightClickArea;
    private OnClickListener btnListener;
    private int ids[] = {R.drawable.t1,R.drawable.t2,R.drawable.t2,R.drawable.t2,R.drawable.t2,R.drawable.t2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.template1_layout, null);
        slidePanel = (ImageSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);

        //for (int i = 0; i < ids.length; i++) {
        //    int id = ids[i];
        //    AntiAliasTextView lView = initWidgets(id);
        //    slidePanel.addView(lView);
        //}

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                slidePanel.startInAnim();
                initAnimations();
            }
        };

        t4 = (AntiAliasTextView) rootView.findViewById(R.id.t4);
        t4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
            }
        });

        leftShake = rootView.findViewById(R.id.left_shake);
        rightShake = rootView.findViewById(R.id.right_shake);
        bottomShake = rootView.findViewById(R.id.bottom_shake);
        leftClickArea = rootView.findViewById(R.id.left_click_area);
        rightClickArea = rootView.findViewById(R.id.right_click_area);

        btnListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                int type = 0;
                if (v.getId() == leftClickArea.getId()) {
                    type = -1;
                } else if (v.getId() == rightClickArea.getId()) {
                    type = 1;
                }
                slidePanel.onClickFade(type);
            }
        };

        leftClickArea.setOnClickListener(btnListener);
        rightClickArea.setOnClickListener(btnListener);

        delayShowSlidePanel();
        return rootView;
    }

    /**
     * 动态加载布局
     */
    private AntiAliasTextView initWidgets(int id) {
        float width = 225;
        float heiht = 300;
        AntiAliasTextView view = new AntiAliasTextView(getActivity());

        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) view.getLayoutParams();
        p.bottomMargin = (int) ScreenUtils.dpToPx(getActivity(), 16);
        p.gravity = Gravity.CENTER;
        view.setLayoutParams(p);

        view.setWidth((int) ScreenUtils.dpToPx(getActivity(), width));
        view.setHeight((int) ScreenUtils.dpToPx(getActivity(), heiht));
        view.setGravity(Gravity.CENTER);
        view.setBackgroundResource(id);
        view.setVisibility(View.VISIBLE);

        return view;
    }

    private void initAnimations() {
        Animation animationLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.left_shake);
        Animation animationRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.right_shake);

        animationLeft.setInterpolator(new OvershootInterpolator(3));
        animationRight.setInterpolator(new OvershootInterpolator(3));

        leftShake.startAnimation(animationLeft);
        rightShake.startAnimation(animationRight);

        // �ײ��Ķ���ʹ��keyFrame����
        Keyframe kf0 = Keyframe.ofFloat(0, 0);
        Keyframe kf1 = Keyframe.ofFloat(0.6f, -70);
        Keyframe kf9 = Keyframe.ofFloat(1.0f, -70);
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofKeyframe(
                View.TRANSLATION_Y, kf0, kf1, kf9);

        Keyframe kf2 = Keyframe.ofFloat(0, 1f);
        Keyframe kf3 = Keyframe.ofFloat(0.4f, 1f);
        Keyframe kf4 = Keyframe.ofFloat(0.6f, 0f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe(
                View.ALPHA, kf2, kf3, kf4);

        ObjectAnimator bottomAnim = ObjectAnimator.ofPropertyValuesHolder(
                bottomShake, pvhTranslateY, pvhAlpha);
        bottomAnim.setDuration(1500);
        bottomAnim.setRepeatMode(Animation.RESTART);
        bottomAnim.setRepeatCount(Animation.INFINITE);
        bottomAnim.start();

        leftShake.setVisibility(View.VISIBLE);
        rightShake.setVisibility(View.VISIBLE);
        bottomShake.setVisibility(View.VISIBLE);

    }

    private void delayShowSlidePanel() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1200);

    }
}
