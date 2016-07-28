package net.zsygfddsd.qujing.base.module.network_recyclerview;


import android.content.Context;

import net.zsygfddsd.qujing.base.module.network.BaseNetPresenter;
import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.components.httpLoader.ObservableFactory;
import net.zsygfddsd.qujing.components.httpLoader.Subscriber.NetCheckerSubscriber;
import net.zsygfddsd.qujing.components.httpLoader.transformer.EmitBeforeAndAfterTransformer;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by mac on 16/6/11.
 * DATA:表示ComRespInfo<DATA> 中的DATA的bean
 * D:表示每一个item的bean
 */
public abstract class BasePagePresenter<DATA, D> extends BaseNetPresenter implements BasePageContract.IBaseRecyclerViewPresenter {

    private Context context;
    private BasePageContract.IBaseRecyclerView mView;

    private int page = 1;
    private int pageSize = PageConfig.PageSize;
    private volatile boolean isClear = false;//是否清空列表所有数据
    private volatile List<D> items = new ArrayList<>();// list中当前最新页的数据

    private DefaultLoadingDialogShowConfig defaultLoadingShowConfig;

    public BasePagePresenter(Context context, BasePageContract.IBaseRecyclerView mView) {
        super(mView);
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void start() {
        super.start();
        defaultLoadingShowConfig = getDefaultLoadingShowConfig();
    }

    public NetCheckerSubscriber getDefaultSubscriber() {
        return new NetCheckerSubscriber<ComRespInfo<DATA>>(context) {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ComRespInfo<DATA> dataComRespInfo) {
                if (!dataComRespInfo.isError()) {
                    boolean hasnext = getIsHasNextFromResponse(dataComRespInfo.getResults());
                    mView.setHasNextPage(hasnext);
                    if (hasnext) {
                        mView.showLoadMoreIndication();
                    } else {
                        mView.showScrolledToBottom();
                    }
                    items.clear();
                    items = getListFromResponse(dataComRespInfo.getResults());

                    if (isClear) {
                        mView.getItemDatas().clear();
                        isClear = false;
                    }
                    mView.getItemDatas().addAll(items);
                    mView.updateData();
                    if (mView.getItemDatas().size() == 0) {
                        mView.showEmptyPage();
                    }
                } else {
                    mView.showLoadingError();
                }
            }
        };
    }

    public DefaultLoadingDialogShowConfig getDefaultLoadingShowConfig() {
        return new DefaultLoadingDialogShowConfig(false, false, false);
    }

    public abstract boolean getIsHasNextFromResponse(DATA result);

    public abstract List<D> getListFromResponse(DATA result);

    public abstract Observable<ComRespInfo<DATA>> getRequestObservable(int page, int pageSize);

    public void loadData(Observable<ComRespInfo<DATA>> observable, boolean canShowLoading, boolean canLoadCelable) {
        NetCheckerSubscriber subscriber = getDefaultSubscriber();
        ObservableFactory.createNetObservable(context, observable, mView.getRxView())
                .compose(new EmitBeforeAndAfterTransformer<DATA>(mView, subscriber, canShowLoading, canLoadCelable))
                .subscribe(subscriber);
    }

    @Override
    public void onInitData() {
        page = 1;
        isClear = true;
        loadData(getRequestObservable(page, pageSize), defaultLoadingShowConfig.isInitShow, false);
    }

    @Override
    public void onLoadMore() {
        page++;
        isClear = false;
        loadData(getRequestObservable(page, pageSize), defaultLoadingShowConfig.isLoadMoreShow, false);
    }

    @Override
    public void onLoadRefresh() {
        page = 1;
        isClear = true;
        loadData(getRequestObservable(page, pageSize), defaultLoadingShowConfig.isRefreshShow, false);
    }

    class DefaultLoadingDialogShowConfig {

        boolean isInitShow = false;
        boolean isRefreshShow = false;
        boolean isLoadMoreShow = false;

        public DefaultLoadingDialogShowConfig() {
        }

        public DefaultLoadingDialogShowConfig(boolean isInitShow, boolean isRefreshShow, boolean isLoadMoreShow) {
            this.isInitShow = isInitShow;
            this.isRefreshShow = isRefreshShow;
            this.isLoadMoreShow = isLoadMoreShow;
        }
    }

}








































