package com.example.fancysherry.todolist.MyView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by fancysherry on 14-11-8.
 */
public class TimeshowLayout extends RelativeLayout implements View.OnTouchListener {

    /**
     * 当前滑动的ListView　position
     */
    /**
     * 手指按下X的坐标
     */
    private int downY;
    /**
     * 手指按下Y的坐标
     */
    private int downX;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * ListView的item
     */
    /**
     * 滑动类
     */
    private Scroller scroller;
    private static final int SNAP_VELOCITY = 400;
    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;
    /**
     * 是否响应滑动，默认为不响应
     */
    private boolean isSlide = false;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop;
    /**
     *  移除item后的回调接口
     */
    private AfterDragListener mAfterDragListener;
    /**
     * 用来指示item滑出屏幕的方向,向左或者向右,用一个枚举值来标记
     */
    private RemoveDirection removeDirection;



    // 滑动删除方向的枚举值
    public enum RemoveDirection {
        DOWN, UP;
    }

    public TimeshowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Interpolator interpolator = new BounceInterpolator();
        screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller = new Scroller(context,interpolator);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOnTouchListener(this);
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    /**
     * 设置滑动删除的回调接口
     * @param afterListener
     */
    public void setAfterDragListener(AfterDragListener afterListener) {
        this.mAfterDragListener = afterListener;
    }

//    /**
//     * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                addVelocityTracker(event);
//                Log.e("move_velocity",String.valueOf(getScrollVelocity()));
//                // 假如scroller滚动还没有结束，我们直接返回
//                if (!scroller.isFinished()) {
//                    return super.dispatchTouchEvent(event);
//                }
//                downX = (int) event.getX();
//                downY = (int) event.getY();
//
//
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY) {
//                    isSlide = true;
//
//
//                }
//                break;
//            }
//            case MotionEvent.ACTION_UP:
//                recycleVelocityTracker();
//                break;
//        }
//
//        return super.dispatchTouchEvent(event);
//    }

    /**
     * 往右滑动，getScrollY()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离，所以向右边滑动为负值
     */
    private void scrollDOWN() {
        removeDirection = RemoveDirection.DOWN;
        final int delta = this.getScrollY();
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(0,this.getScrollY(), 0, -delta,
                Math.abs(delta/2));
        this.postInvalidate(); // 刷新this
    }

    /**
     * 向左滑动，根据上面我们知道向左滑动为正值
     */
    private void scrollUP() {
//        removeDirection = RemoveDirection.UP;
//        final int delta = (screenWidth - this.getScrollY());
//        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
//        scroller.startScroll(this.getScrollY(), 0, delta, 0,
//                Math.abs(delta));
//        postInvalidate(); // 刷新this
    }

    /**
     * 根据手指滚动this的距离来判断是滚动到开始位置还是向左或者向右滚动
     */
    private void scrollByDistanceX() {
        // 如果向左滚动的距离大于屏幕的二分之一，就让其删除
        if (this.getScrollY() >= screenWidth / 2) {
            scrollDOWN();
        } else if (this.getScrollY() <= -screenWidth / 2) {
            scrollDOWN();
        } else {
            // 滚回到原始位置,为了偷下懒这里是直接调用scrollTo滚动
            this.scrollTo(0, 0);
            
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {

            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(ev);
            final int action = ev.getAction();
            int y = (int) ev.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:

                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaY = downY - y;
                    downY = y;
                    Log.e("move_distance", String.valueOf(deltaY));

                    // 手指拖动this滚动, deltaX大于0向左滚动，小于0向右滚
                    this.scrollBy(0, deltaY);

                    return true;  //拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:
//                    int velocityY = getScrollVelocity();
//                    if (velocityY > SNAP_VELOCITY) {
//                        scrollDOWN();
//                    } else if (velocityY < -SNAP_VELOCITY) {
//                        scrollUP();
//                    } else {
//                        scrollByDistanceX();
//                    }
//
//                    recycleVelocityTracker();
                    // 手指离开的时候就不响应左右滚动


                    scrollByDistanceX();
                    isSlide = false;
                    break;
            }
        

        //否则直接交给ListView来处理onTouchEvent事件
        return super.onTouchEvent(ev);
    }

    /**
     * 这个方法是activity的响应，ontouch为true会把这个方法给屏蔽掉
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return false;
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (scroller.computeScrollOffset()) {
            // 让ListView item根据当前的滚动偏移量进行滚动
            this.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            isSlide = true;
            this.postInvalidate();

            // 滚动动画结束的时候调用回调接口
            if (scroller.isFinished()) {
                if (mAfterDragListener == null) {
                    throw new NullPointerException("afterListener is null, we should called setAfterDragListener()");
                }

                this.scrollTo(0, 0);
            mAfterDragListener.after_drag();
            }
        }
        else
        {isSlide=false;
        }
    }

    /**
     * 添加用户的速度跟踪器
     *
     * @param event
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    /**
     * 移除用户速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 获取X方向的滑动速度,大于0向右滑动，反之向左
     *
     * @return
     */
    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getYVelocity();
        Log.e("get-y's velocity",String.valueOf(velocity));
        return velocity;
    }

    /**
     *
     * 当ListView item滑出屏幕，回调这个接口
     * 我们需要在回调方法removeItem()中移除该Item,然后刷新ListView
     *
     * @author xiaanming
     *
     */
    public interface AfterDragListener {
        public void after_drag();
    }





}
