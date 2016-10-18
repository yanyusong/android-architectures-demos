package com.zsygfddsd.spacestation.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/***********************************************
 * @类名 ：抽象方法GeneralListAdapter
 * @描述 ：一个简化操作的ListView适配器
 * @主要参数 ：
 * @主要接口 :
 * @作者 ：yanys
 * @日期 ：2015年7月10日上午10:01:11
 * @版本 ：1.0
 * @备注 ：使用时定义一个自己的adapter类继承GeneralListAdapter
 ***********************************************/
public abstract class GeneralListAdapter<T> extends BaseAdapter {

    public Context ct;
    public List<T> data;
    private int itemLayoutId;

    /**
     * ListView的通用适配器
     *
     * @param ct           上下文
     * @param data         传入的列表数据封装，eg:List<HashMap<K, V>>;
     * @param itemLayoutId 传入的条目Item的布局ID
     */
    public GeneralListAdapter (Context ct, List<T> data, int itemLayoutId) {
        this.ct = ct;
        this.data = data;
        this.itemLayoutId = itemLayoutId;
    }

    public GeneralListAdapter (Context ct, int itemLayoutId) {
        this (ct, new ArrayList<T> (), itemLayoutId);
    }

    @Override
    public int getCount () {
        if (data == null) {
            return 0;
        }
        return data.size ();
    }

    @Override
    public T getItem (int position) {
        if (data.isEmpty ()) {
            return null;
        }
        return data.get (position);
    }

    @Override
    public long getItemId (int position) {
        if (data.isEmpty ()) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder = getViewHolder (position, convertView, parent);
        SetChildViewData (mViewHolder, data.get (position), position);
        return mViewHolder.getView ();
    }

    protected ViewHolder getViewHolder (int position, View convertView, ViewGroup parent) {
        return ViewHolder.Create (ct, convertView, parent, itemLayoutId, position);
    }

    public void update (List<T> data) {
        this.data = data;
        notifyDataSetChanged ();
    }

    /**
     * 将数据分发绑定给Item中的各个子View
     *
     * @param mViewHolder  ViewHolder帮助类
     * @param itemData     该Item的所有数据
     * @param position     该Item的position
     */
    public abstract void SetChildViewData (ViewHolder mViewHolder, T itemData, int position);

}
