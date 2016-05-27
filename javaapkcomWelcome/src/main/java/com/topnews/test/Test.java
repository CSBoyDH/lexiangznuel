package com.topnews.test;

import com.topnews.bean.News;
import com.topnews.config.Config;
import com.topnews.tool.NewsTools;

import java.util.ArrayList;

/**
 * Created by zzx on 2016/5/19.
 */
public class Test
{
    public static void main(String[] args)
    {
        int pages = NewsTools.getNewsPageCounts(Config.wenLanYaoWenURL);
        System.out.println(pages);
        
        ArrayList<News> newses = NewsTools.getNewsList(Config.wenLanYaoWenURL, 1);
        System.out.println(newses);
    }
}
