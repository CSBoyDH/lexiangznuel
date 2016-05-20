package com.topnews.tool;

import android.util.Log;

import com.topnews.bean.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by zzx on 2016/5/19.
 */
public class NewsTools
{
    //����ʱ���ʽ
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //��ȡ�����������ҳ��
    public static int getNewsPageCounts(String url)
    {
        int counts = 0;
        try
        {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("all_pages");
            String pages = elements.first().text();
            counts = Integer.valueOf(pages);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e("test","��ȡ������ҳ������");
        }
        return counts;
    }
    
    //�õ�ĳһҳ��News���󼯺�
    public static ArrayList<News> getNewsList(String url,int page)
    {
        ArrayList<News> newses = new ArrayList<News>();

        try
        {
            Document doc = Jsoup.connect(url + "list" + String.valueOf(page) + ".htm").get();
            Elements elements = doc.getElementsByClass("wp_article_list");
            Elements newsItems = elements.select("li");
            
            for(int i = 0;i<newsItems.size();i++)
            {
                News news = new News();
                news.setTitle(newsItems.get(i).select("a").first().text());
                news.setDate(simpleDateFormat.parse(newsItems.get(i).select("span.Article_PublishDate").first().text()));
                news.setUrl(newsItems.get(i).select("a").first().attr("href"));
                
                newses.add(news);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e("test","Jsoup���������ȡhtml����");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            Log.e("test","�ַ�������ת������");
        }
        return newses;
    }
}
