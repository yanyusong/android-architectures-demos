package net.zsygfddsd.qujing.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.PageModel;
import net.zsygfddsd.qujing.common.utils.DeviceUtils;
import net.zsygfddsd.qujing.components.HttpVolley.RequestInfo;
import net.zsygfddsd.qujing.components.HttpVolley.VolleyLoader;
import net.zsygfddsd.qujing.components.HttpVolley.VolleyResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/3/3.
 */
public abstract class BaseRecyclerViewFragment<T> extends RecyclerViewFragment<T> {

    public static final int REQUEST_CODE_TOKEN_INABLE = 123;

    private static final String REQUEST_TAG = "reqTag";
    private static final String REQUEST_INFO = "reqInfo";
    private static final String REQUEST_SHOW_DIALOG = "showDialog";
    private static final String REQUEST_CANCELABLE = "cancelable";
    //    private static final String REQUEST_CALLBACK = "strReqCallback";

    private String reqTag = "BaseRecyclerFrag";
    private RequestInfo reqInfo;
    private Boolean isShowDialog = true;
    private Boolean cancelable = false;
    //是否已经在加载了，true则无法刷新或加载更多
    private volatile Boolean isLoading = false;

    private volatile boolean isClear = false;//是否清空列表所有数据
    private List<T> items = new ArrayList<>();// list中当前最新页的数据

    protected volatile int page = 0;//分页的页码
    protected volatile int pageSize = 10;//每页有多少条目

    Handler handlerResult = new Handler();


    protected static Bundle data2Bundle(String reqTag, RequestInfo reqInfo, boolean isShowDialog,
                                        boolean cancelable, int itemLayoutId) {
        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_TAG, reqTag);
        if (reqInfo != null) {
            bundle.putSerializable(REQUEST_INFO, reqInfo);
        } else {
            Log.e("BaseRecyclerFrag", "Error:reqInfo不能为null");
        }
        bundle.putBoolean(REQUEST_SHOW_DIALOG, isShowDialog);
        bundle.putBoolean(REQUEST_CANCELABLE, cancelable);
        bundle.putInt(ITEM_LAYOUT_ID, itemLayoutId);
        return bundle;
    }

    public void init(String reqTag, RequestInfo reqInfo, boolean isShowDialog,
                     boolean cancelable, int itemLayoutId) {
        Bundle bundle = data2Bundle(reqTag, reqInfo, isShowDialog, cancelable, itemLayoutId);
        setArguments(bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.reqTag = args.getString(REQUEST_TAG);
            this.reqInfo = (RequestInfo) args.getSerializable(REQUEST_INFO);
            this.isShowDialog = args.getBoolean(REQUEST_SHOW_DIALOG, true);
            this.cancelable = args.getBoolean(REQUEST_CANCELABLE, false);
            this.itemLayoutId = args.getInt(ITEM_LAYOUT_ID) == -1 ? android.R.layout.simple_list_item_1 : args.getInt(ITEM_LAYOUT_ID);
            Log.e("BaseRecyclerFrag", "reqTag =" + reqTag + ",reqInfo =" + reqInfo + ",isShowDialog =" + isShowDialog + ",cancelable =" + cancelable + ",itemLayoutId =" + itemLayoutId);
        } else {
            Log.e("BaseRecyclerFrag", "Error:请调用BaseRecyclerViewFragment的init()方法进行初始化");
        }
    }

    @Override
    public void onInitData() {
        if (!isLoading) {
            isLoading = true;
            loadMore(reqInfo, isShowDialog, cancelable);
        }
    }

    @Override
    public void onLoadMore() {
        if (!isLoading) {
            isLoading = true;
            loadMore(reqInfo, false, true);
        }
    }

    @Override
    public void onLoadRefresh() {
        if (!isLoading) {
            isLoading = true;
            page = 0;
            isClear = true;
            loadMore(reqInfo, true, false);
        }
    }

    /**
     * 请求参数改变后，重新发送服务器请求，常用于重新排序或者筛选等功能
     */
    public void reLoadData(RequestInfo requestInfo) {
        if (!isLoading) {
            isLoading = true;
            page = 0;
            isClear = true;
            reqInfo = requestInfo;
            loadMore(reqInfo, false, false);
        }
    }

    /**
     * 请求参数不变，重新发送服务器请求
     */
    public void reLoadData() {
        if (!isLoading) {
            isLoading = true;
            page = 0;
            isClear = true;
            loadMore(reqInfo, false, false);
        }
    }
    //在这里处理分页操作,根据项目情况复写此方法
    protected void loadMore(RequestInfo reqInfo, boolean showDialog, boolean canCancel) {
        page++;
        pageSize = 15;
        StringBuilder strUrl = new StringBuilder(reqInfo.getUrl());
        strUrl.append("/" + pageSize).append("/" + page);
        loadData(Request.Method.GET,new RequestInfo(strUrl.toString()), showDialog, canCancel);
    }

    private void loadData(int requestMethod, RequestInfo reqInfo, boolean showDialog, boolean canCancel) {
        if (DeviceUtils.isHasNetWork()) {
        VolleyLoader.start(ct).request(requestMethod,reqTag, reqInfo, showDialog, canCancel, new VolleyResponse.strReqCallback() {
            @Override
            public void success(String response) {
                handleResult(response);
                completeRefreshing();
                isLoading = false;
            }

            @Override
            public void error(VolleyError error) {
                completeRefreshing();
                isLoading = false;

            }
        });
    }else{
       if(isRefreshing()){
                completeRefreshing();
            }
            showToast("请检查网络连接"); 
    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {

            switch (requestCode) {
                case REQUEST_CODE_TOKEN_INABLE:
                    reLoadData();
                    break;
                default:
                    break;
            }

        }

    }



    private void handleResult(final String response) {
        handlerResult.post(new Runnable() {
            @Override
            public void run() {
                ComRespInfo comRespInfo = JSON.parseObject(response, ComRespInfo.class);
                PageModel pageModel = new PageModel();
                pageModel.setList(comRespInfo.getResults());
                pageModel.setHasNext(true);

                setHasNextPage(pageModel.getHasNext());
                changeFooterText(!pageModel.getHasNext());
                items.clear();
                items = handleData(pageModel);
                if (isClear) {
                    itemDatas.clear();
                    isClear = false;
                }
                itemDatas.addAll(items);
                updateData();
                if (itemDatas.size() == 0) {
                    // TODO: 16/1/6  在此显示无数据时的图片
                }
            }
        });

    }

    /**
     * 得到子类model的class
     *
     * @return Class
     */
    public abstract List<T> handleData(PageModel pageModel);
}



















