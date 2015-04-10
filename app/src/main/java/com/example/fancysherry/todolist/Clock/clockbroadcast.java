package com.example.fancysherry.todolist.Clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by fancysherry on 14-9-28.
 */
public class clockbroadcast extends BroadcastReceiver {

    private Vibrator mVibrator;

    private final String CLOCKBROAD_ACTION1="action_timer_start";

    @Override
    public void onReceive(Context context, Intent intent) {


            Intent mIntent = new Intent(context, RemindPage.class);

            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);

            mVibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            mVibrator.vibrate(2000);
//        ScrollView

        }

}
