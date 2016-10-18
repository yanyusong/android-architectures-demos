package com.zsygfddsd.spacestation.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zsygfddsd.spacestation.R;


/**
 * 使用原生toolbar的兼容
 * Created by Clock on 2016/2/3.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    protected FrameLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yys_activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mContentView = (FrameLayout) findViewById(R.id.contentView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitleTextColor(0xffffffff);//白色
    }

    public void setToolBarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public TextView getToolbarTitleTextView() {
        return mToolbarTitle;
    }

    public void addViewToContent(@LayoutRes int layoutId) {
        mContentView.addView(View.inflate(this, layoutId, null));
    }
}
