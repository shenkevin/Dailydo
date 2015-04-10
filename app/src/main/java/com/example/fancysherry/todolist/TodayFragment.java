package com.example.fancysherry.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.example.fancysherry.todolist.Adapter.TodayListViewAdapter;
import com.example.fancysherry.todolist.Clock.clockbroadcast;
import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.MyView.TimeshowLayout;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TodayFragment extends Fragment implements View.OnClickListener,OnDismissCallback,TimeshowLayout.AfterDragListener,TimePickerDialog.OnTimeSetListener, ContextualUndoAdapter.DeleteItemCallback {

    private ImageView clock_pic;
    private String remind_time;
    private ListView listView;
    private TodayListViewAdapter todayListViewAdapter;
    private TimeshowLayout timeshowLayout;



    //用一个list储存所有数据，通过遍历赋值到三个数组中
    private List<Task> TodayTasks=new ArrayList<Task>();//用于储存today界面的任务表




    private final String CLOCKBROAD_ACTION1="action_timer_start";

    private int hour_set_int;
    private int minute_set_int;

    private ImageView imageView_one;
    private ImageView imageView_two;
    private ImageView imageView_three;
    private ImageView imageView_four;
    private Handler handler=new Handler();



    private int set_minute;
    private int set_hour;
    private int number_one;
    private int number_two;
    private int number_three;
    private int number_four;

    private Calendar calendar;






    public static DatabaseManager dbManage;
//    //注册接受的广播
//    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//
//
//        }
//
//    };



    private void clock_set()
    {


        //开启提醒界面的broadcast
        Intent mIntent=new Intent(getActivity(),clockbroadcast.class);
        PendingIntent mPendingIntent=PendingIntent.getBroadcast(getActivity().getApplicationContext(),0,mIntent,0);

        //获取全局的服务管理器
        AlarmManager mAlarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        /**
         * 指定的任务只会执行一次，如果该pendingIntent指定的任务已经被执行过了，那么该方法直接会被cancel掉。
         *  set(int type, long triggerAtTime, PendingIntent operation)
         *  type 指定定时模式。
         *  triggerAtTime 触发任务的时间。该参数和定时模式息息相关
         *  operation 该参数指定一个广播Intent，当时间到了时，系统会广播里面的intent，触发相应的广播接收者执行某些操作，比如响铃……
         */

        Time mTime=new Time();
        mTime.setToNow();
        //获取设定时间与系统当前时间的差值
        hour_set_int=number_one*10+number_two;
        minute_set_int=number_three*10+number_four;
        Log.e("time_now",String.valueOf(mTime.hour)+String.valueOf(mTime.minute));
        long timesetting = 1000 * (3600 * (hour_set_int-mTime.hour) + 60 * (minute_set_int-mTime.minute));
        Log.e("time_interval",String.valueOf(timesetting));
        if(timesetting<0)
            timesetting+=1000*3600*24;

        //如果没有重新设置时间，默认为24小时重启一次
        //系统时间不是绝对时间。。。。。。。
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timesetting+System.currentTimeMillis(),24*3600*1000,mPendingIntent);



        final Animation alphaAnim_fade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
        final Animation alphaAnim_show_one = AnimationUtils.loadAnimation(getActivity(), R.anim.show_one);
        final Animation alphaAnim_show_two = AnimationUtils.loadAnimation(getActivity(), R.anim.show_two);
        final Animation alphaAnim_show_three = AnimationUtils.loadAnimation(getActivity(), R.anim.show_three);
        final Animation alphaAnim_show_four = AnimationUtils.loadAnimation(getActivity(), R.anim.show_four);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里进行UI操作
                        imageView_one.startAnimation(alphaAnim_fade);
                        set_number(number_one,imageView_one);
                    }
                });

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里进行UI操作
                        imageView_two.startAnimation(alphaAnim_fade);
                        set_number(number_two,imageView_two);
                    }
                });

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里进行UI操作
                        imageView_three.startAnimation(alphaAnim_fade);
                        set_number(number_three,imageView_three);
                    }
                });

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在这里进行UI操作
                        imageView_four.startAnimation(alphaAnim_fade);
                        set_number(number_four,imageView_four);
                    }
                });
            }
        }).start();



    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //从sharepreference中取出设定的时间数据
        SharedPreferences mSharedPreferences=getActivity().getSharedPreferences("time",0);
        remind_time=mSharedPreferences.getString("remind_time",null);

        dbManage=new DatabaseManager(getActivity(),"today");

    }




    @Override
    public void after_drag() {

        AnimateShake(timeshowLayout,true);
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");



    }

    private void setSwipeDismissAdapter() {
        SwipeDismissAdapter adapter = new SwipeDismissAdapter(todayListViewAdapter, this);
        adapter.setAbsListView(listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void deleteItem(int position) {
        todayListViewAdapter.slide_delete(position);
    }

    @Override
    public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            todayListViewAdapter.slide_delete(position);
        }
    }



    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        update(hourOfDay,minute);
    }

    private void update(int hour,int minute) {
       set_minute=minute;
        if(minute<10)
        {
            number_three=0;
            number_four=minute;
        }
        else
        {
            number_three=minute/10;
            number_four=minute%10;
        }
        set_hour=hour;
        if(hour<10)
        {
            number_one=0;
            number_two=hour;
        }
        else
        {
            number_one=hour/10;
            number_two=hour%10;
        }
        clock_set();
    }



    private void set_number(int i,ImageView pic)
    {
        switch(i)
        {
            case 0:
                pic.setBackgroundResource(R.drawable.zero);
                break;
            case 1:
                pic.setBackgroundResource(R.drawable.one);
                break;
            case 2:
                pic.setBackgroundResource(R.drawable.two);
                break;
            case 3:
                pic.setBackgroundResource(R.drawable.three);
                break;
            case 4:
                pic.setBackgroundResource(R.drawable.four);
                break;
            case 5:
                pic.setBackgroundResource(R.drawable.five);
                break;
            case 6:
                pic.setBackgroundResource(R.drawable.six);
                break;
            case 7:
                pic.setBackgroundResource(R.drawable.seven);
                break;
            case 8:
                pic.setBackgroundResource(R.drawable.eight);
                break;
            case 9:
                pic.setBackgroundResource(R.drawable.nine);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        IntentFilter mIntentFilter=new IntentFilter();
        mIntentFilter.addAction("refresh_today");
        mIntentFilter.addAction("clock_set");

//
//        getActivity().registerReceiver(broadcastReceiver,mIntentFilter);


        calendar = Calendar.getInstance();

        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_today, container, false);


// 图片更新
//
//        update();

        timeshowLayout=(TimeshowLayout)mView.findViewById(R.id.time_show_layout);
        timeshowLayout.setAfterDragListener(this);


        //time is set
        imageView_one=(ImageView)mView.findViewById(R.id.number_one);
        imageView_two=(ImageView)mView.findViewById(R.id.number_two);
        imageView_three=(ImageView)mView.findViewById(R.id.number_three);
        imageView_four=(ImageView)mView.findViewById(R.id.number_four);


        SharedPreferences mSharedPreferences=getActivity().getSharedPreferences("time",0);
        number_one=mSharedPreferences.getInt("number_one",0);
        number_two=mSharedPreferences.getInt("number_two", 0);
        number_three=mSharedPreferences.getInt("number_three",0);
        number_four=mSharedPreferences.getInt("number_four",0);

        set_number(number_one,imageView_one);
        set_number(number_two,imageView_two);
        set_number(number_three,imageView_three);
        set_number(number_four,imageView_four);


        // 保存todayadapter中3个默认位置的改变
        SharedPreferences mSharedPreferences2=getActivity().getSharedPreferences("adapter_default",0);


        listView=(ListView)mView.findViewById(R.id.today_listView);
        TodayTasks=dbManage.queryAll();
        todayListViewAdapter=new TodayListViewAdapter(getActivity(),R.layout.today_listview_item,mSharedPreferences2.getInt("position_one",0),mSharedPreferences2.getInt("position_three",1),mSharedPreferences2.getInt("position_five",2));
        todayListViewAdapter.setMode(SwipeItemMangerImpl.Mode.Single);


        setSwipeDismissAdapter();






        //载入today的text数据

        clock_pic=(ImageView)mView.findViewById(R.id.clock_pic);
        clock_pic.setOnClickListener(this);





    return mView;
    }



    private void sendBroadcast_my()
    {
        Intent mIntent=new Intent();
        mIntent.setAction("refresh_inbox");
        getActivity().sendBroadcast(mIntent);
    }

    //获得当前时间
    public String date(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(date);
        return str;
    }

////    private boolean delete(int i)
////    {
////        return dbManage.delete("_id= ? ",new String[]{String.valueOf(i)} );
////    }
//









    @Override
    public void onStop() {
        super.onStop();
//        getActivity().unregisterReceiver(broadcastReceiver);
        SharedPreferences mPreferences = getActivity().getSharedPreferences("adapter_default", 0);
        mPreferences.edit()
                .putInt("position_one",todayListViewAdapter.get_one_default())
                .putInt("position_three",todayListViewAdapter.get_three_default())
                .putInt("position_five",todayListViewAdapter.get_five_default())
                .commit();


        SharedPreferences mPreferences2 = getActivity().getSharedPreferences("time", 0);
        mPreferences2.edit()
                .putInt("number_one",number_one)
                .putInt("number_two",number_two)
                .putInt("number_three",number_three)
                .putInt("number_four",number_four)
                .commit();

    }


    private void AnimateShake(View view,boolean fade)
    {
        AnimationSet animationSet=new AnimationSet(true);
        Animation animation;
        animation= AnimationUtils.loadAnimation(getActivity(), R.anim.animshake);
        animationSet.addAnimation(animation);
        view.startAnimation(animationSet);
    }


    @Override
    public void onClick(View v) {
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
    }



}
