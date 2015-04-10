package com.example.fancysherry.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.R;

import java.util.List;

/**
 * Created by fancysherry on 14-10-5.
 */
public class NotifyAdapter extends BaseAdapter
{
    private List<Task> nofinish_tasks;//这个task保存的是完成的list
    private LayoutInflater layoutInflater;

public NotifyAdapter(Context pContext,List<Task> nofinish_tasks)
{
    layoutInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.nofinish_tasks=nofinish_tasks;
}

    @Override
    public int getCount() {
        return nofinish_tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return nofinish_tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nofinish_tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.notify_item, null);
            viewHolder.color=(ImageView)convertView.findViewById(R.id.notify_color);
            viewHolder.title=(TextView)convertView.findViewById(R.id.notify_text);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }




//        Log.e("finish_color", finish_tasks.get(position).Color());
        viewHolder.title.setText(nofinish_tasks.get(position).getTitle());

        if(nofinish_tasks.get(position).Color().equals("five"))
            viewHolder.color.setBackgroundResource(R.drawable.history_five);

        if(nofinish_tasks.get(position).Color().equals("one"))
            viewHolder.color.setBackgroundResource(R.drawable.history_one);
        if(nofinish_tasks.get(position).Color().equals("three"))
            viewHolder.color.setBackgroundResource(R.drawable.history_three);


        return convertView;
    }



    class ViewHolder
    {
        ImageView color;
        TextView title;

    }
}
