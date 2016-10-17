package net.zsygfddsd.qujing.base.adapter.multirecycler;


import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;

/**
 * Created by mac on 16/6/18.
 */
public interface OnBind<T> {

    /**
     * @param holder
     * @param position
     */
    void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position);
}
