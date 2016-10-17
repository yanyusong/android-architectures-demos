package net.zsygfddsd.qujing.modules.welfarelist;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.module.network_recyclerview.BaseRecyclerViewNetFragment;
import net.zsygfddsd.qujing.data.bean.Welfare;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by mac on 16/5/12.
 */
public class WelfareListFragment extends BaseRecyclerViewNetFragment<WelfareListContract.Presenter> implements WelfareListContract.View {


    private WelfareListContract.Presenter mPresenter;

    public static WelfareListFragment newInstance(@LayoutRes int itemLayoutId) {
        WelfareListFragment welfareListFragment = new WelfareListFragment();
        welfareListFragment.init(itemLayoutId);
        return welfareListFragment;
    }

    @Override
    public void setPresenter(WelfareListContract.Presenter presenter) {
        super.setPresenter(presenter);
        mPresenter = presenter;
    }

    @Override
    public void bindChildViewsData(GeneralRecyclerViewHolder holder, Object itemData, final int position) {
        final Welfare data = (Welfare) itemData;

        final ImageView welfareImg = holder.getChildView(R.id.iv_welfare);
        TextView welfareDec = holder.getChildView(R.id.tv_welfare_dec);
        if (!TextUtils.isEmpty(data.getUrl())) {
            //            Transformation transformation = new Transformation() {
            //                @Override
            //                public Bitmap transform(Bitmap source) {
            //                    int targetWidth = welfareImg.getWidth();
            //                    Log.i("welfareImg", "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);
            //
            //                    if (source.getWidth() == 0) {
            //                        return source;
            //                    }
            //
            //                    //如果图片小于设置的宽度，则返回原图
            //                    if (source.getWidth() < targetWidth) {
            //                        return source;
            //                    } else {
            //                        //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
            //                        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            //                        int targetHeight = (int) (targetWidth * aspectRatio);
            //                        if (targetHeight != 0 && targetWidth != 0) {
            //                            //                            Bitmap result = BitmapUtils.compressBitmapWidthAndHeight(source, targetWidth, -1);
            //                            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            //                            if (result != source) {
            //                                // Same bitmap is returned if sizes are the same
            //                                source.recycle();
            //                            }
            //                            return result;
            //                        } else {
            //                            return source;
            //                        }
            //                    }
            //                }
            //
            //                @Override
            //                public String key() {
            //                    return "transformation" + " desiredWidth";
            //                }
            //            };
            //            Picasso.with(ct).load(data.getUrl()).transform(transformation)/*.resize(ScreenUtils.getScreenWidth(ct), DensityUtils.dp2px(ct, 200f))*/.into(welfareImg);
            Picasso.with(ct).load(data.getUrl()).resize(800, 800).centerCrop().into(welfareImg);
        }
        welfareDec.setText(data.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(data, position);
            }
        });
    }

    public void onItemClicked(final Welfare itemData, final int position) {

        Observable.just(position)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "当前点击的位置是" + position;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToast(s);
                    }
                });
    }


}





















