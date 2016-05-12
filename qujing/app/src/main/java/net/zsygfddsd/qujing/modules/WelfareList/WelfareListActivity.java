package net.zsygfddsd.qujing.modules.WelfareList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.common.URLs;
import net.zsygfddsd.qujing.common.utils.FragUtils;
import net.zsygfddsd.qujing.components.HttpVolley.RequestInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareListActivity extends AppCompatActivity {

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
                    welfareListFragment.init(Tag_WelfareListFragment,new RequestInfo(URLs.GET_LIST_WELFARE),true,false,R.layout.item_welfare);
                    tempFrag = welfareListFragment;
                }

                return tempFrag;
            }
        });
        fragUtils.showFragment(Tag_WelfareListFragment);
    }
}
