package com.topnews.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.text.TextUtils;
import android.util.Log;

public class NewsDetailsService {
	final static String TAG = "NewsDetailsService";
	public static String getNewsDetails(String url, String news_title,
			String news_date) {
		Document document = null;
		String data = "<body >" +
				"<center><h2 style='font-size:16px;'>" + news_title + "</h2></center>";
		data = data + "<p align='left' style='margin-left:10px'>" 
				+ "<span style='font-size:10px;'>" 
				+ news_date
				+ "</span>" 
				+ "</p>";
		data = data + "<hr size='1' />";
		Log.d(TAG,url);
		try {
			document = Jsoup.connect(url).timeout(9000).get();
			Elements element = null;
			if (TextUtils.isEmpty(url)) {
				data = "";
				element = document.getElementsByClass("wp_articlecontent");
				Log.d(TAG,"1");
			} else {
				element = document.getElementsByClass("wp_articlecontent");
				Log.d(TAG,"2");
			}
			if (element != null) {
				data = data + element.toString();
				Log.d(TAG,"3");
			}else{
				Log.d(TAG,"4");
			}
			data = data + "</body>";
			data = getImageHtml(data);
			Log.d(TAG,data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	public static String getImageHtml(String data){
		String target="<img style=\"float:none\" src=\"";
		String insertContent = "http://news.zuel.edu.cn";
		data.indexOf(target,0);
		int index = 0;
		while(index<data.length()){
			int i = data.indexOf(target,index);
			if(i!=-1){
				data = insertString(data,insertContent, i + target.length());
				index = i+target.length()+insertContent.length();
			}else{
				break;
			}
		}
		return data;
	}
	public static String insertString( String descStr,String srcStr, int off)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(descStr.substring(0, off));
		sb.append(srcStr);
		sb.append(descStr.substring(off));

		return sb.toString();
	}
}
