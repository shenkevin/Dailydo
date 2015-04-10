package com.example.fancysherry.todolist.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fancysherry.todolist.Database.Task;
import com.example.fancysherry.todolist.R;
import com.nhaarman.listviewanimations.ArrayAdapter;

import java.util.List;

/**
 * Created by fancysherry on 14-9-7.
 */
public class HistoryListViewAdapter extends ArrayAdapter<Task> {

    private List<Task> finish_tasks;//这个task保存的是完成的list
    private LayoutInflater layoutInflater;
    private Context context;

    public HistoryListViewAdapter(Context pContext,List<Task> finish_tasks)
    {
        super(finish_tasks);
        layoutInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.finish_tasks=finish_tasks;
          this.context=pContext;
    }



    @Override
    public int getCount() {

        return finish_tasks.size();
    }

    @Override
    public Task getItem(int position)
    {
        return finish_tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return finish_tasks.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_history_item, null);
            viewHolder.color=(ImageView)convertView.findViewById(R.id.history_color);
            viewHolder.title=(TextView)convertView.findViewById(R.id.history_text);



            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        //下面给view赋值
        Log.e("historytitle",finish_tasks.get(position).getTitle());
        viewHolder.title.setText(finish_tasks.get(position).getTitle());


//        Log.e("finish_color", finish_tasks.get(position).Color());


        if(finish_tasks.get(position).Color().equals("five"))
            viewHolder.color.setBackgroundResource(R.drawable.history_five);

        if(finish_tasks.get(position).Color().equals("one"))
            viewHolder.color.setBackgroundResource(R.drawable.history_one);
        if(finish_tasks.get(position).Color().equals("three"))
            viewHolder.color.setBackgroundResource(R.drawable.history_three);


        return convertView;
    }



    class ViewHolder
    {
        ImageView color;
        TextView title;
        TextView time;

    }



}
