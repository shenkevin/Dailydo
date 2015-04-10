package com.example.fancysherry.todolist.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
* Created by fancysherry on 14-8-31.
*/
public class DatabaseHelp extends SQLiteOpenHelper {


    private final static String DATABASE_NAME = "notepad_db";
    private final static int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_ONE = "inbox";
    public static final String TABLE_NAME_TWO = "today";

    public DatabaseHelp(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


//    public DatabaseHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql1 = "create table if not exists " + TABLE_NAME_ONE
                + " ( _id integer primary key autoincrement, "
                + "   date  varchar(20),  "
                + "   title varchar(100),"
                + "   color varchar(10), "
                + "   time char(20), "
                + "   flagCompleted varchar(10) "
                + "  )";
        db.execSQL(sql1);

        final String sql2 = "create table if not exists " + TABLE_NAME_TWO
                + " ( _id integer primary key autoincrement, "
                + "   date  varchar(20),  "
                + "   title varchar(100),"
                + "   color varchar(10), "
                + "   time char(20), "
                + "   flagCompleted varchar(10) "
                + "  )";
        db.execSQL(sql2);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }



}
