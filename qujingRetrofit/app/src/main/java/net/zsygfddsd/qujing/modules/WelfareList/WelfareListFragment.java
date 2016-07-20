package net.zsygfddsd.qujing.modules.WelfareList;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.fragment.BaseRecyclerViewFragment;
import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.Welfare;
import net.zsygfddsd.qujing.common.utils.DeviceUtils;
import net.zsygfddsd.qujing.components.httpLoader.HttpLoader;
import net.zsygfddsd.qujing.components.httpLoader.RequestInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 16/5/12.
 */
public class WelfareListFragment extends BaseRecyclerViewFragment<Welfare> {
    @Override
    public void loadData(RequestInfo reqInfo, String pageSize, String page, boolean showDialog, boolean canCancel) {
        if (DeviceUtils.isHasNetWork()) {
            String type = reqInfo.getBodyParams().get("type");
            Call<ComRespInfo<List<Welfare>>> call = HttpLoader.getInstance().welfareHttp().getWelfareList(type, pageSize, page);
            call.enqueue(new Callback<ComRespInfo<List<Welfare>>>() {
                @Override
                public void onResponse(Call<ComRespInfo<List<Welfare>>> call, Response<ComRespInfo<List<Welfare>>> response) {
                    if (response.isSuccessful()) {
                        setHasNextPage(true);
                        items.clear();
                        items = response.body().getResults();
                        if (isClear) {
                            itemDatas.clear();
                            isClear = false;
                        }
                        itemDatas.addAll(items);
                        updateData();
                        if (itemDatas.size() == 0) {
                            // TODO: 16/1/6  在此显示无数据时的图片
                        }
                    }else{
                        showToast("获取失败!");
                    }

                    if (isRefreshing()) {
                        completeRefreshing();
                    }
                }

                @Override
                public void onFailure(Call<ComRespInfo<List<Welfare>>> call, Throwable t) {
                    showToast("获取失败!");
                    if (isRefreshing()) {
                        completeRefreshing();
                    }
                }
            });
        } else {
            if (isRefreshing()) {
                completeRefreshing();
            }
            showToast("请检查网络连接");
        }
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
