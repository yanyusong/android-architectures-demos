package com.zsygfddsd.spacestation.common.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * <p/>
 * 分割线绘制规则,
 * 上下左右都出头,分割线要求完全不透明,不然交叉处会出现重叠
 */

public abstract class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    //    private Drawable mDrawable;

    private Paint mPaint;
    private int lineWidth;//px 分割线宽
    /**
     * A single color value in the form 0xAARRGGBB.
     **/
    private int colorRGB;

    private boolean isLastItemShowDivider = true;
    private boolean isLastItemShowTopDivider = true;

    public DividerGridItemDecoration(Context context, int lineWidthDp, @ColorInt int mColorRGB) {
        this.colorRGB = mColorRGB;
        this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(colorRGB);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public DividerGridItemDecoration(Context context, float lineWidthDp, @ColorInt int mColorRGB) {
        this.colorRGB = mColorRGB;
        this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(colorRGB);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //上下左右
        drawChildTopHorizontal(c, parent);
        drawChildBottomHorizontal(c, parent);
        drawChildLeftVertical(c, parent);
        drawChildRightVertical(c, parent);
    }

    public void drawChildBottomHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft() - params.leftMargin - lineWidth;
            int right = child.getRight() + params.rightMargin + lineWidth;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + lineWidth;

            if ((!isLastItemShowDivider && i == childCount - 1)) {

            } else {
                c.drawRect(left, top, right, bottom, mPaint);
            }

        }
    }

    //配置最后一个item的bottom是否显示分割线
    public DividerGridItemDecoration configLastItemShowDivider(boolean isLastItemShowDivider) {
        this.isLastItemShowDivider = isLastItemShowDivider;
        return this;
    }

    //配置position item的不显示分割线
    public DividerGridItemDecoration configLastItemShowTopDivider(boolean isLastItemShowTopDivider) {
        this.isLastItemShowTopDivider = isLastItemShowTopDivider;
        return this;
    }

    public void drawChildTopHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getLeft() - params.leftMargin - lineWidth;
            int right = child.getRight() + params.rightMargin + lineWidth;
            int bottom = child.getTop() - params.topMargin;
            int top = bottom - lineWidth;

            if ((!isLastItemShowTopDivider && i == childCount - 1)) {

            } else {
                c.drawRect(left, top, right, bottom, mPaint);
            }

        }
    }

    public void drawChildLeftVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin - lineWidth;
            int bottom = child.getBottom() + params.bottomMargin + lineWidth;
            int right = child.getLeft() - params.leftMargin;
            int left = right - lineWidth;
            c.drawRect(left, top, right, bottom, mPaint);

        }
    }

    public void drawChildRightVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getTop() - params.topMargin - lineWidth;
            int bottom = child.getBottom() + params.bottomMargin + lineWidth;
            int left = child.getRight() + params.rightMargin;
            int right = left + lineWidth;
            c.drawRect(left, top, right, bottom, mPaint);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //
        boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);

        int left = sideOffsetBooleans[0] ? lineWidth : 0;
        int top = sideOffsetBooleans[1] ? lineWidth : 0;
        int right = sideOffsetBooleans[2] ? lineWidth : 0;
        int bottom = sideOffsetBooleans[3] ? lineWidth : 0;

        outRect.set(left, top, right, bottom);
    }

    /**
     * 顺序:left, top, right, bottom
     *
     * @return boolean[4]
     */
    public abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);


}

















