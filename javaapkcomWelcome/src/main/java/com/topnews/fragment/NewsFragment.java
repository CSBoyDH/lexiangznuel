package com.topnews.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.ArrayList;

public class NewsFragment extends Fragment{
	private final static String TAG = "NewsFragment";
	Activity activity;
	ArrayList<News> newses = new ArrayList<News>();
	HeadListView mListView;
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
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
        url = AppApplication.getApp().getConfig().getUrlByID(channel_id);
		initData(url);
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
//	/** �˷�����˼Ϊfragment�Ƿ�ɼ� ,�ɼ�ʱ��������� */
//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		if (isVisibleToUser) {
//			//fragment�ɼ�ʱ��������
//			if(newses !=null && newses.size() !=0){
//				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
//			}else{
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						try {
//							Thread.sleep(2);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						handler.obtainMessage(SET_NEWSLIST).sendToTarget();
//					}
//				}).start();
//			}
//		}else{
//			//fragment���ɼ�ʱ��ִ�в���
//		}
//		super.setUserVisibleHint(isVisibleToUser);
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
		mListView = (HeadListView) view.findViewById(R.id.mListView);
		TextView item_textview = (TextView)view.findViewById(R.id.item_textview);
		detail_loading = (ImageView)view.findViewById(R.id.detail_loading);
		//Toast��ʾ��
		notify_view = (RelativeLayout)view.findViewById(R.id.notify_view);
		notify_view_text = (TextView)view.findViewById(R.id.notify_view_text);
		item_textview.setText(text);
		return view;
	}

	private void initData(final String url) {
		//�¿��߳����������ȡ��������
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
                newses = NewsTools.getNewsList(url, 1);
                handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			}
		}).start();
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SET_NEWSLIST:
				detail_loading.setVisibility(View.GONE);
				if(mAdapter == null){
					mAdapter = new NewsAdapter(activity, newses);
					//�ж��ǲ��ǳ��е�Ƶ��
					/*if(channel_id == Constants.CHANNEL_CITY){
						//�ǳ���Ƶ��
						mAdapter.setCityChannel(true);
						initCityChannel();
					}*/
				}
				mListView.setAdapter(mAdapter);
				mListView.setOnScrollListener(mAdapter);
				mListView.setPinnedHeaderView(LayoutInflater.from(activity).inflate(R.layout.list_item_section, mListView, false));
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
                        Intent intent = new Intent(activity, NewsDetailsActivity.class);
                        News news = mAdapter.getNewses().get(position);
                        intent.putExtra("url", "http://wellan.zuel.edu.cn/"+news.getUrl());
                        startActivity(intent);

					}
				});
				if(channel_id == 1){
					initNotify();
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	/* ��ʼ��ѡ����е�header*/
	/*public void initCityChannel() {
		View headview = LayoutInflater.from(activity).inflate(R.layout.city_category_list_tip, null);
		TextView chose_city_tip = (TextView) headview.findViewById(R.id.chose_city_tip);
		chose_city_tip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, CityListActivity.class);
				startActivity(intent);
			}
		});
		mListView.addHeaderView(headview);
	}*/
	
	/* ��ʼ��֪ͨ��Ŀ*/
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
	/* �ݻ���ͼ */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		mAdapter = null;
	}
	/* �ݻٸ�Fragment��һ����FragmentActivity ���ݻٵ�ʱ������Ŵݻ� */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
	}
}
