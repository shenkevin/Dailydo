package com.example.fancysherry.todolist.MyView;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by fancysherry on 14-9-13.
 */
public class ColorBallSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;
    private ShapeHolder shapeHolder;





    public ColorBallSurfaceView(Context context) {
        super(context);
    }


    //surface对象创建后，该方法立即调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    //surface发生结构（格式和大小）性变化时调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    //surface对象在将要销毁前调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {

    }
}
