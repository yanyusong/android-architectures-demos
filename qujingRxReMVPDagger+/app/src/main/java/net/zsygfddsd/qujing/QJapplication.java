package net.zsygfddsd.qujing;

import com.zsygfddsd.spacestation.base.BaseApplication;

import net.zsygfddsd.qujing.modules.common.ContextModule;

/**
 * Created by mac on 16/5/12.
 */
public class QJapplication extends BaseApplication {

    private static final String TAG = QJapplication.class.getSimpleName();
    private static QJapplication qjApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        qjApplication = this;
        DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build().inject(this);
    }

    public static QJapplication getInstance() {
        return qjApplication;
    }


}
