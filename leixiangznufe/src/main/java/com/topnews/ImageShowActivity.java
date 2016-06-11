package com.topnews;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.topnews.adapter.ImagePagerAdapter;
import com.topnews.base.BaseActivity;
import com.topnews.view.imageshow.ImageShowViewPager;

/*
 * 图片展示
 */
public class ImageShowActivity extends BaseActivity {
	/** 图片展示 */
	private ImageShowViewPager image_pager;
	private TextView page_number;
	/** 图片下载按钮 */
	//private ImageView download;
	/** 图片列表 */
	private ArrayList<String> imgsUrl;
	/** PagerAdapter */
	private ImagePagerAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_imageshow);
		initView();
		initData();
		initViewPager();
	}

	private void initData() {
		imgsUrl = getIntent().getStringArrayListExtra("infos");
		page_number.setText("1" + "/" + imgsUrl.size());
	}

	private void initView() {
		image_pager = (ImageShowViewPager) findViewById(R.id.image_pager);
		page_number = (TextView) findViewById(R.id.page_number);
		//download = (ImageView) findViewById(R.id.download);
		image_pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				page_number.setText((arg0 + 1) + "/" + imgsUrl.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	public static String getSDPath()
	{
		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(hasSDCard)
		{
			return Environment.getExternalStorageDirectory().toString() + "/saving_picture";
		}
		else
			return "/data/data/com.topnews.imageviewsave2bitmap/saving_picture";
	}

	public static Bitmap convertViewToBitmap(View view)
	{
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		return bitmap;
	}

	// first SDCard is in the device, if yes, the pic will be stored in the SDCard, folder "HaHa_Picture"
	// second if SDCard not exist, the picture will be stored in /data/data/HaHa_Picture
	// file will be named by the customer
	public void saveImage(String strFileName)
	{
		Bitmap bitmap = convertViewToBitmap(image_pager.mCurrentView);
		String strPath = getSDPath();

		try
		{
			File destDir = new File(strPath);
			if (!destDir.exists())
			{
				Log.d("MagicMirror", "Dir not exist create it " + strPath);
				destDir.mkdirs();
				Log.d("MagicMirror", "Make dir success: " + strPath);
			}

			File imageFile = new File(strPath + "/" + strFileName);
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initViewPager() {
		if (imgsUrl != null && imgsUrl.size() != 0) {
			mAdapter = new ImagePagerAdapter(getApplicationContext(), imgsUrl);
			image_pager.setAdapter(mAdapter);
		}
	}
}
