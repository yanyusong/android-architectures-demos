package com.zsygfddsd.spacestation.base.module.network;


import com.zsygfddsd.spacestation.base.module.base.BaseContract;

/**
 * Created by mac on 16/6/13.
 */
public class BaseNetPresenter implements BaseContract.IBasePresenter {

    private BaseNetContract.IBaseNetView mView;

    public BaseNetPresenter(BaseNetContract.IBaseNetView mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
