package com.topnews;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.topnews.base.BaseActivity;
import com.topnews.bean.News;
import com.topnews.bean.NewsEntity;
import com.topnews.service.NewsDetailsService;
import com.topnews.tool.BaseTools;
import com.topnews.tool.DataTools;
import com.topnews.tool.DateTools;

@SuppressLint("JavascriptInterface")
public class NewsDetailsActivity extends BaseActivity {
    final  static  String TAG = "NewsDetailsActivity";
    private TextView title;
    private ProgressBar progressBar;
    private FrameLayout customview_layout;
    private String news_url;
    private String news_title;
    private String news_source;
    private String news_date;
    private News news;
    private TextView action_comment_count;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        setNeedBackGesture(true);//设置需要手势监听
        getData();
        initView();
        initWebView();
    }
    /* 获取传递过来的数据 */
    private void getData() {
        news  = (News) getIntent().getSerializableExtra("news");
        news_url = "http://wellan.zuel.edu.cn/" + news.getUrl();
        news_title = news.getTitle();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        news_date =formatter.format(news.getDate());
    }

    private void initWebView() {
        webView = (WebView)findViewById(R.id.wb_details);
        webView.getSettings().setBlockNetworkImage(false);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        if (!TextUtils.isEmpty(news_url)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
//			settings.setTextZoom(120);//Sets the text zoom of the page in percent. The default is 100.
            settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//			settings.setUseWideViewPort(true); //打开页面时， 自适应屏幕
//			settings.setLoadWithOverviewMode(true);//打开页面时， 自适应屏幕
            settings.setSupportZoom(false);// 用于设置webview放大
            settings.setBuiltInZoomControls(false);
            webView.setBackgroundResource(R.color.transparent);
            // 添加js交互接口类，并起别名 imagelistner
            webView.addJavascriptInterface(new JavascriptInterface(getApplicationContext()),"imagelistner");
            webView.setWebChromeClient(new MyWebChromeClient());
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(news_url, news_title, news_date);
        }
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        customview_layout = (FrameLayout) findViewById(R.id.customview_layout);
        //底部栏目
        action_comment_count = (TextView) findViewById(R.id.action_comment_count);

        progressBar.setVisibility(View.VISIBLE);
        title.setTextSize(13);
        title.setVisibility(View.VISIBLE);
        title.setText(news_url);
       // action_comment_count.setText(String.valueOf(news.getCommentNum()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private class MyAsnycTask extends AsyncTask<String, String,String>{

        @Override
        protected String doInBackground(String... urls) {
            String data=NewsDetailsService.getNewsDetails(urls[0],urls[1],urls[2]);
            Log.d(TAG,data.toString());
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            webView.loadDataWithBaseURL (null, data, "text/html", "utf-8",null);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\");"
                + "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
                + "imgurl+=objs[i].src+',';"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(imgurl);  "
                + "    }  " + "}" + "})()");
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {

            //
            String[] imgs = img.split(",");
            ArrayList<String> imgsUrl = new ArrayList<String>();
            for (String s : imgs) {
                imgsUrl.add(s);
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra("infos", imgsUrl);
            intent.setClass(context, ImageShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            if(newProgress != 100){
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
/*

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import com.topnews.bean.News;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsDetailsActivity extends Activity
{
    private News news;
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
        news  = (News) getIntent().getSerializableExtra("news");
        final String url = "http://wellan.zuel.edu.cn/" + news.getUrl();
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

}*/
