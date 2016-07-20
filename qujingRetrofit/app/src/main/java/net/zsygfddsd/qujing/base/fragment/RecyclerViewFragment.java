package net.zsygfddsd.qujing.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerAdapter;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 15/12/19.
 */
public abstract class RecyclerViewFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected static String Tag_footer_text = "footertext";
    protected static final String ITEM_LAYOUT_ID = "itemLayoutId";

    protected SwipeRefreshLayout refreshView;
    protected RecyclerView recyclerView;
    protected GeneralRecyclerAdapter adapter;
    protected List<T> itemDatas = new ArrayList<>();
    protected int itemLayoutId = android.R.layout.simple_list_item_1;// item的布局id,默认是只有一个textview
    protected int headViewLayoutId = -1;//header布局id
    protected HashMap<String, Object> headerData = new HashMap<>();//headerview的数据
    protected HashMap<String, Object> footerData = new HashMap<>();//footerview的数据
    protected boolean hasNextPage = true;//是否还有下一页数据

    private boolean canLoadMore = true;
    private int loadOffset = 2;//设置滚动到倒数第几个时开始加载下一页，默认是倒数第2个


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_com_recyclerview, null);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.com_refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.com_recyclerView);
        //        refreshView.setColorSchemeResources();
        //        recyclerView.setHasFixedSize(true);//如果item大小不会因为内容变化而变化，则设为true，提高绘制效率
        recyclerView.setLayoutManager(new LinearLayoutManager(ct, LinearLayout.VERTICAL, false));
        initRecyclerView(recyclerView);
        refreshView.setOnRefreshListener(this);
        return view;
    }

    //    public void setLoadOffset(int mLoadOffset) {
    //        this.loadOffset = mLoadOffset;
    //    }

    @Override
    public void initData(Bundle savedInstanceState) {

        adapter = initAdapter(ct, itemDatas, itemLayoutId);
        if (adapter == null) {
            iHeaderViewSetting = initHeadViewSetting();
            if (iHeaderViewSetting != null) {
                headViewLayoutId = iHeaderViewSetting.getHeadViewLayoutId();
                headerData = iHeaderViewSetting.getHeadViewData();
            } else {
                headViewLayoutId = -1;
                headerData = null;
            }
            footerData.put(Tag_footer_text, "正在加载中...");
            adapter = new RecyclerViewAdapter(ct, itemLayoutId, itemDatas, headViewLayoutId, headerData, R.layout.item_recycler_bottom_view, footerData);
        }
        recyclerView.setAdapter(adapter);
        onInitData();

        canLoadMore = getCanLoadMore();
        if (canLoadMore) {

            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            final int[] lastVisibleItemPos = new int[1];

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                 @Override
                                                 public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                     super.onScrollStateChanged(recyclerView, newState);
                                                     Log.e("scroll", "Scroll State--------" + newState);
//                                                     if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
//                                                         lastVisibleItemPos[0] = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
//                                                     }
//                                                     int totalCount = layoutManager.getItemCount();
//                                                     if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos[0] == totalCount - 1) {
//                                                         if (isHasNextPage()) {
//                                                             footerData.put(Tag_footer_text, "正在加载中...");
//                                                             adapter.notifyFooterDataChanged();
//                                                             onLoadMore();
//                                                         } else {
//                                                             footerData.put(Tag_footer_text, "您已滚动到最底部了");
//                                                             adapter.notifyFooterDataChanged();
//                                                         }
//                                                     }
                                                 }

                                                 @Override
                                                 public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                     super.onScrolled(recyclerView, dx, dy);
                                                     Log.e("scroll", "Scroll vertically--------" + dy);
                                                     if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
                                                         lastVisibleItemPos[0] = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                                                     }

                                                     int totalCount = layoutManager.getItemCount();
                                                     if (lastVisibleItemPos[0] == totalCount - 1) {
                                                         if (isHasNextPage()) {
//                                                             footerData.put(Tag_footer_text, "正在加载中...");
//                                                             adapter.notifyFooterDataChanged();
//                                                             onLoadMore();

                                                             updateFooterHandler.postDelayed(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     footerData.put(Tag_footer_text, "正在加载中...");
                                                                     adapter.notifyFooterDataChanged();
                                                                     onLoadMore();
                                                                 }
                                                             },0);

                                                         } else {
                                                             updateFooterHandler.postDelayed(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     footerData.put(Tag_footer_text, "您已滚动到最底部了");
                                                                     adapter.notifyFooterDataChanged();
                                                                 }
                                                             },0);

                                                         }
                                                     }
                                                 }
                                             }
            );

        } else {
            updateFooterHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    footerData.put(Tag_footer_text, "您已滚动到最底部了");
                    adapter.notifyFooterDataChanged();
                }
            },0);

        }
    }

    Handler updateFooterHandler = new Handler();
    
    protected void changeFooterText(boolean isToBottom){
        if (isToBottom) {
            footerData.put(Tag_footer_text, "您已滚动到最底部了");
            adapter.notifyFooterDataChanged();
        }else{
            footerData.put(Tag_footer_text, "正在加载中...");
            adapter.notifyFooterDataChanged();
        }
    }    /*******
     * headerview -start
     **************/
    public interface IHeaderViewSetting {
        int getHeadViewLayoutId();

        HashMap<String, Object> getHeadViewData();

        void onBindHeadViewData(GeneralRecyclerViewHolder headViewHolder, HashMap<String, Object> headerData);
    }

    private IHeaderViewSetting iHeaderViewSetting = null;

    /**
     * 若要添加一个headerview则重写此方法
     */
    public IHeaderViewSetting initHeadViewSetting() {
        return null;
    }

    /*******
     * headerview -end
     **************/

    /*******
     * footerview -start
     **************/
    // TODO: 16/3/3

    /*******
     * footerview -end
     **************/

    /**
     * 设置是否可以加载更多，默认true可以加载，
     * 重写它改false，没有加载更多功能
     *
     * @return
     */
    protected boolean getCanLoadMore() {
        return true;
    }

    @Override
    public void onRefresh() {
        onLoadRefresh();
        if (canLoadMore) {
            footerData.put(Tag_footer_text, "正在加载中...");
            adapter.notifyFooterDataChanged();
        } else {
            footerData.put(Tag_footer_text, "您已滚动到最底部了");
            adapter.notifyFooterDataChanged();
        }
    }

    /**
     * 若想改变RecyclerView的某些属性，只需重写此方法
     *
     * @param mRecyclerView 该fragment中默认的RecyclerView
     */
    public void initRecyclerView(RecyclerView mRecyclerView) {

    }

    /**
     * 得到当前列表中数据的数目
     *
     * @return
     */
    public int getItemDatasCount() {
        return itemDatas.size();
    }

    /**
     * 若想改变listview的adapter，只需重写此方法
     *
     * @param ct
     * @param data
     * @param itemLayoutId
     * @return BaseAdapter
     */
    public GeneralRecyclerAdapter initAdapter(Context ct, List<T> data, int itemLayoutId) {

        return null;
    }
    /**
     * 模拟手下拉，自动刷新
     * 要在oncreate方法中调用必须开一个子线程
     */
    public void performAutoRefresh() {
        refreshView.setRefreshing(true);
        onRefresh();
    }
    /*
     * 列表上的操作－》更新界面列表
    */
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    public void updateData(List<T> datas) {
        itemDatas = datas;
        adapter.notifyDataSetChanged();
    }

    public void updateData(int position) {
        adapter.notifyItemChanged(position);
    }

    /**
     * 是否还有下一页
     *
     * @return
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * 设置是否还有下一页
     *
     * @param hasNextPage
     */
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void completeRefreshing() {
        refreshView.setRefreshing(false);
    }
    public boolean isRefreshing(){
        return refreshView.isRefreshing();
    }

    private class RecyclerViewAdapter extends GeneralRecyclerAdapter<T> {

        public RecyclerViewAdapter(Context ct, int itemLayoutId, List<T> itemDatas, int headerLayoutId, HashMap<String, Object> headerData, int footerLayoutId, HashMap<String, Object> footerData) {
            super(ct, itemLayoutId, itemDatas, headerLayoutId, headerData, footerLayoutId, footerData);
            //            this.imageOptions = initImageOptions();
        }

        //        public ImageOptions initImageOptions() {
        //            return new ImageOptions.Builder()
        //                    //				.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
        //                    //				.setRadius(DensityUtil.dip2px(5))
        //                    .setCrop(true)
        //                            // 加载中或错误图片的ScaleType
        //                            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
        //                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        //                    .setLoadingDrawableId(R.mipmap.ic_launcher)
        //                    .setFailureDrawableId(R.mipmap.ic_launcher)
        //                    .build();
        //        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
                            return gridManager.getSpanCount();
                        } else if (getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal()) {
                            if ((getContentItemCount() % gridManager.getSpanCount()) == 0) {
                                return gridManager.getSpanCount();
                            } else {
                                return 1;
                            }
                        } else {
                            return 1;
                        }
                    }
                });
            }
        }

        @Override
        public void onBindChildViewData(GeneralRecyclerViewHolder holder, final T itemData, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemClicked(itemData, position);
                }
            });
            bindChildViewsData(holder, itemData, position/*, imageOptions*/);
        }

        @Override
        public void onBindHeadViewData(GeneralRecyclerViewHolder headViewHolder, HashMap<String, Object> headerData) {
            if (iHeaderViewSetting != null) {
                iHeaderViewSetting.onBindHeadViewData(headViewHolder, headerData);
            }
        }

        @Override
        public void onBindFootViewData(GeneralRecyclerViewHolder footViewHolder, HashMap<String, Object> footerData) {
            TextView textview = footViewHolder.getChildView(R.id.item_bottom_text);
            if (itemDatas.size() > 13) {
                String text = (String) footerData.get(Tag_footer_text);
                textview.setText(text);
            } else {
                textview.setText("");
            }

        }
    }


    /**
     * 第一页的数据加载
     */
    public abstract void onInitData();

    /**
     * 加载更多
     */
    public abstract void onLoadMore();

    /**
     * 下拉刷新
     */
    public abstract void onLoadRefresh();

    /**
     * 给Item布局的各个控件设置分配好的数据
     *
     * @param mViewHolder item的holder，利用getChildView(eg:控件id)的方法得到该控件
     * @param itemData    封装好的分配给该item的数据，数据一般为Hashmap<K,V>或者Modle等类型
     * @param position    当前item的position
     *                    //     * @param imageOptions 定义好的xUtils中的图片加载工具的配置
     */
    public abstract void bindChildViewsData(GeneralRecyclerViewHolder mViewHolder, T itemData,
                                            int position/*, ImageOptions imageOptions*/);

    /*
    * item的点击事件
    * @param parent
    * @param itemDatas
    * @param view
    * @param position
    * @param id
    */
    public abstract void OnItemClicked(T itemData, int position);


}
















