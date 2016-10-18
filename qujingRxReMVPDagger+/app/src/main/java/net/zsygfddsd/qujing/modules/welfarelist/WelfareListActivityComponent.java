package net.zsygfddsd.qujing.modules.welfarelist;

import com.zsygfddsd.spacestation.common.helpers.dagger.ActivityScoped;

import net.zsygfddsd.qujing.modules.common.ContextModule;
import net.zsygfddsd.qujing.modules.common.PresenterModule;
import net.zsygfddsd.qujing.modules.common.RepositoryModule;
import net.zsygfddsd.qujing.modules.common.ViewModule;

import dagger.Component;

/**
 * Created by mac on 2016/10/11.
 */
@ActivityScoped
@Component(modules = {RepositoryModule.class, PresenterModule.class, ViewModule.class, ContextModule.class})
public interface WelfareListActivityComponent {

    void inject(WelfareListActivity activity);

}
