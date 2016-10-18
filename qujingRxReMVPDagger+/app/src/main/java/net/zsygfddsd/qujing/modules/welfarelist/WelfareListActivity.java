package net.zsygfddsd.qujing.modules.welfarelist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.zsygfddsd.spacestation.base.activity.BaseToolBarActivity;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.modules.common.ContextModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelfareListActivity extends BaseToolBarActivity {

    private static String Tag_WelfareListFragment = "WelfareListFragment";

    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
    //    @Inject
    //    Context _context;
    @Inject
    WelfareListPresenter _welfareListPresenter;
    //    @Inject
    //    DataSource.WelfareDataSource _welfareRepository;
    @Inject
    WelfareListContract.View _welfareListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addViewToContent(R.layout.activity_welfare_list);
        ButterKnife.bind(this);
        DaggerWelfareListActivityComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, (Fragment) _welfareListFragment).commit();


    }
}
