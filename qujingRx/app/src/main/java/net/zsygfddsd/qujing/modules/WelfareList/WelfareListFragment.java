package net.zsygfddsd.qujing.modules.WelfareList;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.trello.rxlifecycle.FragmentEvent;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.fragment.BaseRecyclerViewFragment;
import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.Welfare;
import net.zsygfddsd.qujing.common.utils.DeviceUtils;
import net.zsygfddsd.qujing.components.httpLoader.HttpLoader;
import net.zsygfddsd.qujing.components.httpLoader.RequestInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/5/12.
 */
public class WelfareListFragment extends BaseRecyclerViewFragment<Welfare> {
    @Override
    public void loadData(RequestInfo reqInfo, String pageSize, String page, boolean showDialog, boolean canCancel) {
        if (DeviceUtils.isHasNetWork()) {
            String type = reqInfo.getBodyParams().get("type");

            Observable<ComRespInfo<List<Welfare>>> observable = HttpLoader.getInstance().welfareHttp().getWelfareList(type, pageSize, page);
            observable.compose(this.<ComRespInfo<List<Welfare>>>bindUntilEvent(FragmentEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<ComRespInfo<List<Welfare>>>() {
                        @Override
                        public void onCompleted() {
                            if (isRefreshing()) {
                                completeRefreshing();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showToast("获取失败!");
                            if (isRefreshing()) {
                                completeRefreshing();
                            }
                        }

                        @Override
                        public void onNext(ComRespInfo<List<Welfare>> listComRespInfo) {
                            if (!listComRespInfo.isError()) {
                                setHasNextPage(true);
                                items.clear();
                                items = listComRespInfo.getResults();
                                if (isClear) {
                                    itemDatas.clear();
                                    isClear = false;
                                }
                                itemDatas.addAll(items);
                                updateData();
                                if (itemDatas.size() == 0) {
                                    // TODO: 16/1/6  在此显示无数据时的图片
                                }
                            } else {
                                showToast("获取失败!");
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
                Log.i("welfareImg", "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if (source.getWidth() < targetWidth) {
                    return source;
                } else {
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
    public void OnItemClicked(final Welfare itemData, final int position) {

//        Observable<Welfare> clickObservable = Observable.create(new Observable.OnSubscribe<Welfare>() {
//            @Override
//            public void call(Subscriber<? super Welfare> subscriber) {
//                try {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onStart();
//                        subscriber.onNext(itemData);
//                        subscriber.onCompleted();
//                    }
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
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





















