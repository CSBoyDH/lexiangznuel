package com.topnews.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topnews.NewsDetailsActivity;
import com.topnews.R;
import com.topnews.adapter.NewsAdapter;
import com.topnews.app.AppApplication;
import com.topnews.bean.News;
import com.topnews.tool.NewsTools;
import com.topnews.view.HeadListView;
import com.topnews.view.xlistview.XListView;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements XListView.IXListViewListener{
	private final static String TAG = "NewsFragment";
	Activity activity;
	ArrayList<News> newses = new ArrayList<News>();
	private Handler mHandler;
	private View viewFragment;
	XListView mListView;
	private int start = 0;
	private static int refreshCnt = 0;
	NewsAdapter mAdapter;
	String text;
	int channel_id;
	ImageView detail_loading;
	public final static int SET_NEWSLIST = 0;
	//Toast��ʾ��
	private RelativeLayout notify_view;
	private TextView notify_view_text;
    //����Ӧ�Ļ�ȡ������Ŀ��url
    private String url;
    int page = 1; //页数
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
        url = AppApplication.getApp().getConfig().getUrlByID(channel_id);
		super.onCreate(savedInstanceState);
	}

//    @Override
//    public void setArguments(Bundle args)
//    {
//        url = Config.getUrlByID(args.getInt("id"));
//        super.setArguments(args);
//    }

    @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
		mListView = (XListView) view.findViewById(R.id.mListView);
		TextView item_textview = (TextView)view.findViewById(R.id.item_textview);
		detail_loading = (ImageView)view.findViewById(R.id.detail_loading);
		//Toast��ʾ��
		notify_view = (RelativeLayout)view.findViewById(R.id.notify_view);
		notify_view_text = (TextView)view.findViewById(R.id.notify_view_text);
		item_textview.setText(text);
		init(url);

		return view;
	}
	private void initView(){
		mAdapter = new NewsAdapter(activity, newses);
		mListView.setPullLoadEnable(true);
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mListView.setOnScrollListener(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(activity, NewsDetailsActivity.class);
				News news = mAdapter.getNewses().get(position-1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				intent.putExtras(bundle);
				//intent.putExtra("url", "http://wellan.zuel.edu.cn/" + news.getUrl());
				startActivity(intent);
			}
		});
		mHandler = new Handler();
	}
	private void init(final String url) {

		AsyncTask<Void,Void,Void> ATgetNewes = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... voids) {
				newses = NewsTools.getNewsList(url,page);
				Log.d(TAG,newses.toString());
				return null;
			}
			protected void onPostExecute(Void v){
				Log.d(TAG, "###########################onPostExecute");
				initView();

			}
		};
		ATgetNewes.execute();
	}
	

	private void initNotify() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				notify_view_text.setText(String.format(getString(R.string.ss_pattern_update), 10));
				notify_view.setVisibility(View.VISIBLE);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						notify_view.setVisibility(View.GONE);
					}
				}, 2000);
			}
		}, 1000);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		mAdapter = null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		//mListView.setRefreshTime("");
	}

	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				page = 1;
				AsyncTask<Void,Void,Void> ATgetLatestNewes = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... voids) {
						newses.removeAll(newses);
						newses = NewsTools.getNewsList(url,page);
						Log.d(TAG,newses.toString());
						mAdapter = new NewsAdapter(getActivity(),newses);
						return null;
					}
					protected void onPostExecute(Void v){
						Log.d(TAG, "###########################33333onPostExecute");
						mListView.setAdapter(mAdapter);
						//mAdapter.notifyDataSetChanged();

					}
				};
				ATgetLatestNewes.execute();
				onLoad();
			}
		}, 2000);
	}

	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				page++;
				AsyncTask<Void,Void,Void> ATgetMoreNewes = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... voids) {
						ArrayList<News> moreNewses = new ArrayList<News>();
						moreNewses = NewsTools.getNewsList(url,page);
						newses.addAll(moreNewses);
						Log.d(TAG,newses.toString());
						return null;
					}
					protected void onPostExecute(Void v){
						mAdapter.notifyDataSetChanged();
						Log.d(TAG,"###########################22222onPostExecute");
					}
				};
				ATgetMoreNewes.execute();

				onLoad();
			}
		}, 2000);
	}
}
