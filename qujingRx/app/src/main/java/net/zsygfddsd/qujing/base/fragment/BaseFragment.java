package net.zsygfddsd.qujing.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.zsygfddsd.qujing.common.utils.DeviceUtils;
import net.zsygfddsd.qujing.components.HttpVolley.RequestInfo;
import net.zsygfddsd.qujing.components.HttpVolley.VolleyLoader;
import net.zsygfddsd.qujing.components.HttpVolley.VolleyResponse;


/**
 * Created by mac on 16/3/1.
 */
public abstract class BaseFragment extends Fragment {

    protected Context ct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    //*************与服务器交互封装start************//

    public void loadData(String reqTag, RequestInfo reqInfo, boolean isShowDialog,
                         boolean cancelable, VolleyResponse.strReqCallback callback){
        if (DeviceUtils.isHasNetWork()) {
            //            reqInfo.getBodyParams().put(); //添加公共参数
            VolleyLoader.start(ct).post(reqTag, reqInfo, isShowDialog, cancelable, callback);
        } else {
            showToast("请检查网络连接");
        }
    }

    public void getLoadData(String reqTag, RequestInfo reqInfo, boolean isShowDialog,
                            boolean cancelable, VolleyResponse.strReqCallback callback){
        if (DeviceUtils.isHasNetWork()) {
            //            reqInfo.getBodyParams().put(); //添加公共参数
            VolleyLoader.start(ct).get(reqTag, reqInfo, isShowDialog, cancelable, callback);
        } else {
            showToast("请检查网络连接");
        }
    }

    public void showToast(String content) {
        Toast.makeText(ct, content, Toast.LENGTH_SHORT).show();
    }
}
