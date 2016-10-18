package com.zsygfddsd.spacestation.base.module.base;


import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by mac on 16/6/11.
 */
public class BaseContract {

    public interface IBaseView<T extends IBasePresenter>{

        void setPresenter(T presenter);

        RxFragment getRxView();

        void showToast(String content);

    }

    public interface IBasePresenter {

        void start();

        void destroy();

    }

}
