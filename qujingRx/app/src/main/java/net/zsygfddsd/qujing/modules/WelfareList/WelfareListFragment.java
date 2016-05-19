package net.zsygfddsd.qujing.modules.WelfareList;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.fragment.BaseRecyclerViewFragment;
import net.zsygfddsd.qujing.bean.PageModel;
import net.zsygfddsd.qujing.bean.Welfare;

import java.util.List;

/**
 * Created by mac on 16/5/12.
 */
public class WelfareListFragment extends BaseRecyclerViewFragment<Welfare> {



    @Override
    public List<Welfare> handleData(PageModel pageModel) {
        List<Welfare> welfareList = JSON.parseArray(pageModel.getList(), Welfare.class);
        return welfareList;
    }

    @Override
    public void bindChildViewsData(GeneralRecyclerViewHolder mViewHolder, Welfare itemData, int position) {
        final ImageView welfareImg = mViewHolder.getChildView(R.id.iv_welfare);
        TextView welfareDec = mViewHolder.getChildView(R.id.tv_welfare_dec);

        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = welfareImg.getWidth();
                Log.i("welfareImg","source.getHeight()="+source.getHeight()+",source.getWidth()="+source.getWidth()+",targetWidth="+targetWidth);

                if(source.getWidth()==0){
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if(source.getWidth()<targetWidth){
                    return source;
                }else{
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
        if (!TextUtils.isEmpty(itemData.getUrl())) {
            Picasso.with(ct).load(itemData.getUrl()).transform(transformation)/*.resize(ScreenUtils.getScreenWidth(ct), DensityUtils.dp2px(ct, 200f))*/.into(welfareImg);
        }
        welfareDec.setText(itemData.getDesc());
    }

    @Override
    public void OnItemClicked(Welfare itemData, int position) {

    }
}
