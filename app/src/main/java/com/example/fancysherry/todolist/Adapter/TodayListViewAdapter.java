package com.example.fancysherry.todolist.Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.MyView.TypeFaces;
import com.example.fancysherry.todolist.R;
import com.example.fancysherry.todolist.TodayFragment;


import java.util.Calendar;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class TodayListViewAdapter extends ArraySwipeAdapter<Task>{

    private List<Task> tasks;
    private LayoutInflater layoutInflater;
    public Button totoday;
    public Button delete;
    private Activity context;

    public int one_default;
    public int three_default;
    public int five_default;

    private String edittext_content;

    private int position_select;//这个值对应了实际位置
    private int resize_position_select=0;//这个变量指的是修改选中的position在实际数据库中对应的id


    private Calendar ca = Calendar.getInstance();
    private int year = ca.get(Calendar.YEAR);//获取年份
    private int month=ca.get(Calendar.MONTH);//获取月份
    private int day=ca.get(Calendar.DATE);//获取日
    private String date_now=year+"-"+month+"-"+day;


    public int get_one_default()
    {
        return one_default;
    }

    public int get_three_default()
    {
        return three_default;
    }

    public int get_five_default()
    {
        return five_default;
    }



    public TodayListViewAdapter(Activity pContext,int resource,int one,int three,int five)
    {
        super(pContext,resource);
        layoutInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tasks=TodayFragment.dbManage.queryAll_today(date_now);
        this.context=pContext;
        this.one_default=one;
        this.three_default=three;
        this.five_default=five;
    }



    public void refresh(List<Task> r)
    {
        this.tasks=sort(r);
        notifyDataSetChanged();
    }

    public List<Task> sort(List<Task> r)
    {
        for(int i=0;i<r.size()-1;i++)
        {
            for(int j=i;j<r.size()-1;j++)
            {
                String color_judge1=r.get(j).Color();
                String color_judge2=r.get(j+1).Color();
                if(color_judge1.equals(color_judge2))
                {
                   if(r.get(j).getId()>r.get(j+1).getId())
                       swap(r,j,j+1);
                }

                else
                {
                    if(color_judge1.equals("five"))
                        swap(r,j,j+1);
                    if(color_judge1.equals("three")&&color_judge2.equals("one"))
                        swap(r,j,j+1);
                }


            }
        }

        return r;
    }

    public static <Task> void swap(List<Task> a, int i, int j) {
        Task tmp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, tmp);
    }

    @Override
    public int getCount() {
        Log.e("return",String.valueOf(tasks.size()));

        return tasks.size()+3;
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_today;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
        viewHolder = new ViewHolder();
        convertView = layoutInflater.inflate(R.layout.today_listview_item, parent,false);
        viewHolder.color=(ImageView)convertView.findViewById(R.id.color);
        viewHolder.title=(EditText)convertView.findViewById(R.id.content_today);

            viewHolder.title.setTypeface(TypeFaces.getTypeFace(context.getApplicationContext(),
                    "fonts/mytype.TTF"));


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

//        Log.e("position",String.valueOf(position));



        if(position==one_default||position==three_default||position==five_default)
        {

            if(position==one_default) {
                viewHolder.title.setText("");
                viewHolder.color.setBackgroundResource(R.drawable.yellow_egg);
            }
                if(position==three_default) {
                    viewHolder.title.setText("");
                    viewHolder.color.setBackgroundResource(R.drawable.red_egg);

                }if(position==five_default) {
            viewHolder.title.setText("");
            viewHolder.color.setBackgroundResource(R.drawable.blue_egg);
        }


        }

        else {

            //下面给view赋值
            if(position>five_default) {
                Log.e("five_default", String.valueOf(five_default));
                viewHolder.title.setText(tasks.get(position - 3).getTitle());
                position_select=position-3;
            }
                else if (position>three_default) {
                Log.e("three_default",String.valueOf(three_default));
                viewHolder.title.setText(tasks.get(position - 2).getTitle());
                position_select=position-2;
            }
                else if (position>one_default) {
                Log.e("one_default",String.valueOf(one_default));
                viewHolder.title.setText(tasks.get(position - 1).getTitle());
                position_select=position-1;
            }
                else {
                viewHolder.title.setText(tasks.get(position).getTitle());
                position_select=position;
            }


//        Log.e("color",tasks.get(position).Color());


            if (tasks.get(position_select).Color().equals("five")) {
//            Log.e("inboxprint", "five");
                viewHolder.color.setBackgroundResource(R.drawable.today_blue1);
            }
            if (tasks.get(position_select).Color().equals("one")) {
//            Log.e("inboxprint", "one");
                viewHolder.color.setBackgroundResource(R.drawable.today_yellow1);
            }
            if (tasks.get(position_select).Color().equals("three")) {
//            Log.e("inboxprint", "three");
                viewHolder.color.setBackgroundResource(R.drawable.today_red1);
            }

        }

        totoday=(Button)convertView.findViewById(R.id.to_inbox);
        totoday.setTag(position_select+"");
        totoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(v.getTag().toString());
//                Log.e("id", String.valueOf(id));
                insert_inbox(tasks.get(id).getTitle(),tasks.get(id).Color());
                if(tasks.get(id).Color().equals("five_inb"))
                {
                    five_default--;
                }

                if(tasks.get(id).Color().equals("three"))
                {
                    three_default--;
                    five_default--;
                }

                if(tasks.get(id).Color().equals("one"))
                {
                    one_default--;
                    three_default--;
                    five_default--;
                }

                TodayFragment.dbManage.delete("_id= ? ", new String[]{String.valueOf(tasks.get(id).getId())});
                insert_inbox(tasks.get(id).getTitle(),"null");
                Intent mIntent=new Intent();
                mIntent.setAction("refresh_inbox");
                context.sendBroadcast(mIntent);




                tasks=TodayFragment.dbManage.queryAll_today(date_now);
                refresh(tasks);
            }
        });
        delete=(Button)convertView.findViewById(R.id.delete_today);
        delete.setTag(position_select+"");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(v.getTag().toString());
                Log.e("id",String.valueOf(id));
                if(tasks.get(id).Color().equals("five"))
                {
                    five_default--;
                }

                if(tasks.get(id).Color().equals("three"))
                {
                    three_default--;
                    five_default--;
                }

                if(tasks.get(id).Color().equals("one"))
                {
                    one_default--;
                    three_default--;
                    five_default--;
                }
                TodayFragment.dbManage.delete("_id= ? ", new String[]{String.valueOf(tasks.get(id).getId())});//局部内部类方法域中的变量访问需要final
                tasks=TodayFragment.dbManage.queryAll_today(date_now);
                refresh(tasks);


            }
        });



        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);



        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                Log.e("open","open");


            }
        });


        viewHolder.title.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String color = null;
                    edittext_content = viewHolder.title.getText().toString();
                    viewHolder.title.setText(edittext_content);
                    Log.e("position",String.valueOf(position));


                    if(position != one_default &&position != three_default&&position != five_default)
                    {

                        //下面给view赋值
                        if(position>five_default) {
                            resize_position_select=position-3;
                        }
                        else if (position>three_default) {
                            resize_position_select=position-2;
                        }
                        else if (position>one_default) {
                            resize_position_select=position-1;
                        }
                        else {
                            resize_position_select=position;
                        }
                        update(resize_position_select,edittext_content);
                        tasks.clear();
                        tasks.addAll(TodayFragment.dbManage.queryAll_today(date_now));
                        refresh(tasks);
                    }
                    else {

                        if((position==one_default) && (one_default==1))
                        {
                            Crouton.makeText(context,"one important thing is filled", Style.CONFIRM).show();
                            viewHolder.title.setText("");
                        }

                        else if((position==three_default)&&(three_default-one_default==3))
                        {
                            Crouton.makeText(context,"three important thing is filled", Style.CONFIRM).show();
                            viewHolder.title.setText("");
                        }

                        else if((position==five_default)&&(five_default-three_default==5))
                        {
                            Crouton.makeText(context,"five important thing is filled", Style.CONFIRM).show();
                            viewHolder.title.setText("");
                        }

                        else {
                            if (position == one_default) {
                                color = "one";
                                one_default++;
                                three_default++;
                                five_default++;

                            }
                            if (position == three_default) {
                                color = "three";
                                three_default++;
                                five_default++;
                            }
                            if (position == five_default) {
                                color = "five";
                                five_default++;

                            }
                            insert_today(edittext_content, color);
                            tasks.clear();
                            tasks.addAll(TodayFragment.dbManage.queryAll_today(date_now));
                            refresh(tasks);
                        }
                    }

                    return true;
                }
                return false;
            }

        });





        return convertView;
    }

    class ViewHolder
    {
        ImageView color;
        EditText title;

    }

    private boolean insert_today(String title,String color)
    {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        String date_now=year+"-"+month+"-"+day;

        ContentValues mContentValues=new ContentValues();
        mContentValues.put("title",title);
        mContentValues.put("color",color);
        mContentValues.put("time","");
        mContentValues.put("date",date_now);
        mContentValues.put("flagCompleted","no");
        return TodayFragment.dbManage.insert(mContentValues);
    }

    private boolean insert_inbox(String title,String color)
    {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        String date_now=year+"-"+month+"-"+day;

        ContentValues mContentValues=new ContentValues();
        mContentValues.put("title",title);
        mContentValues.put("color",color);
        mContentValues.put("time","");
        mContentValues.put("date",date_now);
        mContentValues.put("flagCompleted","no");
        return TodayFragment.dbManage.insert(mContentValues);
    }

    private void update(int delete_select,String resize_content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",resize_content);
        TodayFragment.dbManage.update(contentValues, "_id = ? ", new String[]{String.valueOf(tasks.get(delete_select).getId())});
        Log.e("id",String.valueOf(tasks.get(delete_select).getId()));
    }

    private void update(int delete_select){
        ContentValues contentValues = new ContentValues();
        contentValues.put("flagCompleted","yes");
        TodayFragment.dbManage.update(contentValues, "_id = ? ", new String[]{String.valueOf(tasks.get(delete_select).getId())});
        Log.e("id",String.valueOf(tasks.get(delete_select).getId()));
    }

     public void slide_delete(int position)
     {    int delete_select;
         Log.e("task",String.valueOf(tasks.size()));
         Log.e("position1",String.valueOf(one_default));
         Log.e("position2",String.valueOf(three_default));
         Log.e("position3",String.valueOf(five_default));
         if(position==one_default||position==three_default||position==five_default)
         {

         }

        else {
             //下面给view赋值
             if (position > five_default) {
                 delete_select = position - 3;
             } else if (position > three_default) {
                 delete_select = position - 2;
             } else if (position > one_default) {
                 delete_select = position - 1;
             } else {
                 delete_select = position;
             }

             if(tasks.get(delete_select).Color().equals("five"))
             {
                 five_default--;
             }

             if(tasks.get(delete_select).Color().equals("three"))
             {
                 three_default--;
                 five_default--;
             }

             if(tasks.get(delete_select).Color().equals("one"))
             {
                 one_default--;
                 three_default--;
                 five_default--;
             }

//            this.remove(delete_select);
             update(delete_select);
             tasks=TodayFragment.dbManage.queryAll_today(date_now);
             Log.e("task",String.valueOf(tasks.size()));
             refresh(tasks);



         }
     }



}
