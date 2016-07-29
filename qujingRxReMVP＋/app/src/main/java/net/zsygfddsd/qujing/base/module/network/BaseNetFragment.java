package net.zsygfddsd.qujing.base.module.network;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import net.zsygfddsd.qujing.base.module.base.BaseContract;
import net.zsygfddsd.qujing.base.module.base.BaseFragment;


/**
 * Created by mac on 16/3/1.
 */
public class BaseNetFragment<T extends BaseContract.IBasePresenter> extends BaseFragment<T> implements BaseNetContract.IBaseNetView<T> {

    public ProgressDialog pDialog;
    private T mPresenter;

    @Override
    public void showLoading(boolean cancelable, @Nullable final ILoadingCancelListener listener) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(ct);
        }
        pDialog.setCancelable(cancelable);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (listener != null) {
                    listener.onLoadCancelListener();
                }
                hideLoading();
            }
        });
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    @Override
    public void hideLoading() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void showLoadingError() {
        showToast("获取失败");
    }

    public void showEmptyPage() {
        showToast("暂无数据");
    }

    public void showNoNetWork() {
        showToast("网络连接失败");
    }

    @CallSuper
    @Override
    public void setPresenter(T presenter) {
        super.setPresenter(presenter);
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }


}
