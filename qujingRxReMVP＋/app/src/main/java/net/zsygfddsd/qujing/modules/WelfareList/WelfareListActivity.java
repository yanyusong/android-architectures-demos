package net.zsygfddsd.qujing.modules.WelfareList;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import net.zsygfddsd.qujing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareListActivity extends RxAppCompatActivity {

    private static String Tag_WelfareListFragment = "WelfareListFragment";

    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    private WelfareListFragment welfareListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_list);
        ButterKnife.bind(this);
        welfareListFragment = WelfareListFragment.newInstance(R.layout.item_welfare);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, welfareListFragment).commit();
        new WelfareListPresenter(this, welfareListFragment);
    }
}
