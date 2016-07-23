package net.zsygfddsd.qujing.modules.WelfareList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.common.utils.FragUtils;
import net.zsygfddsd.qujing.components.httpLoader.RequestInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareListActivity extends RxAppCompatActivity {

    private static String Tag_WelfareListFragment = "WelfareListFragment";

    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;

    private FragUtils fragUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_list);
        ButterKnife.bind(this);
        fragUtils = new FragUtils(getSupportFragmentManager(), R.id.mainFrame, new String[]{"WelfareListFragment"}, new FragUtils.IFragmentInitMethods() {
            @Override
            public Fragment initFrag(String fragTag) {
                Fragment tempFrag = null;
                if (fragTag == Tag_WelfareListFragment) {
                    WelfareListFragment welfareListFragment = new WelfareListFragment();
                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.addBodyParams("type", "福利");
                    welfareListFragment.init(Tag_WelfareListFragment, requestInfo, true, false, R.layout.item_welfare);
                    tempFrag = welfareListFragment;
                }

                return tempFrag;
            }
        });
        fragUtils.showFragment(Tag_WelfareListFragment);
    }
}
