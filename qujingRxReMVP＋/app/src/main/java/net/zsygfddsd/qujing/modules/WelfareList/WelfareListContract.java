package net.zsygfddsd.qujing.modules.WelfareList;

import net.zsygfddsd.qujing.base.module.network_recyclerview.BasePageContract;
import net.zsygfddsd.qujing.bean.Welfare;

/**
 * Created by mac on 16/7/24.
 */
public class WelfareListContract {

    interface View extends BasePageContract.IBaseRecyclerView<Presenter, Welfare> {

    }


    interface Presenter extends BasePageContract.IBaseRecyclerViewPresenter {


    }

}
