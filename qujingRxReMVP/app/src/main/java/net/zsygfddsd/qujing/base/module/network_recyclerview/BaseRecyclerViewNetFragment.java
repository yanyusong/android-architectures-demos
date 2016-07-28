package net.zsygfddsd.qujing.base.module.network_recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerAdapter;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.module.network.BaseNetFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 15/12/19.
 * T: 是IBaseRecyclerViewPresenter
 * D: 是item的bean
 */
public abstract class BaseRecyclerViewNetFragment<T extends BasePageContract.IBaseRecyclerViewPresenter, D> extends BaseNetFragment<T> implements BasePageContract.IBaseRecyclerView<T, D>, SwipeRefreshLayout.OnRefreshListener {

    protected static String Tag_footer_text = "footertext";
    protected static final String ITEM_LAYOUT_ID = "itemLayoutId";
    private static final String REQUEST_TAG = "reqTag";

    protected SwipeRefreshLayout refreshView;
    protected RecyclerView recyclerView;
    protected GeneralRecyclerAdapter adapter;
    private T mPresenter;
    public List<D> itemDatas = new ArrayList<>();

    private String reqTag = "BaseRecyclerFrag";
    protected int itemLayoutId = android.R.layout.simple_list_item_1;// item的布局id,默认是只有一个textview

    protected int headViewLayoutId = -1;//header布局id
    protected HashMap<String, Object> headerData = new HashMap<>();//headerview的数据
    protected HashMap<String, Object> footerData = new HashMap<>();//footerview的数据
    protected boolean hasNextPage = true;//是否还有下一页数据

    private boolean canLoadMore = true;
    private int loadOffset = 2;//设置滚动到倒数第几个时开始加载下一页，默认是倒数第2个

    protected Bundle data2Bundle(int itemLayoutId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_LAYOUT_ID, itemLayoutId);
        return bundle;
    }

    protected void init(int itemLayoutId) {
        Bundle bundle = data2Bundle(itemLayoutId);
        setArguments(bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.itemLayoutId = args.getInt(ITEM_LAYOUT_ID) == -1 ? android.R.layout.simple_list_item_1 : args.getInt(ITEM_LAYOUT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    private View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private void initData(Bundle savedInstanceState) {

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
            footerData.put(Tag_footer_text, "");
            adapter = new RecyclerViewAdapter(ct, itemLayoutId, itemDatas, headViewLayoutId, headerData, R.layout.item_recycler_bottom_view, footerData);
        }
        recyclerView.setAdapter(adapter);

        canLoadMore = getCanLoadMore();

        onInitData();

        if (canLoadMore) {

            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            final int[] lastVisibleItemPos = new int[1];

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                 @Override
                                                 public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                                     super.onScrollStateChanged(recyclerView, newState);
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
                                                     if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
                                                         lastVisibleItemPos[0] = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                                                     }

                                                     int totalCount = layoutManager.getItemCount();
                                                     if (lastVisibleItemPos[0] == totalCount - 1) {
                                                         if (isHasNextPage()) {

                                                             updateFooterHandler.postDelayed(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     showLoadMoreIndication();
                                                                     onLoadMore();
                                                                 }
                                                             }, 0);

                                                         } else {
                                                             updateFooterHandler.postDelayed(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     showScrolledToBottom();
                                                                 }
                                                             }, 0);

                                                         }
                                                     }
                                                 }
                                             }
            );

        } else {
            updateFooterHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showScrolledToBottom();
                }
            }, 0);

        }
    }

    Handler updateFooterHandler = new Handler();

    @CallSuper
    @Override
    public void setPresenter(T presenter) {
        super.setPresenter(presenter);
        mPresenter = presenter;
    }

    /*******
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
            showLoadMoreIndication();
        } else {
            showScrolledToBottom();
        }
    }

    @Override
    public void showRefreshIndication() {
        if (!refreshView.isRefreshing()) {
            refreshView.setRefreshing(true);
        }
    }

    @Override
    public void hideRefreshInfication() {
        if (refreshView.isRefreshing()) {
            refreshView.setRefreshing(false);
        }
    }

    @Override
    public void showLoadMoreIndication() {
        footerData.put(Tag_footer_text, "正在加载中...");
        adapter.notifyFooterDataChanged();
    }

    @Override
    public void hideLoadMoreIndication() {

    }

    public void clearFooterData() {
        footerData.put(Tag_footer_text, "");
        adapter.notifyFooterDataChanged();
    }

    @Override
    public void showScrolledToBottom() {
        footerData.put(Tag_footer_text, "您已滚动到最底部了");
        adapter.notifyFooterDataChanged();
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
    public GeneralRecyclerAdapter initAdapter(Context ct, List<D> data, int itemLayoutId) {

        return null;
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<D> getItemDatas() {
        return itemDatas;
    }

    @Override
    public void updateData(List<D> itemdatas) {
        itemDatas = itemdatas;
        adapter.notifyDataSetChanged();
    }

    @Override
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
     * @param hasNext
     */
    @Override
    public void setHasNextPage(boolean hasNext) {
        this.hasNextPage = hasNext;
    }

    private class RecyclerViewAdapter extends GeneralRecyclerAdapter<D> {

        public RecyclerViewAdapter(Context ct, int itemLayoutId, List<D> itemDatas, int headerLayoutId, HashMap<String, Object> headerData, int footerLayoutId, HashMap<String, Object> footerData) {
            super(ct, itemLayoutId, itemDatas, headerLayoutId, headerData, footerLayoutId, footerData);
        }

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
        public void onBindChildViewData(GeneralRecyclerViewHolder holder, final D itemData, final int position) {
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
    public void onInitData() {
        mPresenter.onInitData();
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        mPresenter.onLoadMore();
    }

    /**
     * 下拉刷新
     */
    public void onLoadRefresh() {
        mPresenter.onLoadRefresh();
    }

    /**
     * 给Item布局的各个控件设置分配好的数据
     *
     * @param mViewHolder item的holder，利用getChildView(eg:控件id)的方法得到该控件
     * @param itemData    封装好的分配给该item的数据，数据一般为Hashmap<K,V>或者Modle等类型
     * @param position    当前item的position
     *                    //     * @param imageOptions 定义好的xUtils中的图片加载工具的配置
     */
    public abstract void bindChildViewsData(GeneralRecyclerViewHolder mViewHolder, D itemData,
                                            int position);

    /*
    * item的点击事件
    * @param parent
    * @param itemDatas
    * @param view
    * @param position
    * @param id
    */
    public abstract void OnItemClicked(D itemData, int position);


}
















