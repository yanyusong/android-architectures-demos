package net.zsygfddsd.qujing.modules.common;

import android.content.Context;

import net.zsygfddsd.qujing.common.helpers.dagger.ActivityScoped;
import net.zsygfddsd.qujing.data.DataSource;
import net.zsygfddsd.qujing.modules.welfarelist.WelfareListContract;
import net.zsygfddsd.qujing.modules.welfarelist.WelfareListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mac on 2016/10/14.
 */
@Module
public class PresenterModule {
    @ActivityScoped
    @Provides
    WelfareListPresenter provideWelfareListPresenter(Context context, WelfareListContract.View mView, DataSource.WelfareDataSource mRepository) {
        return new WelfareListPresenter(context, mView, mRepository);
    }


}
