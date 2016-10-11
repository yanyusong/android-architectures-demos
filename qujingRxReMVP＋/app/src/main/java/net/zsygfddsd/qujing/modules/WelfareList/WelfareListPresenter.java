package net.zsygfddsd.qujing.modules.WelfareList;

import android.content.Context;

import net.zsygfddsd.qujing.base.module.network_recyclerview.BasePagePresenter;
import net.zsygfddsd.qujing.data.bean.ComRespInfo;
import net.zsygfddsd.qujing.data.bean.Welfare;
import net.zsygfddsd.qujing.data.http.HttpLoader;

import java.util.List;

import rx.Observable;

/**
 * Created by mac on 16/7/24.
 */
public class WelfareListPresenter extends BasePagePresenter<List<Welfare>, Welfare> implements WelfareListContract.Presenter {

    private WelfareListContract.View mView;

    public WelfareListPresenter(Context context, WelfareListContract.View mView) {
        super(context, mView);
        this.mView = mView;
        mView.setPresenter(this);
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
        return HttpLoader.getInstance().welfareHttp().getWelfareList("福利", pageSize + "", page + "");
    }

}
