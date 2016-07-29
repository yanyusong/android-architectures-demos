package net.zsygfddsd.qujing.common.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 正方形的相对布局，边长即为宽度，布局的的高度＝宽度
 */
public class SquareRelativeLayout extends RelativeLayout {

    public SquareRelativeLayout (Context context) {
        super (context);
        // TODO Auto-generated constructor stub
    }

    public SquareRelativeLayout (Context context, AttributeSet attrs) {
        super (context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SquareRelativeLayout (Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measureWidth (widthMeasureSpec);
        int measuredHeight = measuredWidth;
        setMeasuredDimension (measuredWidth, measuredHeight);
    }

    private int measureWidth (int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode (widthMeasureSpec);
        int specSize = MeasureSpec.getSize (widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            default:
                break;
        }
        return result;
    }


}
