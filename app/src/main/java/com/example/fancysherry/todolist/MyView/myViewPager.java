package com.example.fancysherry.todolist.MyView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fancysherry on 14-9-6.
 */
public class myViewPager extends ViewPager {
    private float mDownX;
    private float mDownY;


    private boolean willIntercept=true;

    public myViewPager(Context context) {
        this(context,null);
    }

    public myViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(willIntercept){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }

    }


    public void setTouchIntercept(boolean value){
        willIntercept = value;
    }
}

//   @Override
//    public boolean dispatchTouchEvent(MotionEvent ev)
//   {
//       switch(ev.getAction())
//       {
//           case MotionEvent.ACTION_DOWN:
////               mDownX=ev.getRawX();
////               mDownY=ev.getRawY();
////               getParent().requestDisallowInterceptTouchEvent(true);
//               break;
//
//           case MotionEvent.ACTION_MOVE:
//          if(mDownX<100||mDownY>400){
////              (Math.abs(ev.getX())-mDownX)>(Math.abs(ev.getY())-mDownY)
//
//
//
////               getParent().requestDisallowInterceptTouchEvent(true);
//           }
//           else
//           {
////               getParent().requestDisallowInterceptTouchEvent(false);
//           }
//               break;
//
//           case MotionEvent.ACTION_UP:
//               break;
//           case MotionEvent.ACTION_CANCEL:
////               getParent().requestDisallowInterceptTouchEvent(false);
//               break;
//
//
//
//       }
//
//       return super.dispatchTouchEvent(ev);
//   }






