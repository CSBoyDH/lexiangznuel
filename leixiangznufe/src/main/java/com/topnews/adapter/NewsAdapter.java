package com.topnews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.topnews.R;
import com.topnews.bean.News;
import com.topnews.view.HeadListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener,HeadListView.HeaderAdapter
{

	private ArrayList<News> newses;
	private Context context;
//	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();

	public NewsAdapter(Context context, ArrayList<News> newses) {
		super();
		this.newses = newses;
		if(newses ==null){
			this.newses = new ArrayList<News>();
		}
		this.context = context;
	}

	public void addNews(ArrayList<News> newNewses){
		this.newses.addAll(newNewses);
	}

	public ArrayList<News> getNewses() {
		return newses;
	}

	@Override
	public int getCount() {
		return newses.size();
	}

	@Override
	public Object getItem(int position) {
		return newses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        News news = newses.get(position);
		if(convertView == null){
			convertView = View.inflate(context, R.layout.listview_fragment_news, null);
		}
		getTextViewByViewAndId(convertView, R.id.news_title).setText(news.getTitle());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newsTime = formatter.format(news.getDate());
		getTextViewByViewAndId(convertView,R.id.news_date).setText(newsTime);

		return convertView;
	}



	public TextView getTextViewByViewAndId(View view, int id){
		return (TextView)view.findViewById(id);
	}

	public ImageView getImageViewByViewAndId(View view,int id){
		return (ImageView)view.findViewById(id);
	}

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {

    }

    @Override
    public int getHeaderState(int position)
    {
        return 0;
    }

    @Override
    public void configureHeader(View header, int position, int alpha)
    {

    }
}


