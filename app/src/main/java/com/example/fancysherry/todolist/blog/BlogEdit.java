package com.example.fancysherry.todolist.blog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.example.fancysherry.todolist.R;

public class BlogEdit extends Activity {
    private DrawerLayout drawerLayout;
    private FrameLayout right_drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_edit);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        right_drawer=(FrameLayout)findViewById(R.id.drawer_right);






    }



}
