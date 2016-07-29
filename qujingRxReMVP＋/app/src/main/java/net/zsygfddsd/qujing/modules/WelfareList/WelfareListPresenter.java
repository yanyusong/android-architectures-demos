package net.zsygfddsd.qujing.modules.WelfareList;

import android.content.Context;
import android.util.Log;

import net.zsygfddsd.qujing.base.module.network_recyclerview.BasePagePresenter;
import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.Welfare;
import net.zsygfddsd.qujing.components.httpLoader.HttpLoader;

import java.util.List;

import rx.Observable;

/**
 * Created by mac on 16/7/24.
 */
public class WelfareListPresenter extends BasePagePresenter<List<Welfare>, Welfare> implements WelfareListContract.Presenter {

    private WelfareListContract.View mView;

    public WelfareListPresenter(Context context, WelfareListContract.View mView) {
        super(context, mView);
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean getIsHasNextFromResponse(List<Welfare> result) {
        return true;
    }

    @Override
    public List<Welfare> getListFromResponse(List<Welfare> result) {
        return result;
    }

    @Override
    public Observable<ComRespInfo<List<Welfare>>> getRequestObservable(int page, int pageSize) {
        Log.e("http", "page=" + page);
        return HttpLoader.getWelfareInstance().welfareHttp().getWelfareList("福利", pageSize + "", page + "");
    }


}
