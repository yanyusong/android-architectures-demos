package net.zsygfddsd.qujing.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import net.zsygfddsd.qujing.QJapplication;

import java.util.ArrayList;
import java.util.List;

public class DeviceUtils {
    private static final String mDataBaseName = "QJ";
    private static Application application = QJapplication.getInstance();
    private static Context context = application.getBaseContext ();

    /**
     * 判断能否使用网络
     */
    public static boolean isHasNetWork () {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo ();
        return network != null && (network.isConnected () || network.isRoaming () || network.isAvailable ());
    }

    public static String getDeviceId () {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService (Context.TELEPHONY_SERVICE);
        return tm.getDeviceId ();
    }

    /**
     * 获取程序是否在后台运行
     *
     * @param mContext
     * @return
     */
    public static boolean isRunBackground (Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService (Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks (1);
        if (tasksInfo.size () > 0) {
            // 应用程序位于堆栈的顶层
            if (mContext.getPackageName ().equals (
                    tasksInfo.get (0).topActivity.getPackageName ())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断手机内是否安装某个包的应用
     *
     * @param packageName 应用包名
     * @return 安装则为true，没有安装则为false
     */
    public static boolean isInstalled (String packageName) {
        PackageManager packageManager = application.getPackageManager ();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages (0);
        List<String> pName = new ArrayList<> ();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (PackageInfo aPinfo : pinfo) {
                String pn = aPinfo.packageName;
                pName.add (pn);
            }
        }
        return pName.contains (packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public static String getDataBaseName () {
        return mDataBaseName;
    }

    public static void callPhone (Activity activity, String phoneNum) {
        if (!TextUtils.isEmpty (phoneNum)) {
            Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:" + phoneNum));
            activity.startActivity (call);
        } else {
            Log.i("DeviceUtils","电话号码为空");
        }
    }

}
