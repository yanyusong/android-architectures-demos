package net.zsygfddsd.qujing.modules.WelfareList;

import android.os.Bundle;
import android.widget.FrameLayout;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.activity.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareListActivity extends BaseToolBarActivity {

    private static String Tag_WelfareListFragment = "WelfareListFragment";

    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    private WelfareListFragment welfareListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addViewToContent(R.layout.activity_welfare_list);
        ButterKnife.bind(this);
        welfareListFragment = WelfareListFragment.newInstance(R.layout.item_welfare);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, welfareListFragment).commit();
        new WelfareListPresenter(this, welfareListFragment);
    }
}
