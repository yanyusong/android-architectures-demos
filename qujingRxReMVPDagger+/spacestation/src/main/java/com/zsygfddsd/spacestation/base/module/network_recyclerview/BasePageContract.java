package com.zsygfddsd.spacestation.base.module.network_recyclerview;


import com.zsygfddsd.spacestation.base.adapter.multirecycler.ItemEntityList;
import com.zsygfddsd.spacestation.base.module.base.BaseContract;
import com.zsygfddsd.spacestation.base.module.network.BaseNetContract;

/**
 * Created by mac on 16/6/11.
 */
public class BasePageContract {

    public interface IBaseRecyclerView<T extends IBaseRecyclerViewPresenter> extends BaseNetContract.IBaseNetView<T> {

        void setHasNextPage(boolean hasNext);

        int getItemLayoutId();

        int getBottomViewLayoutId();

        void setRefreshEnable(boolean enable);

        ItemEntityList getItemEntityList();

        //        List<D> getItemDatas();

        void updateData();
        //
        //        void updateData(List<D> itemdatas);
        //
        //        void updateData(int position);

        void showRefreshIndication();

        void hideRefreshInfication();

    }

    public interface IBaseRecyclerViewPresenter extends BaseContract.IBasePresenter {

        void onInitData();

        void onLoadMore();

        void onLoadRefresh();

    }

}
