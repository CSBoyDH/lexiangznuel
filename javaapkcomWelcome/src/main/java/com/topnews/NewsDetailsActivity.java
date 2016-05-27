package com.topnews;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsDetailsActivity extends Activity
{

    private WebView webView;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            String html = (String)msg.obj;
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final String url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
//        webView.setInitialScale(35);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    URL url1 = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);

                    StringBuffer stringBuffer = new StringBuffer();
                    if(httpURLConnection.getResponseCode() == 200)
                    {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");

                        char []buffer = new char[1024];

                        int len = 0;

                        while((len = inputStreamReader.read(buffer))!=-1)
                        {
                            stringBuffer.append(new String(buffer,0,len));
                        }

//                    Log.i("web","1"+stringBuffer.toString());
                        Message message  = new Message();
                        message.obj = stringBuffer.toString();
                        handler.sendMessage(message);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webView.getSettings().setBlockNetworkImage(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
        //webView.setWebViewClient(new MyWebViewClient());

//        Map<String,String> extraHeaders = new HashMap<String, String>();
//        extraHeaders.put("X-User-Agent", "R809T__sinanews__4.9.5__android__4.2.1");
//        extraHeaders.put("X-Requested-With", "com.sina.news");

    }

}