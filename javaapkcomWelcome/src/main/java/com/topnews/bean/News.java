package com.topnews.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzx on 2016/5/19.
 */
public class News implements Serializable
{
    private String title;
    private Date date;
    private String extraInfo;
    private String url;

    public News()
    {
    }

    public News(String title, Date date, String extraInfo, String url)
    {
        this.title = title;
        this.date = date;
        this.extraInfo = extraInfo;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getExtraInfo()
    {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo)
    {
        this.extraInfo = extraInfo;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "News{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", extraInfo='" + extraInfo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
