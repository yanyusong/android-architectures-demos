package net.zsygfddsd.qujing.base.module.network_recyclerview;


import net.zsygfddsd.qujing.base.adapter.multirecycler.ItemEntityList;
import net.zsygfddsd.qujing.base.module.base.BaseContract;
import net.zsygfddsd.qujing.base.module.network.BaseNetContract;

/**
 * Created by mac on 16/6/11.
 */
public class BasePageContract {

    public interface IBaseRecyclerView<T extends IBaseRecyclerViewPresenter, D> extends BaseNetContract.IBaseNetView<T> {

        void setHasNextPage(boolean hasNext);

        int getItemLayoutId();

        int getBottomViewLayoutId();

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
