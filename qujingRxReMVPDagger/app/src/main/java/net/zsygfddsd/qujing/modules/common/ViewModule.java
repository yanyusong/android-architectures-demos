package net.zsygfddsd.qujing.modules.common;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.common.helpers.dagger.ActivityScoped;
import net.zsygfddsd.qujing.modules.welfarelist.WelfareListContract;
import net.zsygfddsd.qujing.modules.welfarelist.WelfareListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mac on 2016/10/14.
 */
@Module
public class ViewModule {
    @ActivityScoped
    @Provides
    WelfareListContract.View provideWelfareListFragment() {
        return WelfareListFragment.newInstance(R.layout.item_welfare);
    }


}
