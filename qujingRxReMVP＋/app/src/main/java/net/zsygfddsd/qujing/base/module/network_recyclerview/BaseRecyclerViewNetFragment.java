package net.zsygfddsd.qujing.base.module.network_recyclerview;

import android.os.Bundle;
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

import net.zsygfddsd.qujing.R;
import net.zsygfddsd.qujing.base.adapter.GeneralRecyclerViewHolder;
import net.zsygfddsd.qujing.base.adapter.multirecycler.ItemEntityList;
import net.zsygfddsd.qujing.base.adapter.multirecycler.MultiRecyclerAdapter;
import net.zsygfddsd.qujing.base.adapter.multirecycler.OnBind;
import net.zsygfddsd.qujing.base.module.network.BaseNetFragment;
import net.zsygfddsd.qujing.common.widgets.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/12/19.
 * T: 是IBaseRecyclerViewPresenter
 * D: 是item的bean
 */
public abstract class BaseRecyclerViewNetFragment<T extends BasePageContract.IBaseRecyclerViewPresenter, D> extends BaseNetFragment<T> implements BasePageContract.IBaseRecyclerView<T, D>, SwipeRefreshLayout.OnRefreshListener {

    protected static final String ITEM_LAYOUT_ID = "itemLayoutId";

    protected SwipeRefreshLayout refreshView;
    protected RecyclerView recyclerView;
    protected MultiRecyclerAdapter adapter;
    private T mPresenter;
    public List<D> itemDatas = new ArrayList<>();
    private ItemEntityList itemEntityList = new ItemEntityList();

    protected int itemLayoutId = android.R.layout.simple_list_item_1;// item的布局id,默认是只有一个textview
    protected int bottomItemLayoutId = android.R.layout.simple_list_item_1;// item的布局id,默认是只有一个textview

    protected boolean hasNextPage = true;//是否还有下一页数据

    private boolean canLoadMore = true;
    private int loadOffset = 2;//设置滚动到倒数第几个时开始加载下一页，默认是倒数第2个
    private GridLayoutManager layoutManager;

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
        this.bottomItemLayoutId = getBottomViewLayoutId();
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
        //                recyclerView.setLayoutManager(new LinearLayoutManager(ct, LinearLayout.VERTICAL, false));
        layoutManager = new GridLayoutManager(ct, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(ct, 1, 0xFF000000) {
            @Override
            public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {

                boolean[] temp = {true, true, true, true};

                if (itemPosition == itemEntityList.getItemCount() - 1) {
                    temp[0] = false;
                    temp[1] = false;
                    temp[2] = false;
                    temp[3] = false;
                } else {
                    switch (itemPosition % 4) {
                        case 0:
                            ;
                        case 1:
                            ;
                        case 2:
                            temp[0] = false;
                            temp[3] = false;
                            break;
                        case 3:
                            temp[0] = false;
                            temp[2] = false;
                            temp[3] = false;
                            break;
                        default:
                            break;
                    }
                }

                return temp;
            }
        });
        initRecyclerView(recyclerView);
        refreshView.setOnRefreshListener(this);
        return view;
    }

    private void initData(Bundle savedInstanceState) {
        itemEntityList
                .addOnBind(itemLayoutId, new OnBind() {
                    @Override
                    public void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position) {
                        bindChildViewsData(holder, itemData, position);
                    }
                })
                .addOnBind(bottomItemLayoutId, new OnBind() {
                    @Override
                    public void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position) {
                        if (position + 1 > PageConfig.PageSize) {
                            if (hasNextPage) {
                                holder.setText(R.id.item_bottom_text, "正在加载中...");
                            } else {
                                holder.setText(R.id.item_bottom_text, "您已滚动到最底部了");
                            }
                        } else {
                            holder.setText(R.id.item_bottom_text, "");
                        }
                    }
                });
        adapter = new MultiRecyclerAdapter(ct, itemEntityList);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == itemLayoutId ? 1 : 4;
            }
        });
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
                                                             //加载下一页
                                                             onLoadMore();
                                                         } else {

                                                         }
                                                     }
                                                 }
                                             }
            );

        }
    }

    @CallSuper
    @Override
    public void setPresenter(T presenter) {
        super.setPresenter(presenter);
        mPresenter = presenter;
    }

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

    /**
     * 若想改变RecyclerView的某些属性，只需重写此方法
     *
     * @param mRecyclerView 该fragment中默认的RecyclerView
     */
    public void initRecyclerView(RecyclerView mRecyclerView) {

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

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public ItemEntityList getItemEntityList() {
        return itemEntityList;
    }

    @Override
    public int getItemLayoutId() {
        return itemLayoutId;
    }

    @Override
    public int getBottomViewLayoutId() {
        return R.layout.item_recycler_bottom_view;
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
     * @param holder   item的holder，利用getChildView(eg:控件id)的方法得到该控件
     * @param itemData 封装好的分配给该item的数据，数据一般为Hashmap<K,V>或者Modle等类型
     * @param position 当前item的position
     */
    public abstract void bindChildViewsData(GeneralRecyclerViewHolder holder, Object itemData, int position);


}
















