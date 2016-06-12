package com.topnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.topnews.adapter.NewsFragmentPagerAdapter;
import com.topnews.app.AppApplication;
import com.topnews.base.BaseActivity;
import com.topnews.bean.ChannelItem;
import com.topnews.bean.ChannelManage;
import com.topnews.fragment.NewsFragment;
import com.topnews.tool.BaseTools;
import com.topnews.view.ColumnHorizontalScrollView;
import com.topnews.view.DrawerView;
/**
 * Created by pc on 2016/6/11.
 */
public class AboutActivity extends BaseActivity{
    protected SlidingMenu aside_drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
       // initSlidingMenu();
    }

/*   // protected void initSlidingMenu() {
        aside_drawer = new DrawerView(this).initSlidingMenu();
    }*/
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
