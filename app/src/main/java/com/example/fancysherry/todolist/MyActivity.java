package com.example.fancysherry.todolist;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 获取当前屏幕的密度
 */




public class MyActivity extends FragmentActivity {


    private InboxFragment inboxFragment;
    private TodayFragment todayFragment;
    private HistoryFragment historyFragment;

    private FragmentActivity context;

    private FragmentTabHost fragmentTabHost;
    private DrawerLayout drawerLayout;

    //定义一个布局
    private LayoutInflater layoutInflater;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {InboxFragment.class, TodayFragment.class, HistoryFragment.class};
    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.inbox_icon, R.drawable.today_icon, R.drawable.history_icon};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"inbox", "today", "history"};






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }


        ActionBar mActionBar=getActionBar();
        Drawable mDrawable=getResources().getDrawable(R.drawable.menu);
        mActionBar.setBackgroundDrawable(mDrawable);
        mActionBar.setIcon(R.drawable.actionbar_icon);

        SystemBarTintManager mTintManager=new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintDrawable(mDrawable);

        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        fragmentTabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.setCurrentTab(2);


        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            fragmentTabHost.setBackgroundResource(R.drawable.menu);
        }






    }



    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_content_layout, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }



    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
