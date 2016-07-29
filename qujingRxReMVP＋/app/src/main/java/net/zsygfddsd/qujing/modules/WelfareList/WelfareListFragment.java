package net.zsygfddsd.qujing.modules.WelfareList;

import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.module.network_recyclerview.BaseRecyclerViewNetFragment;
import net.zsygfddsd.qujing.bean.Welfare;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by mac on 16/5/12.
 */
public class WelfareListFragment extends BaseRecyclerViewNetFragment<WelfareListContract.Presenter, Welfare> implements WelfareListContract.View {


    public static WelfareListFragment newInstance(@LayoutRes int itemLayoutId) {
        WelfareListFragment welfareListFragment = new WelfareListFragment();
        welfareListFragment.init(itemLayoutId);
        return welfareListFragment;
    }

    @Override
    public void setPresenter(WelfareListContract.Presenter presenter) {
        super.setPresenter(presenter);
    }

    @Override
    public void bindChildViewsData(GeneralRecyclerViewHolder holder, Object itemData, final int position) {
        final Welfare data = (Welfare) itemData;

        final ImageView welfareImg = holder.getChildView(R.id.iv_welfare);
        TextView welfareDec = holder.getChildView(R.id.tv_welfare_dec);
        if (!TextUtils.isEmpty(data.getUrl())) {
            //            Picasso.with(ct).load(itemData.getUrl())/*.transform(transformation).resize(ScreenUtils.getScreenWidth(ct), DensityUtils.dp2px(ct, 200f))*/.into(welfareImg);
            Picasso.with(ct).load(data.getUrl()).resize(200, 200).centerCrop().into(welfareImg);
        }
        welfareDec.setText(data.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemClicked(data, position);
            }
        });
    }

    public void OnItemClicked(final Welfare itemData, final int position) {

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





















