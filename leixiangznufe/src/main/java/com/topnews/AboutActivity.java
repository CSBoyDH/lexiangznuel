package com.topnews;

import android.os.Bundle;

import com.topnews.base.BaseActivity;
/**
 * Created by pc on 2016/6/11.
 */
public class AboutActivity extends BaseActivity{

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
