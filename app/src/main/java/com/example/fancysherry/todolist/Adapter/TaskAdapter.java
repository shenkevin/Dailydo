package com.example.fancysherry.todolist.Adapter;

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
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.InboxFragment;
import com.example.fancysherry.todolist.MyView.TypeFaces;
import com.example.fancysherry.todolist.R;
import com.example.fancysherry.todolist.TodayFragment;

import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends BaseSwipeAdapter
{
    private List<Task> tasks;
    private LayoutInflater layoutInflater;
    private String colorString_fromfb;
    public Button totoday;
    public Button delete;
    public Button complete;
    private Context context;
    
    
    private String edittext_content;





    public TaskAdapter(Context pContext,List<Task> tasks)
    {
       layoutInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tasks=tasks;
        this.context=pContext;
    }



   public void refresh(List<Task> r)
   {
       this.tasks=r;
       notifyDataSetChanged();
   }

    @Override
    public int getCount() {
        return tasks.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override//return the id of swipelayout
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe_inbox;
    }

    @Override
    public View generateView(final int position, ViewGroup pViewGroup) {
        final View convertView;
       final ViewHolder viewHolder;
//        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_inbox_item, pViewGroup,false);
            viewHolder.color=(ImageView)convertView.findViewById(R.id.color);
            viewHolder.title=(EditText)convertView.findViewById(R.id.content_inbox);


//从assert中获取有资源，获得app的assert，采用getAserts()，通过给出在assert/下面的相对路径。在实际使用中，字体库可能存在于SD卡上，可以采用createFromFile()来替代createFromAsset。
        viewHolder.title.setTypeface(TypeFaces.getTypeFace(context.getApplicationContext(),
                "fonts/mytype.TTF"));

            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }


        if(position<tasks.size())
        viewHolder.title.setText(tasks.get(position).getTitle());


//        Log.e("color",tasks.get(position).Color());

        int flag=position%7;
        switch (flag)
        {
            case 0:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg1);
                break;
            case 1:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg2);
                break;
            case 2:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg3);
                break;
            case 3:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg4);
                break;
            case 4:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg5);
                break;
            case 5:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg6);
                break;
            case 6:
                viewHolder.color.setBackgroundResource(R.drawable.greyegg7);
                break;
        }


            if(position==(tasks.size()))
            {
                viewHolder.title.setClickable(true);
                viewHolder.title.setFocusable(true);
                viewHolder.title.setFocusableInTouchMode(true);
                viewHolder.title.requestFocus();

            }

        totoday=(Button)convertView.findViewById(R.id.to_today);
        totoday.setTag(position+"");
        totoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(v.getTag().toString());
                Log.e("id",String.valueOf(id));
                today_insert(tasks.get(id).getTitle(),tasks.get(id).Color());
                InboxFragment.dbManager.delete("_id= ? ", new String[]{String.valueOf(tasks.get(id).getId())});
//                today_insert(tasks.get(id).getTitle());

                Intent mIntent=new Intent();
                mIntent.setAction("refresh_today");
                context.sendBroadcast(mIntent);

                tasks=InboxFragment.dbManager.queryAll();
                refresh(tasks);
            }
        });
        delete=(Button)convertView.findViewById(R.id.delete);
        delete.setTag(position+"");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(v.getTag().toString());
                Log.e("id",String.valueOf(id));
                InboxFragment.dbManager.delete("_id= ? ", new String[]{String.valueOf(tasks.get(id).getId())});//局部内部类方法域中的变量访问需要final
                tasks=InboxFragment.dbManager.queryAll();
                refresh(tasks);

            }
        });
//        complete=(Button)convertView.findViewById(R.id.complete);
//        complete.setTag(position + "");
//        complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = Integer.parseInt(v.getTag().toString());
//                Log.e("id", String.valueOf(id));
////                InboxFragment.dbManager.delete("title= ? ", new String[]{tasks.get(id).getTitle()});//局部内部类方法域中的变量访问需要final
////                tasks=InboxFragment.dbManager.queryAll();
////                refresh(tasks);
//
//            }
//        });


        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
//        Log.e("huadong","huadong");



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
//                    Log.e("ruanjianpan","ruanjianpan");
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    edittext_content = viewHolder.title.getText().toString();

                    // 如果没有输入任何文字，则不添加新的item
                    if(edittext_content.equals(""))
                        return false;

                    if (viewHolder.title.getTag()==null||!(edittext_content.equals(viewHolder.title.getTag().toString()))) ;
                    {
                        viewHolder.title.setText(edittext_content);
                        inbox_insert(edittext_content);
                        tasks.clear();
                        tasks.addAll(InboxFragment.dbManager.queryAll());
                        refresh(tasks);
                    }


                    viewHolder.title.setTag(edittext_content);


//不修改就不添加



                    return true;
                }
                return false;
            }

        }); 
        
        
        

        return convertView;
    }

    @Override
    public void fillValues(int i, View pView) {

    }



    class ViewHolder
    {
        ImageView color;
        EditText title;
    }

    private boolean today_insert(String title,String color)
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

    private boolean inbox_insert(String title)
    {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);//获取年份
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        String date_now=year+"-"+month+"-"+day;

        ContentValues mContentValues=new ContentValues();
        mContentValues.put("title",title);
        mContentValues.put("color","");
        mContentValues.put("time","");
        mContentValues.put("date",date_now);
        mContentValues.put("flagCompleted","no");
        return InboxFragment.dbManager.insert(mContentValues);
    }


}