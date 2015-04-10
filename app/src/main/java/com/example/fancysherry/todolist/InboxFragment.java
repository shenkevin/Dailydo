package com.example.fancysherry.todolist;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.datetimepicker.date.DatePickerDialog;

import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.example.fancysherry.todolist.Adapter.TaskAdapter;
import com.example.fancysherry.todolist.Database.DatabaseManager;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.blog.BlogEdit;
import com.example.fancysherry.todolist.blog.BlogShow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;





public class InboxFragment extends Fragment implements AdapterView.OnItemLongClickListener, DatePickerDialog.OnDateSetListener {



    public static ListView listView;







    private List<Task> tasks = new ArrayList<Task>();
    private TaskAdapter taskAdapter;
    public static DatabaseManager dbManager;



    private String lblTime;
    private Calendar calendar;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";

   //注册接受的广播
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("refresh_inbox"))
            {
                tasks.clear();
                tasks.addAll(dbManager.queryAll());
                taskAdapter.refresh(tasks);
            }

        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        }

    private void update() {
        lblTime=timeFormat.format(calendar.getTime());
    }



//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//        update();
//    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        new AlertDialog.Builder(getActivity())
                .setTitle("title")
                .setItems(R.array.dialog_arrays,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        if(which==0)
                        {
                            intent.setClass(getActivity(), BlogShow.class);
                            startActivity(intent);
                        }
                        if(which==1)
                        {
                            intent.setClass(getActivity(), BlogEdit.class);
                            startActivity(intent);
                        }
                        if(which==2)
                        {
                            DatePickerDialog.newInstance(new InboxFragment(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");
                        }
                   }
                }).create().show();

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       //注册接受的广播
        IntentFilter mIntentFilter=new IntentFilter();
        mIntentFilter.addAction("refresh_inbox");
        getActivity().registerReceiver(broadcastReceiver,mIntentFilter);





        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_inbox, container, false);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());


        update();


        listView=(ListView)mView.findViewById(R.id.inbox_listview);
//         截获viewpage的事件传递
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN)
//                {
//                    MyActivity.pager.setTouchIntercept(false);
//                }
//                if(event.getAction()==MotionEvent.ACTION_UP)
//                {
//                    MyActivity.pager.setTouchIntercept(true);
//                }
//
//                return false;
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });




        listView.setOnItemLongClickListener(this);
        dbManager=new DatabaseManager(getActivity(),"inbox");//后一个参数是表名
        tasks=dbManager.queryAll();
        taskAdapter=new TaskAdapter(getActivity(),tasks);
        taskAdapter.setMode(SwipeItemMangerImpl.Mode.Single);


        listView.setAdapter(taskAdapter);
//        view=(View)container.findViewById(R.id.)

        return mView;
    }









    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
*/



}
