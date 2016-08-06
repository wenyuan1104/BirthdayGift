package com.wenyuan.birthdaygift.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wenyuan.birthdaygift.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({"HandlerLeak", "NewApi"})
public class ImageSlidePanel extends FrameLayout {
    private List<TextView> viewList = new ArrayList<TextView>();
    private TextView lastView;
    private Handler uiHandler;

    private final ViewDragHelper mDragHelper;
    private int initCenterViewX = 0;
    private int screenWidth = 0;

    private int rotateDegreeStep = 5;
    private int rotateAnimTime = 100;

    private static final int MSG_TYPE_IN_ANIM = 1;
    private static final int MSG_TYPE_ROTATION = 2;
    private static final int XVEL_THRESHOLD = 100;
    private boolean isRotating = false;

    public ImageSlidePanel(Context context) {
        this(context, null);
    }

    public ImageSlidePanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("deprecation")
    public ImageSlidePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int cycleNum = data.getInt("cycleNum");
                if (msg.what == MSG_TYPE_IN_ANIM) {
                    processInAnim(cycleNum);
                } else if (msg.what == MSG_TYPE_ROTATION) {
                    processRotaitonAnim(cycleNum);
                }
            }
        };

        mDragHelper = ViewDragHelper
                .create(this, 10f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        initViewList();
    }

    class XScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            return Math.abs(dy) > Math.abs(dx);
        }
    }

    private void initViewList() {
        viewList.clear();
        int num = getChildCount();
        for (int i = 0; i < num; i++) {
            TextView tv = (TextView) getChildAt(i);
            tv.setRotation((num - 1 - i) * rotateDegreeStep);
            viewList.add(tv);
        }

        lastView = viewList.get(viewList.size() - 1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initCenterViewX = lastView.getLeft();
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (left == -lastView.getWidth() || left == screenWidth) {

                if (!isRotating) {
                    isRotating = true;
                    mDragHelper.abort();

                    int offsetLeftAndRight;
                    if (left < 0) {
                        offsetLeftAndRight = Math.abs(left) + initCenterViewX;
                    } else {
                        offsetLeftAndRight = initCenterViewX - left;
                    }

                    lastView.offsetLeftAndRight(offsetLeftAndRight);
                    orderViewStack();
                }
            } else if (!isRotating && changedView.getRotation() == 0) {
                processAlphaGradual(changedView, left);
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == lastView) {
                return true;
            }
            return false;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 256;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animToFade(xvel);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop();
        }
    }

    private void orderViewStack() {
        int num = viewList.size();
        for (int i = 0; i < num - 1; i++) {
            TextView tempView = viewList.get(i);
            tempView.bringToFront();
        }
        invalidate();

        lastView.setAlpha(1);
        lastView.setRotation((viewList.size() - 1) * rotateDegreeStep);
        viewList.remove(lastView);
        viewList.add(0, lastView);
        lastView = viewList.get(viewList.size() - 1);

        new MyThread(MSG_TYPE_ROTATION, viewList.size(), rotateAnimTime).start();
    }

    private void animToFade(float xvel) {
        int finalLeft = initCenterViewX;

        if (xvel > XVEL_THRESHOLD) {
            finalLeft = screenWidth;
        } else if (xvel < -XVEL_THRESHOLD) {
            finalLeft = -lastView.getWidth();
        } else {
            if (lastView.getLeft() > screenWidth / 2) {
                finalLeft = screenWidth;
            } else if (lastView.getRight() < screenWidth / 2) {
                finalLeft = -lastView.getWidth();
            }
        }

        if (mDragHelper.smoothSlideViewTo(lastView, finalLeft,
                lastView.getTop())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    public void onClickFade(int type) {
        int finalLeft = 0;
        if (type == -1) {
            finalLeft = -lastView.getWidth();
        } else if (type == 1) {
            finalLeft = screenWidth;
        }

        if (finalLeft != 0) {
            if (mDragHelper.smoothSlideViewTo(lastView, finalLeft,
                    lastView.getTop())) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void processAlphaGradual(View changedView, int left) {
        float alpha = 1.0f;
        int halfScreenWidth = screenWidth / 2;
        if (left > initCenterViewX) {
            if (left > halfScreenWidth) {
                alpha = ((float) left - halfScreenWidth) / halfScreenWidth;
                alpha = 1 - alpha;
            }
        } else if (left < initCenterViewX) {
            if (changedView.getRight() < halfScreenWidth) {
                alpha = ((float) halfScreenWidth - changedView.getRight())
                        / halfScreenWidth;
                alpha = 1 - alpha;
            }
        }

        changedView.setAlpha(alpha);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            mDragHelper.processTouchEvent(ev);
        }

        return shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mDragHelper.processTouchEvent(e);
        return true;
    }

    private void processInAnim(int cycleNum) {
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.image_in);

        Interpolator interpolator = new OvershootInterpolator(0.8f);
        animation.setInterpolator(interpolator);
        View view = viewList.get(cycleNum);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    private void processRotaitonAnim(int cycleNum) {
        if (cycleNum >= viewList.size() - 1) {
            isRotating = false;
            return;
        }

        TextView tv = viewList.get(viewList.size() - 1 - cycleNum);
        float fromDegree = tv.getRotation();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(tv, "rotation", fromDegree,
                        fromDegree - rotateDegreeStep)
                .setDuration(rotateAnimTime * 3);
        animator.start();
    }

    public void startInAnim() {
        new MyThread(MSG_TYPE_IN_ANIM, viewList.size(), 100).start();
    }

    class MyThread extends Thread {
        private int num;
        private int type;
        private int sleepTime;

        public MyThread(int type, int num, int sleepTime) {
            this.type = type;
            this.num = num;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {

                Message msg = uiHandler.obtainMessage();
                msg.what = type;
                Bundle data = new Bundle();
                data.putInt("cycleNum", i);
                msg.setData(data);
                msg.sendToTarget();

                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
