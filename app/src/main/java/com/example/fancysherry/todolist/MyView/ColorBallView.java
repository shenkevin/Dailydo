package com.example.fancysherry.todolist.MyView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by fancysherry on 14-9-13.
 */
public class ColorBallView  implements ValueAnimator.AnimatorUpdateListener {

    private  AnimatorSet animatorSet=null;
    private  Context context;




    public ColorBallView(Context pContext)
    {
        this.context=pContext;
    }




    public void createAnimation(ImageView pColorBallView)
    {
         if(animatorSet==null)
         {
             ObjectAnimator mAnimatorUP=ObjectAnimator.ofFloat(pColorBallView,"y",0f,-100f).setDuration(80);
             mAnimatorUP.setInterpolator(new AccelerateInterpolator());
             ObjectAnimator mAnimatorDown=ObjectAnimator.ofFloat(pColorBallView,"y",0f,350f).setDuration(80);
             mAnimatorDown.setInterpolator(new AccelerateInterpolator());
             animatorSet=new AnimatorSet();
             animatorSet.playSequentially(mAnimatorUP,mAnimatorDown);//顺序播放动画
             animatorSet.start();

//             animatorSet.end();






         }





    }



//    public void startAnimation(ColorBallContain pColorBallView)
//    {
//        createAnimation(pColorBallView);
//        animatorSet.start();
//    }



    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }
}
