package com.example.fancysherry.todolist.MyView;

import android.content.Context;
import android.util.AttributeSet;

import com.daimajia.swipe.SwipeLayout;

/**
 * Created by fancysherry on 14-10-17.
 */
public class SwipeLayout_out extends SwipeLayout {
    private boolean willIntercept = true;
    private float mLastX = 0;

    public SwipeLayout_out(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//
//        switch (arg0.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                willIntercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mLastX < arg0.getX()) {
//                    willIntercept = true;
//                    //Log.i(TAG, "swipe left");
//                } else {
//                    willIntercept = false;
//                    //Log.i(TAG, "not swipe left");
//                }
//                break;
//            default:
//                willIntercept = false;
//                break;
//        }
//
//        return willIntercept;
//
//    }
}