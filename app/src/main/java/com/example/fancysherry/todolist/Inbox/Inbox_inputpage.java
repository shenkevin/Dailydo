package com.example.fancysherry.todolist.Inbox;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fancysherry.todolist.Adapter.TaskAdapter;
import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.InboxFragment;
import com.example.fancysherry.todolist.MyView.ColorBallView;
import com.example.fancysherry.todolist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inbox_inputpage extends Activity {
    public Button button;
    public ImageView button_one;
    public ImageView button_three;
    public ImageView button_five;
    private String color="one";
    private EditText editText;
    private String Edit_content;
//    private int id;

    private ColorBallView colorBallView=new ColorBallView(this);

    private static List<Task> tasks = new ArrayList<Task>();

    private List<String> selectedTasks = new ArrayList<String>();


    private TaskAdapter taskAdapter;
    private DatabaseManager dbManager;//其他类通过DatabaseManager这个类来实现数据库的插入删除等



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_inputpage);

        dbManager=new DatabaseManager(this,"inbox");
        tasks=dbManager.queryAll();
        taskAdapter=new TaskAdapter(Inbox_inputpage.this,tasks);





        editText=(EditText)findViewById(R.id.inbox_input_edittext);
        button=(Button)findViewById(R.id.inbox_input_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Edit_content=editText.getText().toString();
                if (editText.getText().toString().equals("")) {
                    Edit_content="no content";
                }
//              Log.e("sdws","shujuku"+InboxFragment.sDatabaseHelp);//sDatabaseHelp报空指针
//              Log.e("color",color);//color和content有数据
//              Log.e("content",Edit_content);


//               InboxFragment.sDatabaseHelp.insert(color, Edit_content);
//               InboxFragment.setCursor.requery();
//               InboxFragment.listView.invalidate();


              if(insert())
              {

                  Intent mIntent=new Intent();
                  mIntent.setAction("refresh_inbox");
                  sendBroadcast(mIntent);

////                 int size=dbManager.queryAll().size();
////                 tasks.add(dbManager.queryAll().get(size-1));
//
//                  tasks.clear();
//                  tasks.addAll(dbManager.queryAll());
//
////                  taskAdapter.refresh(tasks);
//                  Log.e("list",tasks.toString());
//                 taskAdapter.notifyDataSetChanged();
////                 InboxFragment.listView.invalidate();
              }




               Intent mIntent=null;
               mIntent=new Intent(Inbox_inputpage.this,InboxFragment.class);
//              Bundle mBundle=new Bundle();
//              mBundle.putString("color",color);
//              mIntent.putExtras(mBundle);

               Inbox_inputpage.this.finish();
            }
        });



        button_one=(ImageView)findViewById(R.id.inbox_color_one);
        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="one";
                colorBallView.createAnimation(button_one);
            }
        });

        button_three=(ImageView)findViewById(R.id.inbox_color_three);
        button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="three";
                colorBallView.createAnimation(button_three);
            }
        });

        button_five=(ImageView)findViewById(R.id.inbox_color_five);
        button_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color="five";
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
