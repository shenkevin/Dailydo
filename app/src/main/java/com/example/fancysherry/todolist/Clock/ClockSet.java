package com.example.fancysherry.todolist.Clock;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.fancysherry.todolist.R;

public class ClockSet extends Activity {

    private ImageView clockset_finish;
//    //与后台绑定
//    private ServiceConnection conn = new ServiceConnection() {
//        private ClockService mServices = null;
//
//        public void onServiceConnected(android.content.ComponentName arg0,
//                                       android.os.IBinder binder) {
//            mServices = ((ClockService.myBinder) binder).getServices();
//        };
//
//        public void onServiceDisconnected(android.content.ComponentName name) {
//        };
//    };
    private TimePicker timePicker;
    private String hour;
    private String minute;
    private String remind_time;

    private int hour_int;
    private int minute_int;
    private Time mTime=new Time();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_set);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        mTime.setToNow();
        hour=String.valueOf(mTime.hour);
        minute=String.valueOf(mTime.minute);

        timePicker = (TimePicker) findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                hour=String.valueOf(arg1);
                if(arg2<10)
                minute="0"+String.valueOf(arg2);
                else
                minute=String.valueOf(arg2);

                hour_int=arg1;
                minute_int=arg2;
                remind_time=hour+":"+minute;
            }
        });


        clockset_finish=(ImageView)findViewById(R.id.clockset_finish);
        clockset_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        SharedPreferences mPreferences = getSharedPreferences("time", 0);
                        mPreferences.edit()
                        .putString("remind_time",remind_time)
                        .putInt("hour_int",hour_int)
                        .putInt("minute_int",minute_int)
                        .commit();

                        Intent mIntent=new Intent();
                        mIntent.setAction("clock_set");
                        sendBroadcast(mIntent);
                        finish();
                    }
                }) ;


    }
    @Override
    protected void onStop() {
        super.onStop();
//        SharedPreferences mPreferences = getSharedPreferences("time", 0);
//        mPreferences.edit().putString("remind_time",remind_time).commit();
//        Intent mIntent=new Intent();
//        mIntent.setAction("clock_set");
//        sendBroadcast(mIntent);


    }

}
















