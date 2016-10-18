package net.zsygfddsd.qujing.modules.welfarelist;

import android.content.Context;

import net.zsygfddsd.qujing.base.common.ComRespInfo;
import net.zsygfddsd.qujing.base.module.network_recyclerview.BasePagePresenter;
import net.zsygfddsd.qujing.data.DataSource;
import net.zsygfddsd.qujing.data.bean.Welfare;

import java.util.List;

import rx.Observable;

/**
 * Created by mac on 16/7/24.
 */
public class WelfareListPresenter extends BasePagePresenter<List<Welfare>, Welfare> implements WelfareListContract.Presenter {

    private Context _context;
    private WelfareListContract.View _view;
    private DataSource.WelfareDataSource _repository;

//    @Inject
    public WelfareListPresenter(Context context, WelfareListContract.View mView, DataSource.WelfareDataSource mRepository) {
        super(context, mView);
        _context = context;
        _view = mView;
        _repository = mRepository;
        _view.setPresenter(this);
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
        return _repository.getWelfareList("福利", pageSize + "", page + "");
    }

}
