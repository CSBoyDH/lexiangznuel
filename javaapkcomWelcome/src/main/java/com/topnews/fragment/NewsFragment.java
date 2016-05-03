package com.topnews.fragment;

import java.util.ArrayList;
import java.util.List;

import com.topnews.DetailsActivity;
import com.topnews.R;
import com.topnews.adapter.NewsAdapter;
import com.topnews.bean.NewsEntity;
import com.topnews.tool.Constants;
import com.topnews.tool.DateTools;
import com.topnews.view.HeadListView;
import com.topnews.view.xlistview.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewsFragment extends Fragment implements XListView.IXListViewListener {
	private Handler mHandler;
	private View viewFragment;
	private XListView mListView=null;
	private int start = 0;
	private static int refreshCnt = 0;

	private final static String TAG = "NewsFragment";
	Activity activity;
	ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();
	/*HeadListView mListView;*/
	NewsAdapter mAdapter;
	String text;
	int channel_id;
	ImageView detail_loading;
	public final static int SET_NEWSLIST = 0;
	//Toast提示框
	private RelativeLayout notify_view;
	private TextView notify_view_text;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;

		initData();
		/*initViews();*/
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {

		this.activity = activity;
		super.onAttach(activity);
	}
	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	/*public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			//fragment可见时加载数据
			if(newsList !=null && newsList.size() !=0){
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			}else{
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.obtainMessage(SET_NEWSLIST).sendToTarget();
					}
				}).start();
			}
		}else{
			//fragment不可见时不执行操作
		}
		super.setUserVisibleHint(isVisibleToUser);
	}*/

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
		TextView item_textview = (TextView)view.findViewById(R.id.item_textview);
		detail_loading = (ImageView)view.findViewById(R.id.detail_loading);
		//Toast提示框
		notify_view = (RelativeLayout)view.findViewById(R.id.notify_view);
		notify_view_text = (TextView)view.findViewById(R.id.notify_view_text);
		item_textview.setText(text);
		mAdapter = new NewsAdapter(activity, newsList);
		mListView = (XListView) view.findViewById(R.id.mListView);
		mListView.setPullLoadEnable(true);
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		/*mListView.setOnScrollListener(mAdapter);*/
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				/*Intent intent = new Intent(activity, NewsDetailsActivity.class);
				if(channel_id == Constants.CHANNEL_CITY){
					if(position != 0){
						intent.putExtra("news", mAdapter.getItem(position - 1));
						startActivity(intent);
						activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					}
				}else{
					intent.putExtra("news", mAdapter.getItem(position));
					startActivity(intent);
					activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}*/
			}
		});
		mHandler = new Handler();

		return view;
	}


	private void initData() {
		newsList = Constants.getNewsList();
	}

/*	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SET_NEWSLIST:
				detail_loading.setVisibility(View.GONE);
				if(mAdapter == null){
					mAdapter = new NewsAdapter(activity, newsList);
					//判断是不是城市的频道
					if(channel_id == Constants.CHANNEL_CITY){
						//是城市频道
						mAdapter.setCityChannel(true);
					initCityChannel();
					}
				}
				mListView.setAdapter(mAdapter);
				mListView.setOnScrollListener(mAdapter);
				mListView.setPinnedHeaderView(LayoutInflater.from(activity).inflate(R.layout.list_item_section, mListView, false));
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(activity, NewsDetailsActivity.class);
						if(channel_id == Constants.CHANNEL_CITY){
							if(position != 0){
								intent.putExtra("news", mAdapter.getItem(position - 1));
								startActivity(intent);
								activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							}
						}else{
							intent.putExtra("news", mAdapter.getItem(position));
							startActivity(intent);
							activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						}
					}
				});
				if(channel_id == 1){
					initNotify();//更新多少条消息
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};*/


	private void geneItems() {
		for(int i =0 ; i < 10 ; i++){
			NewsEntity news = new NewsEntity();
			news.setId(i);
			news.setNewsId(i);
			news.setCollectStatus(false);
			news.setCommentNum(i + 10);//评论数
			news.setInterestedStatus(false);
			news.setLikeStatus(true);
			news.setReadStatus(true);//读过为灰色，并没有实现
			news.setNewsCategory("推荐");//并没有用
			news.setNewsCategoryId(1);
			news.setSource_url("http://202.114.238.65/");
			news.setTitle("学校获评“湖北省高校绿化管理先进单位”");
			List<String> url_list = new ArrayList<String>();
			if(i%2 == 1){
				String url1 = "http://wellan.znufe.edu.cn/upload/2015/4/24161749851.jpg";
				String url2 = "http://infopic.gtimg.com/qq_news/digi/pics/102/102066/102066096_400_640.jpg";
				String url3 = "http://infopic.gtimg.com/qq_news/digi/pics/102/102066/102066099_400_640.jpg";
				news.setPicOne(url1);
				news.setPicTwo(url2);
				news.setPicThr(url3);
				news.setSource_url("http://202.114.238.65/");
				news.setSource_url("http://wellan.znufe.edu.cn/ttxw/2/33932.html?__r=1652");

				url_list.add(url1);
				url_list.add(url2);
				url_list.add(url3);
			}else{
				news.setTitle("2015南湖论坛聚焦知识产权强国建设");
				String url = "http://wellan.znufe.edu.cn/upload/2015/4/20154051257.jpg";
				news.setReadStatus(false);
				news.setPicOne(url);
				url_list.add(url);
			}
			news.setPicList(url_list);
			news.setPublishTime(Long.valueOf(i));
			news.setReadStatus(false);
			news.setSource("文澜新闻网");
			news.setSummary("腾讯数码讯（编译：Gin）谷歌眼镜可能是目前最酷的可穿戴数码设备，你可以戴着它去任何地方（只要法律法规允许或是没有引起众怒），作为手机的第二块“增强现实显示屏”来使用。另外，虽然它仍未正式销售，但谷歌近日在美国市场举行了仅限一天的开放购买活动，价格则为1500美元（约合人民币9330元），虽然仍十分昂贵，但至少可以满足一些尝鲜者的需求，也预示着谷歌眼镜的公开大规模销售离我们越来越近了。");
			news.setMark(i);
			if(i == 4){
				news.setTitle("徐汉明在联合国预防犯罪与刑司大会作报告");
				news.setLocal("推广");
				news.setIsLarge(true);
				String url = "http://wellan.znufe.edu.cn/upload/2015/4/15111121491.jpg";
				news.setSource_url("http://wellan.znufe.edu.cn/ttxw/2/33812.html");
				news.setPicOne(url);
				url_list.clear();
				url_list.add(url);
			}else{
				news.setIsLarge(false);
			}
			if(i == 2){
				news.setComment("评论部分，说的非常好。");
			}

			if(i <= 2){
				news.setPublishTime(Long.valueOf(DateTools.getTime()));
			}else if(i >2 && i <= 5){
				news.setPublishTime(Long.valueOf(DateTools.getTime()) - 86400);
			}else{
				news.setPublishTime(Long.valueOf(DateTools.getTime()) - 86400 * 2);
			}
			newsList.add(news);
		}
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				newsList.clear();
				geneItems();
				//	mAdapter.notifyDataSetChanged();
				mAdapter = new NewsAdapter(activity, newsList);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	/* 初始化通知栏目*/
	private void initNotify() {//更新多少条消息
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
	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		mAdapter = null;
	}
	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
	}

}
