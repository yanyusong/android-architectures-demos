package com.zsygfddsd.spacestation.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***********************************************
 * @类名 ：ViewHolder
 * @描述 ：在GeneralListAdapter中调用，用于ListView列表的ViewHolder缓存
 * @主要参数 ：
 * @主要接口 :
 * @作者 ：yanys
 * @日期 ：2015年7月10日上午10:16:11
 * @版本 ：1.0
 * @备注 ：无
 ***********************************************/
public class ViewHolder {
    private final SparseArray<View> ChildViews;  //视图中子View集合
    private View mConvertView;  //父视图
    private ViewGroup parent;

    private ViewHolder (Context context, ViewGroup parent, int layoutId, int position){
        this.ChildViews = new SparseArray<>(20);
        this.parent = parent;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * Create方法用于初始化ViewHolder
     *
     * @param context
     * @param convertView 父View
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder Create (Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if(convertView == null){
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过子视图的Id返回其View
     *
     * @param childViewId 控件的id
     * @return ChildView 视图view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getChildView (int childViewId){
        View view = ChildViews.get(childViewId);
        if(view == null){
            view = mConvertView.findViewById(childViewId);
            ChildViews.put(childViewId, view);
        }
        return (T) view;
    }

    /**
     * 父视图
     *
     * @return mConvertView
     */
    public View getView (){
        return mConvertView;
    }

    /**
     * ViewGroup
     *
     * @return parent
     */
    public ViewGroup getViewGroup (){
        return parent;
    }


}
