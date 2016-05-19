package net.zsygfddsd.qujing.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 16/1/6.
 */
public abstract class GeneralRecyclerAdapter<T> extends RecyclerView.Adapter<GeneralRecyclerViewHolder> {

    protected Context ct;
    private int itemLayoutId;
    private List<T> itemDatas;
    private int headerLayoutId;
    private HashMap<String, Object> headerData;
    private int footerLayoutId;
    private HashMap<String, Object> footerData;
    private LayoutInflater mLayoutInflater;
    protected int mHeaderCount;//头部View个数
    protected int mFooterCount;//底部View个数

    public enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CONTENT,
        ITEM_TYPE_BOTTOM
    }

    //    public GeneralRecyclerAdapter(Context ct, int itemLayoutId, List<T> itemDatas, View headerView, HashMap<String, Object> headerData, View footerView, HashMap<String, Object> footerData) {
    //        this.ct = ct;
    //        LayoutInflater mLayoutInflater = LayoutInflater.from(ct);
    //        this.itemView = mLayoutInflater.inflate(itemLayoutId, null);
    //        this.itemDatas = itemDatas;
    //        this.headerView = headerView;
    //        this.headerData = headerData;
    //        this.footerView = footerView;
    //        this.footerData = footerData;
    //    }

    public GeneralRecyclerAdapter (Context ct, int itemLayoutId, List<T> itemDatas, int headerLayoutId, HashMap<String, Object> headerData, int footerLayoutId, HashMap<String, Object> footerData) {
        this.ct = ct;
        this.itemLayoutId = itemLayoutId;
        this.itemDatas = itemDatas;
        this.headerLayoutId = headerLayoutId;
        this.headerData = headerData;
        this.footerLayoutId = footerLayoutId;
        this.footerData = footerData;
        mLayoutInflater = LayoutInflater.from (ct);

    }

    public GeneralRecyclerAdapter (Context ct, int itemLayoutId, List<T> itemDatas) {
        this (ct, itemLayoutId, itemDatas, -1, null, -1, null);
    }

    @Override
    public GeneralRecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal ()) {
            return new GeneralRecyclerViewHolder (mLayoutInflater.inflate (itemLayoutId, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal ()) {
            return new GeneralRecyclerViewHolder (mLayoutInflater.inflate (headerLayoutId, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal ()) {
            return new GeneralRecyclerViewHolder (mLayoutInflater.inflate (footerLayoutId, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder (GeneralRecyclerViewHolder holder, int position) {
        if (holder != null) {
            if (getItemViewType (position) == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal ()) {
                T itemData = itemDatas.get (position - getHeadItemCount ());
                onBindChildViewData (holder, itemData, position);
            } else if (getItemViewType (position) == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal ()) {
                onBindFootViewData (holder, footerData);
            } else if (getItemViewType (position) == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal ()) {
                onBindHeadViewData (holder, headerData);
            }
        }
    }

    @Override
    public int getItemViewType (int position) {

        int dataItemCount = getContentItemCount ();
        if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal ();
        } else if (mFooterCount != 0 && position >= (mHeaderCount + dataItemCount)) {//底部View
            return ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal ();
        } else {
            return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal ();
        }
    }

    @Override
    public int getItemCount () {
        return getHeadItemCount () + getContentItemCount () + getFootItemCount ();
    }

    public void notifyHeaderDataChanged () {
        if (mHeaderCount > 0) {
            notifyItemChanged (1);
        }
    }

    public void notifyFooterDataChanged () {
        if (mFooterCount > 0) {
            notifyItemChanged (getHeadItemCount () + getContentItemCount ());
        }
    }

    //获取中间内容个数
    public int getContentItemCount () {
        return itemDatas.size ();
    }

    public int getHeadItemCount () {
        if (headerLayoutId == -1) {
            mHeaderCount = 0;
        } else {
            mHeaderCount = 1;
        }
        return mHeaderCount;
    }

    public int getFootItemCount () {
        if (footerLayoutId == -1) {
            mFooterCount = 0;
        } else {
            mFooterCount = 1;
        }
        mFooterCount = 1;
        return mFooterCount;
    }

    public abstract void onBindChildViewData (GeneralRecyclerViewHolder holder, T itemData, int position);

    public abstract void onBindHeadViewData (GeneralRecyclerViewHolder headViewHolder, HashMap<String, Object> headerData);

    public abstract void onBindFootViewData (GeneralRecyclerViewHolder footViewHolder, HashMap<String, Object> footerData);

}
