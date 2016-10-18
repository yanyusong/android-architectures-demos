package com.zsygfddsd.spacestation.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class DeviceUtils {

    private Context context;

    public DeviceUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 判断能否使用网络
     */
    public boolean isHasNetWork() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return network != null && (network.isConnected() || network.isRoaming() || network.isAvailable());
    }

    public String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public void callPhone(Activity activity, String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            activity.startActivity(call);
        } else {
            Log.e("DeviceUtils", "电话号码为空");
        }
    }

}
