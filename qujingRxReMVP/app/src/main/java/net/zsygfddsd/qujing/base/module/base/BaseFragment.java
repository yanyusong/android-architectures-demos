package net.zsygfddsd.qujing.base.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;


/**
 * Created by mac on 16/3/1.
 */
public class BaseFragment<T extends BaseContract.IBasePresenter> extends RxFragment implements BaseContract.IBaseView<T> {

    public Context ct;
    private BaseContract.IBasePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
    }

    public void showToast(String content) {
        Toast.makeText(ct, content, Toast.LENGTH_SHORT).show();
    }

    @CallSuper
    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public RxFragment getRxView() {
        return this;
    }
}
