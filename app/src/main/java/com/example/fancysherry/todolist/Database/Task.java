package com.example.fancysherry.todolist.Database;


/**
 * Created by fancysherry on 14-9-27.
 */
public class Task {
   private int id;
   private String title;
   private String color;
   private String createDate;
   private String createTime;
   private String flagCompleted;

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String Color()//inbox项里的颜色记录
    {
        return color;
    }
    public void setColor(String color)
    {
        this.color=color;
    }
    public String getFlagCompleted() {
        return flagCompleted;
    }
    public void setFlagCompleted(String flagCompleted) {
        this.flagCompleted = flagCompleted;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }





}
