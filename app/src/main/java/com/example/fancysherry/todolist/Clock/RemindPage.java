package com.example.fancysherry.todolist.Clock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.fancysherry.todolist.Adapter.NotifyAdapter;
import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RemindPage extends Activity {

    private DatabaseManager dbManage;
    private List<Task> nofinsh_tasks=new ArrayList<Task>();

    private ListView notifyListView;
    private NotifyAdapter notifyAdapter;


    private Calendar ca = Calendar.getInstance();
    private int year = ca.get(Calendar.YEAR);//获取年份
    private int month=ca.get(Calendar.MONTH);//获取月份
    private int day=ca.get(Calendar.DATE);//获取日
    private String date_now=year+"-"+month+"-"+day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_page);

        dbManage=new DatabaseManager(this,"today");
        nofinsh_tasks=dbManage.queryAll(date_now);

        notifyListView=(ListView)findViewById(R.id.notifyListView);
        notifyAdapter=new NotifyAdapter(this,nofinsh_tasks);
        notifyListView.setAdapter(notifyAdapter);




//        TimePicker mTimePicker;
//        NumberPicker






    }







}
