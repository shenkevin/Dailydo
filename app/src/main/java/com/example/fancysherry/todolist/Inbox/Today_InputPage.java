package com.example.fancysherry.todolist.Inbox;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.MyView.ColorBallView;
import com.example.fancysherry.todolist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Today_InputPage extends Activity {
    public Button button;
    public ImageView button_one;
    public ImageView button_three;
    public ImageView button_five;
    private String color="one";

    private String Edit_content;
//    private int id;

    private ColorBallView colorBallView=new ColorBallView(this);

    private static List<Task> todaytasks = new ArrayList<Task>();

    private List<String> selectedTasks = new ArrayList<String>();


    private DatabaseManager dbManager;//其他类通过DatabaseManager这个类来实现数据库的插入删除等



    private Calendar ca = Calendar.getInstance();
    private int year = ca.get(Calendar.YEAR);//获取年份
    private int month=ca.get(Calendar.MONTH);//获取月份
    private int day=ca.get(Calendar.DATE);//获取日
    private String date_now=year+"-"+month+"-"+day;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_inputpage);

        dbManager=new DatabaseManager(this,"today");


        final EditText editText=(EditText)findViewById(R.id.today_input_editText);




        button=(Button)findViewById(R.id.today_input_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_content=editText.getText().toString();
//                if (editText.getText().toString().equals("")) {
//                    Edit_content="no content";
//                }
                if(insert())
                {
                    Intent mIntent=new Intent();
                    mIntent.setAction("refresh_today");
                    sendBroadcast(mIntent);

                    Log.e("todaybroadcast",date_now);
                }
                Today_InputPage.this.finish();
            }
        });



        button_one=(ImageView)findViewById(R.id.today_color_one);
        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("todaycolor","");
                color="one";
                colorBallView.createAnimation(button_one);
            }
        });

        button_three=(ImageView)findViewById(R.id.today_color_three);
        button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="three";
                Log.e("todaycolor","");
                colorBallView.createAnimation(button_three);
            }
        });

        button_five=(ImageView)findViewById(R.id.today_color_five);
        button_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="five";
                Log.e("todaycolor","");
                colorBallView.createAnimation(button_five);
            }
        });


    }

    private boolean insert()
    {
        ContentValues mContentValues=new ContentValues();
        mContentValues.put("title",Edit_content);
        mContentValues.put("color",color);
        mContentValues.put("time",date());
        mContentValues.put("date",date_now);
        mContentValues.put("flagCompleted","no");
        return dbManager.insert(mContentValues);
    }
    private boolean update()
    {
        return false;
    }

    //获得当前时间
    public String date(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(date);
        return str;
    }

//   public void initData()
//   {
//
//   }


}
