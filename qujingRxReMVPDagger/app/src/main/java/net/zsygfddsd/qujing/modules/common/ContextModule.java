package net.zsygfddsd.qujing.modules.common;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mac on 2016/10/13.
 */
@Singleton
@Module
public final class ContextModule {

    private final Context _context;


    public ContextModule(Context context) {
        _context = context;
    }

    @Provides
    Context provideContext() {
        return _context;
    }


}
