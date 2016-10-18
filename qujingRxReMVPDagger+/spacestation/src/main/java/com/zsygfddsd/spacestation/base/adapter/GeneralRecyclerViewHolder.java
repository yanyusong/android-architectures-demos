package com.zsygfddsd.spacestation.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mac on 15/12/19.
 */
public class GeneralRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final View mItemView;
    private final SparseArray<View> childViews;
    private int childViewsCount;

    public GeneralRecyclerViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        this.childViews = new SparseArray<>(20);
    }

    public <T extends View> T getChildView(int childViewId) {
        View view = childViews.get(childViewId);
        if (view == null) {
            view = mItemView.findViewById(childViewId);
                childViews.put(childViewId, view);
        }
        return (T) view;
    }

    public void setText(int childViewId,String text){
        TextView textView = getChildView(childViewId);
        textView.setText(text);
    }

}
