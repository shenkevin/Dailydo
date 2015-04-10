package com.example.fancysherry.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fancysherry on 14-9-27.
 */
public class DatabaseManager {

    private static final String TAG = "com.example.fancysherry.todolist.Database.DatabaseManager";

    private SQLiteDatabase database;

    private String TABLE_NAME="inbox";

    private String SQL_DROP_TABLE;

    private String SQL_INSERT;

    private String SQL_QUERY;

//我想通过一个DatabaseManager来管理多个表，故这里通过每次执行数据库的操作用传表名的方法

    public DatabaseManager(Context pContext,String table_name)
    {
        TABLE_NAME=table_name;
        DatabaseHelp mDatabaseHelp=new DatabaseHelp(pContext);
        database =mDatabaseHelp.getWritableDatabase();

        initSQL();
    }


    //根据不同的表初始化各个sqlite语句，在这个DatabaseManager中，用过sql语句实现增删
    public void initSQL()
    {
        SQL_INSERT = "insert into " + TABLE_NAME + " values (NULL, ?, ?, ?)";
        SQL_DROP_TABLE = "drop table if exists " + TABLE_NAME;
        SQL_QUERY = "select * from " + TABLE_NAME;
    }




    public void close()
    {
        database.close();
    }

    public void dropTable()
    {
        database.execSQL(SQL_DROP_TABLE);
    }



    //有两种插入数据到数据库的方法，一种是直接操作sql语句，第二种是通过contentValues来封装数据
    public boolean insert(Object[] values){
        database.execSQL(SQL_INSERT,values);
        return true;
    }

    public boolean insert(ContentValues contentValues){
        database.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean update(ContentValues contentValues, String whereClause, String[] whereArgs){
        database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        return true;
    }

    public boolean delete(String whereClause, String[] whereArgs){
        database.delete(TABLE_NAME, whereClause, whereArgs);
        return true;
    }


    public List<Task> queryAll()
    {
        List<Task> list=new ArrayList<Task>();
        Cursor mCursor=database.rawQuery(SQL_QUERY,null);
        if(mCursor==null)
        {
            //日志
        }

        else if(!mCursor.moveToFirst())
        {
            //日志
        }

        else//对cursor的操作
        {
            int columnID=mCursor.getColumnIndex("_id");
            int columnTITLE=mCursor.getColumnIndex("title");
            int columnCOLOR=mCursor.getColumnIndex("color");
            int columnTime=mCursor.getColumnIndex("time");

            int columnDate=mCursor.getColumnIndex("date");//日历列表记录每天
            int columnFlag=mCursor.getColumnIndex("flagCompleted");//标记是否完成

            do {
                String title=mCursor.getString(columnTITLE);
                int id=mCursor.getInt(columnID);
                String color=mCursor.getString(columnCOLOR);
                String time=mCursor.getString(columnTime);
                String Date=mCursor.getString(columnDate);
                String flagComplete=mCursor.getString(columnFlag);


                Task task=new Task();
                task.setId(id);
                Log.e("Database_inbox_id",String.valueOf(id));
                task.setColor(color);
                task.setCreateTime(time);
                task.setTitle(title);
                task.setCreateDate(Date);
                task.setFlagCompleted(flagComplete);
                list.add(task);
            }
            while(mCursor.moveToNext());
           //此为循环取出数据的方法，movetonext为假时跳出循环

        }
         mCursor.close();//使用完cursor后关闭


        return list;
    }


    public List<Task> queryAll_today(String date)
    {
        List<Task> list=new ArrayList<Task>();

//        if(search_style.equals("today_fragment"))
        Cursor mCursor=database.rawQuery(SQL_QUERY+" WHERE date="+"'"+date+"'"+" AND "+ "flagCompleted="+"'no'",null);
//        else if(search_style.equals("history_fragment"))
//         Cursor mCursor=database.rawQuery(SQL_QUERY+" WHERE flagCompleted= yes",null);

//        else
//            Cursor mCursor=database.rawQuery(SQL_QUERY,null);

        if(mCursor==null)
        {
            Log.e("log_cursor_is null","");
            //日志
        }

        else if(!mCursor.moveToFirst())
        {
            //日志
        }

        else//对cursor的操作
        {
            int columnID=mCursor.getColumnIndex("_id");
            int columnTITLE=mCursor.getColumnIndex("title");
            int columnCOLOR=mCursor.getColumnIndex("color");
            int columnTime=mCursor.getColumnIndex("time");

            int columnDate=mCursor.getColumnIndex("date");//日历列表记录每天
            int columnFlag=mCursor.getColumnIndex("flagCompleted");//标记是否完成

            do {
                String title=mCursor.getString(columnTITLE);
                int id=mCursor.getInt(columnID);
                String color=mCursor.getString(columnCOLOR);
                String time=mCursor.getString(columnTime);
                String Date=mCursor.getString(columnDate);
                String flagComplete=mCursor.getString(columnFlag);


                Task task=new Task();
                task.setId(id);
                task.setColor(color);
                task.setCreateTime(time);
                task.setTitle(title);
                task.setCreateDate(Date);
                task.setFlagCompleted(flagComplete);
                list.add(task);
            }
            while(mCursor.moveToNext());
            //此为循环取出数据的方法，movetonext为假时跳出循环

        }
        mCursor.close();//使用完cursor后关闭


        return list;
    }




    public List<Task> queryAll(String date)
    {
        List<Task> list=new ArrayList<Task>();

//        if(search_style.equals("today_fragment"))
        Cursor mCursor=database.rawQuery(SQL_QUERY+" WHERE date="+"'"+date+"'"+" AND "+ "flagCompleted="+"'yes'",null);
//        else if(search_style.equals("history_fragment"))
//         Cursor mCursor=database.rawQuery(SQL_QUERY+" WHERE flagCompleted= yes",null);

//        else
//            Cursor mCursor=database.rawQuery(SQL_QUERY,null);

        if(mCursor==null)
        {
            Log.e("log_cursor_is null","");
            //日志
        }

        else if(!mCursor.moveToFirst())
        {
            //日志
        }

        else//对cursor的操作
        {
            int columnID=mCursor.getColumnIndex("_id");
            int columnTITLE=mCursor.getColumnIndex("title");
            int columnCOLOR=mCursor.getColumnIndex("color");
            int columnTime=mCursor.getColumnIndex("time");

            int columnDate=mCursor.getColumnIndex("date");//日历列表记录每天
            int columnFlag=mCursor.getColumnIndex("flagCompleted");//标记是否完成

            do {
                String title=mCursor.getString(columnTITLE);
                int id=mCursor.getInt(columnID);
                String color=mCursor.getString(columnCOLOR);
                String time=mCursor.getString(columnTime);
                String Date=mCursor.getString(columnDate);
                String flagComplete=mCursor.getString(columnFlag);


                Task task=new Task();
                task.setId(id);
                task.setColor(color);
                task.setCreateTime(time);
                task.setTitle(title);
                task.setCreateDate(Date);
                task.setFlagCompleted(flagComplete);
                list.add(task);
            }
            while(mCursor.moveToNext());
            //此为循环取出数据的方法，movetonext为假时跳出循环

        }
        mCursor.close();//使用完cursor后关闭


        return list;
    }




}
